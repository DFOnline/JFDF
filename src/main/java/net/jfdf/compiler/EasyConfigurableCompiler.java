package net.jfdf.compiler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import net.jfdf.compiler.library.InvokeVirtual;
import net.jfdf.compiler.library.References;
import net.jfdf.jfdf.blocks.CodeHeader;
import net.jfdf.jfdf.mangement.CodeManager;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.Scanners;

public abstract class EasyConfigurableCompiler {
   public final Map compile() {
      long startTime = System.currentTimeMillis();
      Set classes = (new Reflections(this.getClass().getPackageName(), new Scanner[]{Scanners.SubTypes.filterResultsBy((s) -> {
         return true;
      })})).getSubTypesOf(Object.class);
      this.configure();
      List var10000 = Arrays.asList(this.excludedClasses());
      Objects.requireNonNull(classes);
      var10000.forEach(classes::remove);
      System.out.println("Got a list of compile classes. Took " + (System.currentTimeMillis() - startTime) + "ms.");
      startTime = System.currentTimeMillis();
      Iterator var4 = classes.iterator();

      while(var4.hasNext()) {
         Class clazz = (Class)var4.next();
         JFDFCompiler.compileClass(clazz);
      }

      JFDFCompiler.compileClass(References.class);
      JFDFCompiler.compileClass(InvokeVirtual.class);
      JFDFCompiler.checkUsedClasses();
      JFDFCompiler.generateInit();
      JFDFCompiler.generateInitMethodMap();
      JFDFCompiler.generateInitNestedObjects();
      Map codeOutputMap = CodeManager.instance.getCodeValuesAsMap();
      System.out.println("Finished code compilation, Took " + (System.currentTimeMillis() - startTime) + "ms.");
      return codeOutputMap;
   }

   public final void send(Map codeOutputMap, String senderName) {
      int sendCooldown = this.sendCooldown();

      try {
         Socket socket = new Socket("127.0.0.1", 31372);

         try {
            Iterator var5 = codeOutputMap.entrySet().iterator();

            while(var5.hasNext()) {
               Map.Entry lineData = (Map.Entry)var5.next();
               CodeHeader lineStarter = (CodeHeader)lineData.getKey();
               String code = (String)lineData.getValue();
               LineStarterType var10000;
               switch (lineStarter.getClass().getSimpleName()) {
                  case "PlayerEventBlock":
                     var10000 = EasyConfigurableCompiler.LineStarterType.PLAYER_EVENT;
                     break;
                  case "EntityEventBlock":
                     var10000 = EasyConfigurableCompiler.LineStarterType.ENTITY_EVENT;
                     break;
                  case "FunctionBlock":
                     var10000 = EasyConfigurableCompiler.LineStarterType.FUNCTION;
                     break;
                  case "ProcessBlock":
                     var10000 = EasyConfigurableCompiler.LineStarterType.PROCESS;
                     break;
                  default:
                     throw new IllegalStateException("Unknown line starter class: " + lineStarter.getClass().getName());
               }

               LineStarterType lineStarterType = var10000;
               name = lineStarter.getTemplateName().split(" ")[2];
               if (this.shouldSendLine(lineStarterType, name)) {
                  String sendDataJson = "{\"type\":\"template\",\"source\":\"§6§lJFDF » §e" + senderName + "\",\"data\":\"{\\\"name\\\":\\\"" + lineStarter.getTemplateNameWithColors().replace("\"", "\\\\\"") + "\\\",\\\"data\\\":\\\"" + code + "\\\"}\"}";
                  OutputStream stream = socket.getOutputStream();
                  stream.write(sendDataJson.getBytes(StandardCharsets.ISO_8859_1));
                  stream.write(10);
                  System.out.println("Sent line: " + name);
                  if (sendCooldown != 0) {
                     TimeUnit.MILLISECONDS.sleep((long)sendCooldown);
                  }
               }
            }
         } catch (Throwable var14) {
            try {
               socket.close();
            } catch (Throwable var13) {
               var14.addSuppressed(var13);
            }

            throw var14;
         }

         socket.close();
      } catch (IOException var15) {
         throw new RuntimeException("Something went wrong. Are you sure your game is open?", var15);
      } catch (InterruptedException var16) {
         throw new RuntimeException(var16);
      }
   }

   public final void compileAndSend(String senderName) {
      this.send(this.compile(), senderName);
   }

   protected final boolean isStandardLine(LineStarterType lineStarterType, String name) {
      return (lineStarterType == EasyConfigurableCompiler.LineStarterType.FUNCTION || lineStarterType == EasyConfigurableCompiler.LineStarterType.PROCESS) && name.startsWith("_jfdf>std>");
   }

   protected final boolean isInitLine(LineStarterType lineStarterType, String name) {
      return lineStarterType == EasyConfigurableCompiler.LineStarterType.FUNCTION && (name.equals("_jfdf>init") || name.equals("_jfdf>initMethodMap") || name.equals("_jfdf>initNestObjs"));
   }

   protected abstract void configure();

   protected boolean shouldSendLine(LineStarterType lineStarterType, String name) {
      return true;
   }

   protected int sendCooldown() {
      return 500;
   }

   protected Class[] excludedClasses() {
      return new Class[0];
   }

   public static enum LineStarterType {
      PLAYER_EVENT,
      ENTITY_EVENT,
      FUNCTION,
      PROCESS;
   }
}

package net.jfdf.compiler;

import net.jfdf.compiler.library.InvokeVirtual;
import net.jfdf.compiler.library.References;
import net.jfdf.jfdf.blocks.CodeHeader;
import net.jfdf.jfdf.blocks.PlayerEventBlock;
import net.jfdf.jfdf.mangement.CodeManager;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class EasyConfigurableCompiler {
    public final Map<CodeHeader, String> compile() {
        long startTime = System.currentTimeMillis();

        Set<Class<?>> classes = new Reflections(this.getClass().getPackageName(), Scanners.SubTypes.filterResultsBy(s -> true))
                .getSubTypesOf(Object.class);

        configure();
        Arrays.asList(excludedClasses()).forEach(classes::remove);

        System.out.println("Got a list of compile classes. Took " + (System.currentTimeMillis() - startTime) + "ms.");
        startTime = System.currentTimeMillis();

        for (Class<?> clazz : classes) {
            JFDFCompiler.compileClass(clazz);
        }

        JFDFCompiler.compileClass(References.class);
        JFDFCompiler.compileClass(InvokeVirtual.class);

        JFDFCompiler.checkUsedClasses();

        JFDFCompiler.generateInit();
        JFDFCompiler.generateInitMethodMap();
        JFDFCompiler.generateInitNestedObjects();

        Map<CodeHeader, String> codeOutputMap = CodeManager.instance.getCodeValuesAsMap();
        System.out.println("Finished code compilation, Took " + (System.currentTimeMillis() - startTime) + "ms.");

        return codeOutputMap;
    }

    public final void send(Map<CodeHeader, String> codeOutputMap, String senderName) {
        int sendCooldown = sendCooldown();

        try(Socket socket = new Socket("127.0.0.1", 31372)) {
            for (Map.Entry<CodeHeader, String> lineData : codeOutputMap.entrySet()) {
                CodeHeader lineStarter = lineData.getKey();
                String code = lineData.getValue();

                LineStarterType lineStarterType = switch (lineStarter.getClass().getSimpleName()) {
                    case "PlayerEventBlock" -> LineStarterType.PLAYER_EVENT;
                    case "EntityEventBlock" -> LineStarterType.ENTITY_EVENT;
                    case "FunctionBlock" -> LineStarterType.FUNCTION;
                    case "ProcessBlock" -> LineStarterType.PROCESS;
                    default -> throw new IllegalStateException("Unknown line starter class: " + lineStarter.getClass().getName());
                };

                String name = lineStarter.getTemplateName().split(" ")[2];

                if (shouldSendLine(lineStarterType, name)) {
                    String sendDataJson = "{\"type\":\"template\",\"source\":\"" +
                            "\u00A76\u00A7lJFDF \u00BB \u00A7e" + senderName +
                            "\",\"data\":\"{\\\"name\\\":\\\"" + lineStarter.getTemplateNameWithColors().replace("\"", "\\\\\"") + "\\\",\\\"data\\\":\\\"" +
                            code +
                            "\\\"}\"}";

                    OutputStream stream = socket.getOutputStream();

                    stream.write(sendDataJson.getBytes(StandardCharsets.ISO_8859_1));
                    stream.write('\n');

                    System.out.println("Sent line: " + name);
                    if(sendCooldown != 0) TimeUnit.MILLISECONDS.sleep(sendCooldown);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong. Are you sure your game is open?", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public final void compileAndSend(String senderName) {
        send(compile(), senderName);
    }

    protected final boolean isStandardLine(LineStarterType lineStarterType, String name) {
        return (lineStarterType == LineStarterType.FUNCTION || lineStarterType == LineStarterType.PROCESS)
                && name.startsWith("_jfdf>std>");
    }

    protected final boolean isInitLine(LineStarterType lineStarterType, String name) {
        return lineStarterType == LineStarterType.FUNCTION
                && (name.equals("_jfdf>init") || name.equals("_jfdf>initMethodMap") || name.equals("_jfdf>initNestObjs"));
    }

    protected abstract void configure();

    protected boolean shouldSendLine(LineStarterType lineStarterType, String name) { return true; }
    protected int sendCooldown() { return 500; } // Setting this to 0 would remove cooldown

    protected Class<?>[] excludedClasses() { return new Class[0]; }

    public enum LineStarterType {
        PLAYER_EVENT,
        ENTITY_EVENT,
        FUNCTION,
        PROCESS
    }
}

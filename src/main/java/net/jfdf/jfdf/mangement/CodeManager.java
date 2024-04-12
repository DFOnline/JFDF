package net.jfdf.jfdf.mangement;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.zip.GZIPOutputStream;
import net.jfdf.jfdf.AddonsManager;
import net.jfdf.jfdf.JFDFAddon;
import net.jfdf.jfdf.blocks.CodeBlock;
import net.jfdf.jfdf.blocks.CodeHeader;
import net.jfdf.jfdf.blocks.FunctionBlock;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;

public class CodeManager {
   public static boolean checksumFunction = false;
   private final List variables = new ArrayList();
   private final List functionsWithArgs = new ArrayList();
   private final LinkedHashMap codeBlocks = new LinkedHashMap();
   private String author = "net.jfdf.jfdf";
   public PLOT_SIZE plotSize;
   public PLAYER_RANK playerRank;
   public static final CodeManager instance = new CodeManager();

   public Variable getVariable(String variableName, Variable.Scope scope) {
      Iterator var3 = this.variables.iterator();

      Variable variable;
      do {
         if (!var3.hasNext()) {
            Variable variable = new Variable(variableName, scope);
            this.variables.add(variable);
            return variable;
         }

         variable = (Variable)var3.next();
      } while(!variable.getName().equals(variableName) || !variable.getScope().equals(scope));

      return variable;
   }

   public void addCodeBlock(CodeBlock codeBlock) {
      List[] array = new List[this.codeBlocks.size()];
      ((List[])this.codeBlocks.values().toArray(array))[this.codeBlocks.size() - 1].add(codeBlock);

      try {
         JFDFAddon.class.getMethod("onAddCodeBlock", CodeBlock.class).invoke((Object)null, codeBlock);
      } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var4) {
      }

   }

   public List getActiveCodeBlocks() {
      List[] array = new List[this.codeBlocks.size()];
      return ((List[])this.codeBlocks.values().toArray(array))[this.codeBlocks.size() - 1];
   }

   public void addCodeBlocks(CodeHeader codeHeader, CodeBlock... codeBlocks) {
      this.codeBlocks.put(codeHeader, Arrays.asList(codeBlocks));
   }

   public void addCodeBlocks(CodeHeader codeHeader, ArrayList codeBlocks) {
      this.codeBlocks.put(codeHeader, codeBlocks);
   }

   public void addFunctionWithArgs(Method functionMethod) {
      this.functionsWithArgs.add(functionMethod);
   }

   public Method[] getFunctionsWithArgs() {
      return (Method[])this.functionsWithArgs.toArray(new Method[0]);
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public List getCodeValuesAsList() {
      Map addonCodeBlocks = new HashMap();
      List codeValues = new ArrayList();
      List checksumFunctions = new ArrayList();
      List checksumValues = new ArrayList();
      Iterator var5 = this.codeBlocks.entrySet().iterator();

      Map.Entry codeBlocksData;
      CodeHeader codeHeader;
      String codeNBT;
      ArrayList codeBlocksJSON;
      Iterator var11;
      CodeBlock codeBlock;
      String compressed;
      String var10000;
      MessageDigest digest;
      ByteArrayOutputStream bos;
      String checksum;
      GZIPOutputStream gzip;
      while(var5.hasNext()) {
         codeBlocksData = (Map.Entry)var5.next();
         codeHeader = (CodeHeader)codeBlocksData.getKey();
         List codeBlocks = new ArrayList((Collection)codeBlocksData.getValue());
         addonCodeBlocks.putAll(AddonsManager.publishPreGenerateLine(codeHeader, codeBlocks));
         codeNBT = "";
         codeBlocksJSON = new ArrayList();
         var11 = codeBlocks.iterator();

         while(var11.hasNext()) {
            codeBlock = (CodeBlock)var11.next();
            codeBlocksJSON.add(codeBlock.asJSON());
         }

         codeNBT = codeNBT + String.join(",", codeBlocksJSON) + "]}";
         if (checksumFunction && codeHeader instanceof FunctionBlock) {
            try {
               digest = MessageDigest.getInstance("SHA-256");
               digest.update(("{\"blocks\":[" + codeNBT).getBytes(StandardCharsets.UTF_8));
               checksum = String.format("%064x", new BigInteger(1, digest.digest()));
               checksumFunctions.add(((FunctionBlock)codeHeader).getName());
               checksumValues.add(checksum);
               codeNBT = "{\"id\":\"block\",\"block\":\"if_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCSM\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"num\",\"data\":{\"name\":\"1\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"bracket\",\"direct\":\"open\",\"type\":\"norm\"},{\"id\":\"block\",\"block\":\"set_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCS\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"txt\",\"data\":{\"name\":\"" + checksum + "\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"block\",\"block\":\"control\",\"args\":{\"items\":[]},\"action\":\"Return\"},{\"id\":\"bracket\",\"direct\":\"close\",\"type\":\"norm\"}" + (codeBlocks.size() == 0 ? "" : ",") + codeNBT;
            } catch (NoSuchAlgorithmException var20) {
               var20.printStackTrace();
            }
         }

         var10000 = codeHeader.asJSON();
         codeNBT = "{\"blocks\":[" + var10000 + (codeBlocks.size() == 0 && !checksumFunction ? "" : ",") + codeNBT;

         try {
            bos = new ByteArrayOutputStream(codeNBT.length());
            gzip = new GZIPOutputStream(bos);
            gzip.write(codeNBT.getBytes(StandardCharsets.UTF_8));
            gzip.close();
            bos.close();
            compressed = new String(Base64.getEncoder().encode(bos.toByteArray()), StandardCharsets.UTF_8);
            codeValues.add(compressed);

            try {
               JFDFAddon.class.getMethod("onGetCodeHeaderValue", String.class).invoke((Object)null, compressed);
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var18) {
            }
         } catch (Exception var19) {
            var19.printStackTrace();
            throw new RuntimeException("Error while compressing");
         }
      }

      var5 = addonCodeBlocks.entrySet().iterator();

      List codeBlocks;
      while(var5.hasNext()) {
         codeBlocksData = (Map.Entry)var5.next();
         codeHeader = (CodeHeader)codeBlocksData.getKey();
         codeBlocks = (List)codeBlocksData.getValue();
         codeNBT = "";
         codeBlocksJSON = new ArrayList();
         var11 = codeBlocks.iterator();

         while(var11.hasNext()) {
            codeBlock = (CodeBlock)var11.next();
            codeBlocksJSON.add(codeBlock.asJSON());
         }

         codeNBT = codeNBT + String.join(",", codeBlocksJSON) + "]}";
         if (checksumFunction && codeHeader instanceof FunctionBlock) {
            try {
               digest = MessageDigest.getInstance("SHA-256");
               digest.update(("{\"blocks\":[" + codeNBT).getBytes(StandardCharsets.UTF_8));
               checksum = String.format("%064x", new BigInteger(1, digest.digest()));
               checksumFunctions.add(((FunctionBlock)codeHeader).getName());
               checksumValues.add(checksum);
               codeNBT = "{\"id\":\"block\",\"block\":\"if_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCSM\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"num\",\"data\":{\"name\":\"1\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"bracket\",\"direct\":\"open\",\"type\":\"norm\"},{\"id\":\"block\",\"block\":\"set_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCS\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"txt\",\"data\":{\"name\":\"" + checksum + "\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"block\",\"block\":\"control\",\"args\":{\"items\":[]},\"action\":\"Return\"},{\"id\":\"bracket\",\"direct\":\"close\",\"type\":\"norm\"}" + (codeBlocks.size() == 0 ? "" : ",") + codeNBT;
            } catch (NoSuchAlgorithmException var17) {
               var17.printStackTrace();
            }
         }

         var10000 = codeHeader.asJSON();
         codeNBT = "{\"blocks\":[" + var10000 + (codeBlocks.size() == 0 && !checksumFunction ? "" : ",") + codeNBT;

         try {
            bos = new ByteArrayOutputStream(codeNBT.length());
            gzip = new GZIPOutputStream(bos);
            gzip.write(codeNBT.getBytes(StandardCharsets.UTF_8));
            gzip.close();
            bos.close();
            compressed = new String(Base64.getEncoder().encode(bos.toByteArray()), StandardCharsets.UTF_8);
            codeValues.add(compressed);
         } catch (Exception var16) {
            var16.printStackTrace();
            throw new RuntimeException("Error while compressing");
         }
      }

      if (checksumFunction) {
         FunctionBlock checksumCodeHeader = new FunctionBlock("_jfdf>checksum");
         this.addCodeBlocks(checksumCodeHeader, (ArrayList)(new ArrayList()));
         SimpleDateFormat currentTime = new SimpleDateFormat("YYYY/MM/dd hh:mm:s a z", Locale.US);
         currentTime.setTimeZone(TimeZone.getTimeZone("GMT"));
         Player var29 = Player.getCurrentSelection();
         CodeValue[] var10001 = new CodeValue[1];
         Text var10004 = new Text();
         String var10005 = currentTime.format(new Date());
         var10001[0] = var10004.Set("§bCode Last Updated: §e" + var10005);
         var29.SendMessage(var10001, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.NO_SPACES);

         int i;
         for(i = 0; i < checksumFunctions.size(); i += 26) {
            if (i == 0) {
               VariableControl.CreateList(new Variable("_jco0", Variable.Scope.LOCAL), (CodeValue[])checksumFunctions.subList(i, Math.min(i + 26, checksumFunctions.size())).stream().map((s) -> {
                  return (new Text()).Set(s);
               }).toArray((x$0) -> {
                  return new Text[x$0];
               }));
            } else {
               VariableControl.AppendValue(new Variable("_jco0", Variable.Scope.LOCAL), (CodeValue[])checksumFunctions.subList(i, Math.min(i + 26, checksumFunctions.size())).stream().map((s) -> {
                  return (new Text()).Set(s);
               }).toArray((x$0) -> {
                  return new Text[x$0];
               }));
            }
         }

         for(i = 0; i < checksumValues.size(); i += 26) {
            if (i == 0) {
               VariableControl.CreateList(new Variable("_jco1", Variable.Scope.LOCAL), (CodeValue[])checksumValues.subList(i, Math.min(i + 26, checksumValues.size())).stream().map((s) -> {
                  return (new Text()).Set(s);
               }).toArray((x$0) -> {
                  return new Text[x$0];
               }));
            } else {
               VariableControl.AppendValue(new Variable("_jco1", Variable.Scope.LOCAL), (CodeValue[])checksumValues.subList(i, Math.min(i + 26, checksumValues.size())).stream().map((s) -> {
                  return (new Text()).Set(s);
               }).toArray((x$0) -> {
                  return new Text[x$0];
               }));
            }
         }

         VariableControl.CreateDict(new Variable("tmp0", Variable.Scope.LOCAL), new Variable("_jco0", Variable.Scope.LOCAL), new Variable("_jco1", Variable.Scope.LOCAL));
         VariableControl.Set(new Variable("_jfdfCSM", Variable.Scope.LOCAL), (new Number()).Set(1));
         Repeat.ForEachEntry(new Variable("tmp1", Variable.Scope.LOCAL), new Variable("tmp2", Variable.Scope.LOCAL), new Variable("tmp0", Variable.Scope.LOCAL));
         Functions.Call("%var(tmp1)");
         If.Variable.NotEquals(new Variable("_jfdfCS", Variable.Scope.LOCAL), new CodeValue[]{new Variable("tmp2", Variable.Scope.LOCAL)}, false);
         Player.getCurrentSelection().SendMessage(new CodeValue[]{(new Text()).Set("§cFound not updated function: §4"), new Variable("tmp1", Variable.Scope.LOCAL)}, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.NO_SPACES);
         If.End();
         Repeat.End();
         VariableControl.Set(new Variable("_jfdfCSM", Variable.Scope.LOCAL), (new Number()).Set(0));
         codeHeader = ((CodeHeader[])this.codeBlocks.keySet().toArray((x$0) -> {
            return new CodeHeader[x$0];
         }))[this.codeBlocks.size() - 1];
         codeBlocks = this.getActiveCodeBlocks();
         codeNBT = "{\"blocks\":[";
         codeBlocksJSON = new ArrayList();
         codeBlocksJSON.add(codeHeader.asJSON());
         var11 = codeBlocks.iterator();

         while(var11.hasNext()) {
            codeBlock = (CodeBlock)var11.next();
            codeBlocksJSON.add(codeBlock.asJSON());
         }

         codeNBT = codeNBT + String.join(",", codeBlocksJSON) + "]}";

         try {
            bos = new ByteArrayOutputStream(codeNBT.length());
            gzip = new GZIPOutputStream(bos);
            gzip.write(codeNBT.getBytes(StandardCharsets.UTF_8));
            gzip.close();
            bos.close();
            compressed = new String(Base64.getEncoder().encode(bos.toByteArray()), StandardCharsets.UTF_8);
            codeValues.add(compressed);
         } catch (Exception var15) {
            var15.printStackTrace();
            throw new RuntimeException("Error while compressing");
         }

         this.codeBlocks.remove(checksumCodeHeader);
      }

      return codeValues;
   }

   public Map getCodeValuesAsMap() {
      Map addonCodeBlocks = new LinkedHashMap();
      Map codeValues = new LinkedHashMap();
      List checksumFunctions = new ArrayList();
      List checksumValues = new ArrayList();
      Iterator var5 = this.codeBlocks.entrySet().iterator();

      Map.Entry codeBlocksData;
      CodeHeader codeHeader;
      String codeNBT;
      ArrayList codeBlocksJSON;
      Iterator var11;
      CodeBlock codeBlock;
      String compressed;
      String var10000;
      MessageDigest digest;
      ByteArrayOutputStream bos;
      String checksum;
      GZIPOutputStream gzip;
      while(var5.hasNext()) {
         codeBlocksData = (Map.Entry)var5.next();
         codeHeader = (CodeHeader)codeBlocksData.getKey();
         List codeBlocks = new ArrayList((Collection)codeBlocksData.getValue());
         addonCodeBlocks.putAll(AddonsManager.publishPreGenerateLine(codeHeader, codeBlocks));
         codeNBT = "";
         codeBlocksJSON = new ArrayList();
         var11 = codeBlocks.iterator();

         while(var11.hasNext()) {
            codeBlock = (CodeBlock)var11.next();
            codeBlocksJSON.add(codeBlock.asJSON());
         }

         codeNBT = codeNBT + String.join(",", codeBlocksJSON) + "]}";
         if (checksumFunction && codeHeader instanceof FunctionBlock) {
            try {
               digest = MessageDigest.getInstance("SHA-256");
               digest.update(("{\"blocks\":[" + codeNBT).getBytes(StandardCharsets.UTF_8));
               checksum = String.format("%064x", new BigInteger(1, digest.digest()));
               checksumFunctions.add(((FunctionBlock)codeHeader).getName());
               checksumValues.add(checksum);
               codeNBT = "{\"id\":\"block\",\"block\":\"if_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCSM\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"num\",\"data\":{\"name\":\"1\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"bracket\",\"direct\":\"open\",\"type\":\"norm\"},{\"id\":\"block\",\"block\":\"set_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCS\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"txt\",\"data\":{\"name\":\"" + checksum + "\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"block\",\"block\":\"control\",\"args\":{\"items\":[]},\"action\":\"Return\"},{\"id\":\"bracket\",\"direct\":\"close\",\"type\":\"norm\"}" + (codeBlocks.size() == 0 ? "" : ",") + codeNBT;
            } catch (NoSuchAlgorithmException var20) {
               var20.printStackTrace();
            }
         }

         var10000 = codeHeader.asJSON();
         codeNBT = "{\"blocks\":[" + var10000 + (codeBlocks.size() == 0 && !checksumFunction ? "" : ",") + codeNBT;

         try {
            bos = new ByteArrayOutputStream(codeNBT.length());
            gzip = new GZIPOutputStream(bos);
            gzip.write(codeNBT.getBytes(StandardCharsets.UTF_8));
            gzip.close();
            bos.close();
            compressed = new String(Base64.getEncoder().encode(bos.toByteArray()), StandardCharsets.UTF_8);
            codeValues.put(codeHeader, compressed);

            try {
               JFDFAddon.class.getMethod("onGetCodeHeaderValue", String.class).invoke((Object)null, compressed);
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var18) {
            }
         } catch (Exception var19) {
            var19.printStackTrace();
            throw new RuntimeException("Error while compressing");
         }
      }

      var5 = addonCodeBlocks.entrySet().iterator();

      List codeBlocks;
      while(var5.hasNext()) {
         codeBlocksData = (Map.Entry)var5.next();
         codeHeader = (CodeHeader)codeBlocksData.getKey();
         codeBlocks = (List)codeBlocksData.getValue();
         codeNBT = "";
         codeBlocksJSON = new ArrayList();
         var11 = codeBlocks.iterator();

         while(var11.hasNext()) {
            codeBlock = (CodeBlock)var11.next();
            codeBlocksJSON.add(codeBlock.asJSON());
         }

         codeNBT = codeNBT + String.join(",", codeBlocksJSON) + "]}";
         if (checksumFunction && codeHeader instanceof FunctionBlock) {
            try {
               digest = MessageDigest.getInstance("SHA-256");
               digest.update(("{\"blocks\":[" + codeNBT).getBytes(StandardCharsets.UTF_8));
               checksum = String.format("%064x", new BigInteger(1, digest.digest()));
               checksumFunctions.add(((FunctionBlock)codeHeader).getName());
               checksumValues.add(checksum);
               codeNBT = "{\"id\":\"block\",\"block\":\"if_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCSM\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"num\",\"data\":{\"name\":\"1\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"bracket\",\"direct\":\"open\",\"type\":\"norm\"},{\"id\":\"block\",\"block\":\"set_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCS\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"txt\",\"data\":{\"name\":\"" + checksum + "\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"block\",\"block\":\"control\",\"args\":{\"items\":[]},\"action\":\"Return\"},{\"id\":\"bracket\",\"direct\":\"close\",\"type\":\"norm\"}" + (codeBlocks.size() == 0 ? "" : ",") + codeNBT;
            } catch (NoSuchAlgorithmException var17) {
               var17.printStackTrace();
            }
         }

         var10000 = codeHeader.asJSON();
         codeNBT = "{\"blocks\":[" + var10000 + (codeBlocks.size() == 0 && !checksumFunction ? "" : ",") + codeNBT;

         try {
            bos = new ByteArrayOutputStream(codeNBT.length());
            gzip = new GZIPOutputStream(bos);
            gzip.write(codeNBT.getBytes(StandardCharsets.UTF_8));
            gzip.close();
            bos.close();
            compressed = new String(Base64.getEncoder().encode(bos.toByteArray()), StandardCharsets.UTF_8);
            codeValues.put(codeHeader, compressed);
         } catch (Exception var16) {
            var16.printStackTrace();
            throw new RuntimeException("Error while compressing");
         }
      }

      if (checksumFunction) {
         FunctionBlock checksumCodeHeader = new FunctionBlock("_jfdf>checksum");
         this.addCodeBlocks(checksumCodeHeader, (ArrayList)(new ArrayList()));
         SimpleDateFormat currentTime = new SimpleDateFormat("YYYY/MM/dd hh:mm:s a z", Locale.US);
         currentTime.setTimeZone(TimeZone.getTimeZone("GMT"));
         Player var29 = Player.getCurrentSelection();
         CodeValue[] var10001 = new CodeValue[1];
         Text var10004 = new Text();
         String var10005 = currentTime.format(new Date());
         var10001[0] = var10004.Set("§bCode Last Updated: §e" + var10005);
         var29.SendMessage(var10001, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.NO_SPACES);

         int i;
         for(i = 0; i < checksumFunctions.size(); i += 26) {
            if (i == 0) {
               VariableControl.CreateList(new Variable("_jco0", Variable.Scope.LOCAL), (CodeValue[])checksumFunctions.subList(i, Math.min(i + 26, checksumFunctions.size())).stream().map((s) -> {
                  return (new Text()).Set(s);
               }).toArray((x$0) -> {
                  return new Text[x$0];
               }));
            } else {
               VariableControl.AppendValue(new Variable("_jco0", Variable.Scope.LOCAL), (CodeValue[])checksumFunctions.subList(i, Math.min(i + 26, checksumFunctions.size())).stream().map((s) -> {
                  return (new Text()).Set(s);
               }).toArray((x$0) -> {
                  return new Text[x$0];
               }));
            }
         }

         for(i = 0; i < checksumValues.size(); i += 26) {
            if (i == 0) {
               VariableControl.CreateList(new Variable("_jco1", Variable.Scope.LOCAL), (CodeValue[])checksumValues.subList(i, Math.min(i + 26, checksumValues.size())).stream().map((s) -> {
                  return (new Text()).Set(s);
               }).toArray((x$0) -> {
                  return new Text[x$0];
               }));
            } else {
               VariableControl.AppendValue(new Variable("_jco1", Variable.Scope.LOCAL), (CodeValue[])checksumValues.subList(i, Math.min(i + 26, checksumValues.size())).stream().map((s) -> {
                  return (new Text()).Set(s);
               }).toArray((x$0) -> {
                  return new Text[x$0];
               }));
            }
         }

         VariableControl.CreateDict(new Variable("tmp0", Variable.Scope.LOCAL), new Variable("_jco0", Variable.Scope.LOCAL), new Variable("_jco1", Variable.Scope.LOCAL));
         VariableControl.Set(new Variable("_jfdfCSM", Variable.Scope.LOCAL), (new Number()).Set(1));
         Repeat.ForEachEntry(new Variable("tmp1", Variable.Scope.LOCAL), new Variable("tmp2", Variable.Scope.LOCAL), new Variable("tmp0", Variable.Scope.LOCAL));
         Functions.Call("%var(tmp1)");
         If.Variable.NotEquals(new Variable("_jfdfCS", Variable.Scope.LOCAL), new CodeValue[]{new Variable("tmp2", Variable.Scope.LOCAL)}, false);
         Player.getCurrentSelection().SendMessage(new CodeValue[]{(new Text()).Set("§cFound not updated function: §4"), new Variable("tmp1", Variable.Scope.LOCAL)}, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.NO_SPACES);
         If.End();
         Repeat.End();
         VariableControl.Set(new Variable("_jfdfCSM", Variable.Scope.LOCAL), (new Number()).Set(0));
         codeHeader = ((CodeHeader[])this.codeBlocks.keySet().toArray((x$0) -> {
            return new CodeHeader[x$0];
         }))[this.codeBlocks.size() - 1];
         codeBlocks = this.getActiveCodeBlocks();
         codeNBT = "{\"blocks\":[";
         codeBlocksJSON = new ArrayList();
         codeBlocksJSON.add(codeHeader.asJSON());
         var11 = codeBlocks.iterator();

         while(var11.hasNext()) {
            codeBlock = (CodeBlock)var11.next();
            codeBlocksJSON.add(codeBlock.asJSON());
         }

         codeNBT = codeNBT + String.join(",", codeBlocksJSON) + "]}";

         try {
            bos = new ByteArrayOutputStream(codeNBT.length());
            gzip = new GZIPOutputStream(bos);
            gzip.write(codeNBT.getBytes(StandardCharsets.UTF_8));
            gzip.close();
            bos.close();
            compressed = new String(Base64.getEncoder().encode(bos.toByteArray()), StandardCharsets.UTF_8);
            codeValues.put(checksumCodeHeader, compressed);
         } catch (Exception var15) {
            var15.printStackTrace();
            throw new RuntimeException("Error while compressing");
         }

         this.codeBlocks.remove(checksumCodeHeader);
      }

      return codeValues;
   }

   public String[] getCodeValues() {
      List codeValuesList = this.getCodeValuesAsList();
      String[] codeValues = new String[codeValuesList.size()];
      return (String[])codeValuesList.toArray(codeValues);
   }

   public List getCodeValuesJSONAsList() {
      List jsons = new ArrayList();
      Iterator var2 = this.codeBlocks.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry codeBlocksData = (Map.Entry)var2.next();
         CodeHeader codeHeader = (CodeHeader)codeBlocksData.getKey();
         List codeBlocks = (List)codeBlocksData.getValue();
         String codeNBT = "{\"blocks\":[";
         List codeBlocksJSON = new ArrayList();
         codeBlocksJSON.add(codeHeader.asJSON());
         Iterator var8 = codeBlocks.iterator();

         while(var8.hasNext()) {
            CodeBlock codeBlock = (CodeBlock)var8.next();
            codeBlocksJSON.add(codeBlock.asJSON());
         }

         codeNBT = codeNBT + String.join(",", codeBlocksJSON) + "]}";
         jsons.add(codeNBT);
      }

      return jsons;
   }

   public String[] getCodeValuesJSON() {
      List codeValuesJsonList = this.getCodeValuesJSONAsList();
      String[] codeValues = new String[codeValuesJsonList.size()];
      return (String[])codeValuesJsonList.toArray(codeValues);
   }

   public List getGiveCommandsAsList() {
      ArrayList giveCommands = new ArrayList();
      List codeValues = this.getCodeValuesAsList();
      int i = 0;

      for(Iterator var4 = this.codeBlocks.entrySet().iterator(); var4.hasNext(); ++i) {
         Map.Entry codeBlocksPair = (Map.Entry)var4.next();
         CodeHeader codeHeader = (CodeHeader)codeBlocksPair.getKey();
         String codeValue = (String)codeValues.get(i);
         String giveCommand = "/give @p ender_chest{PublicBukkitValues: {\"hypercube:codetemplatedata\": '{\"author\":\"" + this.author + "\",\"name\":\"" + codeHeader.getTemplateName() + "\",\"version\":1,\"code\":\"" + codeValue + "\"}'}, display: {Name: '{\"text\":\"" + codeHeader.getTemplateNameWithColors() + "\"}'}} 1";
         giveCommands.add(giveCommand);
      }

      return giveCommands;
   }

   public Set getCodeHeaders() {
      return this.codeBlocks.keySet();
   }

   public List getCodeHeaderBlocks(CodeHeader codeHeader) {
      return (List)this.codeBlocks.get(codeHeader);
   }

   public static enum PLOT_SIZE {
      BASIC,
      LARGE,
      MASSIVE;
   }

   public static enum PLAYER_RANK {
      NO_RANK,
      NOBLE,
      EMPEROR,
      MYTHIC,
      OVERLORD;
   }
}

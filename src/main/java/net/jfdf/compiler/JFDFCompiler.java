package net.jfdf.compiler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.jfdf.compiler.util.MethodsManager;
import net.jfdf.compiler.util.ReferenceUtils;
import net.jfdf.compiler.visitor.CompilerClassAnalyzer;
import net.jfdf.compiler.visitor.CompilerClassVisitor;
import net.jfdf.jfdf.blocks.FunctionBlock;
import net.jfdf.jfdf.mangement.CodeManager;
import net.jfdf.jfdf.mangement.Functions;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Item;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

public final class JFDFCompiler {
   private static final List analyzedClasses = new ArrayList();
   private static final List compiledClasses = new ArrayList();
   private static final Set usedClasses = new HashSet();
   public static boolean useNextPatchFeatures = false;
   public static boolean realDoubles = false;

   private JFDFCompiler() {
   }

   public static void analyzeClass(Class class_) {
      if (!analyzedClasses.contains(class_)) {
         try {
            analyzedClasses.add(class_);
            ClassReader classReader = new ClassReader(class_.getName());
            ClassWriter classWriter = new ClassWriter(classReader, 0);
            CompilerClassAnalyzer visitor = new CompilerClassAnalyzer(classWriter, class_);
            classReader.accept(visitor, 0);
         } catch (Exception var4) {
            var4.printStackTrace();
         }

      }
   }

   public static void compileClass(Class class_) {
      if (!compiledClasses.contains(class_)) {
         if (!analyzedClasses.contains(class_)) {
            analyzeClass(class_);
         }

         try {
            ClassReader classReader = new ClassReader(class_.getName());
            ClassWriter classWriter = new ClassWriter(classReader, 0);
            CompilerClassVisitor visitor = new CompilerClassVisitor(classWriter, class_);
            classReader.accept(visitor, 0);
            compiledClasses.add(class_);
         } catch (Exception var4) {
            var4.printStackTrace();
         }

      }
   }

   public static void generateInit() {
      CodeManager.instance.addCodeBlocks((new FunctionBlock("_jfdf>init")).SetItems(new Item("lime_concrete", 1, "display:{Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#33CC80\",\"text\":\"Initialize Plot\"}],\"text\":\"\"}'},HideFlags:1,Enchantments:[{id:\"minecraft:lure\",lvl:1s}]")), (ArrayList)(new ArrayList()));
      Functions.Call("_jfdf>initMethodMap");
      Functions.Call("_jfdf>initNestObjs");
      Iterator var0 = compiledClasses.iterator();

      while(var0.hasNext()) {
         Class compiledClass = (Class)var0.next();
         if (MethodsManager.hasStaticInitializer(compiledClass)) {
            Functions.Call("_jfdf>" + Type.getInternalName(compiledClass) + "><clinit>>()V");
         }
      }

   }

   public static void generateInitMethodMap() {
      CodeManager.instance.addCodeBlocks((new FunctionBlock("_jfdf>initMethodMap")).SetItems(new Item("lime_concrete", 1, "display:{Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#33CC80\",\"text\":\"Initialize Method Map\"}],\"text\":\"\"}'},HideFlags:1,Enchantments:[{id:\"minecraft:lure\",lvl:1s}]")), (ArrayList)(new ArrayList()));
      Iterator var0 = compiledClasses.iterator();

      while(true) {
         Class compiledClass;
         HashMap methodMap;
         do {
            if (!var0.hasNext()) {
               return;
            }

            compiledClass = (Class)var0.next();
            methodMap = new HashMap();

            for(Class checkClass = compiledClass; checkClass != Object.class; checkClass = checkClass.getSuperclass()) {
               Text checkClassInternalName = (new Text()).Set(Type.getInternalName(checkClass));
               HashMap finalMethodMap = methodMap;
               HashMap finalMethodMap1 = methodMap;
               Arrays.stream(checkClass.getDeclaredMethods()).filter((method) -> {
                  boolean var10000;
                  if (!Modifier.isStatic(method.getModifiers())) {
                     String var10001 = method.getName();
                     if (!finalMethodMap.containsKey(var10001 + ">" + Type.getMethodDescriptor(method))) {
                        var10000 = true;
                        return var10000;
                     }
                  }

                  var10000 = false;
                  return var10000;
               }).forEach((method) -> {
                  finalMethodMap1.put(method.getName() + ">" + Type.getMethodDescriptor(method), checkClassInternalName);
               });
            }
         } while(methodMap.size() <= 0);

         List methodNames = Arrays.asList((IText[])methodMap.keySet().stream().map((s) -> {
            return (new Text()).Set((String) s);
         }).toArray((x$0) -> {
            return new IText[x$0];
         }));
         List methodClasses = Arrays.asList((IText[])methodMap.values().toArray((x$0) -> {
            return new IText[x$0];
         }));

         int i;
         for(i = 0; i < methodNames.size(); i += 26) {
            VariableControl.CreateList(new Variable("_jco0", Variable.Scope.LOCAL), (CodeValue[])methodNames.subList(i, Math.min(i + 26, methodNames.size())).toArray((x$0) -> {
               return new CodeValue[x$0];
            }));
         }

         for(i = 0; i < methodClasses.size(); i += 26) {
            VariableControl.CreateList(new Variable("_jco1", Variable.Scope.LOCAL), (CodeValue[])methodClasses.subList(i, Math.min(i + 26, methodClasses.size())).toArray((x$0) -> {
               return new CodeValue[x$0];
            }));
         }

         VariableControl.CreateDict(new Variable("_jfdfc>" + Type.getInternalName(compiledClass) + ">methods", Variable.Scope.NORMAL), new Variable("_jco0", Variable.Scope.LOCAL), new Variable("_jco1", Variable.Scope.LOCAL));
      }
   }

   public static void generateInitNestedObjects() {
      CodeManager.instance.addCodeBlocks((new FunctionBlock("_jfdf>initNestObjs")).SetItems(new Item("lime_concrete", 1, "display:{Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#33CC80\",\"text\":\"Initialize Method Map\"}],\"text\":\"\"}'},HideFlags:1,Enchantments:[{id:\"minecraft:lure\",lvl:1s}]")), (ArrayList)(new ArrayList()));
      Iterator var0 = compiledClasses.iterator();

      while(var0.hasNext()) {
         Class compiledClass = (Class)var0.next();
         Set objectFields = new HashSet();
         List checkClasses = new ArrayList();

         for(Class checkClass = compiledClass; checkClass != Object.class; checkClass = checkClass.getSuperclass()) {
            checkClasses.add(checkClass);
         }

         int fieldIndex = 1;

         for(int i = checkClasses.size() - 1; i >= 0; --i) {
            Class checkClass = (Class)checkClasses.get(i);
            Field[] var7 = checkClass.getDeclaredFields();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               Field field = var7[var9];
               ++fieldIndex;
               if (ReferenceUtils.isReferenceDescriptor(field.getType().descriptorString())) {
                  objectFields.add((new Number()).Set(fieldIndex));
               }
            }
         }

         if (objectFields.size() > 0) {
            VariableControl.CreateList(new Variable("_jfdfc>" + Type.getInternalName(compiledClass) + ">nobjs", Variable.Scope.NORMAL), (CodeValue[])objectFields.toArray((x$0) -> {
               return new CodeValue[x$0];
            }));
         }
      }

   }

   public static void addUsedClass(Class class_) {
      usedClasses.add(class_);
   }

   public static void checkUsedClasses() {
      Iterator var0 = usedClasses.iterator();

      Class usedClass;
      do {
         if (!var0.hasNext()) {
            return;
         }

         usedClass = (Class)var0.next();
      } while(compiledClasses.contains(usedClass));

      throw new IllegalStateException("Trying to use not compiled class \"" + usedClass.getName() + "\".");
   }
}

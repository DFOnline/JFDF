package net.jfdf.compiler;

import net.jfdf.compiler.util.MethodsManager;
import net.jfdf.compiler.util.ReferenceUtils;
import net.jfdf.compiler.visitor.CompilerClassAnalyzer;
import net.jfdf.compiler.visitor.CompilerClassVisitor;
import net.jfdf.jfdf.blocks.FunctionBlock;
import net.jfdf.jfdf.mangement.CodeManager;
import net.jfdf.jfdf.mangement.Functions;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.*;
import net.jfdf.jfdf.values.Number;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class JFDFCompiler {
    private static final List<Class<?>> analyzedClasses = new ArrayList<>();
    private static final List<Class<?>> compiledClasses = new ArrayList<>();
    private static final Set<Class<?>> usedClasses = new HashSet<>();

    public static boolean useNextPatchFeatures = false;
    public static boolean realDoubles = false;

    private JFDFCompiler() {}

    public static void analyzeClass(Class<?> class_) {
        if(analyzedClasses.contains(class_)) return;

        try {
            analyzedClasses.add(class_);

            // Initialize reader and writer for the analyzer
            ClassReader classReader = new ClassReader(class_.getName());
            ClassWriter classWriter = new ClassWriter(classReader, 0);

            // Analyze the class
            CompilerClassAnalyzer visitor = new CompilerClassAnalyzer(classWriter, class_);
            classReader.accept(visitor, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Compiles a class including its fields
     * and methods to DF code through
     * JFDF library
     *
     * @param class_ A Class to be compiled.
     */
    public static void compileClass(Class<?> class_) {
        if(compiledClasses.contains(class_)) return;

        if(!analyzedClasses.contains(class_)) {
            analyzeClass(class_);
        }

        try {
            // Initialize reader and writer for the compiler
            ClassReader classReader = new ClassReader(class_.getName());
            ClassWriter classWriter = new ClassWriter(classReader, 0);

            // Compile the class
            CompilerClassVisitor visitor = new CompilerClassVisitor(classWriter, class_);
            classReader.accept(visitor, 0);

            compiledClasses.add(class_);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateInit() {
        CodeManager.instance.addCodeBlocks(
                new FunctionBlock("_jfdf>init")
                        .SetItems(new Item("lime_concrete", 1, "display:{Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#33CC80\",\"text\":\"Initialize Plot\"}],\"text\":\"\"}'},HideFlags:1,Enchantments:[{id:\"minecraft:lure\",lvl:1s}]")),
                new ArrayList<>()
        );

        Functions.Call("_jfdf>initMethodMap");
        Functions.Call("_jfdf>initNestObjs");

        for(Class<?> compiledClass : compiledClasses) {
            if(MethodsManager.hasStaticInitializer(compiledClass)) {
                Functions.Call("_jfdf>" + Type.getInternalName(compiledClass) + "><clinit>>()V");
            }
        }
    }

    public static void generateInitMethodMap() {
        CodeManager.instance.addCodeBlocks(
                new FunctionBlock("_jfdf>initMethodMap")
                        .SetItems(new Item("lime_concrete", 1, "display:{Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#33CC80\",\"text\":\"Initialize Method Map\"}],\"text\":\"\"}'},HideFlags:1,Enchantments:[{id:\"minecraft:lure\",lvl:1s}]")),
                new ArrayList<>()
        );

        for(Class<?> compiledClass : compiledClasses) {
            Map<String, Text> methodMap = new HashMap<>();

            Class<?> checkClass = compiledClass;
            while (checkClass != Object.class) {
                Text checkClassInternalName = new Text().Set(Type.getInternalName(checkClass));

                Arrays.stream(checkClass.getDeclaredMethods())
                        .filter(method -> !Modifier.isStatic(method.getModifiers())
                                && !methodMap.containsKey(method.getName() + ">" + Type.getMethodDescriptor(method)))
                        .forEach(method -> methodMap.put(method.getName() + ">" + Type.getMethodDescriptor(method), checkClassInternalName));

                checkClass = checkClass.getSuperclass();
            }

            if(methodMap.size() > 0) {
                List<IText> methodNames = Arrays.asList(methodMap.keySet().stream()
                        .map(s -> new Text().Set(s))
                        .toArray(IText[]::new));

                List<IText> methodClasses = Arrays.asList(methodMap.values().toArray(IText[]::new));

                for (int i = 0; i < methodNames.size(); i += 26) {
                    VariableControl.CreateList(
                            new Variable("_jco0", Variable.Scope.LOCAL),
                            methodNames.subList(i, Math.min(i + 26, methodNames.size())).toArray(CodeValue[]::new)
                    );
                }

                for (int i = 0; i < methodClasses.size(); i += 26) {
                    VariableControl.CreateList(
                            new Variable("_jco1", Variable.Scope.LOCAL),
                            methodClasses.subList(i, Math.min(i + 26, methodClasses.size())).toArray(CodeValue[]::new)
                    );
                }

                VariableControl.CreateDict(
                        new Variable("_jfdfc>" + Type.getInternalName(compiledClass) + ">methods", Variable.Scope.NORMAL),
                        new Variable("_jco0", Variable.Scope.LOCAL),
                        new Variable("_jco1", Variable.Scope.LOCAL)
                );
            }
        }
    }

    public static void generateInitNestedObjects() {
        CodeManager.instance.addCodeBlocks(
                new FunctionBlock("_jfdf>initNestObjs")
                        .SetItems(new Item("lime_concrete", 1, "display:{Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#33CC80\",\"text\":\"Initialize Method Map\"}],\"text\":\"\"}'},HideFlags:1,Enchantments:[{id:\"minecraft:lure\",lvl:1s}]")),
                new ArrayList<>()
        );

        for(Class<?> compiledClass : compiledClasses) {
            Set<Number> objectFields = new HashSet<>();
            List<Class<?>> checkClasses = new ArrayList<>();

            for (Class<?> checkClass = compiledClass;
                 checkClass != Object.class;
                 checkClass = checkClass.getSuperclass()) {
                checkClasses.add(checkClass);
            }

            int fieldIndex = 1;

            for (int i = checkClasses.size() - 1; i >= 0; i--) {
                Class<?> checkClass = checkClasses.get(i);

                for (Field field : checkClass.getDeclaredFields()) {
                    fieldIndex++;

                    if(ReferenceUtils.isReferenceDescriptor(field.getType().descriptorString())) {
                        objectFields.add(new Number().Set(fieldIndex));
                    }
                }
            }

            if(objectFields.size() > 0) {
                VariableControl.CreateList(
                        new Variable("_jfdfc>" + Type.getInternalName(compiledClass) + ">nobjs", Variable.Scope.NORMAL),
                        objectFields.toArray(CodeValue[]::new)
                );
            }
        }
    }

    public static void addUsedClass(Class<?> class_) {
        usedClasses.add(class_);
    }

    public static void checkUsedClasses() {
        for (Class<?> usedClass : usedClasses) {
            if(!compiledClasses.contains(usedClass)) {
                throw new IllegalStateException("Trying to use not compiled class \"" + usedClass.getName() + "\".");
            }
        }
    }
}

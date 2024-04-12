package net.jfdf.jfdf;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.jfdf.jfdf.blocks.CodeHeader;
import net.jfdf.jfdf.blocks.EntityEventBlock;
import net.jfdf.jfdf.blocks.FunctionBlock;
import net.jfdf.jfdf.blocks.PlayerEventBlock;
import net.jfdf.jfdf.blocks.ProcessBlock;
import net.jfdf.jfdf.mangement.CodeManager;
import net.jfdf.jfdf.mangement.EntityEvent;
import net.jfdf.jfdf.mangement.Function;
import net.jfdf.jfdf.mangement.FunctionWithArgs;
import net.jfdf.jfdf.mangement.PlayerEvent;
import net.jfdf.jfdf.mangement.Process;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Item;
import net.jfdf.jfdf.values.Tag;
import net.jfdf.jfdf.values.Variable;

public abstract class CodeExecutor extends CodeClass {
   public void execute() {
      CodeManager.instance.playerRank = this.getAuthorRank();
      CodeManager.instance.plotSize = this.getPlotSize();
      CodeManager.instance.setAuthor(this.getAuthor());
      JFDFAddon.addJFDFAddons(Arrays.asList(this.getAddons()));

      try {
         JFDFAddon.class.getDeclaredMethod("onStart").invoke((Object)null);
      } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var19) {
      }

      List codeClasses = new ArrayList();
      codeClasses.add(this.getClass());
      codeClasses.addAll(Arrays.asList(this.getExportedClasses()));
      Iterator var2 = codeClasses.iterator();

      Class codeClass;
      Method[] methods;
      int var7;
      while(var2.hasNext()) {
         codeClass = (Class)var2.next();
         methods = codeClass.getMethods();
         Method[] var5 = methods;
         int var6 = methods.length;

         for(var7 = 0; var7 < var6; ++var7) {
            Method classMethod = var5[var7];
            if (classMethod.getAnnotation(FunctionWithArgs.class) != null) {
               CodeManager.instance.addFunctionWithArgs(classMethod);
            }
         }
      }

      var2 = codeClasses.iterator();

      while(true) {
         Object objInstance;
         do {
            if (!var2.hasNext()) {
               return;
            }

            codeClass = (Class)var2.next();
            methods = codeClass.getMethods();
            objInstance = null;
         } while(codeClass.isInterface());

         if (codeClass == this.getClass()) {
            objInstance = this;
         } else {
            try {
               Constructor[] constructors = codeClass.getConstructors();
               if (constructors.length > 0) {
                  objInstance = constructors[0].newInstance();
               }
            } catch (InstantiationException | InvocationTargetException | IllegalArgumentException | IllegalAccessException var18) {
               System.err.println("Error while trying to create a constructor for " + codeClass.getName() + ":");
               var18.printStackTrace();
            }
         }

         Method[] var23 = methods;
         var7 = methods.length;

         for(int var24 = 0; var24 < var7; ++var24) {
            Method classMethod = var23[var24];
            Object codeHeader;
            if (classMethod.getAnnotation(Function.class) != null) {
               codeHeader = (new FunctionBlock(((Function)classMethod.getAnnotation(Function.class)).name())).SetTags(new Tag("Is Hidden", ((Function)classMethod.getAnnotation(Function.class)).isHidden() ? "True" : "False"));
               System.out.println("function block");
            } else if (classMethod.getAnnotation(FunctionWithArgs.class) != null) {
               FunctionWithArgs data = (FunctionWithArgs)classMethod.getAnnotation(FunctionWithArgs.class);
               codeHeader = (new FunctionBlock(data.name())).SetTags(new Tag("Is Hidden", data.isHidden() ? "True" : "False"));
               if (!data.iconId().equals("lapis_lazuli") || !data.iconNbt().equals("")) {
                  ((FunctionBlock)codeHeader).SetItems(new Item(data.iconId(), 1, data.iconNbt()));
               }
            } else if (classMethod.getAnnotation(Process.class) != null) {
               codeHeader = (new ProcessBlock(((Process)classMethod.getAnnotation(Process.class)).name())).SetTags(new Tag("Is Hidden", ((Process)classMethod.getAnnotation(Process.class)).isHidden() ? "True" : "False"));
            } else if (classMethod.getAnnotation(PlayerEvent.class) != null) {
               codeHeader = new PlayerEventBlock(((PlayerEvent)classMethod.getAnnotation(PlayerEvent.class)).eventType().getJSONValue());
            } else {
               if (classMethod.getAnnotation(EntityEvent.class) == null) {
                  continue;
               }

               codeHeader = new EntityEventBlock(((EntityEvent)classMethod.getAnnotation(EntityEvent.class)).eventType().getJSONValue());
            }

            CodeManager.instance.addCodeBlocks((CodeHeader)codeHeader, (ArrayList)(new ArrayList()));

            try {
               JFDFAddon.class.getDeclaredMethod("onHeaderStart").invoke((Object)null);
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var17) {
            }

            List args = new ArrayList();
            if (classMethod.getAnnotation(FunctionWithArgs.class) != null) {
               Class[] var12 = classMethod.getParameterTypes();
               int var13 = var12.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  Class arg = var12[var14];
                  if (!CodeValue.class.isAssignableFrom(arg)) {
                     throw new IllegalArgumentException("You can't use a function argument that not code value !");
                  }

                  if (arg == net.jfdf.jfdf.values.List.class) {
                     args.add(new net.jfdf.jfdf.values.List(CodeManager.instance.getVariable("_function_" + classMethod.getName() + "_arg" + args.size(), Variable.Scope.LOCAL)));
                  } else {
                     args.add(CodeManager.instance.getVariable("_function_" + classMethod.getName() + "_arg" + args.size(), Variable.Scope.LOCAL));
                  }

                  VariableControl.Set(CodeManager.instance.getVariable(((Variable)args.get(args.size() - 1)).getName() + "__saved", Variable.Scope.LOCAL), (CodeValue)args.get(args.size() - 1));
               }
            }

            try {
               classMethod.setAccessible(true);
               if (args.size() == 0) {
                  classMethod.invoke(objInstance);
               } else {
                  classMethod.invoke(objInstance, args.toArray());
               }
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var20) {
               if (var20 instanceof InvocationTargetException) {
                  ((InvocationTargetException)var20).getTargetException().printStackTrace();
               }

               System.err.println("Class: " + codeClass);
               System.err.println("Method: " + classMethod.getName());
               var20.printStackTrace();
               throw new RuntimeException("Every code header method should be without any arguments and without \"protected\" tag.");
            }

            if (classMethod.getAnnotation(FunctionWithArgs.class) != null) {
               Iterator var26 = args.iterator();

               while(var26.hasNext()) {
                  Variable arg = (Variable)var26.next();
                  VariableControl.Set(CodeManager.instance.getVariable(arg.getName() + "__saved", Variable.Scope.LOCAL), arg);
               }

               CodeManager.instance.addFunctionWithArgs(classMethod);
            }

            try {
               JFDFAddon.class.getDeclaredMethod("onHeaderEnd", CodeHeader.class, List.class).invoke((Object)null, codeHeader, CodeManager.instance.getCodeHeaderBlocks((CodeHeader)codeHeader));
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var16) {
            }
         }
      }
   }

   public abstract Class[] getCodeClasses();

   public abstract String getAuthor();

   public abstract CodeManager.PLOT_SIZE getPlotSize();

   public abstract CodeManager.PLAYER_RANK getAuthorRank();

   public abstract JFDFAddon[] getAddons();

   public Class[] getExportedClasses() {
      return new Class[0];
   }

   public static String getGiveCommand() {
      return (String)CodeManager.instance.getGiveCommandsAsList().get(0);
   }

   public static String getGiveCommand(int index) {
      return (String)CodeManager.instance.getGiveCommandsAsList().get(index);
   }
}

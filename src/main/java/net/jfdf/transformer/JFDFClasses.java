package net.jfdf.transformer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import net.jfdf.jfdf.blocks.CallProcessBlock;
import net.jfdf.jfdf.mangement.CodeManager;
import net.jfdf.jfdf.mangement.Function;
import net.jfdf.jfdf.mangement.FunctionWithArgs;
import net.jfdf.jfdf.mangement.Functions;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.PlayerEvent;
import net.jfdf.jfdf.mangement.Process;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.List;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Variable;
import org.objectweb.asm.Type;

/** @deprecated */
@Deprecated(
   forRemoval = true
)
public class JFDFClasses {
   public static void Consume(Object object) {
   }

   public static void SetVarValue(List class_, CodeValue value, int varIndex) {
      class_.set((new Number()).Set(varIndex), value);
   }

   public static List GetInitList(int argIndex, int fieldsCount) {
      CodeValue[] listValues = new CodeValue[fieldsCount];
      Number number = (new Number()).Set(0.0F);

      for(int i = 0; i < fieldsCount; ++i) {
         listValues[i] = number;
      }

      return new List("%var(_function_jfdf@init_arg" + argIndex + ")", listValues);
   }

   public static boolean Equals(CodeValue a, CodeValue b, boolean not) {
      if (not) {
         If.Variable.NotEquals(a, new CodeValue[]{b}, false);
      } else {
         If.Variable.Equals(a, new CodeValue[]{b}, false);
      }

      return true;
   }

   public static List getGlobalVar(String className, String varName) {
      try {
         Field field = Class.forName(className.replace('/', '.')).getDeclaredField(varName);
         Saved isSaved = (Saved)field.getAnnotation(Saved.class);
         VariablePrefix prefixAnnotation = (VariablePrefix)field.getAnnotation(VariablePrefix.class);
         VariableSuffix suffixAnnotation = (VariableSuffix)field.getAnnotation(VariableSuffix.class);
         VariableOverride overrideAnnotation = (VariableOverride)field.getAnnotation(VariableOverride.class);
         String newVarName = overrideAnnotation != null ? overrideAnnotation.name() : "";
         String var10000 = prefixAnnotation != null ? prefixAnnotation.prefix() : "";
         newVarName = var10000 + newVarName;
         newVarName = newVarName + (suffixAnnotation != null ? suffixAnnotation.suffix() : "");
         return isSaved != null ? new List(new Variable("_jfdf>" + className + ">" + newVarName, Variable.Scope.SAVED)) : new List(new Variable("_jfdf>" + className + ">" + newVarName, Variable.Scope.NORMAL));
      } catch (NoSuchFieldException | ClassNotFoundException var8) {
         var8.printStackTrace();
         return null;
      }
   }

   public static void setGlobalVar(CodeValue value, String className, String varName) {
      VariableControl.Set(getGlobalVar(className, varName), value);
   }

   public static void callMethod(String className, String methodName, String realDescriptor, String modifiedDescriptor, CodeValue... codeValues) {
      try {
         Class methodClass = Class.forName(className.replace('/', '.'));
         String newModifiedDescriptor = modifiedDescriptor;
         if (methodName.equals("jfdf@init")) {
            newModifiedDescriptor = modifiedDescriptor.replace(")", "Lnet/jfdf/jfdf/values/CodeValue;)");
         } else if (methodClass.getAnnotation(ExportClass.class) != null) {
            newModifiedDescriptor = modifiedDescriptor.replace("(", "(Lnet/jfdf/jfdf/values/List;");
         }

         Method callMethod;
         try {
            callMethod = methodClass.getDeclaredMethod(methodName, readDescriptor(newModifiedDescriptor));
         } catch (NoSuchMethodException var9) {
            callMethod = methodClass.getDeclaredMethod(methodName, readDescriptor(modifiedDescriptor));
         }

         if (callMethod.getAnnotation(PlayerEvent.class) != null) {
            throw new IllegalStateException("Calling a player event is not allowed.");
         }

         if (callMethod.getAnnotation(Function.class) != null) {
            Functions.Call("_jfdf>" + className + ">" + methodName + ">" + realDescriptor);
         } else if (callMethod.getAnnotation(FunctionWithArgs.class) != null) {
            Functions.CallWithArgs("_jfdf>" + className + ">" + methodName + ">" + realDescriptor, codeValues);
         } else if (callMethod.getAnnotation(Process.class) != null) {
            CodeManager.instance.addCodeBlock(new CallProcessBlock("_jfdf>" + className + ">" + methodName + ">" + realDescriptor));
         }
      } catch (NoSuchMethodException | ClassNotFoundException var10) {
         var10.printStackTrace();
      }

   }

   public static CodeValue[] newArray(Number number) {
      return new CodeValue[Integer.parseInt(number.getValue())];
   }

   public static void storeArrayValue(CodeValue[] array, Number number, CodeValue value) {
      array[Integer.parseInt(number.getValue())] = value;
   }

   private static Class[] readDescriptor(String desc) {
      return (Class[])Arrays.stream(Type.getMethodType(desc).getArgumentTypes()).map((type) -> {
         try {
            return Class.forName(type.getClassName());
         } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
         }
      }).toArray((x$0) -> {
         return new Class[x$0];
      });
   }
}

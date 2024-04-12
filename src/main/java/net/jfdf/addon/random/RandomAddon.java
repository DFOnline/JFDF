package net.jfdf.addon.random;

import java.util.List;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.MathFunctionStackValue;
import net.jfdf.compiler.data.stack.MathStackValue;
import net.jfdf.compiler.data.stack.NumberStackValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Number;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;

public class RandomAddon implements ICompilerAddon {
   public boolean onInitClass(String type, List stack) {
      if (type.equals("java/util/Random")) {
         stack.add(new NumberStackValue(0));
         return true;
      } else {
         return false;
      }
   }

   public boolean onInvokeConstructor(String type, String descriptor, List stack) {
      if (type.equals("java/util/Random")) {
         if (descriptor.equals("(J)V")) {
            stack.remove(stack.size() - 1);
         }

         stack.remove(stack.size() - 1);
         return true;
      } else {
         return false;
      }
   }

   public boolean onInvokeDynamic(String name, String descriptor, Handle methodHandle, Object[] methodArgs, List stack) {
      return false;
   }

   public boolean onInvokeMember(String owner, String name, String descriptor, List stack) {
      if (owner.equals("java/util/Random")) {
         stack.remove(stack.size() - (Type.getArgumentTypes(descriptor).length + 1));
         switch (name + descriptor) {
            case "nextBoolean()Z":
               stack.add(new MathFunctionStackValue((new Number()).Set(0), (new Number()).Set(1), MathFunctionStackValue.MathFunction.RANDOM));
               break;
            case "nextInt()I":
               stack.add(new MathFunctionStackValue((new Number()).Set(Integer.MIN_VALUE), (new Number()).Set(Integer.MAX_VALUE), MathFunctionStackValue.MathFunction.RANDOM));
               break;
            case "nextInt(I)I":
               stack.add(new MathFunctionStackValue((new Number()).Set(0), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), MathFunctionStackValue.MathFunction.RANDOM));
               break;
            case "nextLong()J":
               stack.add(new MathFunctionStackValue((new Number()).Set(-9223372036854775L), (new Number()).Set(9223372036854775L), MathFunctionStackValue.MathFunction.RANDOM));
               break;
            case "nextFloat()F":
            case "nextDouble()D":
               stack.add(new MathStackValue(Number.Random((new Number()).Set(0), (new Number()).Set(1000)), (new Number()).Set(1000), MathStackValue.MathOperation.DIVIDE));
               break;
            default:
               throw new IllegalStateException("Unsupported java.lang.Random method: " + name + descriptor + ".");
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean onInvokeStatic(String owner, String name, String descriptor, List stack) {
      return false;
   }
}

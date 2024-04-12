package net.jfdf.addon.math;

import java.util.List;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.MathStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Variable;
import org.objectweb.asm.Handle;

public class MathAddon implements ICompilerAddon {
   public boolean onInitClass(String type, List stack) {
      return false;
   }

   public boolean onInvokeConstructor(String type, String descriptor, List stack) {
      return false;
   }

   public boolean onInvokeDynamic(String name, String descriptor, Handle methodHandle, Object[] methodArgs, List stack) {
      return false;
   }

   public boolean onInvokeMember(String owner, String name, String descriptor, List stack) {
      return false;
   }

   public boolean onInvokeStatic(String owner, String name, String descriptor, List stack) {
      if (owner.equals("java/lang/Math")) {
         Variable result;
         switch (name + descriptor) {
            case "round(F)I":
            case "round(D)J":
               result = CompilerAddons.getTempVariable();
               stack.add(new VariableStackValue("I", result.getName()));
               VariableControl.Round(result, (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)null, Tags.RoundMode.NEAREST);
               break;
            case "floor(D)D":
               result = CompilerAddons.getTempVariable();
               stack.add(new VariableStackValue("I", result.getName()));
               VariableControl.Round(result, (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)null, Tags.RoundMode.FLOOR);
               break;
            case "ceil(D)D":
               result = CompilerAddons.getTempVariable();
               stack.add(new VariableStackValue("I", result.getName()));
               VariableControl.Round(result, (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)null, Tags.RoundMode.CEILING);
               break;
            case "addExact(II)I":
            case "addExact(JJ)J":
               stack.add(new MathStackValue((INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), MathStackValue.MathOperation.ADD));
               break;
            case "multiplyExact(II)I":
            case "multiplyExact(JI)J":
            case "multiplyExact(JJ)J":
               stack.add(new MathStackValue((INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), MathStackValue.MathOperation.MULTIPLY));
               break;
            case "min(II)I":
            case "min(JJ)J":
            case "min(FF)F":
            case "min(DD)D":
               result = CompilerAddons.getTempVariable();
               stack.add(new VariableStackValue("I", result.getName()));
               VariableControl.Set(result, ((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue());
               If.Variable.GreaterThan(result, (INumber)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue(), false);
               VariableControl.Set(result, ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               If.End();
               break;
            case "max(II)I":
            case "max(JJ)J":
            case "max(FF)F":
            case "max(DD)D":
               result = CompilerAddons.getTempVariable();
               stack.add(new VariableStackValue("I", result.getName()));
               VariableControl.Set(result, ((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue());
               If.Variable.LessThan(result, (INumber)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue(), false);
               VariableControl.Set(result, ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               If.End();
               break;
            case "sqrt(D)D":
               result = CompilerAddons.getTempVariable();
               stack.add(new VariableStackValue("D", result.getName()));
               VariableControl.Root(result, (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (new Number()).Set(2));
               break;
            case "abs(I)I":
            case "abs(J)J":
            case "abs(F)F":
            case "abs(D)D":
               result = CompilerAddons.getTempVariable();
               stack.add(new VariableStackValue("D", result.getName()));
               VariableControl.AbsoluteValue(result, (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               break;
            default:
               throw new IllegalStateException("Math." + name + descriptor + " is not supported !");
         }

         return true;
      } else {
         return false;
      }
   }
}

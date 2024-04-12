package net.jfdf.addon.collections;

import java.util.List;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.compiler.library.References;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Variable;
import org.objectweb.asm.Type;

class IteratorMethodHandler {
   private IteratorMethodHandler() {
   }

   public static boolean handle(String owner, String name, String descriptor, List stack) {
      if (!owner.equals("java/util/Iterator")) {
         return false;
      } else {
         int argsCount = Type.getArgumentTypes(descriptor).length;
         Variable iterator = (Variable)((IStackValue)stack.remove(stack.size() - argsCount - 1)).getTransformedValue();
         switch (name + descriptor) {
            case "hasNext()Z":
               stack.add(new IfIteratorStackValue(iterator));
               break;
            case "next()Ljava/lang/Object;":
               Variable result = CompilerAddons.getTempVariable();
               VariableControl.GetListValue(result, new Variable("_jfdfR" + Number.AsListValue(iterator, (new Number()).Set(1)).getValue(), Variable.Scope.NORMAL), Number.AsListValue(iterator, (new Number()).Set(2)));
               VariableControl.SetListValue(iterator, (new Number()).Set(2), Number.Add(Number.AsListValue(iterator, (new Number()).Set(2)), (new Number()).Set(1)));
               References.incrementRefCount(result);
               stack.add(new VariableStackValue("Ljava/lang/Object;", result.getName()));
               break;
            default:
               throw new IllegalStateException("Unsupported Iterator method: " + name + descriptor);
         }

         return true;
      }
   }
}

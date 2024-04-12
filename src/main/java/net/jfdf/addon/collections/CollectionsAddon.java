package net.jfdf.addon.collections;

import java.util.List;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.addon.IfHandler;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.Repeat;
import net.jfdf.jfdf.mangement.SubIf;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;

public class CollectionsAddon implements ICompilerAddon {
   public boolean onInitClass(String type, List stack) {
      if (!type.equals("java/util/List") && !type.equals("java/util/ArrayList")) {
         if (!type.equals("java/util/HashMap") && !type.equals("java/util/LinkedHashMap")) {
            return false;
         } else {
            stack.add(new DictStackValue(CompilerAddons.getTempVariable()));
            return true;
         }
      } else {
         stack.add(new ListStackValue(CompilerAddons.getTempVariable()));
         return true;
      }
   }

   public boolean onInvokeConstructor(String type, String descriptor, List stack) {
      if (type.equals("java/util/ArrayList")) {
         switch (descriptor) {
            case "()V":
               VariableControl.CreateList(((ListStackValue)stack.remove(stack.size() - 1)).getReference(), (new Text()).Set("\u0000r"));
               break;
            case "(Ljava/util/Collection;)V":
               VariableControl.Set(((ListStackValue)stack.remove(stack.size() - 2)).getReference(), ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               break;
            default:
               throw new IllegalStateException("Unsupported ArrayList constructor: ArrayList" + descriptor + ".");
         }

         return true;
      } else if (!type.equals("java/util/HashMap") && !type.equals("java/util/LinkedHashMap")) {
         return false;
      } else {
         switch (descriptor) {
            case "()V":
               VariableControl.CreateDict(((DictStackValue)stack.remove(stack.size() - 1)).getReference(), (Variable)null, (Variable)null);
               break;
            case "(Ljava/util/Map;)V":
               VariableControl.Set(((DictStackValue)stack.remove(stack.size() - 2)).getReference(), ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               break;
            default:
               throw new IllegalStateException("Unsupported HashMap/LinkedHashMap constructor: ArrayList" + descriptor + ".");
         }

         return true;
      }
   }

   public boolean onInvokeMember(String owner, String name, String descriptor, List stack) {
      return ListMethodHandler.handle(owner, name, descriptor, stack) || DictMethodHandler.handle(owner, name, descriptor, stack) || IteratorMethodHandler.handle(owner, name, descriptor, stack);
   }

   public IfHandler onIf(String defaultType, boolean invert, List stack) {
      IStackValue ifValue = (IStackValue)stack.get(stack.size() - 1);
      if (ifValue instanceof IfIteratorStackValue) {
         final IfIteratorStackValue ifIteratorData = (IfIteratorStackValue)ifValue;
         return new IfHandler() {
            public void onIf() {
               If.Variable.LessThanOrEqualTo(Number.AsListValue(ifIteratorData.getIterator(), (new Number()).Set(2)), Number.AsListValue(ifIteratorData.getIterator(), (new Number()).Set(3)), false);
            }

            public void onRepeat() {
               Repeat.While(SubIf.Variable.LessThanOrEqualTo(Number.AsListValue(ifIteratorData.getIterator(), (new Number()).Set(2)), Number.AsListValue(ifIteratorData.getIterator(), (new Number()).Set(3))));
            }
         };
      } else {
         return null;
      }
   }
}

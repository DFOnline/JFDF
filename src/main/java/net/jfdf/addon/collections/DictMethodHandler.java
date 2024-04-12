package net.jfdf.addon.collections;

import java.util.List;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.instruction.InstructionData;
import net.jfdf.compiler.data.instruction.TypeInstructionData;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.NumberStackValue;
import net.jfdf.compiler.data.stack.ReferencedStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.compiler.library.References;
import net.jfdf.compiler.util.ReferenceUtils;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;
import org.objectweb.asm.Type;

class DictMethodHandler {
   private DictMethodHandler() {
   }

   public static boolean handle(String owner, String name, String descriptor, List stack) {
      if (!owner.equals("java/util/Map") && !owner.equals("java/util/HashMap") && !owner.equals("java/util/LinkedHashMap")) {
         return false;
      } else {
         int argsCount = Type.getArgumentTypes(descriptor).length;
         IStackValue pointerStackValue = (IStackValue)stack.remove(stack.size() - argsCount - 1);
         Variable reference;
         if (pointerStackValue instanceof ReferencedStackValue) {
            reference = ((ReferencedStackValue)pointerStackValue).getReference();
         } else {
            Variable pointer = (Variable)pointerStackValue.getTransformedValue();
            reference = new Variable("_jfdfR%var(" + pointer.getName() + ")", Variable.Scope.NORMAL);
         }

         switch (name + descriptor) {
            case "putAll(Ljava/util/Map;)V":
               putAll(reference, stack);
               break;
            case "put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;":
               put(reference, stack);
               break;
            case "get(Ljava/lang/Object;)Ljava/lang/Object;":
               get(reference, stack);
               break;
            case "remove(Ljava/lang/Object;)Ljava/lang/Object;":
               remove1(reference, stack);
               break;
            case "remove(Ljava/lang/Object;Ljava/lang/Object;)Z":
               remove2(reference, stack);
               break;
            case "keySet()Ljava/util/Set;":
               keySet(reference, stack);
               break;
            case "values()Ljava/util/Collection;":
               values(reference, stack);
               break;
            case "containsKey(Ljava/lang/Object;)Z":
               containsKey(reference, stack);
               break;
            case "clear()V":
               VariableControl.ClearDict(reference);
               break;
            case "size()I":
               size(reference, stack);
               break;
            default:
               throw new IllegalStateException("Unsupported Map method: " + name + descriptor);
         }

         return true;
      }
   }

   private static void putAll(Variable reference, List stack) {
      IStackValue dictionaryStackValue = (IStackValue)stack.remove(stack.size() - 1);
      Variable dictionaryReference;
      if (dictionaryStackValue instanceof ReferencedStackValue) {
         dictionaryReference = ((ReferencedStackValue)dictionaryStackValue).getReference();
      } else {
         Variable dictionaryPointer = (Variable)dictionaryStackValue.getTransformedValue();
         dictionaryReference = new Variable("_jfdfR%var(" + dictionaryPointer.getName() + ")", Variable.Scope.NORMAL);
      }

      VariableControl.AppendDict(reference, dictionaryReference);
   }

   private static void put(Variable reference, List stack) {
      CodeValue key = ((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
      IStackValue valueStackValue = (IStackValue)stack.remove(stack.size() - 1);
      CodeValue value = valueStackValue.getTransformedValue();
      if (!(key instanceof IText)) {
         throw new IllegalStateException("Maps don't support non-text keys !");
      } else {
         Variable returnValue = CompilerAddons.getTempVariable();
         VariableControl.GetDictValue(returnValue, reference, (IText)key);
         References.decrementRefCount(returnValue);
         ReferenceUtils.incrementIfReference(valueStackValue.getDescriptor(), value);
         VariableControl.SetDictValue(reference, (IText)key, value);
         stack.add(new VariableStackValue("Ljava/lang/Object;", returnValue.getName()));
      }
   }

   private static void get(Variable reference, List stack) {
      CodeValue key = ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
      if (!(key instanceof IText)) {
         throw new IllegalStateException("Maps don't support non-text keys !");
      } else {
         Variable returnValue = CompilerAddons.getTempVariable();
         VariableControl.GetDictValue(returnValue, reference, (IText)key);
         InstructionData nextInsn = (InstructionData)CompilerAddons.getInstructionDataList().get(CompilerAddons.getInstructionIndex() + 1);
         String getValueDescriptor = "Ljava/lang/Object;";
         if (nextInsn.instructionOpcode == 192) {
            getValueDescriptor = ((TypeInstructionData)nextInsn).type;
            if (!getValueDescriptor.startsWith("[")) {
               getValueDescriptor = "L" + getValueDescriptor + ";";
            }

            String var10000;
            switch (getValueDescriptor) {
               case "Ljava/lang/Boolean;":
                  var10000 = "Z";
                  break;
               case "Ljava/lang/Byte;":
                  var10000 = "B";
                  break;
               case "Ljava/lang/Short;":
                  var10000 = "S";
                  break;
               case "Ljava/lang/Integer;":
                  var10000 = "I";
                  break;
               case "Ljava/lang/Long;":
                  var10000 = "J";
                  break;
               case "Ljava/lang/Float;":
                  var10000 = "F";
                  break;
               case "Ljava/lang/Double;":
                  var10000 = "D";
                  break;
               default:
                  var10000 = getValueDescriptor;
            }

            getValueDescriptor = var10000;
         }

         ReferenceUtils.incrementIfReference(getValueDescriptor, returnValue);
         stack.add(new VariableStackValue("Ljava/lang/Object;", returnValue.getName()));
      }
   }

   private static void remove1(Variable reference, List stack) {
      CodeValue key = ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
      if (!(key instanceof IText)) {
         throw new IllegalStateException("Maps don't support non-text keys !");
      } else {
         InstructionData nextInstruction = (InstructionData)CompilerAddons.getInstructionDataList().get(CompilerAddons.getInstructionIndex() + 1);
         Variable returnValue = CompilerAddons.getTempVariable();
         VariableControl.GetDictValue(returnValue, reference, (IText)key);
         if (nextInstruction.instructionOpcode != 87) {
            stack.add(new VariableStackValue("Ljava/lang/Object;", returnValue.getName()));
         } else {
            References.decrementRefCount(returnValue);
            stack.add(new NumberStackValue());
         }

         VariableControl.RemoveDictEntry(reference, (IText)key);
      }
   }

   private static void remove2(Variable reference, List stack) {
      CodeValue key = ((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
      CodeValue value = ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
      if (!(key instanceof IText)) {
         throw new IllegalStateException("Maps don't support non-text keys !");
      } else {
         InstructionData nextInstruction = (InstructionData)CompilerAddons.getInstructionDataList().get(CompilerAddons.getInstructionIndex() + 1);
         Variable returnValue;
         if (nextInstruction.instructionOpcode != 87) {
            returnValue = CompilerAddons.getTempVariable();
            VariableControl.Set(returnValue, (new Number()).Set(0));
            If.Variable.DictHasKey(reference, (IText)key, false);
            Variable removeValue = CompilerAddons.getTempVariable();
            VariableControl.GetDictValue(removeValue, reference, (IText)key);
            References.decrementRefCount(removeValue);
            VariableControl.Set(returnValue, (new Number()).Set(1));
            If.End();
            stack.add(new VariableStackValue("Z", returnValue.getName()));
         } else {
            If.Variable.DictHasKey(reference, (IText)key, false);
            returnValue = CompilerAddons.getTempVariable();
            VariableControl.GetDictValue(returnValue, reference, (IText)key);
            References.decrementRefCount(returnValue);
            If.End();
            stack.add(new NumberStackValue());
         }

         VariableControl.RemoveDictEntry(reference, (IText)key, value);
      }
   }

   private static void keySet(Variable reference, List stack) {
      ReferencedStackValue returnValue = new ListStackValue(CompilerAddons.getTempVariable());
      VariableControl.GetDictKeys(returnValue.getReference(), reference);
      VariableControl.InsertListValue(returnValue.getReference(), (new Number()).Set(1), (new Text()).Set("\u0000r"));
      stack.add(returnValue);
   }

   private static void values(Variable reference, List stack) {
      ReferencedStackValue returnValue = new ListStackValue(CompilerAddons.getTempVariable());
      VariableControl.GetDictValues(returnValue.getReference(), reference);
      VariableControl.InsertListValue(returnValue.getReference(), (new Number()).Set(1), (new Text()).Set("\u0000r"));
      stack.add(returnValue);
   }

   private static void containsKey(Variable reference, List stack) {
      CodeValue key = ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
      if (!(key instanceof IText)) {
         throw new IllegalStateException("Maps don't support non-text keys !");
      } else {
         Variable returnValue = CompilerAddons.getTempVariable();
         VariableControl.Set(returnValue, (new Number()).Set(0));
         If.Variable.DictHasKey(reference, (IText)key, false);
         VariableControl.Set(returnValue, (new Number()).Set(1));
         If.End();
         stack.add(new VariableStackValue("Z", returnValue.getName()));
      }
   }

   private static void size(Variable reference, List stack) {
      Variable returnValue = CompilerAddons.getTempVariable();
      VariableControl.GetDictSize(returnValue, reference);
      stack.add(new VariableStackValue("I", returnValue.getName()));
   }
}

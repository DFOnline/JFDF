package net.jfdf.addon.collections;

import java.util.List;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.instruction.InstructionData;
import net.jfdf.compiler.data.instruction.TypeInstructionData;
import net.jfdf.compiler.data.stack.ArrayStackValue;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.MathStackValue;
import net.jfdf.compiler.data.stack.NumberStackValue;
import net.jfdf.compiler.data.stack.ReferencedStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.compiler.library.References;
import net.jfdf.compiler.util.ReferenceUtils;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;
import net.jfdf.transformer.util.NumberMath;
import org.objectweb.asm.Type;

class ListMethodHandler {
   private ListMethodHandler() {
   }

   public static boolean handle(String owner, String name, String descriptor, List stack) {
      if (!owner.equals("java/util/List") && !owner.equals("java/util/ArrayList") && !owner.equals("java/util/Collection")) {
         return false;
      } else {
         int argsCount = Type.getArgumentTypes(descriptor).length;
         IStackValue pointerStackValue = (IStackValue)stack.remove(stack.size() - (argsCount + 1));
         Variable reference;
         if (pointerStackValue instanceof ReferencedStackValue) {
            reference = ((ReferencedStackValue)pointerStackValue).getReference();
         } else {
            reference = new Variable("_jfdfR%var(" + ((Variable)pointerStackValue.getTransformedValue()).getName() + ")", Variable.Scope.NORMAL);
         }

         Variable iteratorVariable;
         InstructionData nextInsn;
         String getValueDescriptor;
         Number index;
         String var10000;
         Variable variable;
         IStackValue stackValue;
         String valueDescriptor;
         switch (name + descriptor) {
            case "add(Ljava/lang/Object;)Z":
               if (pointerStackValue instanceof ArrayStackValue) {
                  throw new UnsupportedOperationException("Trying to append value to array.");
               }

               stackValue = (IStackValue)stack.remove(stack.size() - 1);
               VariableControl.AppendValue(reference, stackValue.getTransformedValue());
               valueDescriptor = stackValue.getDescriptor();
               ReferenceUtils.incrementIfReference(valueDescriptor, stackValue.getTransformedValue());
               stack.add(new NumberStackValue(1));
               break;
            case "add(ILjava/lang/Object;)V":
               if (pointerStackValue instanceof ArrayStackValue) {
                  throw new UnsupportedOperationException("Trying to insert value on array.");
               }

               stackValue = (IStackValue)stack.remove(stack.size() - 1);
               VariableControl.InsertListValue(reference, NumberMath.add((INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), (new Number()).Set(2)), stackValue.getTransformedValue());
               valueDescriptor = stackValue.getDescriptor();
               ReferenceUtils.incrementIfReference(valueDescriptor, stackValue.getTransformedValue());
               break;
            case "remove(Ljava/lang/Object;)Z":
               if (pointerStackValue instanceof ArrayStackValue) {
                  throw new UnsupportedOperationException("Trying to remove value from array.");
               }

               System.out.println("[WARNING] Using List.remove(java.lang.Object) is not stable yet");
               VariableControl.RemoveListValue(reference, ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new NumberStackValue(1));
               break;
            case "remove(I)Ljava/lang/Object;":
               if (pointerStackValue instanceof ArrayStackValue) {
                  throw new UnsupportedOperationException("Trying to remove value from array.");
               }

               index = NumberMath.add((INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), (new Number()).Set(2));
               InstructionData nextInsn = (InstructionData)CompilerAddons.getInstructionDataList().get(CompilerAddons.getInstructionIndex() + 1);
               if (nextInsn.instructionOpcode != 87) {
                  String getValueDescriptor = "Ljava/lang/Object;";
                  if (nextInsn.instructionOpcode == 192) {
                     getValueDescriptor = ((TypeInstructionData)nextInsn).type;
                     if (!getValueDescriptor.startsWith("[")) {
                        getValueDescriptor = "L" + getValueDescriptor + ";";
                     }

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

                  Variable variable = CompilerAddons.getTempVariable();
                  VariableControl.GetListValue(variable, reference, index);
                  stack.add(new VariableStackValue(getValueDescriptor, variable.getName()));
               } else {
                  References.decrementRefCount(NumberMath.listValue(reference, index));
                  stack.add(new NumberStackValue(0));
               }

               VariableControl.RemoveListIndex(reference, index);
               break;
            case "size()I":
               iteratorVariable = CompilerAddons.getTempVariable();
               VariableControl.ListLength(iteratorVariable, reference);
               stack.add(new MathStackValue(iteratorVariable, (new Number()).Set(1), MathStackValue.MathOperation.SUBTRACT));
               break;
            case "addAll(Ljava/lang/Collection;)Z":
               if (pointerStackValue instanceof ArrayStackValue) {
                  throw new UnsupportedOperationException("Trying to append a collection to array.");
               }

               System.out.println("[WARNING] Using List.addAll(java.lang.Collection) is not stable yet");
               VariableControl.AppendList(reference, (Variable)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue());
               stack.add(new NumberStackValue(1));
               break;
            case "addAll(ILjava/lang/Collection;)Z":
               if (pointerStackValue instanceof ArrayStackValue) {
                  throw new UnsupportedOperationException("Trying to insert a collection on array.");
               }

               throw new IllegalStateException("Inserting a collection at index is not supported.");
            case "toArray()[Ljava/lang/Object;":
               stack.add(new ArrayStackValue(reference, CompilerAddons.getTempVariable()));
               break;
            case "clear()V":
               if (pointerStackValue instanceof ArrayStackValue) {
                  throw new UnsupportedOperationException("Trying to clear an array.");
               }

               VariableControl.CreateList(reference, (new Text()).Set("\u0000r"));
               break;
            case "indexOf(Ljava/lang/Object;)I":
               CodeValue value = ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
               variable = CompilerAddons.getTempVariable();
               VariableControl.GetValueIndex(variable, reference, value, Tags.SearchOrder.ASCENDING__FIRST_INDEX_);
               stack.add(new MathStackValue(variable, (new Number()).Set(1), MathStackValue.MathOperation.SUBTRACT));
               break;
            case "get(I)Ljava/lang/Object;":
               index = NumberMath.add((INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), (new Number()).Set(2));
               variable = CompilerAddons.getTempVariable();
               VariableControl.GetListValue(variable, reference, index);
               nextInsn = (InstructionData)CompilerAddons.getInstructionDataList().get(CompilerAddons.getInstructionIndex() + 1);
               getValueDescriptor = "Ljava/lang/Object;";
               if (nextInsn.instructionOpcode == 192) {
                  getValueDescriptor = ((TypeInstructionData)nextInsn).type;
                  if (!getValueDescriptor.startsWith("[")) {
                     getValueDescriptor = "L" + getValueDescriptor + ";";
                  }

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

               ReferenceUtils.incrementIfReference(getValueDescriptor, variable);
               stack.add(new VariableStackValue("Ljava/lang/Object;", variable.getName()));
               break;
            case "set(ILjava/lang/Object;)Ljava/lang/Object;":
               index = NumberMath.add((INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), (new Number()).Set(2));
               IStackValue value = (IStackValue)stack.remove(stack.size() - 1);
               nextInsn = (InstructionData)CompilerAddons.getInstructionDataList().get(CompilerAddons.getInstructionIndex() + 1);
               if (nextInsn.instructionOpcode != 87) {
                  getValueDescriptor = "Ljava/lang/Object;";
                  if (nextInsn.instructionOpcode == 192) {
                     getValueDescriptor = ((TypeInstructionData)nextInsn).type;
                     if (!getValueDescriptor.startsWith("[")) {
                        getValueDescriptor = "L" + getValueDescriptor + ";";
                     }

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

                  Variable variable = CompilerAddons.getTempVariable();
                  VariableControl.GetListValue(variable, reference, index);
                  stack.add(new VariableStackValue(getValueDescriptor, variable.getName()));
               } else {
                  References.decrementRefCount(NumberMath.listValue(reference, index));
                  stack.add(new NumberStackValue(0));
               }

               VariableControl.SetListValue(reference, index, value.getTransformedValue());
               ReferenceUtils.incrementIfReference(value.getDescriptor(), value.getTransformedValue());
               break;
            case "iterator()Ljava/util/Iterator;":
               iteratorVariable = CompilerAddons.getTempVariable();
               VariableControl.ListLength(iteratorVariable, reference);
               VariableControl.CreateList(iteratorVariable, pointerStackValue.getTransformedValue(), (new Number()).Set(2), iteratorVariable);
               stack.add(new VariableStackValue("Ljava/util/Iterator;", iteratorVariable.getName()));
         }

         return true;
      }
   }
}

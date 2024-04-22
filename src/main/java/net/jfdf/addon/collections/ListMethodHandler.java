package net.jfdf.addon.collections;

import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.instruction.InstructionData;
import net.jfdf.compiler.data.instruction.TypeInstructionData;
import net.jfdf.compiler.data.stack.*;
import net.jfdf.compiler.library.References;
import net.jfdf.compiler.util.ReferenceUtils;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.*;
import net.jfdf.transformer.util.NumberMath;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.List;

class ListMethodHandler {
    private ListMethodHandler() {}

    public static boolean handle(String owner, String name, String descriptor, List<IStackValue> stack) {
        if(owner.equals("java/util/List") || owner.equals("java/util/ArrayList") || owner.equals("java/util/Collection")) {
            int argsCount = Type.getArgumentTypes(descriptor).length;
            IStackValue pointerStackValue = stack.remove(stack.size() - (argsCount + 1));

            Variable reference;

            if(pointerStackValue instanceof ReferencedStackValue) {
                reference = ((ReferencedStackValue) pointerStackValue).getReference();
            } else {
                reference = new Variable("_jfdfR%var(" + ((Variable) pointerStackValue.getTransformedValue()).getName() + ")", Variable.Scope.NORMAL);
            }

            switch (name + descriptor) {
                case "add(Ljava/lang/Object;)Z" -> {
                    if(pointerStackValue instanceof ArrayStackValue) {
                        throw new UnsupportedOperationException("Trying to append value to array.");
                    }

                    IStackValue stackValue = stack.remove(stack.size() - 1);


                    VariableControl.AppendValue(
                            reference,
                            stackValue.getTransformedValue()
                    );

                    String valueDescriptor = stackValue.getDescriptor();
                    ReferenceUtils.incrementIfReference(valueDescriptor, stackValue.getTransformedValue());

                    stack.add(new NumberStackValue(1));
                }
                case "add(ILjava/lang/Object;)V" -> {
                    if(pointerStackValue instanceof ArrayStackValue) {
                        throw new UnsupportedOperationException("Trying to insert value on array.");
                    }

                    IStackValue stackValue = stack.remove(stack.size() - 1);

                    VariableControl.InsertListValue(
                            reference,
                            NumberMath.add((INumber) stack.remove(stack.size() - 1).getTransformedValue(), new Number().Set(2)),
                            stackValue.getTransformedValue()
                    );

                    String valueDescriptor = stackValue.getDescriptor();
                    ReferenceUtils.incrementIfReference(valueDescriptor, stackValue.getTransformedValue());
                }
                case "remove(Ljava/lang/Object;)Z" -> {
                    if(pointerStackValue instanceof ArrayStackValue) {
                        throw new UnsupportedOperationException("Trying to remove value from array.");
                    }

                    System.out.println("[WARNING] Using List.remove(java.lang.Object) is not stable yet");

                    VariableControl.RemoveListValue(
                            reference,
                            stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new NumberStackValue(1));
                }
                case "remove(I)Ljava/lang/Object;" -> {
                    if(pointerStackValue instanceof ArrayStackValue) {
                        throw new UnsupportedOperationException("Trying to remove value from array.");
                    }

                    INumber index = NumberMath.add((INumber) stack.remove(stack.size() - 1).getTransformedValue(), new Number().Set(2));

                    InstructionData nextInsn = CompilerAddons.getInstructionDataList().get(CompilerAddons.getInstructionIndex() + 1);
                    if(nextInsn.instructionOpcode != Opcodes.POP) {
                        String getValueDescriptor = "Ljava/lang/Object;";

                        if(nextInsn.instructionOpcode == Opcodes.CHECKCAST) {
                            getValueDescriptor = ((TypeInstructionData) nextInsn).type;

                            if(!getValueDescriptor.startsWith("[")) {
                                getValueDescriptor = "L" + getValueDescriptor + ";";
                            }

                            getValueDescriptor = switch (getValueDescriptor) {
                                case "Ljava/lang/Boolean;" -> "Z";
                                case "Ljava/lang/Byte;"    -> "B";
                                case "Ljava/lang/Short;"   -> "S";
                                case "Ljava/lang/Integer;" -> "I";
                                case "Ljava/lang/Long;"    -> "J";

                                case "Ljava/lang/Float;"   -> "F";
                                case "Ljava/lang/Double;"  -> "D";

                                default -> getValueDescriptor;
                            };
                        }

                        Variable variable = CompilerAddons.getTempVariable();

                        VariableControl.GetListValue(
                                variable,
                                reference,
                                index
                        );

                        stack.add(new VariableStackValue(getValueDescriptor, variable.getName()));
                    } else {
                        References.decrementRefCount(NumberMath.listValue(reference, index));

                        // This would go away with POP
                        stack.add(new NumberStackValue(0));
                    }

                    VariableControl.RemoveListIndex(
                            reference,
                            index
                    );
                }
                case "size()I" -> {
                    Variable variable = CompilerAddons.getTempVariable();

                    VariableControl.ListLength(
                            variable,
                            reference
                    );

                    stack.add(new MathStackValue(variable, new Number().Set(1), MathStackValue.MathOperation.SUBTRACT));
                }
                case "addAll(Ljava/lang/Collection;)Z" -> {
                    if(pointerStackValue instanceof ArrayStackValue) {
                        throw new UnsupportedOperationException("Trying to append a collection to array.");
                    }

                    System.out.println("[WARNING] Using List.addAll(java.lang.Collection) is not stable yet");

                    VariableControl.AppendList(
                            reference,
                            (Variable) stack.get(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new NumberStackValue(1));
                }
                case "addAll(ILjava/lang/Collection;)Z" -> {
                    if(pointerStackValue instanceof ArrayStackValue) {
                        throw new UnsupportedOperationException("Trying to insert a collection on array.");
                    }

                    throw new IllegalStateException("Inserting a collection at index is not supported.");
                }
                case "toArray()[Ljava/lang/Object;" ->
                        stack.add(
                                new ArrayStackValue(
                                        reference,
                                        CompilerAddons.getTempVariable()
                                )
                        );
                case "clear()V" -> {
                    if(pointerStackValue instanceof ArrayStackValue) {
                        throw new UnsupportedOperationException("Trying to clear an array.");
                    }

                    VariableControl.CreateList(
                            reference,
                            new Text().Set("\0r")
                    );
                }
                case "indexOf(Ljava/lang/Object;)I" -> {
                    CodeValue value = stack.remove(stack.size() - 1).getTransformedValue();
                    Variable variable = CompilerAddons.getTempVariable();

                    VariableControl.GetValueIndex(
                            variable,
                            reference,
                            value,
                            Tags.SearchOrder.ASCENDING__FIRST_INDEX_
                    );

                    stack.add(new MathStackValue(variable, new Number().Set(1), MathStackValue.MathOperation.SUBTRACT));
                }
                case "get(I)Ljava/lang/Object;" -> {
                    INumber index = NumberMath.add((INumber) stack.remove(stack.size() - 1).getTransformedValue(), new Number().Set(2));
                    Variable variable = CompilerAddons.getTempVariable();

                    VariableControl.GetListValue(
                            variable,
                            reference,
                            index
                    );

                    InstructionData nextInsn = CompilerAddons.getInstructionDataList().get(CompilerAddons.getInstructionIndex() + 1);
                    String getValueDescriptor = "Ljava/lang/Object;";

                    if(nextInsn.instructionOpcode == Opcodes.CHECKCAST) {
                        getValueDescriptor = ((TypeInstructionData) nextInsn).type;

                        if(!getValueDescriptor.startsWith("[")) {
                            getValueDescriptor = "L" + getValueDescriptor + ";";
                        }

                        getValueDescriptor = switch (getValueDescriptor) {
                            case "Ljava/lang/Boolean;" -> "Z";
                            case "Ljava/lang/Byte;"    -> "B";
                            case "Ljava/lang/Short;"   -> "S";
                            case "Ljava/lang/Integer;" -> "I";
                            case "Ljava/lang/Long;"    -> "J";

                            case "Ljava/lang/Float;"   -> "F";
                            case "Ljava/lang/Double;"  -> "D";

                            default -> getValueDescriptor;
                        };
                    }

                    ReferenceUtils.incrementIfReference(getValueDescriptor, variable);
                    stack.add(new VariableStackValue("Ljava/lang/Object;", variable.getName()));
                }
                case "set(ILjava/lang/Object;)Ljava/lang/Object;" -> {
                    INumber index = NumberMath.add((INumber) stack.remove(stack.size() - 1).getTransformedValue(), new Number().Set(2));
                    IStackValue value = stack.remove(stack.size() - 1);

                    InstructionData nextInsn = CompilerAddons.getInstructionDataList().get(CompilerAddons.getInstructionIndex() + 1);
                    if(nextInsn.instructionOpcode != Opcodes.POP) {
                        String getValueDescriptor = "Ljava/lang/Object;";

                        if(nextInsn.instructionOpcode == Opcodes.CHECKCAST) {
                            getValueDescriptor = ((TypeInstructionData) nextInsn).type;

                            if(!getValueDescriptor.startsWith("[")) {
                                getValueDescriptor = "L" + getValueDescriptor + ";";
                            }

                            getValueDescriptor = switch (getValueDescriptor) {
                                case "Ljava/lang/Boolean;" -> "Z";
                                case "Ljava/lang/Byte;"    -> "B";
                                case "Ljava/lang/Short;"   -> "S";
                                case "Ljava/lang/Integer;" -> "I";
                                case "Ljava/lang/Long;"    -> "J";

                                case "Ljava/lang/Float;"   -> "F";
                                case "Ljava/lang/Double;"  -> "D";

                                default -> getValueDescriptor;
                            };
                        }

                        Variable variable = CompilerAddons.getTempVariable();

                        VariableControl.GetListValue(
                                variable,
                                reference,
                                index
                        );

                        stack.add(new VariableStackValue(getValueDescriptor, variable.getName()));
                    } else {
                        References.decrementRefCount(NumberMath.listValue(reference, index));

                        // This would go away with POP
                        stack.add(new NumberStackValue(0));
                    }

                    VariableControl.SetListValue(
                            reference,
                            index,
                            value.getTransformedValue()
                    );

                    ReferenceUtils.incrementIfReference(value.getDescriptor(), value.getTransformedValue());
                }
                case "iterator()Ljava/util/Iterator;" -> {
                    Variable iteratorVariable = CompilerAddons.getTempVariable();

                    VariableControl.ListLength(iteratorVariable, reference);
                    VariableControl.CreateList(iteratorVariable, pointerStackValue.getTransformedValue(), new Number().Set(2), iteratorVariable);

                    stack.add(new VariableStackValue("Ljava/util/Iterator;", iteratorVariable.getName()));
                }
            }

            return true;
        }

        return false;
    }
}

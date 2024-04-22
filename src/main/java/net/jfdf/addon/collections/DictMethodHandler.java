package net.jfdf.addon.collections;

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
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.*;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.List;

class DictMethodHandler {
    private DictMethodHandler() {}

    public static boolean handle(String owner, String name, String descriptor, List<IStackValue> stack) {
        if(!owner.equals("java/util/Map")
                && !owner.equals("java/util/HashMap")
                && !owner.equals("java/util/LinkedHashMap")) return false;

        int argsCount = Type.getArgumentTypes(descriptor).length;
        IStackValue pointerStackValue = stack.remove(stack.size() - argsCount - 1);
        Variable reference;

        if(pointerStackValue instanceof ReferencedStackValue) {
            reference = ((ReferencedStackValue) pointerStackValue).getReference();
        } else {
            Variable pointer = (Variable) pointerStackValue.getTransformedValue();

            reference = new Variable("_jfdfR%var(" + pointer.getName() + ")", Variable.Scope.NORMAL);
        }

        switch (name + descriptor) {
            case "putAll(Ljava/util/Map;)V" -> putAll(reference, stack);
            case "put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;" -> put(reference, stack);
            case "get(Ljava/lang/Object;)Ljava/lang/Object;" -> get(reference, stack);

            case "remove(Ljava/lang/Object;)Ljava/lang/Object;" -> remove1(reference, stack);
            case "remove(Ljava/lang/Object;Ljava/lang/Object;)Z" -> remove2(reference, stack);

            case "keySet()Ljava/util/Set;" -> keySet(reference, stack);
            case "values()Ljava/util/Collection;" -> values(reference, stack);

            case "containsKey(Ljava/lang/Object;)Z" -> containsKey(reference, stack);
            case "clear()V" -> VariableControl.ClearDict(reference);
            case "size()I" -> size(reference, stack);

            default -> throw new IllegalStateException("Unsupported Map method: " + name + descriptor);
        }

        return true;
    }

    private static void putAll(Variable reference, List<IStackValue> stack) {
        IStackValue dictionaryStackValue = stack.remove(stack.size() - 1);
        Variable dictionaryReference;

        if(dictionaryStackValue instanceof ReferencedStackValue) {
            dictionaryReference = ((ReferencedStackValue) dictionaryStackValue).getReference();
        } else {
            Variable dictionaryPointer = (Variable) dictionaryStackValue.getTransformedValue();

            dictionaryReference = new Variable("_jfdfR%var(" + dictionaryPointer.getName() + ")", Variable.Scope.NORMAL);
        }

        VariableControl.AppendDict(reference, dictionaryReference);
    }

    private static void put(Variable reference, List<IStackValue> stack) {
        CodeValue key = stack.remove(stack.size() - 2).getTransformedValue();

        IStackValue valueStackValue = stack.remove(stack.size() - 1);
        CodeValue value = valueStackValue.getTransformedValue();

        if(!(key instanceof IText)) {
            throw new IllegalStateException("Maps don't support non-text keys !");
        }

        Variable returnValue = CompilerAddons.getTempVariable();
        VariableControl.GetDictValue(returnValue, reference, (IText) key);
        References.decrementRefCount(returnValue);

        ReferenceUtils.incrementIfReference(valueStackValue.getDescriptor(), value);

        VariableControl.SetDictValue(reference, (IText) key, value);
        stack.add(new VariableStackValue("Ljava/lang/Object;", returnValue.getName()));
    }

    private static void get(Variable reference, List<IStackValue> stack) {
        CodeValue key = stack.remove(stack.size() - 1).getTransformedValue();

        if(!(key instanceof IText)) {
            throw new IllegalStateException("Maps don't support non-text keys !");
        }

        Variable returnValue = CompilerAddons.getTempVariable();
        VariableControl.GetDictValue(returnValue, reference, (IText) key);

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

        ReferenceUtils.incrementIfReference(getValueDescriptor, returnValue);
        stack.add(new VariableStackValue("Ljava/lang/Object;", returnValue.getName()));
    }

    private static void remove1(Variable reference, List<IStackValue> stack) {
        CodeValue key = stack.remove(stack.size() - 1).getTransformedValue();

        if(!(key instanceof IText)) {
            throw new IllegalStateException("Maps don't support non-text keys !");
        }

        InstructionData nextInstruction = CompilerAddons.getInstructionDataList().get(CompilerAddons.getInstructionIndex() + 1);

        Variable returnValue = CompilerAddons.getTempVariable();
        VariableControl.GetDictValue(returnValue, reference, (IText) key);

        if(nextInstruction.instructionOpcode != Opcodes.POP) {
            stack.add(new VariableStackValue("Ljava/lang/Object;", returnValue.getName()));
        } else {
            References.decrementRefCount(returnValue);
            stack.add(new NumberStackValue());
        }

        VariableControl.RemoveDictEntry(reference, (IText) key);
    }

    private static void remove2(Variable reference, List<IStackValue> stack) {
        CodeValue key = stack.remove(stack.size() - 2).getTransformedValue();
        CodeValue value = stack.remove(stack.size() - 1).getTransformedValue();

        if(!(key instanceof IText)) {
            throw new IllegalStateException("Maps don't support non-text keys !");
        }

        InstructionData nextInstruction = CompilerAddons.getInstructionDataList().get(CompilerAddons.getInstructionIndex() + 1);

        if(nextInstruction.instructionOpcode != Opcodes.POP) {
            Variable returnValue = CompilerAddons.getTempVariable();
            VariableControl.Set(returnValue, new Number().Set(0));

            If.Variable.DictHasKey(reference, (IText) key, false);
                Variable removeValue = CompilerAddons.getTempVariable();

                VariableControl.GetDictValue(removeValue, reference, (IText) key);
                References.decrementRefCount(removeValue);

                VariableControl.Set(returnValue, new Number().Set(1));
            If.End();

            stack.add(new VariableStackValue("Z", returnValue.getName()));
        } else {
            If.Variable.DictHasKey(reference, (IText) key, false);
                Variable removeValue = CompilerAddons.getTempVariable();

                VariableControl.GetDictValue(removeValue, reference, (IText) key);
                References.decrementRefCount(removeValue);
            If.End();

            stack.add(new NumberStackValue());
        }

        VariableControl.RemoveDictEntry(reference, (IText) key, value);
    }

    private static void keySet(Variable reference, List<IStackValue> stack) {
        ReferencedStackValue returnValue = new ListStackValue(CompilerAddons.getTempVariable());
        VariableControl.GetDictKeys(returnValue.getReference(), reference);
        VariableControl.InsertListValue(returnValue.getReference(), new Number().Set(1), new Text().Set("\0r"));

        stack.add(returnValue);
    }

    private static void values(Variable reference, List<IStackValue> stack) {
        ReferencedStackValue returnValue = new ListStackValue(CompilerAddons.getTempVariable());
        VariableControl.GetDictValues(returnValue.getReference(), reference);
        VariableControl.InsertListValue(returnValue.getReference(), new Number().Set(1), new Text().Set("\0r"));

        stack.add(returnValue);
    }

    private static void containsKey(Variable reference, List<IStackValue> stack) {
        CodeValue key = stack.remove(stack.size() - 1).getTransformedValue();

        if(!(key instanceof IText)) {
            throw new IllegalStateException("Maps don't support non-text keys !");
        }
        
        Variable returnValue = CompilerAddons.getTempVariable();
        VariableControl.Set(returnValue, new Number().Set(0));

        If.Variable.DictHasKey(reference, (IText) key, false);
            VariableControl.Set(returnValue, new Number().Set(1));
        If.End();

        stack.add(new VariableStackValue("Z", returnValue.getName()));
    }

    private static void size(Variable reference, List<IStackValue> stack) {
        Variable returnValue = CompilerAddons.getTempVariable();
        VariableControl.GetDictSize(returnValue, reference);

        stack.add(new VariableStackValue("I", returnValue.getName()));
    }
}

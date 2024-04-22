package net.jfdf.compiler.addon;

import net.jfdf.compiler.data.instruction.InstructionData;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.values.Variable;
import org.objectweb.asm.Handle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompilerAddons {
    private static final List<ICompilerAddon> addons = new ArrayList<>();
    private static TempVariableCallback tempVariableCallback;
    private static List<InstructionData> instructionDataList;
    private static int instructionIndex = -1;

    public static void registerAddon(ICompilerAddon addon) {
        addons.add(addon);
    }

    public static void unregisterAddon(ICompilerAddon addon) {
        addons.remove(addon);
    }

    public static boolean publishInitClassEvent(String type, List<IStackValue> stack) {
        for (ICompilerAddon addon : addons) {
            if(addon.onInitClass(type, stack)) return true;
        }

        return false;
    }

    public static boolean publishInvokeConstructorEvent(String type, String descriptor, List<IStackValue> stack) {
        for (ICompilerAddon addon : addons) {
            if(addon.onInvokeConstructor(type, descriptor, stack)) return true;
        }

        return false;
    }

    public static boolean publishInvokeDynamicEvent(String name, String descriptor, Handle methodHandle, Object[] methodArgs, List<IStackValue> stack) {
        for (ICompilerAddon addon : addons) {
            if(addon.onInvokeDynamic(name, descriptor, methodHandle, methodArgs, stack)) return true;
        }

        return false;
    }

    public static boolean publishInvokeMemberEvent(String owner, String name, String descriptor, List<IStackValue> stack) {
        for (ICompilerAddon addon : addons) {
            if(addon.onInvokeMember(owner, name, descriptor, stack)) return true;
        }

        return false;
    }

    public static boolean publishInvokeStaticEvent(String owner, String name, String descriptor, List<IStackValue> stack) {
        for (ICompilerAddon addon : addons) {
            if(addon.onInvokeStatic(owner, name, descriptor, stack)) return true;
        }

        return false;
    }

    public static boolean publishGetFieldEvent(boolean isStatic, String owner, String name, String descriptor, List<IStackValue> stack) {
        for (ICompilerAddon addon : addons) {
            if(addon.onGetField(isStatic, owner, name, descriptor, stack)) return true;
        }

        return false;
    }

    public static IfHandler publishIfEvent(String defaultType, boolean invert, List<IStackValue> stack) {
        for (ICompilerAddon addon : addons) {
            IfHandler ifHandler = addon.onIf(defaultType, invert, stack);
            if(ifHandler != null) return ifHandler;
        }

        return null;
    }

    public static void setTempVariableCallback(TempVariableCallback callback) {
        tempVariableCallback = callback;
    }

    public static void setInstructionDataList(List<InstructionData> list) {
        if(list == null) {
            instructionDataList = null;

            return;
        }

        instructionDataList = Collections.unmodifiableList(list);
    }

    public static void setInstructionIndex(int index) {
        instructionIndex = index;
    }

    public static Variable getTempVariable() {
        return tempVariableCallback.getTempVariable();
    }

    public static List<InstructionData> getInstructionDataList() {
        return instructionDataList;
    }

    public static int getInstructionIndex() {
        return instructionIndex;
    }

    @FunctionalInterface
    public interface TempVariableCallback {
        Variable getTempVariable();
    }
}

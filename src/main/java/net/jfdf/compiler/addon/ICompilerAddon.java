package net.jfdf.compiler.addon;

import net.jfdf.compiler.data.stack.IStackValue;
import org.objectweb.asm.Handle;

import java.util.List;

public interface ICompilerAddon {
    default boolean onInitClass(String type, List<IStackValue> stack) {
        return false;
    }

    default boolean onInvokeConstructor(String type, String descriptor, List<IStackValue> stack) {
        return false;
    }

    default boolean onInvokeDynamic(String name, String descriptor, Handle methodHandle, Object[] methodArgs, List<IStackValue> stack) {
        return false;
    }

    default boolean onInvokeMember(String owner, String name, String descriptor, List<IStackValue> stack) {
        return false;
    }

    default boolean onInvokeStatic(String owner, String name, String descriptor, List<IStackValue> stack) {
        return false;
    }

    default boolean onGetField(boolean isStatic, String owner, String name, String descriptor, List<IStackValue> stack) {
        return false;
    }

    default IfHandler onIf(String defaultType, boolean invert, List<IStackValue> stack) {
        return null;
    }
}

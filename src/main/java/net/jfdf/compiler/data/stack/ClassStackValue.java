package net.jfdf.compiler.data.stack;

import net.jfdf.jfdf.values.CodeValue;

public class ClassStackValue implements IStackValue {
    private final Class<?> clazz;

    public ClassStackValue(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public CodeValue getTransformedValue() {
        throw new IllegalStateException("Trying to access Class<?> stack value.");
    }

    @Override
    public String getDescriptor() {
        return "Ljava/lang/Class;";
    }

    public Class<?> getClazz() {
        return clazz;
    }
}

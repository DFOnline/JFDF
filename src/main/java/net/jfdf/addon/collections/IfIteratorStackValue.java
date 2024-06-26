package net.jfdf.addon.collections;

import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Variable;

class IfIteratorStackValue implements IStackValue {
    private final Variable iterator;

    public IfIteratorStackValue(Variable iterator) {
        this.iterator = iterator;
    }

    @Override
    public CodeValue getTransformedValue() {
        throw new IllegalStateException("Getting transformed value is not supported");
    }

    @Override
    public String getDescriptor() {
        return null;
    }

    public Variable getIterator() {
        return iterator;
    }
}

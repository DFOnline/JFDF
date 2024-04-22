package net.jfdf.addon.string;

import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.values.CodeValue;

class IfStringStackValue implements IStackValue {
    private final IfType ifType;
    private final IStackValue[] stackValues;

    public IfStringStackValue(IfType ifType, IStackValue[] stackValues) {
        this.ifType = ifType;
        this.stackValues = stackValues;
    }

    @Override
    public CodeValue getTransformedValue() {
        throw new IllegalStateException("Getting transformed value is not supported");
    }

    @Override
    public String getDescriptor() {
        return null;
    }

    public IfType getIfType() {
        return ifType;
    }

    public IStackValue[] getStackValues() {
        return stackValues;
    }

    public enum IfType {
        EQUALS_IGNORE_CASE,
        EQUALS_REGEX,
        CONTAINS,
        STARTS_WITH,
        ENDS_WITH
    }
}

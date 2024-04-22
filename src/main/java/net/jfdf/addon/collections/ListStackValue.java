package net.jfdf.addon.collections;

import net.jfdf.compiler.data.stack.ReferencedStackValue;
import net.jfdf.jfdf.values.Variable;

class ListStackValue extends ReferencedStackValue {
    public ListStackValue(Variable variable) {
        super(variable);
    }

    @Override
    public String getDescriptor() {
        return "Ljava/util/ArrayList;";
    }
}

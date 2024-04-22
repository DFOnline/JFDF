package net.jfdf.addon.collections;

import net.jfdf.compiler.data.stack.ReferencedStackValue;
import net.jfdf.jfdf.values.Variable;

class DictStackValue extends ReferencedStackValue {
    public DictStackValue(Variable variable) {
        super(variable);
    }

    @Override
    public String getDescriptor() {
        return "Ljava/util/LinkedHashMap;";
    }
}

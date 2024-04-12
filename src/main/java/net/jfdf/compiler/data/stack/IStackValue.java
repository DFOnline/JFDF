package net.jfdf.compiler.data.stack;

import net.jfdf.jfdf.values.CodeValue;

public interface IStackValue {
   CodeValue getTransformedValue();

   String getDescriptor();
}

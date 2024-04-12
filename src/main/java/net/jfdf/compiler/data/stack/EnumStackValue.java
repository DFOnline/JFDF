package net.jfdf.compiler.data.stack;

import net.jfdf.jfdf.values.CodeValue;

public class EnumStackValue implements IStackValue {
   private final Object enumValue;

   public EnumStackValue(Object tag) {
      this.enumValue = tag;
   }

   public Object getEnumValue() {
      return this.enumValue;
   }

   public CodeValue getTransformedValue() {
      throw new IllegalStateException("Use TagStackValue.getEnumValue() instead.");
   }

   public String getDescriptor() {
      return null;
   }
}

package net.jfdf.compiler.data.stack;

import net.jfdf.jfdf.values.CodeValue;

public class ClassStackValue implements IStackValue {
   private final Class clazz;

   public ClassStackValue(Class clazz) {
      this.clazz = clazz;
   }

   public CodeValue getTransformedValue() {
      throw new IllegalStateException("Trying to access Class<?> stack value.");
   }

   public String getDescriptor() {
      return "Ljava/lang/Class;";
   }

   public Class getClazz() {
      return this.clazz;
   }
}

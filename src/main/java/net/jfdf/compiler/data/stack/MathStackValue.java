package net.jfdf.compiler.data.stack;

import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Number;
import net.jfdf.transformer.util.NumberMath;

public class MathStackValue implements IStackValue {
   private final Number value;

   public MathStackValue(INumber value1, INumber value2, MathOperation operation) {
      Number var10001;
      switch (operation) {
         case ADD:
            var10001 = NumberMath.add(value1, value2);
            break;
         case SUBTRACT:
            var10001 = NumberMath.subtract(value1, value2);
            break;
         case MULTIPLY:
            var10001 = NumberMath.multiply(value1, value2);
            break;
         case DIVIDE_INTEGER:
            var10001 = NumberMath.divideInteger(value1, value2);
            break;
         case DIVIDE:
            var10001 = NumberMath.divide(value1, value2);
            break;
         case REMAINDER:
            var10001 = NumberMath.remainder(value1, value2);
            break;
         default:
            throw new IllegalStateException("Unknown operation: " + operation);
      }

      this.value = var10001;
   }

   public CodeValue getTransformedValue() {
      return this.value;
   }

   public String getDescriptor() {
      return "J";
   }

   public static enum MathOperation {
      ADD,
      SUBTRACT,
      MULTIPLY,
      DIVIDE_INTEGER,
      DIVIDE,
      REMAINDER;
   }
}

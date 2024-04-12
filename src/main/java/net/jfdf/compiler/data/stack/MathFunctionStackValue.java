package net.jfdf.compiler.data.stack;

import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Variable;
import net.jfdf.transformer.util.NumberMath;

public class MathFunctionStackValue implements IStackValue {
   private final Number value;

   public MathFunctionStackValue(INumber value, MathFunction operation) {
      Number var10001;
      switch (operation) {
         case NEGATIVE:
            var10001 = NumberMath.negative(value);
            break;
         case ROUND:
            var10001 = NumberMath.round(value);
            break;
         default:
            throw new IllegalStateException("Unexpected function: " + operation + ".");
      }

      this.value = var10001;
   }

   public MathFunctionStackValue(INumber value, MathFunction operation, String methodName, int blockOperationIndex) {
      Number var10001;
      Variable castVariable;
      switch (operation) {
         case CAST_TO_BYTE:
            castVariable = new Variable("_jco>" + methodName + ">" + blockOperationIndex, Variable.Scope.LOCAL);
            VariableControl.Bitwise(castVariable, NumberMath.add(value, (new Number()).Set(128)), (new Number()).Set(255), Tags.Operator.AND);
            var10001 = Number.Subtract(Number.AsVariable(castVariable), (new Number()).Set(128));
            break;
         case CAST_TO_CHAR:
            castVariable = new Variable("_jco>" + methodName + ">" + blockOperationIndex, Variable.Scope.LOCAL);
            VariableControl.Bitwise(castVariable, value, (new Number()).Set(65535), Tags.Operator.AND);
            var10001 = Number.AsVariable(castVariable);
            break;
         case CAST_TO_SHORT:
            castVariable = new Variable("_jco>" + methodName + ">" + blockOperationIndex, Variable.Scope.LOCAL);
            VariableControl.Bitwise(castVariable, NumberMath.add(value, (new Number()).Set(32768)), (new Number()).Set(65535), Tags.Operator.AND);
            var10001 = Number.Subtract(Number.AsVariable(castVariable), (new Number()).Set(32768));
            break;
         default:
            throw new IllegalStateException("Unexpected function: " + operation + ".");
      }

      this.value = var10001;
   }

   public MathFunctionStackValue(INumber value1, INumber value2, MathFunction operation) {
      switch (operation) {
         case RANDOM:
            this.value = NumberMath.random(value1, value2);
            return;
         default:
            throw new IllegalStateException("Unexpected function: " + operation + ".");
      }
   }

   public MathFunctionStackValue(Variable variable, INumber number, MathFunction operation) {
      switch (operation) {
         case LIST_VALUE:
            this.value = NumberMath.listValue(variable, number);
            return;
         default:
            throw new IllegalStateException("Unexpected function: " + operation + ".");
      }
   }

   public CodeValue getTransformedValue() {
      return this.value;
   }

   public String getDescriptor() {
      return "J";
   }

   public static enum MathFunction {
      NEGATIVE,
      ROUND,
      RANDOM,
      LIST_VALUE,
      CAST_TO_BYTE,
      CAST_TO_CHAR,
      CAST_TO_SHORT;
   }
}

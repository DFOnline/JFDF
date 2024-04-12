package net.jfdf.transformer;

import java.util.Random;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Variable;

/** @deprecated */
@Deprecated(
   forRemoval = true
)
public class JFDFRandom {
   private static Random random = new Random();

   public Variable generateDecimalNumber() {
      return this.generateDecimalNumber(new Variable("tmp_" + random.nextInt(), Variable.Scope.LOCAL));
   }

   public Variable generateIntegerNumber() {
      return this.generateIntegerNumber(new Variable("tmp_" + random.nextInt(), Variable.Scope.LOCAL));
   }

   public Variable generateIntegerNumber(Number maxInt) {
      return this.generateIntegerNumber(maxInt, new Variable("tmp_" + random.nextInt(), Variable.Scope.LOCAL));
   }

   public Variable generateDecimalNumber(Variable var) {
      VariableControl.RandomNumber(var, (new Number()).Set(0.0F), (new Number()).Set(1.0F), Tags.RoundingMode.DECIMAL_NUMBER);
      return var;
   }

   public Variable generateIntegerNumber(Variable var) {
      VariableControl.RandomNumber(var, (new Number()).Set(-9223372036854775L), (new Number()).Set(9223372036854775L), Tags.RoundingMode.WHOLE_NUMBER);
      return var;
   }

   public Variable generateIntegerNumber(Number maxInt, Variable var) {
      VariableControl.RandomNumber(var, (new Number()).Set(0.0F), maxInt, Tags.RoundingMode.WHOLE_NUMBER);
      return var;
   }
}

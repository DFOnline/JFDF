package net.jfdf.jfdf.values;

import java.text.NumberFormat;
import java.util.Locale;

public class Number implements INumber {
   private static final NumberFormat usNumberFormat;
   private String value;

   public static Number New() {
      return new Number();
   }

   public Number Set(int value) {
      this.value = usNumberFormat.format((long)Math.abs(value)).replace(",", "");
      return value < 0 ? Negative(this) : this;
   }

   public Number Set(long value) {
      this.value = usNumberFormat.format(Math.abs(value)).replace(",", "");
      return value < 0L ? Negative(this) : this;
   }

   public Number Set(float value) {
      this.value = String.format(Locale.US, "%.0f", Math.abs(value));
      return value < 0.0F ? Negative(this) : this;
   }

   public Number Set(double value) {
      this.value = String.format(Locale.US, "%.0f", Math.abs(value));
      return value < 0.0 ? Negative(this) : this;
   }

   public Number SetToString(String value) {
      this.value = value;
      return this;
   }

   public static Number Negative(Number number) {
      return (new Number()).setValue("%math(0 - " + number.getValue() + ")");
   }

   public static Number Add(Number number1, Number number2) {
      Number var10000 = new Number();
      String var10001 = number1.getValue();
      return var10000.setValue("%math(" + var10001 + " + " + number2.getValue() + ")");
   }

   public static Number Subtract(Number number1, Number number2) {
      Number var10000 = new Number();
      String var10001 = number1.getValue();
      return var10000.setValue("%math(" + var10001 + " - " + number2.getValue() + ")");
   }

   public static Number Multiplication(Number number1, Number number2) {
      Number var10000 = new Number();
      String var10001 = number1.getValue();
      return var10000.setValue("%math(" + var10001 + " * " + number2.getValue() + ")");
   }

   public static Number Division(Number number1, Number number2) {
      Number var10000 = new Number();
      String var10001 = number1.getValue();
      return var10000.setValue("%math(" + var10001 + " / " + number2.getValue() + ")");
   }

   public static Number Remainder(Number number1, Number number2) {
      Number var10000 = new Number();
      String var10001 = number1.getValue();
      return var10000.setValue("%math(" + var10001 + " % " + number2.getValue() + ")");
   }

   public static Number Random(Number number1, Number number2) {
      Number var10000 = new Number();
      String var10001 = number1.getValue();
      return var10000.setValue("%random(" + var10001 + ", " + number2.getValue() + ")");
   }

   public static Number Round(Number number) {
      return (new Number()).setValue("%round(" + number.getValue() + ")");
   }

   public static Number AsVariable(Variable variable) {
      return (new Number()).setValue("%var(" + variable.getName() + ")");
   }

   public static Number AsListValue(Variable list, Number index) {
      Number var10000 = new Number();
      String var10001 = list.getName();
      return var10000.setValue("%index(" + var10001 + ", " + index.getValue() + ")");
   }

   private Number setValue(String value) {
      this.value = value;
      return this;
   }

   public String getValue() {
      return this.value;
   }

   public String toString() {
      return "Number{value='" + this.value + "'}";
   }

   public String asJSON() {
      return "{\"id\":\"num\",\"data\":{\"name\":\"" + this.value + "\"}}";
   }

   public boolean isConstant() {
      return !this.value.contains("%");
   }

   static {
      usNumberFormat = NumberFormat.getInstance(Locale.US);
   }
}

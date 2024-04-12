package net.jfdf.compiler.util;

import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Variable;

public class ValueUtils {
   public static Variable asVariable(Object variable) {
      throw new IllegalStateException("Trying to call ValueUtils.asVariable(...) with a code running on JVM.");
   }

   public static Variable asReference(Object variable) {
      throw new IllegalStateException("Trying to call ValueUtils.asReference(...) with a code running on JVM.");
   }

   public static Variable asVariable(boolean variable) {
      return asVariable(variable);
   }

   public static Variable asVariable(byte variable) {
      return asVariable(variable);
   }

   public static Variable asVariable(short variable) {
      return asVariable(variable);
   }

   public static Variable asVariable(int variable) {
      return asVariable(variable);
   }

   public static Variable asVariable(long variable) {
      return asVariable(variable);
   }

   public static Variable asVariable(float variable) {
      return asVariable(variable);
   }

   public static Variable asVariable(double variable) {
      return asVariable(variable);
   }

   public static Variable asVariable(char variable) {
      return asVariable(variable);
   }

   public static IText asText(String text) {
      throw new IllegalStateException("Trying to call ValueUtils.asText(...) with a code running on JVM.");
   }

   public static INumber asNumber(int number) {
      throw new IllegalStateException("Trying to call ValueUtils.asNumber(...) with a code running on JVM.");
   }

   public static INumber asNumber(long number) {
      throw new IllegalStateException("Trying to call ValueUtils.asNumber(...) with a code running on JVM.");
   }

   public static INumber asNumber(float number) {
      throw new IllegalStateException("Trying to call ValueUtils.asNumber(...) with a code running on JVM.");
   }

   public static INumber asNumber(double number) {
      throw new IllegalStateException("Trying to call ValueUtils.asNumber(...) with a code running on JVM.");
   }
}

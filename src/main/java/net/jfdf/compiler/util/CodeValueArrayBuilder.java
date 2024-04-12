package net.jfdf.compiler.util;

import net.jfdf.jfdf.values.CodeValue;

public class CodeValueArrayBuilder {
   private CodeValueArrayBuilder() {
   }

   public static CodeValueArrayBuilder start(int length) {
      throw new IllegalStateException("CodeValueArrayBuilder couldn't be used inside code executed by JVM.");
   }

   public CodeValueArrayBuilder next(CodeValue codeValue) {
      throw new IllegalStateException("CodeValueArrayBuilder couldn't be used inside code executed by JVM.");
   }

   public CodeValue[] getArray() {
      throw new IllegalStateException("CodeValueArrayBuilder couldn't be used inside code executed by JVM.");
   }
}

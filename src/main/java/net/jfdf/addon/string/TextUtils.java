package net.jfdf.addon.string;

import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;

class TextUtils {
   public static String toTextCode(CodeValue codeValue) {
      if (codeValue instanceof Variable) {
         return "%var(" + ((Variable)codeValue).getName() + ")";
      } else if (codeValue instanceof Text) {
         return ((Text)codeValue).Get();
      } else {
         throw new UnsupportedOperationException("Couldn't convert " + codeValue.getClass() + " to text code.");
      }
   }
}

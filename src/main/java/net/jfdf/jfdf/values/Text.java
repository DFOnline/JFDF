package net.jfdf.jfdf.values;

public class Text implements IText {
   private String value = "";

   public static Text New() {
      return new Text();
   }

   public Text Set(String value) {
      this.value = value;
      return this;
   }

   public String Get() {
      return new String(this.value);
   }

   public String toString() {
      return "Text{value=\"" + this.value + "\"}";
   }

   public String asJSON() {
      String var10000 = this.value.replace("\\", "\\\\");
      return "{\"id\":\"txt\",\"data\":{\"name\":\"" + var10000.replace("\"", "\\\"") + "\"}}";
   }

   public static Text AsVariable(Variable variable) {
      return (new Text()).Set("%var(" + variable.getName() + ")");
   }

   public static Text AsListValue(Variable list, Number index) {
      Text var10000 = new Text();
      String var10001 = list.getName();
      return var10000.Set("%index(" + var10001 + ", " + index.getValue() + ")");
   }
}

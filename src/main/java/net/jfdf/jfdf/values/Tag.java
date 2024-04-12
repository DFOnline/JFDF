package net.jfdf.jfdf.values;

public class Tag implements CodeValue {
   private final String name;
   private final String value;
   private String block;
   private String action;

   public Tag(String name, String value) {
      this.name = name;
      this.value = value;
   }

   public String toString() {
      return "Tag{name='" + this.name + "', value='" + this.value + "', block='" + this.block + "', action='" + this.action + "'}";
   }

   public String asJSON() {
      if (this.block != null && this.action != null) {
         return "{\"id\":\"bl_tag\",\"data\":{\"option\":\"" + this.value + "\",\"tag\":\"" + this.name + "\",\"action\":\"" + this.action + "\",\"block\":\"" + this.block + "\"}}";
      } else {
         throw new RuntimeException("No Such Block/Action !");
      }
   }

   public void setBlock(String block) {
      this.block = block;
   }

   public void setAction(String action) {
      this.action = action;
   }
}

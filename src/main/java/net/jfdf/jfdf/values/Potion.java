package net.jfdf.jfdf.values;

public class Potion implements IPotion {
   public String potionType;
   public int duration = 100;
   public int amplifier = 1;

   public Potion(String potionType) {
      this.potionType = potionType;
   }

   public Potion setDuration(int ticks) {
      this.duration = ticks;
      return this;
   }

   public Potion setAmplifier(int amplifier) {
      this.amplifier = amplifier;
      return this;
   }

   public String toString() {
      return "Potion{potionType='" + this.potionType + "', duration=" + this.duration + ", amplifier=" + this.amplifier + "}";
   }

   public String asJSON() {
      return "{\"id\":\"pot\",\"data\":{\"pot\":\"" + this.potionType + "\",\"dur\":" + this.duration + ",\"amp\":" + this.amplifier + "}}";
   }
}

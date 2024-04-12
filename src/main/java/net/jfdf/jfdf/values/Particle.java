package net.jfdf.jfdf.values;

public class Particle implements IParticle {
   private String type;

   public Particle(String type) {
      this.type = type;
   }

   public String toString() {
      return "Particle{type='" + this.type + "'}";
   }

   public String asJSON() {
      return "{\"id\":\"part\",\"data\":{\"particle\":\"" + this.type + "\"}}";
   }
}

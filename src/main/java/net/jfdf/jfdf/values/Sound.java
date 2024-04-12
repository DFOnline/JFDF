package net.jfdf.jfdf.values;

public class Sound implements ISound {
   public String name;
   public String variant = null;
   public float pitch = 1.0F;
   public float volume = 2.0F;

   public Sound(String name) {
      this.name = name;
   }

   public Sound(String name, String variant) {
      this.name = name;
      this.variant = variant;
   }

   public Sound setPitch(float pitch) {
      this.pitch = pitch;
      return this;
   }

   public Sound setVolume(float volume) {
      this.volume = volume;
      return this;
   }

   public String toString() {
      String var10000 = this.name;
      return "Sound{name='" + var10000 + "', variant='" + (this.variant == null ? "random" : this.variant) + "', pitch=" + this.pitch + ", volume=" + this.volume + "}";
   }

   public String asJSON() {
      String var10000 = this.name;
      return "{\"id\":\"snd\",\"data\":{\"sound\":\"" + var10000 + "\",\"pitch\":" + this.pitch + (this.variant != null ? ",\"variant\":\"" + this.variant + "\"" : "") + ",\"vol\":" + this.volume + "}}";
   }
}

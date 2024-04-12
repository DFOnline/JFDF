package net.jfdf.jfdf.values;

import java.util.Locale;

public class Location implements ILocation {
   public float x;
   public float y;
   public float z;
   private final double yaw;
   private final double pitch;
   private boolean isBlock;

   public Location(float x, float y) {
      this(x, y, 0.0F);
   }

   public Location(float x, float y, float z) {
      this(x, y, z, 0.0, 0.0);
   }

   public Location(float x, float y, float z, double yaw, double pitch) {
      this.isBlock = true;
      this.x = x;
      this.y = y;
      this.z = z;
      this.yaw = yaw;
      this.pitch = pitch;
   }

   public Location asBlock(boolean asBlock) {
      this.isBlock = asBlock;
      return this;
   }

   public String toString() {
      return "Location{x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", yaw=" + this.yaw + ", pitch=" + this.pitch + ", isBlock=" + this.isBlock + "}";
   }

   public String asJSON() {
      String var10000 = this.isBlock ? "true" : "false";
      return "{\"id\":\"loc\",\"data\":{\"isBlock\":" + var10000 + ",\"loc\":{\"x\":" + String.format(Locale.US, "%.0f", this.x) + ",\"y\":" + String.format(Locale.US, "%.0f", this.y) + ",\"z\":" + String.format(Locale.US, "%.0f", this.z) + ",\"yaw\":" + String.format(Locale.US, "%.0f", this.yaw) + ",\"pitch\":" + String.format(Locale.US, "%.0f", this.pitch) + "}}}";
   }
}

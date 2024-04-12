package net.jfdf.jfdf.values;

import java.util.Locale;

public class Vector implements IVector {
   public double x;
   public double y;
   public double z;

   public Vector(double x, double y) {
      this(x, y, 0.0);
   }

   public Vector(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public String toString() {
      return "Vector{x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
   }

   public String asJSON() {
      String var10000 = String.format(Locale.US, "%.0f", this.x);
      return "{\"id\":\"vec\",\"data\":{\"x\":" + var10000 + ",\"y\":" + String.format(Locale.US, "%.0f", this.y) + ",\"z\":" + String.format(Locale.US, "%.0f", this.z) + "}}";
   }
}

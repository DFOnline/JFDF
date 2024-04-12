package net.jfdf.addon.vector;

import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.Location;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Vector;

public final class Vector2dXZ {
   public Vector2dXZ(double x, double z) {
   }

   public Vector2dXZ add(Vector2dXZ vector) {
      return new Vector2dXZ(0.0, 0.0);
   }

   public Vector2dXZ subtract(Vector2dXZ vector) {
      return new Vector2dXZ(0.0, 0.0);
   }

   public Vector2dXZ multiply(double multiplier) {
      return new Vector2dXZ(0.0, 0.0);
   }

   public Vector2dXZ reflect(Vector2dXZ surface) {
      return new Vector2dXZ(0.0, 0.0);
   }

   public double dot(Vector2dXZ vector) {
      return 0.0;
   }

   public Vector2dXZ rotateAround(Tags.Axis axis, double angle) {
      return new Vector2dXZ(0.0, 0.0);
   }

   public Vector2dXZ rotateAroundDeg(Tags.Axis axis, double angle) {
      return new Vector2dXZ(0.0, 0.0);
   }

   public Vector2dXZ rotateAroundVec(Vector2dXZ vector, double angle) {
      return new Vector2dXZ(0.0, 0.0);
   }

   public Vector2dXZ rotateAroundVecDeg(Vector2dXZ vector, double angle) {
      return new Vector2dXZ(0.0, 0.0);
   }

   public Vector2dXZ setX(double x) {
      return new Vector2dXZ(0.0, 0.0);
   }

   public Vector2dXZ setZ(double z) {
      return new Vector2dXZ(0.0, 0.0);
   }

   public Vector2dXZ setXZ(double x, double z) {
      return new Vector2dXZ(0.0, 0.0);
   }

   public double getX() {
      return 0.0;
   }

   public double getZ() {
      return 0.0;
   }

   public Vector2dXZ setLength(double length) {
      return new Vector2dXZ(0.0, 0.0);
   }

   public double getLength() {
      return 0.0;
   }

   public double getLengthSquared() {
      return 0.0;
   }

   public Location shiftLocation(ILocation loc, double distance) {
      return new Location(0.0F, 0.0F, 0.0F);
   }

   public Vector asCodeValue() {
      return new Vector(0.0, 0.0, 0.0);
   }
}

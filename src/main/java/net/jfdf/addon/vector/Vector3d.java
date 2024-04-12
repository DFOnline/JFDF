package net.jfdf.addon.vector;

import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.Location;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Vector;

public final class Vector3d {
   public Vector3d(double x, double y, double z) {
   }

   public Vector3d(Vector2dXY vector) {
   }

   public Vector3d(Vector2dXZ vector) {
   }

   public Vector3d add(Vector3d vector) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public Vector3d subtract(Vector3d vector) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public Vector3d multiply(double multiplier) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public Vector3d reflect(Vector3d surface) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public Vector3d cross(Vector3d vector) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public double dot(Vector3d vector) {
      return 0.0;
   }

   public Vector3d rotateAround(Tags.Axis axis, double angle) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public Vector3d rotateAroundDeg(Tags.Axis axis, double angle) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public Vector3d rotateAroundVec(Vector3d vector, double angle) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public Vector3d rotateAroundVecDeg(Vector3d vector, double angle) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public Vector3d setX(double x) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public Vector3d setY(double y) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public Vector3d setZ(double z) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public Vector3d setXYZ(double x, double y, double z) {
      return new Vector3d(0.0, 0.0, 0.0);
   }

   public double getX() {
      return 0.0;
   }

   public double getY() {
      return 0.0;
   }

   public double getZ() {
      return 0.0;
   }

   public Vector3d setLength(double length) {
      return new Vector3d(0.0, 0.0, 0.0);
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

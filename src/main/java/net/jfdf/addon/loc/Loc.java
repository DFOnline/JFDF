package net.jfdf.addon.loc;

import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.IVector;
import net.jfdf.jfdf.values.Location;

public final class Loc {
   public Loc(ILocation location) {
   }

   public Loc(double x, double y, double z) {
   }

   public Loc(double x, double y, double z, float yaw, float pitch) {
   }

   public Loc add(Loc loc) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc subtract(Loc loc) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc setX(double x) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc setY(double y) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc setZ(double z) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc setYaw(float yaw) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc setPitch(float pitch) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc setDirection(IVector direction) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc setAllAxes(double x, double y, double z) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc setAllCoordinates(double x, double y, double z, float yaw, float pitch) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc shiftX(double x) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc shiftY(double y) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc shiftZ(double z) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc shiftYaw(float yaw) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc shiftPitch(float pitch) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc shiftAllAxes(double x, double y, double z) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc shiftTowards(Loc loc, double distance) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc shiftByVector(IVector vector) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc shiftInDirection(double distance, ShiftDirection direction) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc shiftInAllDirections(double forwardDistance, double upwardDistance, double sidewaysDistance) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc alignToLowerCorner(AlignAxes alignAxes, boolean removeRotation) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc alignToCenter(AlignAxes alignAxes, boolean removeRotation) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc faceTowards(Loc loc) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Loc faceAwayFrom(Loc loc) {
      return new Loc(0.0, 0.0, 0.0);
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

   public float getYaw() {
      return 0.0F;
   }

   public float getPitch() {
      return 0.0F;
   }

   public IVector getDirection() {
      return null;
   }

   public static Loc center(Loc[] locations) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public static Loc center(Loc loc1, Loc loc2) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public static Loc randomBetween(Loc loc1, Loc loc2) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public static Loc distance3d(Loc loc1, Loc loc2) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public static Loc distance2d(Loc loc1, Loc loc2) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public static Loc altitude(Loc loc1, Loc loc2) {
      return new Loc(0.0, 0.0, 0.0);
   }

   public Location asCodeValue() {
      return new Location(0.0F, 0.0F, 0.0F);
   }

   public static enum AlignAxes {
      ALL_AXES(2),
      X_AND_Z(4),
      Y_ONLY(6);

      private final int id;

      private AlignAxes(int id) {
         this.id = id;
      }

      public int getId() {
         return this.id;
      }
   }

   public static enum ShiftDirection {
      FORWARD(0),
      UPWARD(1),
      SIDEWAYS(2);

      private final int id;

      private ShiftDirection(int id) {
         this.id = id;
      }

      public int getId() {
         return this.id;
      }
   }
}

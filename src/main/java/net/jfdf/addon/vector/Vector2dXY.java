package net.jfdf.addon.vector;

import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.Location;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Vector;

public final class Vector2dXY {
    public Vector2dXY(double x, double y) {}

    public Vector2dXY add(Vector2dXY vector) { return new Vector2dXY(0, 0); }
    public Vector2dXY subtract(Vector2dXY vector) { return new Vector2dXY(0, 0); }
    public Vector2dXY multiply(double multiplier) { return new Vector2dXY(0, 0); }

    public Vector2dXY reflect(Vector2dXY surface) { return new Vector2dXY(0, 0); }
    public double dot(Vector2dXY vector) { return 0; }

    public Vector2dXY rotateAround(Tags.Axis axis, double angle) { return new Vector2dXY(0, 0); }
    public Vector2dXY rotateAroundDeg(Tags.Axis axis, double angle) { return new Vector2dXY(0, 0); }
    public Vector2dXY rotateAroundVec(Vector2dXY vector, double angle) { return new Vector2dXY(0, 0); }
    public Vector2dXY rotateAroundVecDeg(Vector2dXY vector, double angle) { return new Vector2dXY(0, 0); }

    public Vector2dXY setX(double x) { return new Vector2dXY(0, 0); }
    public Vector2dXY setY(double y) { return new Vector2dXY(0, 0); }
    public Vector2dXY setXY(double x, double y) { return new Vector2dXY(0, 0); }

    public double getX() { return 0; }
    public double getY() { return 0; }

    public Vector2dXY setLength(double length) { return new Vector2dXY(0, 0); }
    public double getLength() { return 0; }
    public double getLengthSquared() { return 0; }

    public Location shiftLocation(ILocation loc, double distance) { return new Location(0.0f, 0.0f, 0.0f); }

    public Vector asCodeValue() { return new Vector(0, 0, 0); }
}

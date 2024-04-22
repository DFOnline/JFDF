package net.jfdf.addon.loc;

import net.jfdf.addon.interaction.npe.villager.IVillagerVariant;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.data.stack.*;
import net.jfdf.compiler.util.ReferenceUtils;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.*;
import net.jfdf.transformer.util.NumberMath;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;

import java.util.List;

public class LocAddon implements ICompilerAddon {

    @Override
    public boolean onInitClass(String type, List<IStackValue> stack) {
        if(type.equals("net/jfdf/addon/loc/Loc")) {
            stack.add(new CodeStackValue("net/jfdf/jfdf/values/Location"));
            return true;
        }

        return false;
    }

    @Override
    public boolean onInvokeConstructor(String type, String descriptor, List<IStackValue> stack) {
        if(type.equals("net/jfdf/addon/loc/Loc")) {
            if(descriptor.equals("(DDD)V")) {
                ((CodeStackValue) stack.remove(stack.size() - 4))
                        .callConstructor("(FFF)V",
                                ((NumberStackValue) stack.remove(stack.size() - 3)).getJavaValue().floatValue(),
                                ((NumberStackValue) stack.remove(stack.size() - 2)).getJavaValue().floatValue(),
                                ((NumberStackValue) stack.remove(stack.size() - 1)).getJavaValue().floatValue()
                        );
            } else if(descriptor.equals("(DDDFF)V")) {
                ((CodeStackValue) stack.remove(stack.size() - 6))
                        .callConstructor("(FFFDD)V",
                                ((NumberStackValue) stack.remove(stack.size() - 5)).getJavaValue().floatValue(),
                                ((NumberStackValue) stack.remove(stack.size() - 4)).getJavaValue().floatValue(),
                                ((NumberStackValue) stack.remove(stack.size() - 3)).getJavaValue().floatValue(),
                                ((NumberStackValue) stack.remove(stack.size() - 2)).getJavaValue().doubleValue(),
                                ((NumberStackValue) stack.remove(stack.size() - 1)).getJavaValue().doubleValue()
                        );
            } else {
                stack.remove(stack.size() - 2); // Remove code value created by onInitClass and use provided one instead
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean onInvokeDynamic(String name, String descriptor, Handle methodHandle, Object[] methodArgs, List<IStackValue> stack) {
        return false;
    }

    @Override
    public boolean onInvokeMember(String owner, String name, String descriptor, List<IStackValue> stack) {
        if(owner.equals("net/jfdf/addon/loc/Loc")) {
            switch (name) {
                case "add" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.SetToProduct(
                            result,
                            stack.remove(stack.size() - 2).getTransformedValue(),
                            stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "subtract" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.SetToDifference(
                            result,
                            stack.remove(stack.size() - 2).getTransformedValue(),
                            stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "setX", "setY", "setZ", "setYaw", "setPitch" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.SetCoord(
                            result,
                            (ILocation) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            switch (name) {
                                case "setX" -> Tags.Coordinate.X;
                                case "setY" -> Tags.Coordinate.Y;
                                case "setZ" -> Tags.Coordinate.Z;
                                case "setYaw" -> Tags.Coordinate.YAW;
                                case "setPitch" -> Tags.Coordinate.PITCH;
                                default -> throw new IllegalStateException("Unknown coordinate for method: " + name);
                            },
                            Tags.CoordinateType.PLOT_COORDINATE
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "setDirection" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.Set(result, stack.remove(stack.size() - 2).getTransformedValue());
                    VariableControl.DirectionName(result, (IVector) stack.remove(stack.size() - 1).getTransformedValue());

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "setAllAxes" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.SetAllCoords(
                            result,
                            (ILocation) stack.remove(stack.size() - 4).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 3).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            null,
                            null,
                            Tags.CoordinateType.PLOT_COORDINATE
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "setAllCoordinates" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.SetAllCoords(
                            result,
                            (ILocation) stack.remove(stack.size() - 6).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 5).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 4).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 3).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            Tags.CoordinateType.PLOT_COORDINATE
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "shiftX", "shiftY", "shiftZ" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.ShiftOnAxis(
                            result,
                            (ILocation) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            switch (name) {
                                case "shiftX" -> Tags.Coordinate.X;
                                case "shiftY" -> Tags.Coordinate.Y;
                                case "shiftZ" -> Tags.Coordinate.Z;
                                default -> throw new IllegalStateException("Unknown coordinate for method: " + name);
                            }
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "shiftYaw", "shiftPitch" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.ShiftRotation(
                            result,
                            (ILocation) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            name.equals("shiftYaw") ? Tags.RotationAxis.YAW : Tags.RotationAxis.PITCH
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "shiftTowards" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.ShiftToward(
                            result,
                            (ILocation) stack.remove(stack.size() - 3).getTransformedValue(),
                            (ILocation) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "shiftByVector" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.ShiftOnVector(
                            result,
                            (ILocation) stack.remove(stack.size() - 2).getTransformedValue(),
                            (IVector) stack.remove(stack.size() - 1).getTransformedValue(),
                            new Number().Set(1),
                            Tags.AddLocationRotation.FALSE
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "shiftInDirection" -> {
                    Variable result = CompilerAddons.getTempVariable();
                    INumber direction = (INumber) stack.remove(stack.size() - 1).getTransformedValue();

                    if(direction instanceof Number && ((Number) direction).isConstant()) {
                        String value = ((Number) direction).getValue();

                        VariableControl.ShiftInDirection(
                                result,
                                (ILocation) stack.remove(stack.size() - 2),
                                (INumber) stack.remove(stack.size() - 1),
                                switch(value) {
                                    case "0" -> Tags.Direction.FORWARD;
                                    case "1" -> Tags.Direction.UPWARD;
                                    case "2" -> Tags.Direction.SIDEWAYS;
                                    default -> throw new IllegalStateException("Unknown Shift Direction: " + value);
                                }
                        );

                        stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                        break;
                    }

                    for (Loc.ShiftDirection value : Loc.ShiftDirection.values()) {
                        If.Variable.Equals(direction, new CodeValue[] { new Number().Set(value.getId()) }, false);
                            VariableControl.ShiftInDirection(
                                    result,
                                    (ILocation) stack.remove(stack.size() - 2),
                                    (INumber) stack.remove(stack.size() - 1),
                                    Tags.Direction.valueOf(value.name()));
                        If.End();
                    }

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "shiftInAllDirections" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.ShiftAllDirections(
                            result,
                            (ILocation) stack.remove(stack.size() - 4).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 3).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "alignToLowerCorner", "alignToCenter" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    INumber axes = (INumber) stack.remove(stack.size() - 2).getTransformedValue();
                    INumber removeRotation = (INumber) stack.remove(stack.size() - 1).getTransformedValue();

                    if(axes instanceof Number && ((Number) axes).isConstant()) {
                        Tags.Coordinates constantAxes = switch (((Number) axes).getValue()) {
                            case "2" -> Tags.Coordinates.ALL_COORDINATES;
                            case "4" -> Tags.Coordinates.X_AND_Z;
                            case "6" -> Tags.Coordinates.ONLY_Y;
                            default -> throw new IllegalStateException("Unknown axes for method: " + name);
                        };

                        if(removeRotation instanceof Number && ((Number) removeRotation).isConstant()) {
                            VariableControl.AlignLoc(
                                    result,
                                    (ILocation) stack.remove(stack.size() - 1).getTransformedValue(),
                                    name.equals("alignToCenter") ? Tags.LocationAlignmentMode.BLOCK_CENTER : Tags.LocationAlignmentMode.LOWER_BLOCK_CORNER,
                                    constantAxes,
                                    ((Number) removeRotation).getValue().equals("0") ? Tags.Rotation.KEEP_ROTATION : Tags.Rotation.REMOVE_ROTATION
                            );
                        } else {
                            If.Variable.Equals(removeRotation, new CodeValue[] { new Number().Set(0) }, false);
                                VariableControl.AlignLoc(
                                        result,
                                        (ILocation) stack.remove(stack.size() - 1).getTransformedValue(),
                                        name.equals("alignToCenter") ? Tags.LocationAlignmentMode.BLOCK_CENTER : Tags.LocationAlignmentMode.LOWER_BLOCK_CORNER,
                                        constantAxes,
                                        Tags.Rotation.KEEP_ROTATION
                                );
                            If.Else();
                                VariableControl.AlignLoc(
                                        result,
                                        (ILocation) stack.remove(stack.size() - 1).getTransformedValue(),
                                        name.equals("alignToCenter") ? Tags.LocationAlignmentMode.BLOCK_CENTER : Tags.LocationAlignmentMode.LOWER_BLOCK_CORNER,
                                        constantAxes,
                                        Tags.Rotation.REMOVE_ROTATION
                                );
                            If.End();
                        }
                    } else {
                        if(removeRotation instanceof Number && ((Number) removeRotation).isConstant()) {
                            for (Loc.AlignAxes value : Loc.AlignAxes.values()) {
                                If.Variable.Equals(axes, new CodeValue[] { new Number().Set(value.getId()) }, false);
                                    VariableControl.AlignLoc(
                                            result,
                                            (ILocation) stack.remove(stack.size() - 1).getTransformedValue(),
                                            name.equals("alignToCenter") ? Tags.LocationAlignmentMode.BLOCK_CENTER : Tags.LocationAlignmentMode.LOWER_BLOCK_CORNER,
                                            Tags.Coordinates.valueOf(value.name()),
                                            ((Number) removeRotation).getValue().equals("0") ? Tags.Rotation.KEEP_ROTATION : Tags.Rotation.REMOVE_ROTATION
                                    );
                                If.End();
                            }
                        } else {
                            for (Loc.AlignAxes value : Loc.AlignAxes.values()) {
                                for (int removeRotationValue = 0; removeRotationValue < 2; removeRotationValue++) {
                                    If.Variable.Equals(NumberMath.add(axes, removeRotation), new CodeValue[]{ new Number().Set(value.getId() + removeRotationValue) }, false);
                                        VariableControl.AlignLoc(
                                                result,
                                                (ILocation) stack.remove(stack.size() - 1).getTransformedValue(),
                                                name.equals("alignToCenter") ? Tags.LocationAlignmentMode.BLOCK_CENTER : Tags.LocationAlignmentMode.LOWER_BLOCK_CORNER,
                                                Tags.Coordinates.valueOf(value.name()),
                                                removeRotationValue == 0 ? Tags.Rotation.KEEP_ROTATION : Tags.Rotation.REMOVE_ROTATION
                                        );
                                    If.End();
                                }
                            }
                        }
                    }

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "faceTowards", "faceAwayFrom" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.FaceLocation(
                            result,
                            (ILocation) stack.remove(stack.size() - 2).getTransformedValue(),
                            (ILocation) stack.remove(stack.size() - 1).getTransformedValue(),
                            name.equals("faceTowards") ? Tags.FaceDirection.TOWARD_LOCATION : Tags.FaceDirection.AWAY_FROM_LOCATION
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "getX", "getY", "getZ" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.GetCoord(
                            result,
                            (ILocation) stack.remove(stack.size() - 1).getTransformedValue(),
                            switch (name) {
                                case "getX" -> Tags.Coordinate.X;
                                case "getY" -> Tags.Coordinate.Y;
                                case "getZ" -> Tags.Coordinate.Z;
                                case "getYaw" -> Tags.Coordinate.YAW;
                                case "getPitch" -> Tags.Coordinate.PITCH;
                                default -> throw new IllegalStateException("Unknown coordinate for method: " + name);
                            },
                            Tags.CoordinateType.PLOT_COORDINATE
                    );

                    stack.add(new VariableStackValue("D", result.getName()));
                }
                case "getYaw", "getPitch" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.GetCoord(
                            result,
                            (ILocation) stack.remove(stack.size() - 1).getTransformedValue(),
                            name.equals("getYaw") ? Tags.Coordinate.YAW : Tags.Coordinate.PITCH,
                            Tags.CoordinateType.PLOT_COORDINATE
                    );

                    stack.add(new VariableStackValue("F", result.getName()));
                }
                case "getDirection" -> {
                    Variable result = CompilerAddons.getTempVariable();
                    VariableControl.GetDirection(result, (ILocation) stack.remove(stack.size() - 1).getTransformedValue());

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/value/IVector;", result.getName()));
                }
                case "asCodeValue" -> {}
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean onInvokeStatic(String owner, String name, String descriptor, List<IStackValue> stack) {
        if(owner.equals("net/jfdf/addon/loc/Loc")) {
            switch (name) {
                case "center" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    if(descriptor.equals("([Lnet/jfdf/addon/loc/Loc;")) {
                        Variable array = (Variable) stack.remove(stack.size() - 1).getTransformedValue();
                        Variable reference = new Variable("_jfdfR%var(" + array.getName() + ")", Variable.Scope.NORMAL);

                        VariableControl.TrimList(result, reference, new Number().Set(2), new Number().Set(10000));
                        VariableControl.GetCenterLoc(result, result);
                    } else {
                        VariableControl.GetCenterLoc(
                                result,
                                (ILocation) stack.remove(stack.size() - 2).getTransformedValue(),
                                (ILocation) stack.remove(stack.size() - 1).getTransformedValue()
                        );
                    }

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/value/Location;", result.getName()));
                }
                case "randomBetween" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.RandomLoc(
                            result,
                            (ILocation) stack.remove(stack.size() - 2).getTransformedValue(),
                            (ILocation) stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/value/Location;", result.getName()));
                }
                case "distance3d", "distance2d", "altitude" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.Distance(
                            result,
                            (ILocation) stack.remove(stack.size() - 2).getTransformedValue(),
                            (ILocation) stack.remove(stack.size() - 1).getTransformedValue(),
                            switch (name) {
                                case "distance3d" -> Tags.DistanceType.DISTANCE_3D__X_Y_Z_;
                                case "distance2d" -> Tags.DistanceType.DISTANCE_2D__X_Z_;
                                case "altitude" -> Tags.DistanceType.ALTITUDE__Y_;
                                default -> throw new IllegalStateException("Unknown distance type for method: " + name);
                            }
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/value/Location;", result.getName()));
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean onGetField(boolean isStatic, String owner, String name, String descriptor, List<IStackValue> stack) {
        if(isStatic) {
            String ownerDescriptor = Type.getObjectType(owner).getDescriptor();

            if(ownerDescriptor.equals(descriptor)) {
                if(owner.startsWith("net/jfdf/addon/loc/Loc$")) {
                    switch (owner) {
                        case "net/jfdf/addon/loc/Loc$AlignAxes" ->
                                stack.add(new NumberStackValue(Loc.AlignAxes.valueOf(name).getId()));
                        case "net/jfdf/addon/loc/Loc$ShiftDirection" ->
                                stack.add(new NumberStackValue(Loc.ShiftDirection.valueOf(name).getId()));
                    }

                    return true;
                }
            }
        }

        return false;
    }
}

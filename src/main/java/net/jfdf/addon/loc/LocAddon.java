package net.jfdf.addon.loc;

import java.util.List;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.data.stack.CodeStackValue;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.NumberStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IVector;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Variable;
import net.jfdf.transformer.util.NumberMath;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;

public class LocAddon implements ICompilerAddon {
   public boolean onInitClass(String type, List stack) {
      if (type.equals("net/jfdf/addon/loc/Loc")) {
         stack.add(new CodeStackValue("net/jfdf/jfdf/values/Location"));
         return true;
      } else {
         return false;
      }
   }

   public boolean onInvokeConstructor(String type, String descriptor, List stack) {
      if (type.equals("net/jfdf/addon/loc/Loc")) {
         if (descriptor.equals("(DDD)V")) {
            ((CodeStackValue)stack.remove(stack.size() - 4)).callConstructor("(FFF)V", ((NumberStackValue)stack.remove(stack.size() - 3)).getJavaValue().floatValue(), ((NumberStackValue)stack.remove(stack.size() - 2)).getJavaValue().floatValue(), ((NumberStackValue)stack.remove(stack.size() - 1)).getJavaValue().floatValue());
         } else if (descriptor.equals("(DDDFF)V")) {
            ((CodeStackValue)stack.remove(stack.size() - 6)).callConstructor("(FFFDD)V", ((NumberStackValue)stack.remove(stack.size() - 5)).getJavaValue().floatValue(), ((NumberStackValue)stack.remove(stack.size() - 4)).getJavaValue().floatValue(), ((NumberStackValue)stack.remove(stack.size() - 3)).getJavaValue().floatValue(), ((NumberStackValue)stack.remove(stack.size() - 2)).getJavaValue().doubleValue(), ((NumberStackValue)stack.remove(stack.size() - 1)).getJavaValue().doubleValue());
         } else {
            stack.remove(stack.size() - 2);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean onInvokeDynamic(String name, String descriptor, Handle methodHandle, Object[] methodArgs, List stack) {
      return false;
   }

   public boolean onInvokeMember(String owner, String name, String descriptor, List stack) {
      if (owner.equals("net/jfdf/addon/loc/Loc")) {
         Variable result;
         INumber axes;
         int var11;
         ILocation var10001;
         INumber var24;
         Tags.Coordinate var25;
         switch (name) {
            case "add":
               result = CompilerAddons.getTempVariable();
               VariableControl.SetToProduct(result, ((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "subtract":
               result = CompilerAddons.getTempVariable();
               VariableControl.SetToDifference(result, ((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "setX":
            case "setY":
            case "setZ":
            case "setYaw":
            case "setPitch":
               result = CompilerAddons.getTempVariable();
               var10001 = (ILocation)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
               var24 = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
               switch (name) {
                  case "setX":
                     var25 = Tags.Coordinate.X;
                     break;
                  case "setY":
                     var25 = Tags.Coordinate.Y;
                     break;
                  case "setZ":
                     var25 = Tags.Coordinate.Z;
                     break;
                  case "setYaw":
                     var25 = Tags.Coordinate.YAW;
                     break;
                  case "setPitch":
                     var25 = Tags.Coordinate.PITCH;
                     break;
                  default:
                     throw new IllegalStateException("Unknown coordinate for method: " + name);
               }

               VariableControl.SetCoord(result, var10001, var24, var25, Tags.CoordinateType.PLOT_COORDINATE);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "setDirection":
               result = CompilerAddons.getTempVariable();
               VariableControl.Set(result, ((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue());
               VariableControl.DirectionName(result, (IVector)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "setAllAxes":
               result = CompilerAddons.getTempVariable();
               VariableControl.SetAllCoords(result, (ILocation)((IStackValue)stack.remove(stack.size() - 4)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), (INumber)null, (INumber)null, Tags.CoordinateType.PLOT_COORDINATE);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "setAllCoordinates":
               result = CompilerAddons.getTempVariable();
               VariableControl.SetAllCoords(result, (ILocation)((IStackValue)stack.remove(stack.size() - 6)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 5)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 4)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.CoordinateType.PLOT_COORDINATE);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "shiftX":
            case "shiftY":
            case "shiftZ":
               result = CompilerAddons.getTempVariable();
               var10001 = (ILocation)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
               var24 = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
               switch (name) {
                  case "shiftX":
                     var25 = Tags.Coordinate.X;
                     break;
                  case "shiftY":
                     var25 = Tags.Coordinate.Y;
                     break;
                  case "shiftZ":
                     var25 = Tags.Coordinate.Z;
                     break;
                  default:
                     throw new IllegalStateException("Unknown coordinate for method: " + name);
               }

               VariableControl.ShiftOnAxis(result, var10001, var24, var25);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "shiftYaw":
            case "shiftPitch":
               result = CompilerAddons.getTempVariable();
               VariableControl.ShiftRotation(result, (ILocation)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), name.equals("shiftYaw") ? Tags.RotationAxis.YAW : Tags.RotationAxis.PITCH);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "shiftTowards":
               result = CompilerAddons.getTempVariable();
               VariableControl.ShiftToward(result, (ILocation)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), (ILocation)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "shiftByVector":
               result = CompilerAddons.getTempVariable();
               VariableControl.ShiftOnVector(result, (ILocation)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (IVector)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), (new Number()).Set(1), Tags.AddLocationRotation.FALSE);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "shiftInDirection":
               result = CompilerAddons.getTempVariable();
               axes = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
               if (axes instanceof Number && ((Number)axes).isConstant()) {
                  String value = ((Number)axes).getValue();
                  var10001 = (ILocation)stack.remove(stack.size() - 2);
                  var24 = (INumber)stack.remove(stack.size() - 1);
                  Tags.Direction var10003;
                  switch (value) {
                     case "0":
                        var10003 = Tags.Direction.FORWARD;
                        break;
                     case "1":
                        var10003 = Tags.Direction.UPWARD;
                        break;
                     case "2":
                        var10003 = Tags.Direction.SIDEWAYS;
                        break;
                     default:
                        throw new IllegalStateException("Unknown Shift Direction: " + value);
                  }

                  VariableControl.ShiftInDirection(result, var10001, var24, var10003);
                  stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               } else {
                  Loc.ShiftDirection[] var18 = Loc.ShiftDirection.values();
                  int var16 = var18.length;

                  for(var11 = 0; var11 < var16; ++var11) {
                     Loc.ShiftDirection value = var18[var11];
                     If.Variable.Equals(axes, new CodeValue[]{(new Number()).Set(value.getId())}, false);
                     VariableControl.ShiftInDirection(result, (ILocation)stack.remove(stack.size() - 2), (INumber)stack.remove(stack.size() - 1), Tags.Direction.valueOf(value.name()));
                     If.End();
                  }

                  stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               }
               break;
            case "shiftInAllDirections":
               result = CompilerAddons.getTempVariable();
               VariableControl.ShiftAllDirections(result, (ILocation)((IStackValue)stack.remove(stack.size() - 4)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "alignToLowerCorner":
            case "alignToCenter":
               result = CompilerAddons.getTempVariable();
               axes = (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
               INumber removeRotation = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
               if (axes instanceof Number && ((Number)axes).isConstant()) {
                  Tags.Coordinates var10000;
                  switch (((Number)axes).getValue()) {
                     case "2":
                        var10000 = Tags.Coordinates.ALL_COORDINATES;
                        break;
                     case "4":
                        var10000 = Tags.Coordinates.X_AND_Z;
                        break;
                     case "6":
                        var10000 = Tags.Coordinates.ONLY_Y;
                        break;
                     default:
                        throw new IllegalStateException("Unknown axes for method: " + name);
                  }

                  Tags.Coordinates constantAxes = var10000;
                  if (removeRotation instanceof Number && ((Number)removeRotation).isConstant()) {
                     VariableControl.AlignLoc(result, (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), name.equals("alignToCenter") ? Tags.LocationAlignmentMode.BLOCK_CENTER : Tags.LocationAlignmentMode.LOWER_BLOCK_CORNER, constantAxes, ((Number)removeRotation).getValue().equals("0") ? Tags.Rotation.KEEP_ROTATION : Tags.Rotation.REMOVE_ROTATION);
                  } else {
                     If.Variable.Equals(removeRotation, new CodeValue[]{(new Number()).Set(0)}, false);
                     VariableControl.AlignLoc(result, (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), name.equals("alignToCenter") ? Tags.LocationAlignmentMode.BLOCK_CENTER : Tags.LocationAlignmentMode.LOWER_BLOCK_CORNER, constantAxes, Tags.Rotation.KEEP_ROTATION);
                     If.Else();
                     VariableControl.AlignLoc(result, (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), name.equals("alignToCenter") ? Tags.LocationAlignmentMode.BLOCK_CENTER : Tags.LocationAlignmentMode.LOWER_BLOCK_CORNER, constantAxes, Tags.Rotation.REMOVE_ROTATION);
                     If.End();
                  }
               } else {
                  Loc.AlignAxes[] var10;
                  int var12;
                  Loc.AlignAxes value;
                  if (removeRotation instanceof Number && ((Number)removeRotation).isConstant()) {
                     var10 = Loc.AlignAxes.values();
                     var11 = var10.length;

                     for(var12 = 0; var12 < var11; ++var12) {
                        value = var10[var12];
                        If.Variable.Equals(axes, new CodeValue[]{(new Number()).Set(value.getId())}, false);
                        VariableControl.AlignLoc(result, (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), name.equals("alignToCenter") ? Tags.LocationAlignmentMode.BLOCK_CENTER : Tags.LocationAlignmentMode.LOWER_BLOCK_CORNER, Tags.Coordinates.valueOf(value.name()), ((Number)removeRotation).getValue().equals("0") ? Tags.Rotation.KEEP_ROTATION : Tags.Rotation.REMOVE_ROTATION);
                        If.End();
                     }
                  } else {
                     var10 = Loc.AlignAxes.values();
                     var11 = var10.length;

                     for(var12 = 0; var12 < var11; ++var12) {
                        value = var10[var12];

                        for(int removeRotationValue = 0; removeRotationValue < 2; ++removeRotationValue) {
                           If.Variable.Equals(NumberMath.add(axes, removeRotation), new CodeValue[]{(new Number()).Set(value.getId() + removeRotationValue)}, false);
                           VariableControl.AlignLoc(result, (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), name.equals("alignToCenter") ? Tags.LocationAlignmentMode.BLOCK_CENTER : Tags.LocationAlignmentMode.LOWER_BLOCK_CORNER, Tags.Coordinates.valueOf(value.name()), removeRotationValue == 0 ? Tags.Rotation.KEEP_ROTATION : Tags.Rotation.REMOVE_ROTATION);
                           If.End();
                        }
                     }
                  }
               }

               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "faceTowards":
            case "faceAwayFrom":
               result = CompilerAddons.getTempVariable();
               VariableControl.FaceLocation(result, (ILocation)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), name.equals("faceTowards") ? Tags.FaceDirection.TOWARD_LOCATION : Tags.FaceDirection.AWAY_FROM_LOCATION);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
               break;
            case "getX":
            case "getY":
            case "getZ":
               result = CompilerAddons.getTempVariable();
               var10001 = (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
               Tags.Coordinate var10002;
               switch (name) {
                  case "getX":
                     var10002 = Tags.Coordinate.X;
                     break;
                  case "getY":
                     var10002 = Tags.Coordinate.Y;
                     break;
                  case "getZ":
                     var10002 = Tags.Coordinate.Z;
                     break;
                  case "getYaw":
                     var10002 = Tags.Coordinate.YAW;
                     break;
                  case "getPitch":
                     var10002 = Tags.Coordinate.PITCH;
                     break;
                  default:
                     throw new IllegalStateException("Unknown coordinate for method: " + name);
               }

               VariableControl.GetCoord(result, var10001, var10002, Tags.CoordinateType.PLOT_COORDINATE);
               stack.add(new VariableStackValue("D", result.getName()));
               break;
            case "getYaw":
            case "getPitch":
               result = CompilerAddons.getTempVariable();
               VariableControl.GetCoord(result, (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), name.equals("getYaw") ? Tags.Coordinate.YAW : Tags.Coordinate.PITCH, Tags.CoordinateType.PLOT_COORDINATE);
               stack.add(new VariableStackValue("F", result.getName()));
               break;
            case "getDirection":
               result = CompilerAddons.getTempVariable();
               VariableControl.GetDirection(result, (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/value/IVector;", result.getName()));
            case "asCodeValue":
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean onInvokeStatic(String owner, String name, String descriptor, List stack) {
      if (owner.equals("net/jfdf/addon/loc/Loc")) {
         Variable result;
         switch (name) {
            case "center":
               result = CompilerAddons.getTempVariable();
               if (descriptor.equals("([Lnet/jfdf/addon/loc/Loc;")) {
                  Variable array = (Variable)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
                  Variable reference = new Variable("_jfdfR%var(" + array.getName() + ")", Variable.Scope.NORMAL);
                  VariableControl.TrimList(result, reference, (new Number()).Set(2), (new Number()).Set(10000));
                  VariableControl.GetCenterLoc(result, result);
               } else {
                  VariableControl.GetCenterLoc(result, (ILocation)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               }

               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/value/Location;", result.getName()));
               break;
            case "randomBetween":
               result = CompilerAddons.getTempVariable();
               VariableControl.RandomLoc(result, (ILocation)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/value/Location;", result.getName()));
               break;
            case "distance3d":
            case "distance2d":
            case "altitude":
               result = CompilerAddons.getTempVariable();
               ILocation var10001 = (ILocation)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
               ILocation var10002 = (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
               Tags.DistanceType var10003;
               switch (name) {
                  case "distance3d":
                     var10003 = Tags.DistanceType.DISTANCE_3D__X_Y_Z_;
                     break;
                  case "distance2d":
                     var10003 = Tags.DistanceType.DISTANCE_2D__X_Z_;
                     break;
                  case "altitude":
                     var10003 = Tags.DistanceType.ALTITUDE__Y_;
                     break;
                  default:
                     throw new IllegalStateException("Unknown distance type for method: " + name);
               }

               VariableControl.Distance(result, var10001, var10002, var10003);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/value/Location;", result.getName()));
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean onGetField(boolean isStatic, String owner, String name, String descriptor, List stack) {
      if (isStatic) {
         String ownerDescriptor = Type.getObjectType(owner).getDescriptor();
         if (ownerDescriptor.equals(descriptor) && owner.startsWith("net/jfdf/addon/loc/Loc$")) {
            switch (owner) {
               case "net/jfdf/addon/loc/Loc$AlignAxes":
                  stack.add(new NumberStackValue(Loc.AlignAxes.valueOf(name).getId()));
                  break;
               case "net/jfdf/addon/loc/Loc$ShiftDirection":
                  stack.add(new NumberStackValue(Loc.ShiftDirection.valueOf(name).getId()));
            }

            return true;
         }
      }

      return false;
   }
}

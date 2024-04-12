package net.jfdf.addon.vector;

import java.util.List;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.data.stack.CodeStackValue;
import net.jfdf.compiler.data.stack.EnumStackValue;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.NumberStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IVector;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Variable;
import org.objectweb.asm.Handle;

public class VectorAddon implements ICompilerAddon {
   public boolean onInitClass(String type, List stack) {
      if (type.startsWith("net/jfdf/addon/vector/Vector") && !type.equals("net/jfdf/addon/vector/VectorAddon")) {
         stack.add(new CodeStackValue("net/jfdf/jfdf/values/Vector"));
         return true;
      } else {
         return false;
      }
   }

   public boolean onInvokeConstructor(String type, String descriptor, List stack) {
      if (type.startsWith("net/jfdf/addon/vector/Vector") && !type.equals("net/jfdf/addon/vector/VectorAddon")) {
         String[] typeSplitted = type.split("/");
         String typeSimpleName = typeSplitted[typeSplitted.length - 1];
         switch (typeSimpleName + descriptor) {
            case "Vector3d(DDD)V":
               ((CodeStackValue)stack.remove(stack.size() - 4)).callConstructor("(DDD)V", ((NumberStackValue)stack.remove(stack.size() - 3)).getJavaValue(), ((NumberStackValue)stack.remove(stack.size() - 2)).getJavaValue(), ((NumberStackValue)stack.remove(stack.size() - 1)).getJavaValue());
               break;
            case "Vector3d(Lnet/jfdf/addon/vector/Vector2dXY;)V":
            case "Vector3d(Lnet/jfdf/addon/vector/Vector2dXZ;)V":
               Variable result = CompilerAddons.getTempVariable();
               VariableControl.Set(result, ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               CodeStackValue vectorStackValue = (CodeStackValue)stack.remove(stack.size() - 1);
               IStackValue newStackValue = new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName());

               for(int i = 0; i < stack.size(); ++i) {
                  if (stack.get(i) == vectorStackValue) {
                     stack.set(i, newStackValue);
                  }
               }

               return true;
            case "Vector2dXY(DD)V":
               ((CodeStackValue)stack.remove(stack.size() - 3)).callConstructor("(DD)V", ((NumberStackValue)stack.remove(stack.size() - 2)).getJavaValue(), ((NumberStackValue)stack.remove(stack.size() - 1)).getJavaValue());
               break;
            case "Vector2dXZ(DD)V":
               ((CodeStackValue)stack.remove(stack.size() - 3)).callConstructor("(DDD)V", ((NumberStackValue)stack.remove(stack.size() - 2)).getJavaValue(), 0, ((NumberStackValue)stack.remove(stack.size() - 1)).getJavaValue());
               break;
            default:
               throw new IllegalStateException("Unknown vector constructor.");
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
      if (owner.startsWith("net/jfdf/addon/vector/Vector") && !owner.equals("net/jfdf/addon/vector/VectorAddon")) {
         Variable result;
         switch (name) {
            case "add":
               result = CompilerAddons.getTempVariable();
               VariableControl.AddVectors(result, (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (IVector)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "subtract":
               result = CompilerAddons.getTempVariable();
               VariableControl.SubtractVectors(result, (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (IVector)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "multiply":
               result = CompilerAddons.getTempVariable();
               VariableControl.MultiplyVector(result, (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "reflect":
               result = CompilerAddons.getTempVariable();
               VariableControl.ReflectVector(result, (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (IVector)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "cross":
               result = CompilerAddons.getTempVariable();
               VariableControl.CrossProduct(result, (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (IVector)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "dot":
               result = CompilerAddons.getTempVariable();
               VariableControl.DotProduct(result, (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (IVector)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("D", result.getName()));
               break;
            case "rotateAround":
               result = CompilerAddons.getTempVariable();
               VariableControl.RotateAroundAxis(result, (IVector)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.AngleUnits.RADIANS, (Tags.Axis)((EnumStackValue)stack.remove(stack.size() - 1)).getEnumValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "rotateAroundDeg":
               result = CompilerAddons.getTempVariable();
               VariableControl.RotateAroundAxis(result, (IVector)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.AngleUnits.DEGREES, (Tags.Axis)((EnumStackValue)stack.remove(stack.size() - 1)).getEnumValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "rotateAroundVec":
               result = CompilerAddons.getTempVariable();
               VariableControl.RotateAroundVec(result, (IVector)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.AngleUnits.RADIANS);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "rotateAroundVecDeg":
               result = CompilerAddons.getTempVariable();
               VariableControl.RotateAroundVec(result, (IVector)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.AngleUnits.DEGREES);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "setX":
               result = CompilerAddons.getTempVariable();
               VariableControl.SetVectorComp(result, (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.Component.X);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "setY":
               result = CompilerAddons.getTempVariable();
               VariableControl.SetVectorComp(result, (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.Component.Y);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "setZ":
               result = CompilerAddons.getTempVariable();
               VariableControl.SetVectorComp(result, (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.Component.Z);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "setXY":
               result = CompilerAddons.getTempVariable();
               VariableControl.Vector(result, (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), (new Number()).Set(0));
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "setXZ":
               result = CompilerAddons.getTempVariable();
               VariableControl.Vector(result, (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (new Number()).Set(0), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "setXYZ":
               result = CompilerAddons.getTempVariable();
               VariableControl.Vector(result, (INumber)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "setLength":
               result = CompilerAddons.getTempVariable();
               VariableControl.SetVectorLength(result, (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
               break;
            case "getLength":
               result = CompilerAddons.getTempVariable();
               VariableControl.GetVectorLength(result, (IVector)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.LengthType.LENGTH);
               stack.add(new VariableStackValue("D", result.getName()));
               break;
            case "getLengthSquared":
               result = CompilerAddons.getTempVariable();
               VariableControl.GetVectorLength(result, (IVector)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.LengthType.LENGTH_SQUARED);
               stack.add(new VariableStackValue("D", result.getName()));
               break;
            case "shiftLocation":
               result = CompilerAddons.getTempVariable();
               VariableControl.ShiftOnVector(result, (ILocation)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (IVector)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.AddLocationRotation.FALSE);
               stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
            case "asCodeValue":
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean onInvokeStatic(String owner, String name, String descriptor, List stack) {
      return false;
   }
}

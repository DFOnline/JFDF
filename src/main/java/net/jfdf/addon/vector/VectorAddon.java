package net.jfdf.addon.vector;

import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.data.stack.*;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.*;
import net.jfdf.jfdf.values.Number;
import org.objectweb.asm.Handle;

import java.util.List;

public class VectorAddon implements ICompilerAddon {

    @Override
    public boolean onInitClass(String type, List<IStackValue> stack) {
        if(type.startsWith("net/jfdf/addon/vector/Vector")
                && !type.equals("net/jfdf/addon/vector/VectorAddon")) {
            stack.add(new CodeStackValue("net/jfdf/jfdf/values/Vector"));
            return true;
        }

        return false;
    }

    @Override
    public boolean onInvokeConstructor(String type, String descriptor, List<IStackValue> stack) {
        if(type.startsWith("net/jfdf/addon/vector/Vector")
                && !type.equals("net/jfdf/addon/vector/VectorAddon")) {
            String[] typeSplitted = type.split("/");
            String typeSimpleName = typeSplitted[typeSplitted.length - 1];

            switch (typeSimpleName + descriptor) {
                case "Vector3d(DDD)V" ->
                        ((CodeStackValue) stack.remove(stack.size() - 4))
                            .callConstructor("(DDD)V",
                                    ((NumberStackValue) stack.remove(stack.size() - 3)).getJavaValue(),
                                    ((NumberStackValue) stack.remove(stack.size() - 2)).getJavaValue(),
                                    ((NumberStackValue) stack.remove(stack.size() - 1)).getJavaValue()
                            );
                
                case "Vector3d(Lnet/jfdf/addon/vector/Vector2dXY;)V", 
                        "Vector3d(Lnet/jfdf/addon/vector/Vector2dXZ;)V" -> {
                    Variable result = CompilerAddons.getTempVariable();
                    VariableControl.Set(result, stack.remove(stack.size() - 1).getTransformedValue());

                    CodeStackValue vectorStackValue = (CodeStackValue) stack.remove(stack.size() - 1);
                    IStackValue newStackValue = new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName());

                    for (int i = 0; i < stack.size(); i++) {
                        if(stack.get(i) == vectorStackValue) {
                            stack.set(i, newStackValue);
                        }
                    }
                }

                case "Vector2dXY(DD)V" ->
                        ((CodeStackValue) stack.remove(stack.size() - 3))
                                .callConstructor("(DD)V",
                                        ((NumberStackValue) stack.remove(stack.size() - 2)).getJavaValue(),
                                        ((NumberStackValue) stack.remove(stack.size() - 1)).getJavaValue()
                                );
                case "Vector2dXZ(DD)V" ->
                        ((CodeStackValue) stack.remove(stack.size() - 3))
                                .callConstructor("(DDD)V",
                                        ((NumberStackValue) stack.remove(stack.size() - 2)).getJavaValue(),
                                        0,
                                        ((NumberStackValue) stack.remove(stack.size() - 1)).getJavaValue()
                                );

                default -> throw new IllegalStateException("Unknown vector constructor.");
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
        if(owner.startsWith("net/jfdf/addon/vector/Vector")
                && !owner.equals("net/jfdf/addon/vector/VectorAddon")) {
            switch (name) {
                case "add" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.AddVectors(
                            result,
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (IVector) stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }
                case "subtract" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.SubtractVectors(
                            result,
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (IVector) stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }
                case "multiply" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.MultiplyVector(
                            result,
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }

                case "reflect" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.ReflectVector(
                            result,
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (IVector) stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }
                case "cross" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.CrossProduct(
                            result,
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (IVector) stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }
                case "dot" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.DotProduct(
                            result,
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (IVector) stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("D", result.getName()));
                }

                case "rotateAround" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.RotateAroundAxis(
                            result,
                            (IVector) stack.remove(stack.size() - 3).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            Tags.AngleUnits.RADIANS,
                            (Tags.Axis) ((EnumStackValue) stack.remove(stack.size() - 1)).getEnumValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }
                case "rotateAroundDeg" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.RotateAroundAxis(
                            result,
                            (IVector) stack.remove(stack.size() - 3).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            Tags.AngleUnits.DEGREES,
                            (Tags.Axis) ((EnumStackValue) stack.remove(stack.size() - 1)).getEnumValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }
                case "rotateAroundVec" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.RotateAroundVec(
                            result,
                            (IVector) stack.remove(stack.size() - 3).getTransformedValue(),
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            Tags.AngleUnits.RADIANS
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }
                case "rotateAroundVecDeg" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.RotateAroundVec(
                            result,
                            (IVector) stack.remove(stack.size() - 3).getTransformedValue(),
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            Tags.AngleUnits.DEGREES
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }

                case "setX" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.SetVectorComp(
                            result,
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            Tags.Component.X
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }
                case "setY" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.SetVectorComp(
                            result,
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            Tags.Component.Y
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }
                case "setZ" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.SetVectorComp(
                            result,
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            Tags.Component.Z
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }

                case "setXY" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.Vector(
                            result,
                            (INumber) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            new Number().Set(0)
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }
                case "setXZ" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.Vector(
                            result,
                            (INumber) stack.remove(stack.size() - 2).getTransformedValue(),
                            new Number().Set(0),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }
                case "setXYZ" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.Vector(
                            result,
                            (INumber) stack.remove(stack.size() - 3).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }

                case "setLength" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.SetVectorLength(
                            result,
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue()
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Vector;", result.getName()));
                }
                case "getLength" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.GetVectorLength(
                            result,
                            (IVector) stack.remove(stack.size() - 1).getTransformedValue(),
                            Tags.LengthType.LENGTH
                    );

                    stack.add(new VariableStackValue("D", result.getName()));
                }
                case "getLengthSquared" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.GetVectorLength(
                            result,
                            (IVector) stack.remove(stack.size() - 1).getTransformedValue(),
                            Tags.LengthType.LENGTH_SQUARED
                    );

                    stack.add(new VariableStackValue("D", result.getName()));
                }
                case "shiftLocation" -> {
                    Variable result = CompilerAddons.getTempVariable();

                    VariableControl.ShiftOnVector(
                            result,
                            (ILocation) stack.remove(stack.size() - 2).getTransformedValue(),
                            (IVector) stack.remove(stack.size() - 2).getTransformedValue(),
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                            Tags.AddLocationRotation.FALSE
                    );

                    stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/Location;", result.getName()));
                }
                case "asCodeValue" -> {}
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean onInvokeStatic(String owner, String name, String descriptor, List<IStackValue> stack) {
        return false;
    }
}

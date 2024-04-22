package net.jfdf.addon.math;

import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.data.stack.*;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.*;
import net.jfdf.jfdf.values.Number;
import org.objectweb.asm.Handle;

import java.util.List;

public class MathAddon implements ICompilerAddon {
    @Override
    public boolean onInitClass(String type, List<IStackValue> stack) {
        return false;
    }

    @Override
    public boolean onInvokeConstructor(String type, String descriptor, List<IStackValue> stack) {
        return false;
    }

    @Override
    public boolean onInvokeDynamic(String name, String descriptor, Handle methodHandle, Object[] methodArgs, List<IStackValue> stack) {
        return false;
    }

    @Override
    public boolean onInvokeMember(String owner, String name, String descriptor, List<IStackValue> stack) {
        return false;
    }

    @Override
    public boolean onInvokeStatic(String owner, String name, String descriptor, List<IStackValue> stack) {
        if(owner.equals("java/lang/Math")) {
            switch (name + descriptor) {
                case "round(F)I", "round(D)J" -> {
                    Variable result = CompilerAddons.getTempVariable();
                    stack.add(new VariableStackValue("I", result.getName()));

                    VariableControl.Round(
                            result,
                            (INumber) stack.remove(stack.size() - 2).getTransformedValue(),
                            null,
                            Tags.RoundMode.NEAREST
                    );
                }
                case "floor(D)D" -> {
                    Variable result = CompilerAddons.getTempVariable();
                    stack.add(new VariableStackValue("I", result.getName()));

                    VariableControl.Round(
                            result,
                            (INumber) stack.remove(stack.size() - 2).getTransformedValue(),
                            null,
                            Tags.RoundMode.FLOOR
                    );
                }
                case "ceil(D)D" -> {
                    Variable result = CompilerAddons.getTempVariable();
                    stack.add(new VariableStackValue("I", result.getName()));

                    VariableControl.Round(
                            result,
                            (INumber) stack.remove(stack.size() - 2).getTransformedValue(),
                            null,
                            Tags.RoundMode.CEILING
                    );
                }
                case "addExact(II)I", "addExact(JJ)J" ->
                        stack.add(new MathStackValue(
                                (INumber) stack.remove(stack.size() - 2).getTransformedValue(),
                                (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                                MathStackValue.MathOperation.ADD
                        ));
                case "multiplyExact(II)I", "multiplyExact(JI)J", "multiplyExact(JJ)J" ->
                        stack.add(new MathStackValue(
                                (INumber) stack.remove(stack.size() - 2).getTransformedValue(),
                                (INumber) stack.remove(stack.size() - 1).getTransformedValue(),
                                MathStackValue.MathOperation.MULTIPLY
                        ));
                case "min(II)I", "min(JJ)J", "min(FF)F", "min(DD)D" -> {
                    Variable result = CompilerAddons.getTempVariable();
                    stack.add(new VariableStackValue("I", result.getName()));

                    VariableControl.Set(
                            result,
                            stack.remove(stack.size() - 2).getTransformedValue()
                    );

                    If.Variable.GreaterThan(
                            result,
                            (INumber) stack.get(stack.size() - 1).getTransformedValue(), false
                    );

                        VariableControl.Set(result, stack.remove(stack.size() - 1).getTransformedValue());

                    If.End();
                }
                case "max(II)I", "max(JJ)J", "max(FF)F", "max(DD)D" -> {
                    Variable result = CompilerAddons.getTempVariable();
                    stack.add(new VariableStackValue("I", result.getName()));

                    VariableControl.Set(
                            result,
                            stack.remove(stack.size() - 2).getTransformedValue()
                    );

                    If.Variable.LessThan(
                            result,
                            (INumber) stack.get(stack.size() - 1).getTransformedValue(), false
                    );

                        VariableControl.Set(result, stack.remove(stack.size() - 1).getTransformedValue());

                    If.End();
                }
                case "sqrt(D)D" -> {
                    Variable result = CompilerAddons.getTempVariable();
                    stack.add(new VariableStackValue("D", result.getName()));

                    VariableControl.Root(
                            result,
                            (INumber) stack.remove(stack.size() - 2).getTransformedValue(),
                            new Number().Set(2));
                }
                case "abs(I)I", "abs(J)J", "abs(F)F", "abs(D)D" -> {
                    Variable result = CompilerAddons.getTempVariable();
                    stack.add(new VariableStackValue("D", result.getName()));

                    VariableControl.AbsoluteValue(
                            result,
                            (INumber) stack.remove(stack.size() - 1).getTransformedValue()
                    );
                }
                default -> throw new IllegalStateException("Math." + name + descriptor + " is not supported !");
            }

            return true;
        }

        return false;
    }
}

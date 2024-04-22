package net.jfdf.addon.string;

import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.addon.IfHandler;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.TextStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.BracketBlock;
import net.jfdf.jfdf.blocks.CodeBlock;
import net.jfdf.jfdf.blocks.IfVariableBlock;
import net.jfdf.jfdf.blocks.RepeatBlock;
import net.jfdf.jfdf.mangement.*;
import net.jfdf.jfdf.values.*;
import net.jfdf.jfdf.values.Number;
import net.jfdf.transformer.util.NumberMath;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;

import java.util.List;

public class StringAddon implements ICompilerAddon {
    @Override
    public boolean onInitClass(String type, List<IStackValue> stack) {
        if(type.equals("java/lang/StringBuilder")) {
            Variable stringBuilder = CompilerAddons.getTempVariable();
            Variable reference = new Variable("_jfdfR%var(" + stringBuilder.getName() + ")", Variable.Scope.NORMAL);

            VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
            VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), new Text().Set(stringBuilder.getName()));
            Functions.Call("_jfdf>std>sallocl");

            VariableControl.CreateList(reference, new Text().Set("\0p"));

            stack.add(new VariableStackValue("Ljava/lang/StringBuilder;", stringBuilder.getName()));
            return true;
        }

        return false;
    }

    @Override
    public boolean onInvokeConstructor(String type, String descriptor, List<IStackValue> stack) {
        if(type.equals("java/lang/StringBuilder")) {
            switch (descriptor) {
                case "()V" -> stack.remove(stack.size() - 1);
                case "(Ljava/lang/String;)V" -> {
                    IStackValue builderValue = stack.remove(stack.size() - 2);
                    Variable stringBuilder = (Variable) builderValue.getTransformedValue();
                    Variable reference = new Variable("_jfdfR%var(" + stringBuilder.getName() + ")", Variable.Scope.NORMAL);

                    VariableControl.AppendValue(reference, stack.remove(stack.size() - 1).getTransformedValue());
                }
                default -> throw new UnsupportedOperationException("StringBuilder" + descriptor + " is not supported !");
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean onInvokeDynamic(String name, String descriptor, Handle methodHandle, Object[] methodArgs, List<IStackValue> stack) {
        if(name.equals("makeConcatWithConstants")
                && methodHandle.getOwner().equals("java/lang/invoke/StringConcatFactory")) {
            String result = (String) methodArgs[0];
            int argsCount = Type.getArgumentTypes(descriptor).length;

            for (int i = 0; i < argsCount; i++) {
                IStackValue argument = stack.remove(stack.size() - (argsCount - i));

                int replaceIndex = result.indexOf('\u0001');
                result = result.substring(0, replaceIndex) + TextUtils.toTextCode(argument.getTransformedValue()) + result.substring(replaceIndex + 1);
            }

            stack.add(new TextStackValue(result));
            return true;
        }

        return false;
    }

    @Override
    public boolean onInvokeMember(String owner, String name, String descriptor, List<IStackValue> stack) {
        switch (owner) {
            case "java/lang/String" -> {
                switch (name + descriptor) {
                    case "concat(Ljava/lang/String;)Ljava/lang/String;" ->
                            stack.add(
                                    new TextStackValue(
                                        TextUtils.toTextCode(
                                                stack.remove(stack.size() - 2).getTransformedValue()
                                        ) + TextUtils.toTextCode(
                                                stack.remove(stack.size() - 1).getTransformedValue()
                                        )
                                    )
                            );
                    case "toLowerCase()Ljava/lang/String;" -> {
                        Variable result = CompilerAddons.getTempVariable();
                        VariableControl.SetCase(result, (IText) stack.remove(stack.size() - 1).getTransformedValue(), Tags.CapitalizationType.LOWERCASE);

                        stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
                    }
                    case "toUpperCase()Ljava/lang/String;" -> {
                        Variable result = CompilerAddons.getTempVariable();
                        VariableControl.SetCase(result, (IText) stack.remove(stack.size() - 1).getTransformedValue(), Tags.CapitalizationType.UPPERCASE);

                        stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
                    }
                    case "replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;" -> {
                        Variable result = CompilerAddons.getTempVariable();

                        VariableControl.ReplaceText(
                                result,
                                (IText) stack.remove(stack.size() - 3).getTransformedValue(),
                                (IText) stack.remove(stack.size() - 2).getTransformedValue(),
                                (IText) stack.remove(stack.size() - 1).getTransformedValue(),
                                Tags.RegularExpressions.DISABLE,
                                Tags.ReplacementType.ALL_OCCURRENCES
                        );

                        stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
                    }
                    case "replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;" -> {
                        Variable result = CompilerAddons.getTempVariable();

                        VariableControl.ReplaceText(
                                result,
                                (IText) stack.remove(stack.size() - 3).getTransformedValue(),
                                (IText) stack.remove(stack.size() - 2).getTransformedValue(),
                                (IText) stack.remove(stack.size() - 1).getTransformedValue(),
                                Tags.RegularExpressions.ENABLE,
                                Tags.ReplacementType.ALL_OCCURRENCES
                        );

                        stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
                    }
                    case "replaceFirst(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;" -> {
                        Variable result = CompilerAddons.getTempVariable();

                        VariableControl.ReplaceText(
                                result,
                                (IText) stack.remove(stack.size() - 3).getTransformedValue(),
                                (IText) stack.remove(stack.size() - 2).getTransformedValue(),
                                (IText) stack.remove(stack.size() - 1).getTransformedValue(),
                                Tags.RegularExpressions.ENABLE,
                                Tags.ReplacementType.FIRST_OCCURRENCE
                        );

                        stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
                    }
                    case "repeat(I)Ljava/lang/String;" -> {
                        Variable result = CompilerAddons.getTempVariable();

                        VariableControl.RepeatText(
                                result,
                                (IText) stack.remove(stack.size() - 2).getTransformedValue(),
                                (INumber) stack.remove(stack.size() - 1).getTransformedValue()
                        );

                        stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
                    }
                    case "substring(II)Ljava/lang/String;" -> {
                        Variable result = CompilerAddons.getTempVariable();

                        VariableControl.TrimText(
                                result,
                                (IText) stack.remove(stack.size() - 3).getTransformedValue(),
                                NumberMath.add((INumber) stack.remove(stack.size() - 2).getTransformedValue(), new Number().Set(1)),
                                (INumber) stack.remove(stack.size() - 1).getTransformedValue()
                        );

                        stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
                    }
                    case "length()I" -> {
                        Variable result = CompilerAddons.getTempVariable();

                        VariableControl.TextLength(
                                result,
                                (IText) stack.remove(stack.size() - 1).getTransformedValue()
                        );

                        stack.add(new VariableStackValue("I", result.getName()));
                    }
                    case "split(Ljava/lang/String;)[Ljava/lang/String;" -> {
                        Variable result = CompilerAddons.getTempVariable();
                        Variable reference = new Variable("_jfdfR%var(" + result.getName() + ")", Variable.Scope.NORMAL);

                        IText splitText = (IText) stack.remove(stack.size() - 2).getTransformedValue();
                        IText splitByText = (IText) stack.remove(stack.size() - 1).getTransformedValue();

                        VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
                        VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), new Text().Set(result.getName()));
                        Functions.Call("_jfdf>std>sallocl");

                        VariableControl.ReplaceText(
                                reference,
                                splitText,
                                splitByText,
                                new Text().Set("\u0001"),
                                Tags.RegularExpressions.ENABLE,
                                Tags.ReplacementType.ALL_OCCURRENCES
                        );

                        VariableControl.SplitText(
                                reference,
                                reference,
                                new Text().Set("\u0001")
                        );

                        VariableControl.InsertListValue(
                                reference,
                                new Number().Set(1),
                                new Text().Set("\0p")
                        );

                        stack.add(new VariableStackValue("[Ljava/lang/String;", result.getName()));
                    }
                    case "equalsIgnoreCase(Ljava/lang/String;)Z",
                            "contains(Ljava/lang/CharSequence;)Z",
                            "matches(Ljava/lang/String;)Z",
                            "startsWith(Ljava/lang/String;)Z",
                            "endsWith(Ljava/lang/String;)Z"->
                            stack.add(
                                    new IfStringStackValue(
                                            switch(name) {
                                                case "equalsIgnoreCase" -> IfStringStackValue.IfType.EQUALS_IGNORE_CASE;
                                                case "matches" -> IfStringStackValue.IfType.EQUALS_REGEX;
                                                case "contains" -> IfStringStackValue.IfType.CONTAINS;
                                                case "startsWith" -> IfStringStackValue.IfType.STARTS_WITH;
                                                case "endsWith" -> IfStringStackValue.IfType.ENDS_WITH;
                                                default -> throw new IllegalStateException("Unexpected value: " + name);
                                            },
                                            new IStackValue[] {
                                                    stack.remove(stack.size() - 2),
                                                    stack.remove(stack.size() - 1)
                                            }
                                    )
                            );
                    default -> throw new UnsupportedOperationException("String." + name + descriptor + " is not supported !");
                }
            }
            case "java/lang/StringBuilder" -> {
                if(name.equals("append") && descriptor.matches("\\(([IJDFZ]|Ljava/lang/String;)\\)Ljava/lang/StringBuilder;")) {
                    IStackValue builderValue = stack.get(stack.size() - 2); // Don't remove because it's the return value
                    Variable stringBuilder = (Variable) builderValue.getTransformedValue();
                    Variable reference = new Variable("_jfdfR%var(" + stringBuilder.getName() + ")", Variable.Scope.NORMAL);

                    VariableControl.AppendValue(reference, stack.remove(stack.size() - 1).getTransformedValue());

                    stack.add(new VariableStackValue("Ljava/lang/StringBuilder;", stringBuilder.getName()));
                } else if(name.equals("toString")) {
                    Variable result = CompilerAddons.getTempVariable();

                    IStackValue builderValue = stack.remove(stack.size() - 1);
                    Variable stringBuilder = (Variable) builderValue.getTransformedValue();
                    Variable builderReference = new Variable("_jfdfR%var(" + stringBuilder.getName() + ")", Variable.Scope.NORMAL);

                    VariableControl.SetListValue(builderReference, new Number().Set(1), new Text().Set(""));
                    VariableControl.JoinText(result, builderReference, new Text().Set(""), null);
                    VariableControl.SetListValue(builderReference, new Number().Set(1), new Text().Set("\0p"));

                    stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
                } else {
                    throw new UnsupportedOperationException("StringBuilder." + name + descriptor + " is not supported !");
                }
            }
            default -> {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean onInvokeStatic(String owner, String name, String descriptor, List<IStackValue> stack) {
        if(owner.equals("java/lang/String")) {
            if(name.equals("join") && descriptor.equals("(Ljava/lang/CharSequence;[Ljava/lang/CharSequence;)Ljava/lang/String;")) {
                Variable result = CompilerAddons.getTempVariable();
                Variable temp = new Variable("tmp0", Variable.Scope.LOCAL);

                IText delimiter = (IText) stack.remove(stack.size() - 2).getTransformedValue();
                Variable arrayPointer = (Variable) stack.remove(stack.size() - 1).getTransformedValue();
                Variable arrayReference = new Variable("_jfdfR%var(" + arrayPointer.getName() + ")", Variable.Scope.NORMAL);

                VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
                VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), new Text().Set(result.getName()));
                Functions.Call("_jfdf>std>sallocl");

                VariableControl.GetListValue(
                        temp,
                        arrayReference,
                        new Number().Set(1)
                );

                VariableControl.RemoveListIndex(
                        arrayReference,
                        new Number().Set(1)
                );

                VariableControl.JoinText(
                        result,
                        arrayReference,
                        delimiter,
                        null
                );

                VariableControl.InsertListValue(
                        arrayReference,
                        new Number().Set(1),
                        temp
                );

                stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
            } else {
                throw new UnsupportedOperationException("String." + name + descriptor + " is not supported !");
            }

            return true;
        }

        return false;
    }

    @Override
    public IfHandler onIf(String defaultType, boolean invert, List<IStackValue> stack) {
        IStackValue ifValue = stack.get(stack.size() - 1);

        if(ifValue instanceof IfStringStackValue) {
            IfStringStackValue ifStringData = (IfStringStackValue) ifValue;
            IStackValue[] stackValues = ifStringData.getStackValues();

            return new IfHandler() {
                @Override
                public void onIf() {
                    CodeManager.instance.addCodeBlock(
                            new IfVariableBlock(getIfAction(), invert)
                                    .SetItems(
                                            stackValues[0].getTransformedValue(),
                                            stackValues[1].getTransformedValue()
                                    ).SetTags(getIfTags())
                    );

                    CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
                }

                @Override
                public void onRepeat() {
                    CodeManager.instance.addCodeBlock(
                            new RepeatBlock(getIfAction(), invert)
                                    .SetItems(
                                            stackValues[0].getTransformedValue(),
                                            stackValues[1].getTransformedValue()
                                    ).SetTags(getIfTags())
                    );

                    CodeManager.instance.addCodeBlock(new BracketBlock(false, true));
                }

                String getIfAction() {
                    return switch (((IfStringStackValue) ifValue).getIfType()) {
                        case EQUALS_IGNORE_CASE,
                                EQUALS_REGEX -> "TextMatches";
                        case CONTAINS -> "Contains";
                        case STARTS_WITH -> "StartsWith";
                        case ENDS_WITH -> "EndsWith";
                    };
                }

                Tag[] getIfTags() {
                    return switch (((IfStringStackValue) ifValue).getIfType()) {
                        case EQUALS_IGNORE_CASE ->
                                new Tag[] {
                                        new Tag("Regular Expressions", "Disable"),
                                        new Tag("Ignore Case", "True")
                        };
                        case EQUALS_REGEX ->
                                new Tag[] {
                                        new Tag("Regular Expressions", "Enable"),
                                        new Tag("Ignore Case", "False")
                                };
                        case CONTAINS, STARTS_WITH, ENDS_WITH ->
                                new Tag[] {
                                        new Tag("Ignore Case", "False")
                                };
                    };
                }
            };
        }

        return null;
    }
}

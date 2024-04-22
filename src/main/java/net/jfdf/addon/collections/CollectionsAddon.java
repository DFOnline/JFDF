package net.jfdf.addon.collections;

import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.addon.IfHandler;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.Repeat;
import net.jfdf.jfdf.mangement.SubIf;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Text;

import java.util.List;

public class CollectionsAddon implements ICompilerAddon {
    @Override
    public boolean onInitClass(String type, List<IStackValue> stack) {
        if(type.equals("java/util/List") || type.equals("java/util/ArrayList")) {
            stack.add(new ListStackValue(CompilerAddons.getTempVariable()));

            return true;
        } else if(type.equals("java/util/HashMap") || type.equals("java/util/LinkedHashMap")) {
            stack.add(new DictStackValue(CompilerAddons.getTempVariable()));

            return true;
        }

        return false;
    }

    @Override
    public boolean onInvokeConstructor(String type, String descriptor, List<IStackValue> stack) {
        if(type.equals("java/util/ArrayList")) {
            switch (descriptor) {
                case "()V" ->
                        VariableControl.CreateList(
                                ((ListStackValue) stack.remove(stack.size() - 1)).getReference(),
                                new Text().Set("\0r")
                        );
                case "(Ljava/util/Collection;)V" ->
                        VariableControl.Set(
                                ((ListStackValue) stack.remove(stack.size() - 2)).getReference(),
                                stack.remove(stack.size() - 1).getTransformedValue()
                        );
                default -> throw new IllegalStateException("Unsupported ArrayList constructor: " + "ArrayList" + descriptor + ".");
            }

            return true;
        } else if(type.equals("java/util/HashMap")
                || type.equals("java/util/LinkedHashMap")) {
            switch (descriptor) {
                case "()V" -> VariableControl.CreateDict(
                        ((DictStackValue) stack.remove(stack.size() - 1)).getReference(),
                        null,
                        null
                );
                case "(Ljava/util/Map;)V" -> VariableControl.Set(
                        ((DictStackValue) stack.remove(stack.size() - 2)).getReference(),
                        stack.remove(stack.size() - 1).getTransformedValue()
                );
                default -> throw new IllegalStateException("Unsupported HashMap/LinkedHashMap constructor: " + "ArrayList" + descriptor + ".");
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean onInvokeMember(String owner, String name, String descriptor, List<IStackValue> stack) {
        return ListMethodHandler.handle(owner, name, descriptor, stack)
                || DictMethodHandler.handle(owner, name, descriptor, stack)
                || IteratorMethodHandler.handle(owner, name, descriptor, stack);
    }

    @Override
    public IfHandler onIf(String defaultType, boolean invert, List<IStackValue> stack) {
        IStackValue ifValue = stack.get(stack.size() - 1);

        if(ifValue instanceof IfIteratorStackValue) {
            IfIteratorStackValue ifIteratorData = (IfIteratorStackValue) ifValue;

            return new IfHandler() {
                @Override
                public void onIf() {
                    If.Variable.LessThanOrEqualTo(
                            Number.AsListValue(ifIteratorData.getIterator(), new Number().Set(2)),
                            Number.AsListValue(ifIteratorData.getIterator(), new Number().Set(3)),
                            false
                    );
                }

                @Override
                public void onRepeat() {
                    Repeat.While(SubIf.Variable.LessThanOrEqualTo(
                            Number.AsListValue(ifIteratorData.getIterator(), new Number().Set(2)),
                            Number.AsListValue(ifIteratorData.getIterator(), new Number().Set(3))
                    ));
                }
            };
        }

        return null;
    }
}

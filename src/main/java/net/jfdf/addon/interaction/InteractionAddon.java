package net.jfdf.addon.interaction;

import net.jfdf.addon.interaction.npe.EntityType;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.data.stack.*;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.mangement.*;
import net.jfdf.jfdf.values.*;
import net.jfdf.jfdf.values.Number;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InteractionAddon implements ICompilerAddon {
    @Override
    public boolean onInitClass(String type, List<IStackValue> stack) {
        if(type.startsWith("net/jfdf/addon/interaction/")) {
            String[] typeSplitted = type.split("/");
            String typeSimpleName = typeSplitted[typeSplitted.length - 1];

            throw new IllegalStateException("Initializing " + typeSimpleName + " class manually is not allowed !");
        }

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
        return onInvoke(owner, name, descriptor, stack);
    }

    @Override
    public boolean onInvokeStatic(String owner, String name, String descriptor, List<IStackValue> stack) {
        return onInvoke(owner, name, descriptor, stack);
    }

    private boolean onInvoke(String owner, String name, String descriptor, List<IStackValue> stack) {
        if(owner.startsWith("net/jfdf/addon/interaction/") && !owner.equals("net/jfdf/addon/interaction/InteractionAddon")) {
            IStackValue thisStackValue = null;

            if(owner.startsWith("net/jfdf/addon/interaction/npe/")
                    || owner.startsWith("net/jfdf/addon/interaction/player")) {
                thisStackValue = stack.remove(stack.size() - Type.getArgumentTypes(descriptor).length - 1);

                if(name.equals("select")) {
                    if(owner.startsWith("net/jfdf/addon/interaction/npe/")) {
                        Selector.EntityName((IText) thisStackValue.getTransformedValue());
                    } else {
                        Selector.PlayerName((IText) thisStackValue.getTransformedValue());
                    }

                    return true;
                } else if(name.equals("unselect")) {
                    Selector.Reset();

                    return true;
                }
            } else if(owner.equals("net/jfdf/addon/interaction/Event")) {
                stack.remove(stack.size() - Type.getArgumentTypes(descriptor).length - 1);
            }

            try {
                Class<?> searchClass = Class.forName(owner.replace('/', '.'));

                searchLoop:
                while (searchClass != Object.class && searchClass != null) {
                    List<Class<?>> checkClasses = new ArrayList<>();

                    checkClasses.add(searchClass);
                    checkClasses.addAll(Arrays.asList(searchClass.getInterfaces()));

                    for (Class<?> checkClass : checkClasses) {
                        if (Arrays.stream(checkClass.getDeclaredMethods())
                                .anyMatch(method -> method.getName().equals(name)
                                        && Type.getMethodDescriptor(method).equals(descriptor))) {
                            Optional<Class<?>> methodHandlerClass = Arrays.stream(checkClass.getDeclaredClasses())
                                    .filter(clazz -> clazz.getSimpleName().equals("MethodHandler"))
                                    .findAny();

                            if (methodHandlerClass.isPresent()) {
                                Method handleMethod = Arrays.stream(methodHandlerClass.get().getDeclaredMethods())
                                        .filter(method -> method.getName().equals(name))
                                        .findAny().orElseThrow();

                                if(handleMethod.getAnnotation(PassThis.class) != null) {
                                    stack.add(thisStackValue);
                                } else {
                                    String handleMethodDescriptor = Type.getMethodDescriptor(handleMethod);

                                    if(thisStackValue != null) {
                                        if (owner.startsWith("net/jfdf/addon/interaction/player/")) {
                                            IText target = (IText) thisStackValue.getTransformedValue();
                                            Selector.PlayerName(target);
                                        } else if (owner.startsWith("net/jfdf/addon/interaction/npe/")) {
                                            IText target = (IText) thisStackValue.getTransformedValue();
                                            Selector.EntityName(target);
                                        }
                                    }

                                    if (handleMethodDescriptor.equals("(Ljava/util/List;)V")) {
                                        handleMethod.invoke(null, stack);
                                    } else if (handleMethodDescriptor.equals("(Ljava/lang/String;Ljava/util/List;)V")) {
                                        handleMethod.invoke(null, descriptor, stack);
                                    } else {
                                        throw new RuntimeException("Couldn't find method handler with correct descriptor for: "
                                                + searchClass.getSimpleName() + "#" + name + descriptor);
                                    }

                                    if(owner.startsWith("net/jfdf/addon/interaction/npe/")
                                            || owner.startsWith("net/jfdf/addon/interaction/player/")) {
                                        Selector.Reset();
                                    }
                                }
                            }

                            break searchLoop;
                        }
                    }

                    searchClass = searchClass.getSuperclass();
                }
            } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            return true;
        }

        return false;
    }
}

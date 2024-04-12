package net.jfdf.addon.interaction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.Selector;
import net.jfdf.jfdf.values.IText;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;

public class InteractionAddon implements ICompilerAddon {
   public boolean onInitClass(String type, List stack) {
      if (type.startsWith("net/jfdf/addon/interaction/")) {
         String[] typeSplitted = type.split("/");
         String typeSimpleName = typeSplitted[typeSplitted.length - 1];
         throw new IllegalStateException("Initializing " + typeSimpleName + " class manually is not allowed !");
      } else {
         return false;
      }
   }

   public boolean onInvokeConstructor(String type, String descriptor, List stack) {
      return false;
   }

   public boolean onInvokeDynamic(String name, String descriptor, Handle methodHandle, Object[] methodArgs, List stack) {
      return false;
   }

   public boolean onInvokeMember(String owner, String name, String descriptor, List stack) {
      return this.onInvoke(owner, name, descriptor, stack);
   }

   public boolean onInvokeStatic(String owner, String name, String descriptor, List stack) {
      return this.onInvoke(owner, name, descriptor, stack);
   }

   private boolean onInvoke(String owner, String name, String descriptor, List stack) {
      if (owner.startsWith("net/jfdf/addon/interaction/") && !owner.equals("net/jfdf/addon/interaction/InteractionAddon")) {
         IStackValue thisStackValue = null;
         if (!owner.startsWith("net/jfdf/addon/interaction/npe/") && !owner.startsWith("net/jfdf/addon/interaction/player")) {
            if (owner.equals("net/jfdf/addon/interaction/Event")) {
               stack.remove(stack.size() - Type.getArgumentTypes(descriptor).length - 1);
            }
         } else {
            thisStackValue = (IStackValue)stack.remove(stack.size() - Type.getArgumentTypes(descriptor).length - 1);
            if (name.equals("select")) {
               if (owner.startsWith("net/jfdf/addon/interaction/npe/")) {
                  Selector.EntityName((IText)thisStackValue.getTransformedValue());
               } else {
                  Selector.PlayerName((IText)thisStackValue.getTransformedValue());
               }

               return true;
            }

            if (name.equals("unselect")) {
               Selector.Reset();
               return true;
            }
         }

         try {
            Class searchClass = Class.forName(owner.replace('/', '.'));

            label78:
            while(searchClass != Object.class && searchClass != null) {
               List checkClasses = new ArrayList();
               checkClasses.add(searchClass);
               checkClasses.addAll(Arrays.asList(searchClass.getInterfaces()));
               Iterator var8 = checkClasses.iterator();

               Class checkClass;
               do {
                  if (!var8.hasNext()) {
                     searchClass = searchClass.getSuperclass();
                     continue label78;
                  }

                  checkClass = (Class)var8.next();
               } while(!Arrays.stream(checkClass.getDeclaredMethods()).anyMatch((method) -> {
                  return method.getName().equals(name) && Type.getMethodDescriptor(method).equals(descriptor);
               }));

               Optional methodHandlerClass = Arrays.stream(checkClass.getDeclaredClasses()).filter((clazz) -> {
                  return clazz.getSimpleName().equals("MethodHandler");
               }).findAny();
               if (methodHandlerClass.isPresent()) {
                  Method handleMethod = (Method)Arrays.stream(((Class)methodHandlerClass.get()).getDeclaredMethods()).filter((method) -> {
                     return method.getName().equals(name);
                  }).findAny().orElseThrow();
                  if (handleMethod.getAnnotation(PassThis.class) != null) {
                     stack.add(thisStackValue);
                  } else {
                     String handleMethodDescriptor = Type.getMethodDescriptor(handleMethod);
                     if (thisStackValue != null) {
                        IText target;
                        if (owner.startsWith("net/jfdf/addon/interaction/player/")) {
                           target = (IText)thisStackValue.getTransformedValue();
                           Selector.PlayerName(target);
                        } else if (owner.startsWith("net/jfdf/addon/interaction/npe/")) {
                           target = (IText)thisStackValue.getTransformedValue();
                           Selector.EntityName(target);
                        }
                     }

                     if (handleMethodDescriptor.equals("(Ljava/util/List;)V")) {
                        handleMethod.invoke((Object)null, stack);
                     } else {
                        if (!handleMethodDescriptor.equals("(Ljava/lang/String;Ljava/util/List;)V")) {
                           throw new RuntimeException("Couldn't find method handler with correct descriptor for: " + searchClass.getSimpleName() + "#" + name + descriptor);
                        }

                        handleMethod.invoke((Object)null, descriptor, stack);
                     }

                     if (owner.startsWith("net/jfdf/addon/interaction/npe/") || owner.startsWith("net/jfdf/addon/interaction/player/")) {
                        Selector.Reset();
                     }
                  }
               }
               break;
            }

            return true;
         } catch (InvocationTargetException | IllegalAccessException | ClassNotFoundException var14) {
            throw new RuntimeException(var14);
         }
      } else {
         return false;
      }
   }
}

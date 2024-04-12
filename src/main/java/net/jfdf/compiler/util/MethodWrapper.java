package net.jfdf.compiler.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import org.objectweb.asm.Type;

public class MethodWrapper {
   private final Method method;
   private final Constructor constructor;
   private final boolean isStaticInitalizer;
   private final String descriptor;

   public MethodWrapper() {
      this.method = null;
      this.constructor = null;
      this.isStaticInitalizer = true;
      this.descriptor = "()V";
   }

   public MethodWrapper(Method method) {
      this.method = method;
      this.constructor = null;
      this.isStaticInitalizer = false;
      this.descriptor = Type.getMethodDescriptor(this.method);
   }

   public MethodWrapper(Constructor constructor) {
      this.method = null;
      this.constructor = constructor;
      this.isStaticInitalizer = false;
      this.descriptor = Type.getConstructorDescriptor(this.constructor);
   }

   public static MethodWrapper getWrapper(Class class_, String methodName, String methodDescriptor) throws NoSuchMethodException {
      Class[] parameterTypes = (Class[])Arrays.stream(Type.getArgumentTypes(methodDescriptor)).map((type) -> {
         try {
            Class var10000;
            switch (type.getInternalName().replace('/', '.')) {
               case "Z":
                  var10000 = Boolean.TYPE;
                  break;
               case "B":
                  var10000 = Byte.TYPE;
                  break;
               case "S":
                  var10000 = Short.TYPE;
                  break;
               case "I":
                  var10000 = Integer.TYPE;
                  break;
               case "J":
                  var10000 = Long.TYPE;
                  break;
               case "F":
                  var10000 = Float.TYPE;
                  break;
               case "D":
                  var10000 = Double.TYPE;
                  break;
               case "C":
                  var10000 = Character.TYPE;
                  break;
               default:
                  var10000 = Class.forName(typeInternalName);
            }

            return var10000;
         } catch (Exception var4) {
            throw new RuntimeException("Something went wrong.", var4);
         }
      }).toArray((x$0) -> {
         return new Class[x$0];
      });
      if (methodName.equals("<init>")) {
         return new MethodWrapper(class_.getDeclaredConstructor(parameterTypes));
      } else {
         return methodName.equals("<clinit>") ? new MethodWrapper() : new MethodWrapper(class_.getDeclaredMethod(methodName, parameterTypes));
      }
   }

   public boolean isConstructor() {
      return this.constructor != null;
   }

   public boolean isStaticInitializer() {
      return this.isStaticInitalizer;
   }

   public boolean isMember() {
      if (this.method != null) {
         return !Modifier.isStatic(this.method.getModifiers());
      } else {
         return this.constructor != null;
      }
   }

   public String getName() {
      if (this.method != null) {
         return this.method.getName();
      } else {
         return this.constructor != null ? "<init>" : "<clinit>";
      }
   }

   public Annotation getAnnotation(Class annotationClass) {
      if (this.method != null) {
         return this.method.getAnnotation(annotationClass);
      } else {
         return this.constructor != null ? this.constructor.getAnnotation(annotationClass) : null;
      }
   }

   public int getModifiers() {
      if (this.method != null) {
         return this.method.getModifiers();
      } else {
         return this.constructor != null ? this.constructor.getModifiers() : 8;
      }
   }

   public String getDescriptor() {
      return this.descriptor;
   }

   public void setAccessible(boolean accessible) {
      if (this.method != null) {
         this.method.setAccessible(accessible);
      } else {
         if (this.constructor == null) {
            throw new IllegalStateException("Couldn't set static initializer accessible.");
         }

         this.constructor.setAccessible(accessible);
      }

   }

   public Object invoke(Object obj, Object... arguments) throws InvocationTargetException, IllegalAccessException, InstantiationException {
      if (this.method != null) {
         return this.method.invoke(obj, arguments);
      } else if (this.constructor != null) {
         return this.constructor.newInstance(arguments);
      } else {
         throw new IllegalStateException("Couldn't invoke static initializer.");
      }
   }
}

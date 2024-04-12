package net.jfdf.compiler.data.stack;

import java.lang.reflect.InvocationTargetException;
import net.jfdf.compiler.util.MethodWrapper;
import net.jfdf.jfdf.values.CodeValue;
import org.objectweb.asm.Type;

public class CodeStackValue implements IStackValue {
   private final Class class_;
   private CodeValue codeValue;

   public CodeStackValue(CodeValue codeValue) {
      this.class_ = codeValue.getClass();
      this.codeValue = codeValue;
   }

   public CodeStackValue(String type) {
      try {
         this.class_ = Class.forName(type.replace('/', '.'));
      } catch (ClassNotFoundException var3) {
         throw new RuntimeException("Something went wrong.", var3);
      }
   }

   public void callConstructor(String descriptor, Object... arguments) {
      try {
         this.codeValue = (CodeValue)MethodWrapper.getWrapper(this.class_, "<init>", descriptor).invoke((Object)null, arguments);
      } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException var4) {
         throw new RuntimeException("Something went wrong.", var4);
      }
   }

   public void invokeMethod(String methodName, String methodDescriptor, Object... arguments) {
      if (!methodDescriptor.endsWith(")V") && !methodDescriptor.endsWith(")L" + Type.getInternalName(this.class_) + ";")) {
         throw new IllegalStateException("Calling code value's method with a return value is not allowed.");
      } else {
         try {
            MethodWrapper.getWrapper(this.class_, methodName, methodDescriptor).invoke(this.codeValue, arguments);
         } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException var5) {
            throw new RuntimeException("Something went wrong.", var5);
         }
      }
   }

   public CodeValue getTransformedValue() {
      return this.codeValue;
   }

   public String getDescriptor() {
      return Type.getDescriptor(this.class_);
   }
}

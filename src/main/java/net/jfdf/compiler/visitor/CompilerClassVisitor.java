package net.jfdf.compiler.visitor;

import net.jfdf.compiler.annotation.MethodFallback;
import net.jfdf.compiler.annotation.NoCompile;
import net.jfdf.compiler.annotation.NoConstructors;
import net.jfdf.compiler.annotation.NoStaticInitializer;
import net.jfdf.compiler.util.FieldsManager;
import net.jfdf.compiler.util.MethodWrapper;
import net.jfdf.compiler.util.MethodsManager;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class CompilerClassVisitor extends ClassVisitor {
   private final Class class_;

   public CompilerClassVisitor(ClassVisitor classVisitor, Class class_) {
      super(589824, classVisitor);
      this.class_ = class_;
   }

   public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
      if (this.class_.getSuperclass() == Enum.class && name.equals("valueOf") && descriptor.equals("(Ljava/lang/String;)L" + Type.getInternalName(this.class_) + ";")) {
         return null;
      } else {
         try {
            MethodWrapper methodWrapper = MethodWrapper.getWrapper(this.class_, name, descriptor);
            if (methodWrapper.getAnnotation(MethodFallback.class) != null) {
               return null;
            } else if (methodWrapper.getAnnotation(NoCompile.class) != null) {
               return null;
            } else if (methodWrapper.isConstructor() && this.class_.getAnnotation(NoConstructors.class) != null) {
               return null;
            } else if (methodWrapper.isStaticInitializer() && this.class_.getAnnotation(NoStaticInitializer.class) != null) {
               return null;
            } else {
               return name.startsWith("lambda$") ? null : new CompilerMethodVisitor(super.visitMethod(access, name, descriptor, signature, exceptions), this.class_, methodWrapper);
            }
         } catch (NoSuchMethodException var7) {
            throw new RuntimeException("Something went wrong.", var7);
         }
      }
   }

   public void visitEnd() {
      if (FieldsManager.hasFieldWithDefaultValue(this.class_) && !MethodsManager.hasStaticInitializer(this.class_)) {
         CompilerMethodVisitor compilerMethodVisitor = new CompilerMethodVisitor(super.visitMethod(8, "<clinit>", "()V", (String)null, (String[])null), this.class_, new MethodWrapper());
         compilerMethodVisitor.visitCode();
         compilerMethodVisitor.visitEnd();
      }

      super.visitEnd();
   }
}

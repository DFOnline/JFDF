package net.jfdf.compiler.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.jfdf.compiler.annotation.NoCompile;
import net.jfdf.compiler.util.FieldsManager;
import net.jfdf.compiler.util.MethodWrapper;
import net.jfdf.compiler.util.MethodsManager;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class CompilerClassAnalyzer extends ClassVisitor {
   private final Map fieldDefaultValues = new HashMap();
   private final Class class_;

   public CompilerClassAnalyzer(ClassVisitor classVisitor, Class class_) {
      super(589824, classVisitor);
      this.class_ = class_;
   }

   public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
      this.fieldDefaultValues.put(name, value);
      return super.visitField(access, name, descriptor, signature, value);
   }

   public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
      try {
         MethodWrapper methodWrapper = MethodWrapper.getWrapper(this.class_, name, descriptor);
         if (methodWrapper.getAnnotation(NoCompile.class) == null) {
            List instructionDataList = new ArrayList();
            MethodsManager.addMethodInstructions(this.class_, name, descriptor, instructionDataList);
            return new CompilerMethodAnalyzer(super.visitMethod(access, name, descriptor, signature, exceptions), instructionDataList);
         }
      } catch (NoSuchMethodException var8) {
         throw new RuntimeException("Something went wrong.", var8);
      }

      return super.visitMethod(access, name, descriptor, signature, exceptions);
   }

   public void visitEnd() {
      FieldsManager.analyzeClassFields(this.class_);
      super.visitEnd();
   }
}

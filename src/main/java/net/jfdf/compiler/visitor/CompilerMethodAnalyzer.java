package net.jfdf.compiler.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.jfdf.compiler.data.instruction.InstructionData;
import net.jfdf.compiler.data.instruction.InstructionType;
import net.jfdf.compiler.data.instruction.IntegerIncrementInstructionData;
import net.jfdf.compiler.data.instruction.JumpInstructionData;
import net.jfdf.compiler.data.instruction.TypeInstructionData;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class CompilerMethodAnalyzer extends MethodVisitor {
   private final List instructionDataList;
   private int instructionIndex = -1;
   private final Map assignToLabels = new HashMap();
   private final Map labelInstructionIndices = new HashMap();

   public CompilerMethodAnalyzer(MethodVisitor methodVisitor, List instructionDataList) {
      super(589824, methodVisitor);
      this.instructionDataList = instructionDataList;
   }

   public void visitCode() {
      super.visitCode();
   }

   public void visitInsn(int opcode) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = opcode;
      instructionData.instructionType = InstructionType.NO_ARGUMENTS;
      this.instructionDataList.add(instructionData);
   }

   public void visitJumpInsn(int opcode, Label label) {
      ++this.instructionIndex;
      JumpInstructionData instructionData = new JumpInstructionData();
      instructionData.instructionOpcode = opcode;
      instructionData.instructionType = InstructionType.JUMP;
      if (!this.assignToLabels.containsKey(label)) {
         this.assignToLabels.put(label, new ArrayList());
      }

      ((List)this.assignToLabels.get(label)).add(instructionData);
      this.instructionDataList.add(instructionData);
   }

   public void visitLabel(Label label) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = -1;
      instructionData.instructionType = InstructionType.LABEL;
      this.labelInstructionIndices.put(label, this.instructionIndex);
      this.instructionDataList.add(instructionData);
   }

   public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = -2;
      instructionData.instructionType = InstructionType.FRAME;
      this.instructionDataList.add(instructionData);
   }

   public void visitLineNumber(int line, Label start) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = -3;
      instructionData.instructionType = InstructionType.LINE_NUMBER;
      this.instructionDataList.add(instructionData);
   }

   public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = opcode;
      instructionData.instructionType = InstructionType.FIELD;
      this.instructionDataList.add(instructionData);
   }

   public void visitVarInsn(int opcode, int var) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = opcode;
      instructionData.instructionType = InstructionType.VARIABLE;
      this.instructionDataList.add(instructionData);
   }

   public void visitIntInsn(int opcode, int operand) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = opcode;
      instructionData.instructionType = InstructionType.INTEGER;
      this.instructionDataList.add(instructionData);
   }

   public void visitIincInsn(int var, int increment) {
      ++this.instructionIndex;
      IntegerIncrementInstructionData instructionData = new IntegerIncrementInstructionData();
      instructionData.var = var;
      instructionData.increment = increment;
      instructionData.instructionOpcode = 132;
      instructionData.instructionType = InstructionType.INTEGER_INCREMENT;
      this.instructionDataList.add(instructionData);
   }

   public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = opcode;
      instructionData.instructionType = InstructionType.INVOKE;
      this.instructionDataList.add(instructionData);
   }

   public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = 186;
      instructionData.instructionType = InstructionType.INVOKE_DYNAMIC;
      this.instructionDataList.add(instructionData);
   }

   public void visitLdcInsn(Object value) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = 18;
      instructionData.instructionType = InstructionType.LDC;
      this.instructionDataList.add(instructionData);
   }

   public void visitTypeInsn(int opcode, String type) {
      ++this.instructionIndex;
      TypeInstructionData instructionData = new TypeInstructionData();
      instructionData.type = type;
      instructionData.instructionOpcode = opcode;
      instructionData.instructionType = InstructionType.TYPE;
      this.instructionDataList.add(instructionData);
   }

   public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = 197;
      instructionData.instructionType = InstructionType.MULTI_ANEW_ARRAY;
      this.instructionDataList.add(instructionData);
   }

   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = -4;
      instructionData.instructionType = InstructionType.TRY_CATCH;
      this.instructionDataList.add(instructionData);
   }

   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = -5;
      instructionData.instructionType = InstructionType.TABLE_SWITCH;
      this.instructionDataList.add(instructionData);
   }

   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = -6;
      instructionData.instructionType = InstructionType.LOOKUP_SWITCH;
      this.instructionDataList.add(instructionData);
   }

   public void visitMaxs(int maxStack, int maxLocals) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = -7;
      instructionData.instructionType = InstructionType.MAXS;
      this.instructionDataList.add(instructionData);
   }

   public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
      ++this.instructionIndex;
      InstructionData instructionData = new InstructionData();
      instructionData.instructionOpcode = -8;
      instructionData.instructionType = InstructionType.LOCAL_VARIABLE;
      this.instructionDataList.add(instructionData);
   }

   public void visitEnd() {
      Iterator var1 = this.assignToLabels.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry entry = (Map.Entry)var1.next();
         int labelInstructionIndex = (Integer)this.labelInstructionIndices.get(entry.getKey());
         List jumpInstructions = (List)entry.getValue();

         JumpInstructionData instructionData;
         for(Iterator var5 = jumpInstructions.iterator(); var5.hasNext(); instructionData.jumpToInstructionIndex = labelInstructionIndex) {
            instructionData = (JumpInstructionData)var5.next();
         }
      }

      super.visitEnd();
   }
}

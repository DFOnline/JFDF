package net.jfdf.compiler.data.stack;

import net.jfdf.jfdf.blocks.SetVariableBlock;
import net.jfdf.jfdf.mangement.CodeManager;
import net.jfdf.jfdf.mangement.Functions;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;

public abstract class ReferencedStackValue implements IStackValue {
   protected final Text allocationMethod = (new Text()).Set("_jfdf>std>sallocl");
   protected final Text allocationVariableName;
   protected final Variable pointer;
   protected final Variable reference;
   private boolean resetDone = false;
   private final int resetIndex;

   protected ReferencedStackValue(String methodName, int blockOperatorIndex) {
      this.allocationVariableName = (new Text()).Set("_jco>" + methodName + ">" + blockOperatorIndex);
      this.pointer = new Variable("_jco>" + methodName + ">" + blockOperatorIndex, Variable.Scope.LOCAL);
      this.reference = new Variable("_jfdfR%var(_jco>" + methodName + ">" + blockOperatorIndex + ")", Variable.Scope.NORMAL);
      this.resetIndex = CodeManager.instance.getActiveCodeBlocks().size();
      VariableControl.Set(new Variable("_jfdfPFD", Variable.Scope.LOCAL), new Variable("_jfdfFD", Variable.Scope.LOCAL));
      VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
      VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), this.allocationVariableName);
      Functions.Call(this.allocationMethod);
   }

   protected ReferencedStackValue(Variable variable) {
      this.allocationVariableName = (new Text()).Set(variable.getName());
      this.pointer = variable;
      this.reference = new Variable("_jfdfR%var(" + variable.getName() + ")", Variable.Scope.NORMAL);
      this.resetIndex = CodeManager.instance.getActiveCodeBlocks().size();
      VariableControl.Set(new Variable("_jfdfPFD", Variable.Scope.LOCAL), new Variable("_jfdfFD", Variable.Scope.LOCAL));
      VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
      VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), this.allocationVariableName);
      Functions.Call(this.allocationMethod);
   }

   public void setAllocationVariable(String name, Variable.Scope scope) {
      if (scope.equals(Variable.Scope.SAVED)) {
         throw new IllegalStateException("Cannot allocate saved variable.");
      } else {
         this.allocationVariableName.Set(name.replace("%var(_jfdfFD)", "%var(_jfdfPFD)"));
         this.reference.setName("_jfdfR%var(" + name + ")");
         this.pointer.setName(name);
         this.pointer.setScope(scope);
         this.allocationMethod.Set(scope.equals(Variable.Scope.NORMAL) ? "_jfdf>std>salloc" : "_jfdf>std>sallocl");
      }
   }

   public void resetBeforeAllocate() {
      if (!this.resetDone) {
         CodeManager.instance.getActiveCodeBlocks().add(this.resetIndex, (new SetVariableBlock("=")).SetItems(this.pointer, (new Number()).Set(0)));
         this.resetDone = true;
      }

   }

   public Text getAllocationMethod() {
      return this.allocationMethod;
   }

   public Text getAllocationVariableName() {
      return this.allocationVariableName;
   }

   public CodeValue getTransformedValue() {
      return this.pointer;
   }

   public Variable getReference() {
      return this.reference;
   }
}

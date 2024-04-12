package net.jfdf.compiler.data.stack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.jfdf.compiler.util.ReferenceUtils;
import net.jfdf.jfdf.blocks.SetVariableBlock;
import net.jfdf.jfdf.mangement.CodeManager;
import net.jfdf.jfdf.mangement.Repeat;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.GameValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;
import net.jfdf.transformer.util.NumberMath;

public class ArrayStackValue extends ReferencedStackValue {
   private final String descriptor;
   private final List dataList;

   public ArrayStackValue(String descriptor, IStackValue length, String methodName, int blockOperatorIndex) {
      super(methodName, blockOperatorIndex);
      this.descriptor = "[" + descriptor;
      if (!(length instanceof NumberStackValue)) {
         VariableControl.CreateList(this.reference, ReferenceUtils.isReferenceDescriptor(descriptor) ? (new Text()).Set("\u0000r") : (new Text()).Set("\u0000p"));
         Repeat.MultipleTimes((Variable)null, (INumber)length.getTransformedValue());
         VariableControl.AppendValue(this.reference, (new Number()).Set(0));
         Repeat.End();
         this.dataList = null;
      } else {
         int arrayLength = ((NumberStackValue)length).getJavaValue().intValue();
         this.dataList = Arrays.asList();
         Collections.fill(this.dataList, (new Number()).Set(0));
         this.dataList.set(0, this.reference);
         this.dataList.set(1, ReferenceUtils.isReferenceDescriptor(descriptor) ? (new Text()).Set("\u0000r") : (new Text()).Set("\u0000p"));
         CodeManager.instance.addCodeBlock((new SetVariableBlock("CreateList")).SetItems(this.dataList.subList(0, Math.min(27, this.dataList.size()))));

         for(int i = 27; i < this.dataList.size(); i += 27) {
            this.dataList.set(i, this.reference);
            CodeManager.instance.addCodeBlock((new SetVariableBlock("AppendValue")).SetItems(this.dataList.subList(i, Math.min(i + 27, this.dataList.size()))));
         }

      }
   }

   public ArrayStackValue(Variable array, String methodName, int blockOperatorIndex) {
      super(methodName, blockOperatorIndex);
      this.descriptor = "[Ljava/lang/Object;";
      VariableControl.Set(this.reference, array);
      this.dataList = null;
   }

   public ArrayStackValue(Variable variable, Variable array) {
      super(variable);
      this.descriptor = "[Ljava/lang/Object;";
      VariableControl.Set(this.reference, array);
      this.dataList = null;
   }

   public void set(IStackValue indexValue, CodeValue value) {
      if (this.dataList != null && indexValue instanceof NumberStackValue && !(value instanceof Variable) && !(value instanceof GameValue)) {
         int index = ((NumberStackValue)indexValue).getJavaValue().intValue() + 1;
         this.dataList.set(index + (int)Math.floor((double)((float)index / 26.0F)) + 1, value);
      } else {
         VariableControl.SetListValue(this.reference, NumberMath.add((INumber)indexValue.getTransformedValue(), (new Number()).Set(2)), value);
      }

   }

   public String getDescriptor() {
      return this.descriptor;
   }
}

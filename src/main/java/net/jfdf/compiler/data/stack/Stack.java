package net.jfdf.compiler.data.stack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.jfdf.compiler.util.ReferenceUtils;
import net.jfdf.jfdf.values.Variable;

public class Stack extends ArrayList {
   private final List tempValueUses = new ArrayList();

   public boolean add(IStackValue stackValue) {
      this.onAddElement(stackValue);
      return super.add(stackValue);
   }

   public IStackValue remove(int index) {
      IStackValue removedValue = (IStackValue)super.remove(index);
      this.onRemoveElement(removedValue);
      return removedValue;
   }

   public IStackValue set(int index, IStackValue element) {
      IStackValue removedValue = (IStackValue)super.set(index, element);
      this.onRemoveElement(removedValue);
      this.onAddElement(element);
      return removedValue;
   }

   public void onVisitInsn(int instructionOpcode) {
      Iterator iterator = this.tempValueUses.iterator();

      while(iterator.hasNext()) {
         TempValueData tempValueData = (TempValueData)iterator.next();
         String variableName = tempValueData.variable.getName();
         if (!variableName.startsWith("_jco")) {
            iterator.remove();
            return;
         }

         if (tempValueData.uses == 0) {
            ReferenceUtils.decrementIfReference(tempValueData.descriptor, tempValueData.variable);
            iterator.remove();
         }
      }

   }

   private void onAddElement(IStackValue element) {
      if (element instanceof VariableStackValue || element instanceof ReferencedStackValue) {
         String variableName = ((Variable)element.getTransformedValue()).getName();
         if (variableName.startsWith("_jco")) {
            boolean tempValueDataFound = false;
            Iterator var4 = this.tempValueUses.iterator();

            while(var4.hasNext()) {
               TempValueData tempValueData = (TempValueData)var4.next();
               if (tempValueData.variable.getName().equals(variableName)) {
                  ++tempValueData.uses;
                  tempValueDataFound = true;
                  break;
               }
            }

            if (!tempValueDataFound) {
               this.tempValueUses.add(new TempValueData(element));
            }
         }
      }

   }

   private void onRemoveElement(IStackValue removedValue) {
      if (removedValue instanceof VariableStackValue || removedValue instanceof ReferencedStackValue) {
         String variableName = ((Variable)removedValue.getTransformedValue()).getName();
         if (variableName.startsWith("_jco")) {
            Iterator var3 = this.tempValueUses.iterator();

            while(var3.hasNext()) {
               TempValueData tempValueData = (TempValueData)var3.next();
               if (tempValueData.variable.getName().equals(variableName)) {
                  --tempValueData.uses;
                  break;
               }
            }
         }
      }

   }

   private static class TempValueData {
      public Variable variable;
      public String descriptor;
      public int uses = 1;

      public TempValueData(IStackValue variableStackValue) {
         this.variable = (Variable)variableStackValue.getTransformedValue();
         this.descriptor = variableStackValue.getDescriptor();
      }
   }
}

package net.jfdf.compiler.data.stack;

import net.jfdf.compiler.util.ReferenceUtils;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;

import java.util.*;

public class Stack extends ArrayList<IStackValue> {
    private final List<TempValueData> tempValueUses = new ArrayList<>();

    @Override
    public boolean add(IStackValue stackValue) {
        onAddElement(stackValue);

        return super.add(stackValue);
    }

    @Override
    public IStackValue remove(int index) {
        IStackValue removedValue = super.remove(index);

        onRemoveElement(removedValue);

        return removedValue;
    }

    @Override
    public IStackValue set(int index, IStackValue element) {
        IStackValue removedValue = super.set(index, element);

        onRemoveElement(removedValue);
        onAddElement(element);

        return removedValue;
    }

    public void onVisitInsn(int instructionOpcode) {
        Iterator<TempValueData> iterator = tempValueUses.iterator();

        while(iterator.hasNext()) {
            TempValueData tempValueData = iterator.next();
            String variableName = tempValueData.variable.getName();

            if(!variableName.startsWith("_jco")) {
                iterator.remove();
                return;
            }

            if(tempValueData.uses == 0) {
                ReferenceUtils.decrementIfReference(tempValueData.descriptor, tempValueData.variable);

                iterator.remove();
            }
        }
    }

    private void onAddElement(IStackValue element) {
        if(element instanceof VariableStackValue
                || element instanceof ReferencedStackValue) {
            String variableName = ((Variable) element.getTransformedValue()).getName();

            if(variableName.startsWith("_jco")) {
                boolean tempValueDataFound = false;

                for(TempValueData tempValueData : tempValueUses) {
                    if(tempValueData.variable.getName().equals(variableName)) {
                        tempValueData.uses += 1;
                        tempValueDataFound = true;

                        break;
                    }
                }

                if(!tempValueDataFound) {
                    tempValueUses.add(new TempValueData(element));
                }
            }
        }
    }

    private void onRemoveElement(IStackValue removedValue) {
        if(removedValue instanceof VariableStackValue
                || removedValue instanceof ReferencedStackValue) {
            String variableName = ((Variable) removedValue.getTransformedValue()).getName();

            if(variableName.startsWith("_jco")) {
                for(TempValueData tempValueData : tempValueUses) {
                    if(tempValueData.variable.getName().equals(variableName)) {
                        tempValueData.uses -= 1;

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
            this.variable = (Variable) variableStackValue.getTransformedValue();
            this.descriptor = variableStackValue.getDescriptor();
        }
    }
}

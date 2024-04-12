package net.jfdf.jfdf.values;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import net.jfdf.jfdf.mangement.CodeManager;
import net.jfdf.jfdf.mangement.VariableControl;

public class List extends Variable {
   public static ListMode listMode;
   private int getIndex = 0;
   private int indexOfIndex = 0;
   private int addedElementsSinceLastLength = 0;
   private GetLengthState getLengthState;

   public List() {
      super("list" + (new Random()).nextInt(1000000), Variable.Scope.LOCAL);
      this.getLengthState = List.GetLengthState.NO_LENGTH;
      VariableControl.CreateList(this);
   }

   public List(Variable list) {
      super(list);
      this.getLengthState = List.GetLengthState.NO_LENGTH;
   }

   public List(String name) {
      super(name, Variable.Scope.LOCAL);
      this.getLengthState = List.GetLengthState.NO_LENGTH;
      VariableControl.CreateList(this);
   }

   public List(Variable.Scope scope) {
      super("list" + (new Random()).nextInt(1000000), scope);
      this.getLengthState = List.GetLengthState.NO_LENGTH;
      VariableControl.CreateList(this);
   }

   public List(String name, Variable.Scope scope) {
      super(name, scope);
      this.getLengthState = List.GetLengthState.NO_LENGTH;
      VariableControl.CreateList(this);
   }

   public List(CodeValue... contents) {
      super("list" + (new Random()).nextInt(1000000), Variable.Scope.LOCAL);
      this.getLengthState = List.GetLengthState.NO_LENGTH;
      if (contents.length >= 27) {
         throw new IllegalArgumentException("You can't create a list with more than 26 elements in one code block.");
      } else {
         VariableControl.CreateList(this, contents);
      }
   }

   public List(String name, CodeValue... contents) {
      super(name, Variable.Scope.LOCAL);
      this.getLengthState = List.GetLengthState.NO_LENGTH;
      if (contents.length >= 27) {
         throw new IllegalArgumentException("You can't create a list with more than 26 elements in one code block.");
      } else {
         VariableControl.CreateList(this, contents);
      }
   }

   public List(Variable.Scope scope, CodeValue... contents) {
      super("list" + (new Random()).nextInt(1000000), scope);
      this.getLengthState = List.GetLengthState.NO_LENGTH;
      if (contents.length >= 27) {
         throw new IllegalArgumentException("You can't create a list with more than 26 elements in one code block.");
      } else {
         VariableControl.CreateList(this, contents);
      }
   }

   public List(String name, Variable.Scope scope, CodeValue... contents) {
      super(name, scope);
      this.getLengthState = List.GetLengthState.NO_LENGTH;
      if (contents.length >= 27) {
         throw new IllegalArgumentException("You can't create a list with more than 26 elements in one code block.");
      } else {
         VariableControl.CreateList(this, contents);
      }
   }

   public List addZeros(int zerosAmount) {
      CodeValue[] fillArray = new CodeValue[26];
      Arrays.fill(fillArray, (new Number()).Set(0.0F));

      for(int i = 0; i < zerosAmount; i += 26) {
         if (zerosAmount - i < 26) {
            fillArray = new CodeValue[zerosAmount - i];
            Arrays.fill(fillArray, (new Number()).Set(0.0F));
         }

         this.add(fillArray);
      }

      return this;
   }

   public List add(CodeValue... values) {
      if (values.length >= 27) {
         throw new IllegalArgumentException("You can't add more than 26 elements in one code block.");
      } else {
         VariableControl.AppendValue(this, values);
         return this;
      }
   }

   public List add(Object value) {
      if (value instanceof CodeValue) {
         this.add((CodeValue)value);
      }

      return this;
   }

   public List addAll(Variable... lists) {
      if (lists.length >= 27) {
         throw new IllegalArgumentException("You can't add more than 26 lists in one code block.");
      } else {
         VariableControl.AppendList(this, (Variable[])Arrays.stream(lists).map(List::new).toArray((x$0) -> {
            return new List[x$0];
         }));
         if (listMode == List.ListMode.CPU_EFFICIENT && this.getLengthState != List.GetLengthState.NO_LENGTH) {
            Variable lengthVariable = CodeManager.instance.getVariable(this.getName() + "_length", Variable.Scope.LOCAL);
            ArrayList lengthVariables = new ArrayList();
            Variable[] var4 = lists;
            int var5 = lists.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Variable listVariable = var4[var6];
               Variable listLengthVariable = CodeManager.instance.getVariable(listVariable.getName() + "_length", Variable.Scope.LOCAL);
               VariableControl.ListLength(listLengthVariable, new List(listVariable));
               lengthVariables.add(listLengthVariable);
            }

            VariableControl.Increment(lengthVariable, (INumber[])lengthVariables.toArray(new INumber[0]));
         } else {
            this.getLengthState = List.GetLengthState.NEEDS_UPDATE_ADD_ALL;
         }

         return this;
      }
   }

   public List remove(CodeValue... values) {
      if (values.length >= 27) {
         throw new IllegalArgumentException("You can't add more than 26 elements in one code block.");
      } else {
         VariableControl.RemoveListValue(this, values);
         return this;
      }
   }

   public List remove(Object value) {
      if (value instanceof CodeValue) {
         this.remove((CodeValue)value);
      }

      return this;
   }

   public List removeAt(INumber... indices) {
      VariableControl.RemoveListIndex(this, indices);
      return this;
   }

   public Variable get(Variable valueVariable, INumber index) {
      VariableControl.GetListValue(valueVariable, this, index);
      return new List(valueVariable);
   }

   public List get(INumber index) {
      Variable valueVariable = CodeManager.instance.getVariable(this.getName() + "_value" + this.getIndex, Variable.Scope.LOCAL);
      VariableControl.GetListValue(valueVariable, this, index);
      ++this.getIndex;
      return new List(valueVariable);
   }

   public List get(int index) {
      return this.get(Number.New().Set(index));
   }

   public void set(INumber index, CodeValue value) {
      VariableControl.SetListValue(this, index, value);
   }

   public Variable length() {
      Variable valueVariable = CodeManager.instance.getVariable(this.getName() + "_length", Variable.Scope.LOCAL);
      if (this.getLengthState != List.GetLengthState.NO_LENGTH && (this.getLengthState != List.GetLengthState.NEEDS_UPDATE_ADD_ALL || listMode == List.ListMode.CPU_EFFICIENT) && (this.getLengthState != List.GetLengthState.NEEDS_UPDATE_ADD || listMode != List.ListMode.LESS_SPACE)) {
         if (this.getLengthState == List.GetLengthState.NEEDS_UPDATE_ADD && listMode != List.ListMode.LESS_SPACE) {
            VariableControl.Increment(valueVariable, Number.New().Set(this.addedElementsSinceLastLength));
            this.addedElementsSinceLastLength = 0;
         }
      } else {
         VariableControl.ListLength(valueVariable, this);
         this.getLengthState = List.GetLengthState.FOUND;
         this.addedElementsSinceLastLength = 0;
      }

      return valueVariable;
   }

   public Variable size() {
      return this.length();
   }

   public Variable length(Variable valueVariable) {
      VariableControl.ListLength(valueVariable, this);
      return valueVariable;
   }

   public Variable indexOf(CodeValue value) {
      Variable valueVariable = CodeManager.instance.getVariable(this.getName() + "_index" + this.indexOfIndex, Variable.Scope.LOCAL);
      VariableControl.GetValueIndex(valueVariable, this, value, Tags.SearchOrder.ASCENDING__FIRST_INDEX_);
      ++this.indexOfIndex;
      return valueVariable;
   }

   public Variable lastIndexOf(CodeValue value) {
      Variable valueVariable = CodeManager.instance.getVariable(this.getName() + "_index" + this.indexOfIndex, Variable.Scope.LOCAL);
      VariableControl.GetValueIndex(valueVariable, this, value, Tags.SearchOrder.DESCENDING__LAST_INDEX_);
      ++this.indexOfIndex;
      return valueVariable;
   }

   public Variable indexOf(Variable valueVariable, CodeValue value) {
      VariableControl.GetValueIndex(valueVariable, this, value, Tags.SearchOrder.ASCENDING__FIRST_INDEX_);
      return valueVariable;
   }

   public Variable lastIndexOf(Variable valueVariable, CodeValue value) {
      VariableControl.GetValueIndex(valueVariable, this, value, Tags.SearchOrder.DESCENDING__LAST_INDEX_);
      return valueVariable;
   }

   public List randomize() {
      VariableControl.RandomizeList(this, (Variable)null);
      return this;
   }

   public String toString() {
      String var10000 = this.getName();
      return "List{name='" + var10000 + "', scope=" + this.getScope() + "}";
   }

   static {
      listMode = List.ListMode.BOTH;
   }

   private static enum GetLengthState {
      NO_LENGTH,
      NEEDS_UPDATE_ADD,
      NEEDS_UPDATE_ADD_ALL,
      FOUND;
   }

   public static enum ListMode {
      CPU_EFFICIENT,
      LESS_SPACE,
      BOTH;
   }
}

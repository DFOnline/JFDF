package net.jfdf.transformer.util;

import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Variable;

public class TransformedList extends Variable {
   public TransformedList(Variable list) {
      super(list);
      this.showError();
   }

   public TransformedList(String name) {
      super(name, Variable.Scope.LOCAL);
      this.showError();
   }

   public TransformedList add(CodeValue... values) {
      this.showError();
      return this;
   }

   public TransformedList add(Object... values) {
      this.showError();
      return this;
   }

   public TransformedList addAll(Variable... lists) {
      this.showError();
      return this;
   }

   public TransformedList remove(CodeValue... values) {
      this.showError();
      return this;
   }

   public TransformedList remove(Object... value) {
      this.showError();
      return this;
   }

   public TransformedList removeAt(int... indices) {
      this.showError();
      return this;
   }

   public TransformedList removeAt(INumber... indices) {
      this.showError();
      return this;
   }

   public Object get(int index) {
      this.showError();
      return null;
   }

   public Object get(INumber index) {
      this.showError();
      return null;
   }

   public TransformedList set(INumber index, CodeValue value) {
      this.showError();
      return this;
   }

   public TransformedList set(int index, Object value) {
      this.showError();
      return this;
   }

   public int size() {
      this.showError();
      return -1;
   }

   private void showError() {
      throw new RuntimeException("You can only use this in a transformable code.");
   }
}

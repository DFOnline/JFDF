package net.jfdf.jfdf;

import net.jfdf.jfdf.mangement.CodeManager;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;

public abstract class CodeClass {
   public CodeManager cm;

   public CodeClass() {
      this.cm = CodeManager.instance;
   }

   public Text text(String value) {
      return Text.New().Set(value);
   }

   public Text txt(String value) {
      return this.text(value);
   }

   public Number number(int value) {
      return Number.New().Set(value);
   }

   public Number num(int value) {
      return this.number(value);
   }

   public Number number(float value) {
      return Number.New().Set(value);
   }

   public Number num(float value) {
      return this.number(value);
   }

   public Variable normalVariable(String name) {
      return this.cm.getVariable(name, Variable.Scope.NORMAL);
   }

   public Variable nvar(String name) {
      return this.normalVariable(name);
   }

   public Variable savedVariable(String name) {
      return this.cm.getVariable(name, Variable.Scope.SAVED);
   }

   public Variable svar(String name) {
      return this.savedVariable(name);
   }

   public Variable localVariable(String name) {
      return this.cm.getVariable(name, Variable.Scope.LOCAL);
   }

   public Variable lvar(String name) {
      return this.localVariable(name);
   }
}

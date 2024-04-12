package net.jfdf.transformer.classes;

import net.jfdf.compiler.annotation.CompileWithExecute;
import net.jfdf.jfdf.mangement.FunctionWithArgs;
import net.jfdf.jfdf.mangement.Functions;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.Repeat;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.List;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;
import net.jfdf.transformer.ExportClass;
import net.jfdf.transformer.NoTransform;
import net.jfdf.transformer.annotations.NoConstructor;

@ExportClass
@NoConstructor
public class ClassBaseContainer {
   private ClassBaseContainer() {
   }

   @FunctionWithArgs(
      name = "_jfdf>std>invokeVirtual",
      iconId = "honey_block",
      iconNbt = "Enchantments:[{id:\"minecraft:lure\",lvl:1s}],HideFlags:1,display:{Lore:['{\"extra\":[{\"text\":\"  \"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"yellow\",\"text\":\"Part of JFDF Standard Library\"}],\"text\":\"\"}'],Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"gold\",\"text\":\"Invoke Virtual\"}],\"text\":\"\"}'}"
   )
   @NoTransform
   @CompileWithExecute
   public static void invokeVirtual(Variable args) {
      List args_ = new List(args);
      Variable pointer = args_.get(new Variable("var0", Variable.Scope.LOCAL), (new Number()).Set(1.0F));
      Variable classInternalName = (new List(new Variable("_jfdfR%var(" + pointer.getName() + ")", Variable.Scope.NORMAL))).get(new Variable("var1", Variable.Scope.LOCAL), (new Number()).Set(1.0F));
      Variable methodName = args_.get(new Variable("var2", Variable.Scope.LOCAL), (new Number()).Set(2.0F));
      Variable methodDescriptor = args_.get(new Variable("var3", Variable.Scope.LOCAL), (new Number()).Set(3.0F));
      Variable var10000 = new Variable("tmp0", Variable.Scope.LOCAL);
      Variable var10001 = new Variable("_jfdfc>%var(" + classInternalName.getName() + ")>methods", Variable.Scope.NORMAL);
      Text var10002 = new Text();
      String var10003 = methodName.getName();
      VariableControl.GetDictValue(var10000, var10001, var10002.Set("%var(" + var10003 + ")>%var(" + methodDescriptor.getName() + ")"));
      VariableControl.Set(new Variable("_function_%var(" + methodName.getName() + ")_arg0", Variable.Scope.LOCAL), pointer);
      Variable i = new Variable("i", Variable.Scope.LOCAL);
      Variable arg = new Variable("arg", Variable.Scope.LOCAL);
      VariableControl.Set(i, (new Number()).Set(-2.0F));
      Repeat.ForEach(arg, args);
      If.Variable.GreaterThanOrEqualTo(i, (new Number()).Set(1.0F), false);
      VariableControl.Set(new Variable("_function_%var(" + methodName.getName() + ")_arg%var(i)", Variable.Scope.LOCAL), arg);
      If.End();
      VariableControl.Increment(i, (new Number()).Set(1.0F));
      Repeat.End();
      String var8 = methodName.getName();
      Functions.Call("_jfdf>%var(tmp0)>%var(" + var8 + ")>%var(" + methodDescriptor.getName() + ")");
   }
}

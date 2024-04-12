package net.jfdf.addon.interaction.npe.property;

import java.util.List;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;

public interface IHasBabyVariant {
   default void setBaby(boolean baby) {
   }

   public static class MethodHandler {
      public static void setBaby(List stack) {
         INumber baby = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         if (baby instanceof Number && ((Number)baby).isConstant()) {
            String value = ((Number)baby).getValue();
            Entity.getCurrentSelection().SetBaby(value.equals("0") ? Tags.Baby.DISABLE : Tags.Baby.ENABLE);
         } else {
            If.Variable.Equals(baby, new CodeValue[]{(new Number()).Set(0)}, false);
            Entity.getCurrentSelection().SetBaby(Tags.Baby.DISABLE);
            If.Else();
            Entity.getCurrentSelection().SetBaby(Tags.Baby.ENABLE);
            If.End();
         }
      }
   }
}

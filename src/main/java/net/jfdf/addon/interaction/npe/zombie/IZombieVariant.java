package net.jfdf.addon.interaction.npe.zombie;

import java.util.List;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;

public interface IZombieVariant {
   default void setArmsRaised(boolean raised) {
   }

   public static class MethodHandler {
      public static void setArmsRaised(List stack) {
         INumber raised = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         if (raised instanceof Number && ((Number)raised).isConstant()) {
            String value = ((Number)raised).getValue();
            Entity.getCurrentSelection().SetArmsRaised(value.equals("0") ? Tags.ArmsRaised.DISABLE : Tags.ArmsRaised.ENABLE);
         } else {
            If.Variable.Equals(raised, new CodeValue[]{(new Number()).Set(0)}, false);
            Entity.getCurrentSelection().SetArmsRaised(Tags.ArmsRaised.DISABLE);
            If.Else();
            Entity.getCurrentSelection().SetArmsRaised(Tags.ArmsRaised.ENABLE);
            If.End();
         }
      }
   }
}

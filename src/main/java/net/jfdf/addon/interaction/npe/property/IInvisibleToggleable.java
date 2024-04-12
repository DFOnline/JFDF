package net.jfdf.addon.interaction.npe.property;

import java.util.List;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;

public interface IInvisibleToggleable {
   default void setInvisibility(boolean invisibility) {
   }

   public static class MethodHandler {
      public static void setInvisibility(List stack) {
         INumber gravity = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         if (gravity instanceof Number && ((Number)gravity).isConstant()) {
            String value = ((Number)gravity).getValue();
            Entity.getCurrentSelection().SetInvisible(value.equals("0") ? Tags.Invisible.DISABLE : Tags.Invisible.ENABLE);
         } else {
            If.Variable.Equals(gravity, new CodeValue[]{(new Number()).Set(0)}, false);
            Entity.getCurrentSelection().SetInvisible(Tags.Invisible.DISABLE);
            If.Else();
            Entity.getCurrentSelection().SetInvisible(Tags.Invisible.ENABLE);
            If.End();
         }
      }
   }
}

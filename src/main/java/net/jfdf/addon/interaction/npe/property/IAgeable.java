package net.jfdf.addon.interaction.npe.property;

import java.util.List;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Tags;

public interface IAgeable {
   default void setAge(int age) {
   }

   public static class MethodHandler {
      public static void setAge(List stack) {
         INumber age = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Entity.getCurrentSelection().SetAge(age, Tags.AgeLock.DON_T_CHANGE);
      }
   }
}

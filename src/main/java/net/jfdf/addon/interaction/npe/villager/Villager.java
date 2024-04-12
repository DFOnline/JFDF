package net.jfdf.addon.interaction.npe.villager;

import java.util.List;
import net.jfdf.addon.interaction.npe.EntityType;
import net.jfdf.addon.interaction.npe.LivingNPE;
import net.jfdf.addon.interaction.npe.property.IAgeable;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.values.INumber;

@EntityType(
   eggId = "villager_spawn_egg"
)
public class Villager extends LivingNPE implements IVillagerVariant, IAgeable {
   public void setVillagerExperience(int experience) {
   }

   public static class MethodHandler {
      public void setVillagerExperience(List stack) {
         INumber experience = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Entity.getCurrentSelection().SetVillagerExp(experience);
      }
   }
}

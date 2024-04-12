package net.jfdf.addon.interaction;

import java.util.List;
import net.jfdf.addon.interaction.npe.EntityType;
import net.jfdf.addon.interaction.npe.LivingNPE;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.stack.ClassStackValue;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.mangement.Game;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.GameValue;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IPotion;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Item;
import net.jfdf.jfdf.values.Location;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Variable;

public class World {
   public void setBlock(String blockId, Location location) {
   }

   public void setRegion(String blockId, Location start, Location end) {
   }

   public void breakBlock(Location location) {
   }

   public String getBlockId(Location location) {
      return "<Block Id>";
   }

   public LivingNPE spawnMob(Location location, Class mobClass) {
      return null;
   }

   public static class MethodHandler {
      public static void setBlock(List stack) {
         IText blockId = (IText)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
         ILocation location = (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Game.SetBlock(blockId, new ILocation[]{location});
      }

      public static void setRegion(List stack) {
         IText blockId = (IText)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue();
         ILocation start = (ILocation)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
         ILocation end = (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Game.SetRegion(blockId, start, end, (IText)null);
      }

      public static void breakBlock(List stack) {
         ILocation location = (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Game.BreakBlock(location);
      }

      public static void getBlockId(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         ILocation location = (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         VariableControl.GetBlockType(result, location, Tags.ReturnValueType.ITEM_ID__GOLDEN_APPLE_);
         stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
      }

      public static void spawnMob(List stack) {
         ILocation location = (ILocation)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
         Class clazz = ((ClassStackValue)stack.remove(stack.size() - 1)).getClazz();
         EntityType entityType = (EntityType)clazz.getAnnotation(EntityType.class);
         if (entityType != null) {
            Variable result = CompilerAddons.getTempVariable();
            String eggId = entityType.eggId();
            Game.SpawnMob(new Item(eggId), location, (INumber)null, (IText)null, (IPotion[])null);
            VariableControl.Set(result, new GameValue("UUID", EntityActionBlock.EntitySelection.LAST_ENTITY_SPAWNED));
            stack.add(new VariableStackValue(clazz.descriptorString(), result.getName()));
         } else {
            throw new RuntimeException("Incorrect entity type: " + clazz.getSimpleName());
         }
      }
   }
}

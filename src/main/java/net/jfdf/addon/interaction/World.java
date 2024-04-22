package net.jfdf.addon.interaction;

import net.jfdf.addon.interaction.npe.EntityType;
import net.jfdf.addon.interaction.npe.LivingNPE;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.stack.ClassStackValue;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.mangement.Game;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.*;

import java.util.List;

public class World {
    public World() {}

    public void setBlock(String blockId, Location location) {}
    public void setRegion(String blockId, Location start, Location end) {}

    public void breakBlock(Location location) {}

    public String getBlockId(Location location) { return "<Block Id>"; }

    public <T extends LivingNPE> T spawnMob(Location location, Class<T> mobClass) { return null; }

    public static class MethodHandler {
        public static void setBlock(List<IStackValue> stack) {
            IText blockId = (IText) stack.remove(stack.size() - 2).getTransformedValue();
            ILocation location = (ILocation) stack.remove(stack.size() - 1).getTransformedValue();

            Game.SetBlock(blockId, new ILocation[] { location });
        }

        public static void setRegion(List<IStackValue> stack) {
            IText blockId = (IText) stack.remove(stack.size() - 3).getTransformedValue();
            ILocation start = (ILocation) stack.remove(stack.size() - 2).getTransformedValue();
            ILocation end = (ILocation) stack.remove(stack.size() - 1).getTransformedValue();

            Game.SetRegion(blockId, start, end, null);
        }

        public static void breakBlock(List<IStackValue> stack) {
            ILocation location = (ILocation) stack.remove(stack.size() - 1).getTransformedValue();
            Game.BreakBlock(location);
        }

        public static void getBlockId(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            ILocation location = (ILocation) stack.remove(stack.size() - 1).getTransformedValue();

            VariableControl.GetBlockType(result, location, Tags.ReturnValueType.ITEM_ID__GOLDEN_APPLE_);
            stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
        }

        public static void spawnMob(List<IStackValue> stack) {
            ILocation location = (ILocation) stack.remove(stack.size() - 2).getTransformedValue();
            Class<?> clazz = ((ClassStackValue) stack.remove(stack.size() - 1)).getClazz();

            EntityType entityType = clazz.getAnnotation(EntityType.class);

            if(entityType != null) {
                Variable result = CompilerAddons.getTempVariable();
                String eggId = entityType.eggId();

                Game.SpawnMob(new Item(eggId), location, null, null, null);
                VariableControl.Set(result, new GameValue("UUID", EntityActionBlock.EntitySelection.LAST_ENTITY_SPAWNED));

                stack.add(new VariableStackValue(clazz.descriptorString(), result.getName()));
            } else {
                throw new RuntimeException("Incorrect entity type: " + clazz.getSimpleName());
            }
        }
    }
}

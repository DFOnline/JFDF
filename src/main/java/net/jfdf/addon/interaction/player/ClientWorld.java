package net.jfdf.addon.interaction.player;

import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.Player;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Tags;

import java.util.List;

public class ClientWorld {
    public ClientWorld() {}

    public void setBlock(String blockId, ILocation location) {}
    public void setBlockBreakStage(int stage, ILocation location) {}

    public static class MethodHandler {
        public static void setBlock(List<IStackValue> stack) {
            IText blockId = (IText) stack.remove(stack.size() - 2).getTransformedValue();
            ILocation location = (ILocation) stack.remove(stack.size() - 1).getTransformedValue();

            Player.getCurrentSelection().DisplayBlock(blockId, location, null);
        }

        public static void setBlockBreakStage(List<IStackValue> stack) {
            INumber stage = (INumber) stack.remove(stack.size() - 2).getTransformedValue();
            ILocation location = (ILocation) stack.remove(stack.size() - 1).getTransformedValue();

            Player.getCurrentSelection().DisplayFracture(new ILocation[] { location }, stage, Tags.OverwritePreviousFracture.TRUE);
        }
    }
}

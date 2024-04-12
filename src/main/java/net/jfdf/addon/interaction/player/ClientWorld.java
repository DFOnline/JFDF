package net.jfdf.addon.interaction.player;

import java.util.List;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Tags;

public class ClientWorld {
   public void setBlock(String blockId, ILocation location) {
   }

   public void setBlockBreakStage(int stage, ILocation location) {
   }

   public static class MethodHandler {
      public static void setBlock(List stack) {
         IText blockId = (IText)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
         ILocation location = (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().DisplayBlock(blockId, location, (ILocation)null);
      }

      public static void setBlockBreakStage(List stack) {
         INumber stage = (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
         ILocation location = (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().DisplayFracture(new ILocation[]{location}, stage, Tags.OverwritePreviousFracture.TRUE);
      }
   }
}

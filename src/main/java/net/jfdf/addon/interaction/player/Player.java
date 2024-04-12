package net.jfdf.addon.interaction.player;

import java.util.List;
import net.jfdf.addon.interaction.PassThis;
import net.jfdf.addon.interaction.npe.NonPlayerEntity;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.GameValue;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.ISound;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Variable;

public class Player {
   public void sendMessage(String message) {
   }

   public void sendActionbar(String message) {
   }

   public void playSound(ISound sound) {
   }

   public void sendTitle(String title) {
   }

   public void sendTitle(String title, String subtitle) {
   }

   public void sendTitle(String title, String subtitle, int stayTicks, int fadeInTicks, int fadeOutTicks) {
   }

   public void teleport(ILocation location) {
   }

   public void ridePlayer(Player player) {
   }

   public void rideNPE(NonPlayerEntity entity) {
   }

   public ClientWorld getClientWorld() {
      return new ClientWorld();
   }

   public PlayerInventory getInventory() {
      return new PlayerInventory();
   }

   public String getName() {
      return "";
   }

   public String getUUID() {
      return "";
   }

   public void select() {
   }

   public void unselect() {
   }

   public static class MethodHandler {
      public static void sendMessage(List stack) {
         IText message = (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().SendMessage(new CodeValue[]{message}, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.NO_SPACES);
      }

      public static void sendActionbar(List stack) {
         IText actionbar = (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().ActionBar(new IText[]{actionbar}, Tags.TextValueMerging.NO_SPACES);
      }

      public static void playSound(List stack) {
         ISound sound = (ISound)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().PlaySound(new ISound[]{sound}, (ILocation)null, Tags.SoundSource.MASTER);
      }

      public static void sendTitle(String descriptor, List stack) {
         IText title;
         if (descriptor.equals("(Ljava/lang/String;)V")) {
            title = (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
            net.jfdf.jfdf.mangement.Player.getCurrentSelection().SendTitle(title, (IText)null, (INumber)null, (INumber)null, (INumber)null);
         } else {
            IText subtitle;
            if (descriptor.equals("(Ljava/lang/String;Ljava/lang/String;)V")) {
               title = (IText)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
               subtitle = (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
               net.jfdf.jfdf.mangement.Player.getCurrentSelection().SendTitle(title, subtitle, (INumber)null, (INumber)null, (INumber)null);
            } else {
               title = (IText)((IStackValue)stack.remove(stack.size() - 5)).getTransformedValue();
               subtitle = (IText)((IStackValue)stack.remove(stack.size() - 4)).getTransformedValue();
               INumber stay = (INumber)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue();
               INumber fadeIn = (INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
               INumber fadeOut = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
               net.jfdf.jfdf.mangement.Player.getCurrentSelection().SendTitle(title, subtitle, stay, fadeIn, fadeOut);
            }
         }

      }

      public static void teleport(List stack) {
         ILocation location = (ILocation)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().Teleport(location, Tags.KeepCurrentRotation.FALSE);
      }

      public static void ridePlayer(List stack) {
         IText player = (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().RideEntity(player);
      }

      public static void rideNPE(List stack) {
         ridePlayer(stack);
      }

      @PassThis
      public static void getClientWorld() {
      }

      @PassThis
      public static void getInventory() {
      }

      public static void getName(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.Set(result, new GameValue("Name", EntityActionBlock.EntitySelection.CURRENT_SELECTION));
         stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
      }

      public static void getUUID(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.Set(result, new GameValue("UUID", EntityActionBlock.EntitySelection.CURRENT_SELECTION));
         stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
      }
   }
}

package net.jfdf.addon.interaction;

import java.util.List;
import net.jfdf.addon.interaction.player.Player;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.TextStackValue;
import net.jfdf.jfdf.mangement.Control;
import net.jfdf.jfdf.mangement.Game;
import net.jfdf.jfdf.values.IItem;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.ISound;
import net.jfdf.jfdf.values.Tags;

public class Event {
   public Player getPlayer() {
      return new Player();
   }

   public Player getKiller() {
      return new Player();
   }

   public Player getDamager() {
      return new Player();
   }

   public Player getShooter() {
      return new Player();
   }

   public Player getVictim() {
      return new Player();
   }

   public void sleep(int ticks) {
   }

   public void cancel() {
   }

   public void uncancel() {
   }

   public void setDamage(int damage) {
   }

   public void setHeal(int heal) {
   }

   public void setProjectile(IItem projectile) {
   }

   public void setSound(ISound sound) {
   }

   public void setExperience(int experience) {
   }

   public static class MethodHandler {
      public static void getPlayer(List stack) {
         stack.add(new TextStackValue("%default"));
      }

      public static void getKiller(List stack) {
         stack.add(new TextStackValue("%killer"));
      }

      public static void getDamager(List stack) {
         stack.add(new TextStackValue("%damager"));
      }

      public static void getShooter(List stack) {
         stack.add(new TextStackValue("%shooter"));
      }

      public static void getVictim(List stack) {
         stack.add(new TextStackValue("%victim"));
      }

      public static void sleep(List stack) {
         INumber ticks = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Control.Wait(ticks, Tags.TimeUnit.TICKS);
      }

      public static void cancel(List stack) {
         Game.CancelEvent();
      }

      public static void uncancel(List stack) {
         Game.UncancelEvent();
      }

      public static void setDamage(List stack) {
         INumber damage = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Game.SetEventDamage(damage);
      }

      public static void setHeal(List stack) {
         INumber heal = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Game.SetEventHeal(heal);
      }

      public static void setProjectile(List stack) {
         IItem projectile = (IItem)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Game.SetEventProj(projectile);
      }

      public static void setSound(List stack) {
         ISound sound = (ISound)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Game.SetEventSound(sound);
      }

      public static void setExperience(List stack) {
         INumber experience = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Game.SetEventXP(experience);
      }
   }
}

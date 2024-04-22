package net.jfdf.addon.interaction;

import net.jfdf.addon.interaction.player.Player;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.NumberStackValue;
import net.jfdf.compiler.data.stack.TextStackValue;
import net.jfdf.jfdf.mangement.Control;
import net.jfdf.jfdf.mangement.Game;
import net.jfdf.jfdf.values.*;

import java.util.List;

public class Event {
    public Event() {}

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

    public void sleep(int ticks) {}

    public void cancel() {}
    public void uncancel() {}

    public void setDamage(int damage) {}
    public void setHeal(int heal) {}
    public void setProjectile(IItem projectile) {}
    public void setSound(ISound sound) {}
    public void setExperience(int experience) {}

    public static class MethodHandler {
        public static void getPlayer(List<IStackValue> stack) {
            stack.add(new TextStackValue("%default"));
        }

        public static void getKiller(List<IStackValue> stack) {
            stack.add(new TextStackValue("%killer"));
        }

        public static void getDamager(List<IStackValue> stack) {
            stack.add(new TextStackValue("%damager"));
        }

        public static void getShooter(List<IStackValue> stack) {
            stack.add(new TextStackValue("%shooter"));
        }

        public static void getVictim(List<IStackValue> stack) {
            stack.add(new TextStackValue("%victim"));
        }

        public static void sleep(List<IStackValue> stack) {
            INumber ticks = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
            Control.Wait(ticks, Tags.TimeUnit.TICKS);
        }

        public static void cancel(List<IStackValue> stack) {
            Game.CancelEvent();
        }

        public static void uncancel(List<IStackValue> stack) {
            Game.UncancelEvent();
        }

        public static void setDamage(List<IStackValue> stack) {
            INumber damage = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
            Game.SetEventDamage(damage);
        }

        public static void setHeal(List<IStackValue> stack) {
            INumber heal = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
            Game.SetEventHeal(heal);
        }

        public static void setProjectile(List<IStackValue> stack) {
            IItem projectile = (IItem) stack.remove(stack.size() - 1).getTransformedValue();
            Game.SetEventProj(projectile);
        }

        public static void setSound(List<IStackValue> stack) {
            ISound sound = (ISound) stack.remove(stack.size() - 1).getTransformedValue();
            Game.SetEventSound(sound);
        }

        public static void setExperience(List<IStackValue> stack) {
            INumber experience = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
            Game.SetEventXP(experience);
        }
    }
}

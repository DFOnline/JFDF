package net.jfdf.addon.interaction.player;

import net.jfdf.addon.interaction.PassThis;
import net.jfdf.addon.interaction.npe.NonPlayerEntity;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.*;

import java.util.List;

public class Player {
    public Player() {}

    public void sendMessage(String message) {}
    public void sendActionbar(String message) {}
    public void playSound(ISound sound) {}

    public void sendTitle(String title) {}
    public void sendTitle(String title, String subtitle) {}
    public void sendTitle(String title, String subtitle, int stayTicks, int fadeInTicks, int fadeOutTicks) {}

    public void teleport(ILocation location) {}
    public void ridePlayer(Player player) {}
    public void rideNPE(NonPlayerEntity entity) {}

    public ClientWorld getClientWorld() { return new ClientWorld(); }
    public PlayerInventory getInventory() { return new PlayerInventory(); }

    public String getName() { return ""; }
    public String getUUID() { return ""; }

    /**
     * Uses Select Object to select the player
     *
     * @apiNote You should never use this unless you need to access an action
     * isn't supported by Interaction API, make sure to use {@link Player#unselect()}
     * after you finish
     * @see Player#unselect()
     */
    public void select() {}

    /**
     * Uses Select Object: Reset
     *
     * @apiNote You should never use this unless you needed to call {@link Player#select()}
     * @see Player#select()
     */
    public void unselect() {}

    public static class MethodHandler {
        public static void sendMessage(List<IStackValue> stack) {
            IText message = (IText) stack.remove(stack.size() - 1).getTransformedValue();
            net.jfdf.jfdf.mangement.Player.getCurrentSelection().SendMessage(new CodeValue[] { message }, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.NO_SPACES);
        }

        public static void sendActionbar(List<IStackValue> stack) {
            IText actionbar = (IText) stack.remove(stack.size() - 1).getTransformedValue();
            net.jfdf.jfdf.mangement.Player.getCurrentSelection().ActionBar(new IText[] { actionbar }, Tags.TextValueMerging.NO_SPACES);
        }

        public static void playSound(List<IStackValue> stack) {
            ISound sound = (ISound) stack.remove(stack.size() - 1).getTransformedValue();
            net.jfdf.jfdf.mangement.Player.getCurrentSelection().PlaySound(new ISound[] { sound }, null, Tags.SoundSource.MASTER);
        }

        public static void sendTitle(String descriptor, List<IStackValue> stack) {
            if(descriptor.equals("(Ljava/lang/String;)V")) {
                IText title = (IText) stack.remove(stack.size() - 1).getTransformedValue();
                net.jfdf.jfdf.mangement.Player.getCurrentSelection().SendTitle(title, null, null, null, null);
            } else if(descriptor.equals("(Ljava/lang/String;Ljava/lang/String;)V")) {
                IText title = (IText) stack.remove(stack.size() - 2).getTransformedValue();
                IText subtitle = (IText) stack.remove(stack.size() - 1).getTransformedValue();

                net.jfdf.jfdf.mangement.Player.getCurrentSelection().SendTitle(title, subtitle, null, null, null);
            } else {
                IText title = (IText) stack.remove(stack.size() - 5).getTransformedValue();
                IText subtitle = (IText) stack.remove(stack.size() - 4).getTransformedValue();
                INumber stay = (INumber) stack.remove(stack.size() - 3).getTransformedValue();
                INumber fadeIn = (INumber) stack.remove(stack.size() - 2).getTransformedValue();
                INumber fadeOut = (INumber) stack.remove(stack.size() - 1).getTransformedValue();

                net.jfdf.jfdf.mangement.Player.getCurrentSelection().SendTitle(title, subtitle, stay, fadeIn, fadeOut);
            }
        }

        public static void teleport(List<IStackValue> stack) {
            ILocation location = (ILocation) stack.remove(stack.size() - 1).getTransformedValue();
            net.jfdf.jfdf.mangement.Player.getCurrentSelection().Teleport(location, Tags.KeepCurrentRotation.FALSE);
        }

        public static void ridePlayer(List<IStackValue> stack) {
            IText player = (IText) stack.remove(stack.size() - 1).getTransformedValue();
            net.jfdf.jfdf.mangement.Player.getCurrentSelection().RideEntity(player);
        }

        public static void rideNPE(List<IStackValue> stack) {
            // Same code, just different API type
            ridePlayer(stack);
        }

        @PassThis
        public static void getClientWorld() {}

        @PassThis
        public static void getInventory() {}

        public static void getName(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.Set(result, new GameValue("Name", EntityActionBlock.EntitySelection.CURRENT_SELECTION));

            stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
        }

        public static void getUUID(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.Set(result, new GameValue("UUID", EntityActionBlock.EntitySelection.CURRENT_SELECTION));

            stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
        }
    }
}

package net.jfdf.addon.interaction.npe;

import net.jfdf.addon.interaction.player.Player;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.*;
import net.jfdf.jfdf.values.Number;

import java.util.List;

public class NonPlayerEntity {
    /**
     * Sets entity's name.
     *
     * @see NonPlayerEntity#getName()
     * @see NonPlayerEntity#setNameVisibility(boolean)
     * @param name New Entity Name
     */
    public void setName(String name) {}

    /**
     * This method is used to get entity's name.
     *
     * @see NonPlayerEntity#getName()
     * @return Entity's Name
     */
    public String getName() { return ""; }

    /**
     * Sets whether entity's name should
     * be visible or not.
     *
     * @see NonPlayerEntity#getName()
     * @see NonPlayerEntity#setName(String) 
     * @param visibility Name Tag Visibility
     */
    public void setNameVisibility(boolean visibility) {}

    /**
     * This method is used to get entity's unique
     * user identifier (UUID).
     *
     * @return Entity's Unique User Identifier (UUID)
     */
    public String getUUID() { return ""; }

    /**
     * Sets whether entity's movement should
     * be affected by gravity or not.
     *
     * @param gravity Enable Gravity
     */
    public void setGravityEnabled(boolean gravity) {}

    public static class MethodHandler {
        public static void setName(List<IStackValue> stack) {
            IText name = (IText) stack.remove(stack.size() - 1).getTransformedValue();
            Entity.getCurrentSelection().SetName(name, Tags.HideNameTag.DON_T_CHANGE);
        }

        public static void getName(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.Set(result, new GameValue("Name", EntityActionBlock.EntitySelection.CURRENT_SELECTION));

            stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
        }

        public static void setNameVisibility(List<IStackValue> stack) {
            INumber visibility = (INumber) stack.remove(stack.size() - 1).getTransformedValue();

            if(visibility instanceof Number && ((Number) visibility).isConstant()) {
                String value = ((Number) visibility).getValue();

                Entity.getCurrentSelection().SetNameVisible(value.equals("0")
                        ? Tags.NameTagVisible.DISABLE : Tags.NameTagVisible.ENABLE);

                return;
            }

            If.Variable.Equals(visibility, new CodeValue[] { new Number().Set(0) }, false);
                Entity.getCurrentSelection().SetNameVisible(Tags.NameTagVisible.DISABLE);
            If.Else();
                Entity.getCurrentSelection().SetNameVisible(Tags.NameTagVisible.ENABLE);
            If.End();
        }

        public static void getUUID(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.Set(result, new GameValue("UUID", EntityActionBlock.EntitySelection.CURRENT_SELECTION));

            stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
        }

        public static void setGravityEnabled(List<IStackValue> stack) {
            INumber gravity = (INumber) stack.remove(stack.size() - 1).getTransformedValue();

            if(gravity instanceof Number && ((Number) gravity).isConstant()) {
                String value = ((Number) gravity).getValue();

                Entity.getCurrentSelection().SetGravity(value.equals("0")
                        ? Tags.Gravity.DISABLE : Tags.Gravity.ENABLE);

                return;
            }

            If.Variable.Equals(gravity, new CodeValue[] { new Number().Set(0) }, false);
                Entity.getCurrentSelection().SetGravity(Tags.Gravity.DISABLE);
            If.Else();
                Entity.getCurrentSelection().SetGravity(Tags.Gravity.ENABLE);
            If.End();
        }

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

    }
}

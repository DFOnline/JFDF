package net.jfdf.addon.interaction.npe;

import net.jfdf.addon.interaction.npe.property.IInvisibleToggleable;
import net.jfdf.addon.interaction.npe.villager.IVillagerVariant;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.mangement.Functions;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.*;
import net.jfdf.jfdf.values.Number;

import java.util.List;

public class LivingNPE extends NonPlayerEntity implements IInvisibleToggleable {
    /**
     * Heals an entity to maximum health.
     * 
     * @see LivingNPE#heal(int)
     * @see LivingNPE#setHealth(int)
     */
    public void heal() {}

    /**
     * Heals an entity by amount of heal, 
     * if heal amount is too big then
     * it heals to maximum health. 
     *
     * @see LivingNPE#heal()
     * @see LivingNPE#setHealth(int) 
     * @param health Amount to heal
     */
    public void heal(int health) {}

    /**
     * Damages entity's by amount of damage.
     *
     * @see LivingNPE#heal()
     * @see LivingNPE#heal(int)
     * @see LivingNPE#setHealth(int)
     * @param damage Amount of damage
     */
    public void damage(int damage) {}

    /**
     * Sets entity's current health to
     * a value between 0 and entity's maximum health.
     *
     * @see LivingNPE#getHealth()
     * @see LivingNPE#setMaximumHealth(int)
     * @param health New Health
     */
    public void setHealth(int health) {}

    /**
     * Gets entity's health between
     * 0 and maximum health.
     *
     * @see LivingNPE#setHealth(int)
     * @see LivingNPE#setMaximumHealth(int)
     * @return Current Health
     */
    public int getHealth() { return 0; }


    /**
     * Sets entity's current absorption health
     * (yellow hearts).
     *
     * @see LivingNPE#getAbsorptionHealth()
     * @see LivingNPE#setHealth(int)
     * @param health New Absorption Health
     */
    public void setAbsorptionHealth(int health) {}

    /**
     * This method is used to get entity's 
     * absorption health.
     *
     * @see LivingNPE#setAbsorptionHealth(int)
     * @see LivingNPE#getHealth()
     * @return Current Absorption Health
     */
    public int getAbsorptionHealth() { return 0; }

    /**
     * Sets entity's maximum health.
     *
     * @see LivingNPE#getMaximumHealth() 
     * @see LivingNPE#setHealth(int)
     * @param health New Maximum Health
     */
    public void setMaximumHealth(int health) {}

    /**
     * This method is used to get entity's
     * absorption health.
     *
     * @see LivingNPE#setMaximumHealth(int)
     * @see LivingNPE#getHealth() 
     * @return Current Absorption Health
     */
    public int getMaximumHealth() { return 0; }

    /**
     * Adds a potion effect to entity's
     * potion effects.
     *
     * @see LivingNPE#removePotionEffect(IPotion)
     * @see LivingNPE#clearPotionEffects()
     * @see LivingNPE#getPotionEffects()
     * @param potion Potion Effect to give
     */
    public void givePotionEffect(IPotion potion) {}

    /**
     * Removes a potion effect from entity's
     * potion effects.
     *
     * @see LivingNPE#clearPotionEffects()
     * @see LivingNPE#givePotionEffect(IPotion)
     * @see LivingNPE#getPotionEffects()
     * @param potion Potion Effect to remove
     */
    public void removePotionEffect(IPotion potion) {}

    /**
     * Removes all potions effects from entity.
     *
     * @see LivingNPE#removePotionEffect(IPotion)
     * @see LivingNPE#givePotionEffect(IPotion)
     * @see LivingNPE#getPotionEffects()
     */
    public void clearPotionEffects() {}

    /**
     * This method is used to get entity's
     * active potion effects.
     *
     * @see LivingNPE#givePotionEffect(IPotion)
     * @see LivingNPE#removePotionEffect(IPotion)
     * @see LivingNPE#clearPotionEffects()
     * @return Active Potion Effects
     */
    public IPotion[] getPotionEffects() { return new IPotion[0]; }

    /**
     * Sets entity's remaining ticks until
     * being able to take damage.
     *
     * @see LivingNPE#getInvulnerabilityTicks() 
     * @param ticks New Invulnerability Ticks
     */
    public void setInvulnerabilityTicks(int ticks) {}

    /**
     * Gets entity's remaining ticks until
     * being able to take damage.
     *
     * @see LivingNPE#setInvulnerabilityTicks(int)
     * @return Current Invulnerability Ticks
     */
    public int getInvulnerabilityTicks() { return 0; }

    /**
     * Sets entity's fall distance which
     * effects entity's fall damage.
     *
     * @see LivingNPE#getFallDistance() 
     * @param distance New Fall Distance
     */
    public void setFallDistance(float distance) {}

    /**
     * Gets entity's fall distance,
     * this is set to 0 upon landing
     *
     * @see LivingNPE#setFallDistance(float)
     * @return Current Fall Distance
     */
    public int getFallDistance() { return 0; }

    /**
     * Sets how entity's AI should
     * behave.
     *
     * @param ai Artificial Intelligence (AI) Mode
     */
    public void setAIMode(AIMode ai) {}

    /**
     * Sets whether entity should
     * produce sounds or not.
     *
     * @param silence Silence
     */
    public void setSilenced(boolean silence) {}

    public enum AIMode {
        /**
         * The entity doesn't act by itself, nor affected by gravity.
         */
        NONE(0),
        /**
         * The entity doesn't act by itself, however it's affected by gravity.
         */
        INSENTIENT(1),
        /**
         * The entity acts normally.
         */
        SENTIENT(2);

        private final int id;

        AIMode(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public static class MethodHandler {
        public static void heal(String descriptor, List<IStackValue> stack) {
            if(descriptor.equals("()V")) {
                Entity.getCurrentSelection().Heal(null);
            } else {
                INumber heal = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
                Entity.getCurrentSelection().Heal(heal);
            }
        }

        public static void damage(List<IStackValue> stack) {
            INumber damage = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
            Entity.getCurrentSelection().Damage(damage);
        }

        public static void setHealth(List<IStackValue> stack) {
            INumber health = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
            Entity.getCurrentSelection().SetHealth(health);
        }

        public static void getHealth(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.Set(result, new GameValue(GameValue.Value.CURRENT_HEALTH, EntityActionBlock.EntitySelection.CURRENT_SELECTION));

            stack.add(new VariableStackValue("I", result.getName()));
        }

        public static void setAbsorptionHealth(List<IStackValue> stack) {
            INumber health = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
            Entity.getCurrentSelection().SetAbsorption(health);
        }

        public static void getAbsorptionHealth(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.Set(result, new GameValue("Absorption Health", EntityActionBlock.EntitySelection.CURRENT_SELECTION));

            stack.add(new VariableStackValue("I", result.getName()));
        }

        public static void setMaximumHealth(List<IStackValue> stack) {
            INumber health = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
            Entity.getCurrentSelection().SetMaxHealth(health, Tags.HealMobtoMaxHealth.FALSE);
        }

        public static void getMaximumHealth(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.Set(result, new GameValue(GameValue.Value.MAX_HEALTH, EntityActionBlock.EntitySelection.CURRENT_SELECTION));

            stack.add(new VariableStackValue("I", result.getName()));
        }

        public static void givePotionEffect(List<IStackValue> stack) {
            IPotion potion = (IPotion) stack.remove(stack.size() - 1).getTransformedValue();
            Entity.getCurrentSelection().GivePotion(new IPotion[] { potion }, Tags.EffectParticles.NONE, Tags.OverwriteEffect.TRUE);
        }

        public static void removePotionEffect(List<IStackValue> stack) {
            IPotion potion = (IPotion) stack.remove(stack.size() - 1).getTransformedValue();
            Entity.getCurrentSelection().RemovePotion(potion);
        }

        public static void clearPotionEffects(List<IStackValue> stack) {
            Entity.getCurrentSelection().ClearPotions();
        }

        public static void getPotionEffects(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            Variable reference = new Variable("_jfdfR%var(" + result.getName() + ")", Variable.Scope.NORMAL);

            VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
            VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), new Text().Set(result.getName()));
            Functions.Call("_jfdf>std>sallocl");

            VariableControl.Set(reference, new GameValue("Potion Effects", EntityActionBlock.EntitySelection.CURRENT_SELECTION));
            VariableControl.InsertListValue(reference, new Number().Set(1), new Text().Set("\0p"));

            stack.add(new VariableStackValue("[Lnet/jfdf/jfdf/values/IPotion;", result.getName()));
        }

        public void setInvulnerabilityTicks(List<IStackValue> stack) {
            INumber ticks = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
            Entity.getCurrentSelection().SetInvulTicks(ticks);
        }

        public static void getInvulnerabilityTicks(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.Set(result, new GameValue("Invulnerability Ticks", EntityActionBlock.EntitySelection.CURRENT_SELECTION));

            stack.add(new VariableStackValue("I", result.getName()));
        }

        public void setFallDistance(List<IStackValue> stack) {
            INumber distance = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
            Entity.getCurrentSelection().SetFallDistance(distance);
        }

        public static void getFallDistance(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.Set(result, new GameValue("Fall Distance"));

            stack.add(new VariableStackValue("I", result.getName()));
        }

        public static void setAIEnabled(List<IStackValue> stack) {
            INumber aiMode = (INumber) stack.remove(stack.size() - 1).getTransformedValue();

            if(aiMode instanceof Number && ((Number) aiMode).isConstant()) {
                String value = ((Number) aiMode).getValue();

                Entity.getCurrentSelection().SetAI(
                        switch(value) {
                            case "0" -> Tags.AI.NONE;
                            case "1" -> Tags.AI.INSENTIENT;
                            case "2" -> Tags.AI.SENTIENT;
                            default -> throw new IllegalStateException("Unknown Villager Profession ID: " + value);
                        }
                );

                return;
            }

            for (AIMode value : AIMode.values()) {
                If.Variable.Equals(aiMode, new CodeValue[] { new Number().Set(value.getId()) }, false);
                    Entity.getCurrentSelection().SetAI(Tags.AI.valueOf(value.name()));
                If.End();
            }
        }

        public static void setSilenced(List<IStackValue> stack) {
            INumber silence = (INumber) stack.remove(stack.size() - 1).getTransformedValue();

            if(silence instanceof Number && ((Number) silence).isConstant()) {
                String value = ((Number) silence).getValue();

                Entity.getCurrentSelection().SetSilenced(value.equals("0")
                        ? Tags.Silenced.DISABLE : Tags.Silenced.ENABLE);

                return;
            }

            If.Variable.Equals(silence, new CodeValue[] { new Number().Set(0) }, false);
                Entity.getCurrentSelection().SetSilenced(Tags.Silenced.DISABLE);
            If.Else();
                Entity.getCurrentSelection().SetSilenced(Tags.Silenced.ENABLE);
            If.End();
        }
    }
}

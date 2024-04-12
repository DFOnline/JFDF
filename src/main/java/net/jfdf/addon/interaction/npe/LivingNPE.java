package net.jfdf.addon.interaction.npe;

import java.util.List;
import net.jfdf.addon.interaction.npe.property.IInvisibleToggleable;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.mangement.Functions;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.GameValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IPotion;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;

public class LivingNPE extends NonPlayerEntity implements IInvisibleToggleable {
   public void heal() {
   }

   public void heal(int health) {
   }

   public void damage(int damage) {
   }

   public void setHealth(int health) {
   }

   public int getHealth() {
      return 0;
   }

   public void setAbsorptionHealth(int health) {
   }

   public int getAbsorptionHealth() {
      return 0;
   }

   public void setMaximumHealth(int health) {
   }

   public int getMaximumHealth() {
      return 0;
   }

   public void givePotionEffect(IPotion potion) {
   }

   public void removePotionEffect(IPotion potion) {
   }

   public void clearPotionEffects() {
   }

   public IPotion[] getPotionEffects() {
      return new IPotion[0];
   }

   public void setInvulnerabilityTicks(int ticks) {
   }

   public int getInvulnerabilityTicks() {
      return 0;
   }

   public void setFallDistance(float distance) {
   }

   public int getFallDistance() {
      return 0;
   }

   public void setAIMode(AIMode ai) {
   }

   public void setSilenced(boolean silence) {
   }

   public static class MethodHandler {
      public static void heal(String descriptor, List stack) {
         if (descriptor.equals("()V")) {
            Entity.getCurrentSelection().Heal((INumber)null);
         } else {
            INumber heal = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
            Entity.getCurrentSelection().Heal(heal);
         }

      }

      public static void damage(List stack) {
         INumber damage = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Entity.getCurrentSelection().Damage(damage);
      }

      public static void setHealth(List stack) {
         INumber health = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Entity.getCurrentSelection().SetHealth(health);
      }

      public static void getHealth(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.Set(result, new GameValue(GameValue.Value.CURRENT_HEALTH, EntityActionBlock.EntitySelection.CURRENT_SELECTION));
         stack.add(new VariableStackValue("I", result.getName()));
      }

      public static void setAbsorptionHealth(List stack) {
         INumber health = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Entity.getCurrentSelection().SetAbsorption(health);
      }

      public static void getAbsorptionHealth(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.Set(result, new GameValue("Absorption Health", EntityActionBlock.EntitySelection.CURRENT_SELECTION));
         stack.add(new VariableStackValue("I", result.getName()));
      }

      public static void setMaximumHealth(List stack) {
         INumber health = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Entity.getCurrentSelection().SetMaxHealth(health, Tags.HealMobtoMaxHealth.FALSE);
      }

      public static void getMaximumHealth(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.Set(result, new GameValue(GameValue.Value.MAX_HEALTH, EntityActionBlock.EntitySelection.CURRENT_SELECTION));
         stack.add(new VariableStackValue("I", result.getName()));
      }

      public static void givePotionEffect(List stack) {
         IPotion potion = (IPotion)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Entity.getCurrentSelection().GivePotion(new IPotion[]{potion}, Tags.EffectParticles.NONE, Tags.OverwriteEffect.TRUE);
      }

      public static void removePotionEffect(List stack) {
         IPotion potion = (IPotion)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Entity.getCurrentSelection().RemovePotion(potion);
      }

      public static void clearPotionEffects(List stack) {
         Entity.getCurrentSelection().ClearPotions();
      }

      public static void getPotionEffects(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         Variable reference = new Variable("_jfdfR%var(" + result.getName() + ")", Variable.Scope.NORMAL);
         VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
         VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), (new Text()).Set(result.getName()));
         Functions.Call("_jfdf>std>sallocl");
         VariableControl.Set(reference, new GameValue("Potion Effects", EntityActionBlock.EntitySelection.CURRENT_SELECTION));
         VariableControl.InsertListValue(reference, (new Number()).Set(1), (new Text()).Set("\u0000p"));
         stack.add(new VariableStackValue("[Lnet/jfdf/jfdf/values/IPotion;", result.getName()));
      }

      public void setInvulnerabilityTicks(List stack) {
         INumber ticks = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Entity.getCurrentSelection().SetInvulTicks(ticks);
      }

      public static void getInvulnerabilityTicks(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.Set(result, new GameValue("Invulnerability Ticks", EntityActionBlock.EntitySelection.CURRENT_SELECTION));
         stack.add(new VariableStackValue("I", result.getName()));
      }

      public void setFallDistance(List stack) {
         INumber distance = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Entity.getCurrentSelection().SetFallDistance(distance);
      }

      public static void getFallDistance(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.Set(result, new GameValue("Fall Distance"));
         stack.add(new VariableStackValue("I", result.getName()));
      }

      public static void setAIEnabled(List stack) {
         INumber aiMode = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         if (aiMode instanceof Number && ((Number)aiMode).isConstant()) {
            String value = ((Number)aiMode).getValue();
            Entity var10000 = Entity.getCurrentSelection();
            Tags.AI var10001;
            switch (value) {
               case "0":
                  var10001 = Tags.AI.NONE;
                  break;
               case "1":
                  var10001 = Tags.AI.INSENTIENT;
                  break;
               case "2":
                  var10001 = Tags.AI.SENTIENT;
                  break;
               default:
                  throw new IllegalStateException("Unknown Villager Profession ID: " + value);
            }

            var10000.SetAI(var10001);
         } else {
            AIMode[] var2 = LivingNPE.AIMode.values();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               AIMode value = var2[var4];
               If.Variable.Equals(aiMode, new CodeValue[]{(new Number()).Set(value.getId())}, false);
               Entity.getCurrentSelection().SetAI(Tags.AI.valueOf(value.name()));
               If.End();
            }

         }
      }

      public static void setSilenced(List stack) {
         INumber silence = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         if (silence instanceof Number && ((Number)silence).isConstant()) {
            String value = ((Number)silence).getValue();
            Entity.getCurrentSelection().SetSilenced(value.equals("0") ? Tags.Silenced.DISABLE : Tags.Silenced.ENABLE);
         } else {
            If.Variable.Equals(silence, new CodeValue[]{(new Number()).Set(0)}, false);
            Entity.getCurrentSelection().SetSilenced(Tags.Silenced.DISABLE);
            If.Else();
            Entity.getCurrentSelection().SetSilenced(Tags.Silenced.ENABLE);
            If.End();
         }
      }
   }

   public static enum AIMode {
      NONE(0),
      INSENTIENT(1),
      SENTIENT(2);

      private final int id;

      private AIMode(int id) {
         this.id = id;
      }

      public int getId() {
         return this.id;
      }
   }
}

package net.jfdf.addon.interaction.npe;

import java.util.List;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.GameValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Variable;

public class NonPlayerEntity {
   public void setName(String name) {
   }

   public String getName() {
      return "";
   }

   public void setNameVisibility(boolean visibility) {
   }

   public String getUUID() {
      return "";
   }

   public void setGravityEnabled(boolean gravity) {
   }

   public static class MethodHandler {
      public static void setName(List stack) {
         IText name = (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         Entity.getCurrentSelection().SetName(name, Tags.HideNameTag.DON_T_CHANGE);
      }

      public static void getName(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.Set(result, new GameValue("Name", EntityActionBlock.EntitySelection.CURRENT_SELECTION));
         stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
      }

      public static void setNameVisibility(List stack) {
         INumber visibility = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         if (visibility instanceof Number && ((Number)visibility).isConstant()) {
            String value = ((Number)visibility).getValue();
            Entity.getCurrentSelection().SetNameVisible(value.equals("0") ? Tags.NameTagVisible.DISABLE : Tags.NameTagVisible.ENABLE);
         } else {
            If.Variable.Equals(visibility, new CodeValue[]{(new Number()).Set(0)}, false);
            Entity.getCurrentSelection().SetNameVisible(Tags.NameTagVisible.DISABLE);
            If.Else();
            Entity.getCurrentSelection().SetNameVisible(Tags.NameTagVisible.ENABLE);
            If.End();
         }
      }

      public static void getUUID(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.Set(result, new GameValue("UUID", EntityActionBlock.EntitySelection.CURRENT_SELECTION));
         stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
      }

      public static void setGravityEnabled(List stack) {
         INumber gravity = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         if (gravity instanceof Number && ((Number)gravity).isConstant()) {
            String value = ((Number)gravity).getValue();
            Entity.getCurrentSelection().SetGravity(value.equals("0") ? Tags.Gravity.DISABLE : Tags.Gravity.ENABLE);
         } else {
            If.Variable.Equals(gravity, new CodeValue[]{(new Number()).Set(0)}, false);
            Entity.getCurrentSelection().SetGravity(Tags.Gravity.DISABLE);
            If.Else();
            Entity.getCurrentSelection().SetGravity(Tags.Gravity.ENABLE);
            If.End();
         }
      }

      public void select() {
      }

      public void unselect() {
      }
   }
}

package net.jfdf.addon.interaction.player;

import java.util.List;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.mangement.Functions;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.GameValue;
import net.jfdf.jfdf.values.IItem;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Item;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;

public class PlayerInventory {
   public void giveItem(IItem item) {
   }

   public void removeItem(IItem item) {
   }

   public void clearItem(IItem item) {
   }

   public void setItemCooldown(IItem item, int ticks) {
   }

   public void setCursor(IItem item) {
   }

   public void setMainHand(IItem item) {
   }

   public void setOffHand(IItem item) {
   }

   public void setHead(IItem item) {
   }

   public void setChest(IItem item) {
   }

   public void setLegs(IItem item) {
   }

   public void setFeet(IItem item) {
   }

   public void setSlot(int slot, IItem item) {
   }

   public void clear() {
   }

   public void load() {
   }

   public IItem getCursor() {
      return new Item();
   }

   public IItem getMainHand() {
      return new Item();
   }

   public IItem getOffHand() {
      return new Item();
   }

   public IItem getHead() {
      return new Item();
   }

   public IItem getChest() {
      return new Item();
   }

   public IItem getLegs() {
      return new Item();
   }

   public IItem getFeet() {
      return new Item();
   }

   public IItem getSlot(int slot) {
      return new Item();
   }

   public IItem[] getItems() {
      return new Item[0];
   }

   public void save() {
   }

   public static class MethodHandler {
      public static void giveItem(List stack) {
         IItem item = (IItem)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().GiveItems(new IItem[]{item}, (INumber)null);
      }

      public static void removeItem(List stack) {
         IItem item = (IItem)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().RemoveItems(item);
      }

      public static void clearItem(List stack) {
         IItem item = (IItem)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().ClearItems(item);
      }

      public static void setItemCooldown(List stack) {
         IItem item = (IItem)((IStackValue)stack.get(stack.size() - 2)).getTransformedValue();
         INumber ticks = (INumber)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().SetItemCooldown(item, ticks);
      }

      public static void setCursor(List stack) {
         IItem item = (IItem)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().SetCursorItem(item);
      }

      public static void setMainHand(List stack) {
         IItem item = (IItem)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().SetEquipment(item, Tags.EquipmentSlot.MAIN_HAND);
      }

      public static void setOffHand(List stack) {
         IItem item = (IItem)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().SetEquipment(item, Tags.EquipmentSlot.OFF_HAND);
      }

      public static void setHead(List stack) {
         IItem item = (IItem)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().SetEquipment(item, Tags.EquipmentSlot.HEAD);
      }

      public static void setChest(List stack) {
         IItem item = (IItem)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().SetEquipment(item, Tags.EquipmentSlot.CHEST);
      }

      public static void setLegs(List stack) {
         IItem item = (IItem)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().SetEquipment(item, Tags.EquipmentSlot.LEGS);
      }

      public static void setFeet(List stack) {
         IItem item = (IItem)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().SetEquipment(item, Tags.EquipmentSlot.FEET);
      }

      public static void setSlot(List stack) {
         INumber slot = (INumber)((IStackValue)stack.get(stack.size() - 2)).getTransformedValue();
         IItem item = (IItem)((IStackValue)stack.get(stack.size() - 1)).getTransformedValue();
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().SetSlotItem(item, slot);
      }

      public static void clear(List stack) {
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().ClearInv(Tags.ClearMode.ENTIRE_INVENTORY, Tags.ClearCraftingAndCursor.TRUE);
      }

      public static void load(List stack) {
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().LoadInv();
      }

      public static void getCursor(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.Set(result, new GameValue(GameValue.Value.CURSOR_ITEM));
         stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
      }

      public static void getMainHand(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.Set(result, new GameValue(GameValue.Value.MAIN_HAND_ITEM));
         stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
      }

      public static void getOffHand(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.Set(result, new GameValue(GameValue.Value.OFF_HAND_ITEM));
         stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
      }

      public static void getHead(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.GetListValue(result, new GameValue(GameValue.Value.ARMOR_ITEMS), (new Number()).Set(1));
         stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
      }

      public static void getChest(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.GetListValue(result, new GameValue(GameValue.Value.ARMOR_ITEMS), (new Number()).Set(2));
         stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
      }

      public static void getLegs(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.GetListValue(result, new GameValue(GameValue.Value.ARMOR_ITEMS), (new Number()).Set(3));
         stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
      }

      public static void getFeet(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         VariableControl.GetListValue(result, new GameValue(GameValue.Value.ARMOR_ITEMS), (new Number()).Set(1));
         stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
      }

      public static void getSlot(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         INumber slot = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         VariableControl.GetListValue(result, new GameValue(GameValue.Value.INVENTORY_ITEMS), slot);
         stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
      }

      public static void getItems(List stack) {
         Variable result = CompilerAddons.getTempVariable();
         Variable reference = new Variable("_jfdfR%var(" + result.getName() + ")", Variable.Scope.NORMAL);
         VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
         VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), (new Text()).Set(result.getName()));
         Functions.Call("_jfdf>std>sallocl");
         VariableControl.Set(reference, new GameValue(GameValue.Value.INVENTORY_ITEMS, EntityActionBlock.EntitySelection.CURRENT_SELECTION));
         VariableControl.InsertListValue(reference, (new Number()).Set(1), (new Text()).Set("\u0000p"));
         stack.add(new VariableStackValue("[Lnet/jfdf/jfdf/values/IItem;", result.getName()));
      }

      public static void save(List stack) {
         net.jfdf.jfdf.mangement.Player.getCurrentSelection().SaveInv();
      }
   }
}

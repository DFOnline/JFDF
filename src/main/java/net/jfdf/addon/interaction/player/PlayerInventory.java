package net.jfdf.addon.interaction.player;

import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.mangement.Functions;
import net.jfdf.jfdf.mangement.Player;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.*;
import net.jfdf.jfdf.values.Number;

import java.util.List;

public class PlayerInventory {
    public PlayerInventory() {}

    public void giveItem(IItem item) {}
    public void removeItem(IItem item) {}
    public void clearItem(IItem item) {}

    public void setItemCooldown(IItem item, int ticks) {}

    public void setCursor(IItem item) {}
    public void setMainHand(IItem item) {}
    public void setOffHand(IItem item) {}
    public void setHead(IItem item) {}
    public void setChest(IItem item) {}
    public void setLegs(IItem item) {}
    public void setFeet(IItem item) {}
    public void setSlot(int slot, IItem item) {}
    public void clear() {}
    public void load() {}

    public IItem getCursor() { return new Item(); }
    public IItem getMainHand() { return new Item(); }
    public IItem getOffHand() { return new Item(); }
    public IItem getHead() { return new Item(); }
    public IItem getChest() { return new Item(); }
    public IItem getLegs() { return new Item(); }
    public IItem getFeet() { return new Item(); }
    public IItem getSlot(int slot) { return new Item(); }
    public IItem[] getItems() { return new Item[0]; }
    public void save() {}

    public static class MethodHandler {
        public static void giveItem(List<IStackValue> stack) {
            IItem item = (IItem) stack.get(stack.size() - 1).getTransformedValue();
            Player.getCurrentSelection().GiveItems(new IItem[] { item }, null);
        }

        public static void removeItem(List<IStackValue> stack) {
            IItem item = (IItem) stack.get(stack.size() - 1).getTransformedValue();
            Player.getCurrentSelection().RemoveItems(item);
        }

        public static void clearItem(List<IStackValue> stack) {
            IItem item = (IItem) stack.get(stack.size() - 1).getTransformedValue();
            Player.getCurrentSelection().ClearItems(item);
        }

        public static void setItemCooldown(List<IStackValue> stack) {
            IItem item = (IItem) stack.get(stack.size() - 2).getTransformedValue();
            INumber ticks = (INumber) stack.get(stack.size() - 1).getTransformedValue();

            Player.getCurrentSelection().SetItemCooldown(item, ticks);
        }

        public static void setCursor(List<IStackValue> stack) {
            IItem item = (IItem) stack.get(stack.size() - 1).getTransformedValue();
            Player.getCurrentSelection().SetCursorItem(item);
        }

        public static void setMainHand(List<IStackValue> stack) {
            IItem item = (IItem) stack.get(stack.size() - 1).getTransformedValue();
            Player.getCurrentSelection().SetEquipment(item, Tags.EquipmentSlot.MAIN_HAND);
        }

        public static void setOffHand(List<IStackValue> stack) {
            IItem item = (IItem) stack.get(stack.size() - 1).getTransformedValue();
            Player.getCurrentSelection().SetEquipment(item, Tags.EquipmentSlot.OFF_HAND);
        }

        public static void setHead(List<IStackValue> stack) {
            IItem item = (IItem) stack.get(stack.size() - 1).getTransformedValue();
            Player.getCurrentSelection().SetEquipment(item, Tags.EquipmentSlot.HEAD);
        }

        public static void setChest(List<IStackValue> stack) {
            IItem item = (IItem) stack.get(stack.size() - 1).getTransformedValue();
            Player.getCurrentSelection().SetEquipment(item, Tags.EquipmentSlot.CHEST);
        }

        public static void setLegs(List<IStackValue> stack) {
            IItem item = (IItem) stack.get(stack.size() - 1).getTransformedValue();
            Player.getCurrentSelection().SetEquipment(item, Tags.EquipmentSlot.LEGS);
        }

        public static void setFeet(List<IStackValue> stack) {
            IItem item = (IItem) stack.get(stack.size() - 1).getTransformedValue();
            Player.getCurrentSelection().SetEquipment(item, Tags.EquipmentSlot.FEET);
        }

        public static void setSlot(List<IStackValue> stack) {
            INumber slot = (INumber) stack.get(stack.size() - 2).getTransformedValue();
            IItem item = (IItem) stack.get(stack.size() - 1).getTransformedValue();

            Player.getCurrentSelection().SetSlotItem(item, slot);
        }

        public static void clear(List<IStackValue> stack) {
            Player.getCurrentSelection().ClearInv(Tags.ClearMode.ENTIRE_INVENTORY, Tags.ClearCraftingAndCursor.TRUE);
        }

        public static void load(List<IStackValue> stack) {
            Player.getCurrentSelection().LoadInv();
        }

        public static void getCursor(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.Set(result, new GameValue(GameValue.Value.CURSOR_ITEM));

            stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
        }

        public static void getMainHand(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.Set(result, new GameValue(GameValue.Value.MAIN_HAND_ITEM));

            stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
        }

        public static void getOffHand(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.Set(result, new GameValue(GameValue.Value.OFF_HAND_ITEM));

            stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
        }

        public static void getHead(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.GetListValue(result, new GameValue(GameValue.Value.ARMOR_ITEMS), new Number().Set(1));

            stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
        }

        public static void getChest(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.GetListValue(result, new GameValue(GameValue.Value.ARMOR_ITEMS), new Number().Set(2));

            stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
        }

        public static void getLegs(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.GetListValue(result, new GameValue(GameValue.Value.ARMOR_ITEMS), new Number().Set(3));

            stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
        }

        public static void getFeet(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            VariableControl.GetListValue(result, new GameValue(GameValue.Value.ARMOR_ITEMS), new Number().Set(1));

            stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
        }

        public static void getSlot(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            INumber slot = (INumber) stack.remove(stack.size() - 1).getTransformedValue();

            VariableControl.GetListValue(result, new GameValue(GameValue.Value.INVENTORY_ITEMS), slot);
            stack.add(new VariableStackValue("Lnet/jfdf/jfdf/values/IItem;", result.getName()));
        }

        public static void getItems(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            Variable reference = new Variable("_jfdfR%var(" + result.getName() + ")", Variable.Scope.NORMAL);

            VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
            VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), new Text().Set(result.getName()));
            Functions.Call("_jfdf>std>sallocl");

            VariableControl.Set(reference, new GameValue(GameValue.Value.INVENTORY_ITEMS, EntityActionBlock.EntitySelection.CURRENT_SELECTION));
            VariableControl.InsertListValue(reference, new Number().Set(1), new Text().Set("\0p"));

            stack.add(new VariableStackValue("[Lnet/jfdf/jfdf/values/IItem;", result.getName()));
        }

        public static void save(List<IStackValue> stack) {
            Player.getCurrentSelection().SaveInv();
        }
    }
}

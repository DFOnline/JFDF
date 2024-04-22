package net.jfdf.addon.interaction;

import net.jfdf.addon.interaction.player.Player;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.data.stack.CodeStackValue;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.NumberStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.mangement.Selector;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.GameValue;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Variable;

import java.util.List;

public class Plot {
    private Plot() {}

    public static World getWorld() { return new World(); }
    public static Event getActiveEvent() { return new Event(); }

    public static int getPlayerCount() { return 0; }
    public static Player getPlayerByName(String name) { return new Player(); }
    public static Player getPlayerByUUID(String uuid) { return new Player(); }

    public static class MethodHandler {
        public static void getWorld(List<IStackValue> stack) {
            stack.add(new NumberStackValue(0));
        }

        public static void getActiveEvent(List<IStackValue> stack) {
            stack.add(new NumberStackValue(0));
        }

        public static void getPlayerCount(List<IStackValue> stack) {
            stack.add(new CodeStackValue(new GameValue(GameValue.Value.TOTAL_PLAYER_COUNT)));
        }

        public static void getPlayerByName(List<IStackValue> stack) {
            Variable result = CompilerAddons.getTempVariable();
            IText name = (IText) stack.remove(stack.size() - 1).getTransformedValue();

            VariableControl.Set(result, new Number().Set(0));

            Selector.PlayerName(name);
                VariableControl.Set(result, new GameValue("UUID", EntityActionBlock.EntitySelection.CURRENT_SELECTION));
            Selector.Reset();

            stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
        }

        public static void getPlayerByUUID(List<IStackValue> stack) {
            // Same code because PlayerName supports both name and UUID
            getPlayerByName(stack);
        }
    }
}

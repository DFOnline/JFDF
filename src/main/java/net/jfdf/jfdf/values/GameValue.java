package net.jfdf.jfdf.values;

import java.util.HashMap;
import java.util.Map;
import net.jfdf.jfdf.blocks.EntityActionBlock;

public class GameValue implements CodeValue, IText, INumber, ILocation, IItem {
   private final String value;
   private EntityActionBlock.EntitySelection target;

   public GameValue(Value value) {
      this(value.getJSONValue());
   }

   public GameValue(String value) {
      this.target = EntityActionBlock.EntitySelection.DEFAULT_ENTITY;
      this.value = value;
   }

   public GameValue(Value value, EntityActionBlock.EntitySelection target) {
      this(value.getJSONValue(), target);
   }

   public GameValue(String value, EntityActionBlock.EntitySelection target) {
      this.target = EntityActionBlock.EntitySelection.DEFAULT_ENTITY;
      this.value = value;
      this.target = target;
   }

   public String asJSON() {
      String var10000 = this.value;
      return "{\"id\":\"g_val\",\"data\":{\"type\":\"" + var10000 + "\",\"target\":\"" + this.target.getJSONValue() + "\"}}";
   }

   public static enum Value {
      TOTAL_PLAYER_COUNT("Player Count"),
      CPU_USAGE_PERCENT("CPU Usage"),
      SERVER_TPS("Server TPS"),
      SELECTION_SIZE("Selection Size"),
      SELECTION_TARGET_NAMES("Selection Target Names"),
      CURRENT_HEALTH("Current Health"),
      MAX_HEALTH("Maximum Health"),
      CURRENT_FOOD_LEVEL("Current Food Level"),
      CURRENT_SATURATION_LEVEL("Current Saturation Level"),
      CURRENT_XP_LEVEL("Current XP Level"),
      CURRENT_XP_PROGRESS("Current XP Level Progress"),
      CURRENT_ARMOR_POINTS("Current Armor Points"),
      CURRENT_FIRE_TICKS("Current Fire Ticks"),
      CURRENT_AIR_REMAINING("Current Air Remaining"),
      CURRENT_FALL_DISTANCE("Current Fall Distance"),
      CURRENT_HELD_SLOT("Current Held Slot"),
      INVENTORY_NAME("Inventory Name"),
      ACTIVE_POTION_EFFECTS("Active Potion Effects"),
      ENTITY_TYPE("Entity Type"),
      CURRENT_LOCATION("Location"),
      TARGET_BLOCK_LOCATION("Target Block Location"),
      TARGET_FLUID_LOCATION("Target Fluid Location"),
      TARGET_BLOCK_FACE("Target Block Face"),
      EYE_LOCATION("Eye Location"),
      X_Coordinate("X-Coordinate"),
      Y_Coordinate("Y-Coordinate"),
      Z_Coordinate("Z-Coordinate"),
      PITCH("Pitch"),
      YAW("Yaw"),
      FACING_DIRECTION("Facing Direction"),
      SPAWN_LOCATION("Spawn Location"),
      MAIN_HAND_ITEM("Main Hand Item"),
      OFF_HAND_ITEM("Off Hand Item"),
      ARMOR_ITEMS("Armor Items"),
      HOTBAR_ITEMS("Hotbar Items"),
      INVENTORY_ITEMS("Inventory Items"),
      CURSOR_ITEM("Cursor Item"),
      INVENTORY_MENU_ITEMS("Inventory Menu Items"),
      SADDLE_ITEM("Saddle Item"),
      EVENT_BLOCK_LOCATION("Event Block Location"),
      EVENT_BLOCK_FACE("Event Block Face"),
      EVENT_DAMAGE("Event Damage"),
      DAMAGE_CAUSE("Damage Cause"),
      BOW_POWER("Bow Power"),
      COMMNAD("Event Command"),
      COMMAND_ARGS("Event Command Arguments"),
      EVENT_ITEM("Event Item"),
      NEW_SLOT("New Slot"),
      OLD_SLOT("Old Slot"),
      CLICKED_SLOT_INDEX("Clicked Slot Index"),
      CLICKED_SLOT_ITEM("Clicked Slot Item"),
      NEW_CLICKED_SLOT_ITEM("New Clicked Slot Item"),
      INVENTORY_CLOSE_CAUSE("Inventory Close Cause"),
      TIMESTAMP("Timestamp");

      private static final Map values = new HashMap();
      private int value;
      private final String jsonValue;

      private Value(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public static Value valueOf(int Value) {
         return (Value)values.get(Value);
      }

      public int getValue() {
         return this.value;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }

      static {
         Value[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Value value = var0[var2];
            value.value = values.size();
            values.put(value.getValue(), value);
         }

      }
   }
}

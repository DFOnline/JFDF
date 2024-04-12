package net.jfdf.jfdf.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Tag;

public class GameActionBlock implements CodeBlock {
   private List items = new ArrayList();
   private List tags = new ArrayList();
   private String action;

   /** @deprecated */
   @Deprecated
   public GameActionBlock(Action action) {
      this.action = action.getJSONValue();
   }

   public GameActionBlock(String action) {
      this.action = action;
   }

   /** @deprecated */
   @Deprecated
   public static GameActionBlock New(Action action) {
      return new GameActionBlock(action);
   }

   public static GameActionBlock New(String action) {
      return new GameActionBlock(action);
   }

   public GameActionBlock SetItems(List items) {
      this.items = items;
      return this;
   }

   public GameActionBlock SetItems(CodeValue... items) {
      this.items = Arrays.asList(items);
      return this;
   }

   public GameActionBlock SetTags(Tag... tags) {
      this.tags = Arrays.asList(tags);
      Tag[] var2 = tags;
      int var3 = tags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Tag tag = var2[var4];
         tag.setAction(this.action);
         tag.setBlock("game_action");
      }

      return this;
   }

   public String asJSON() {
      String json = "{\"id\":\"block\",\"block\":\"game_action\",\"args\":{\"items\":[";
      List itemsJSON = new ArrayList();
      if (this.tags.size() > 9) {
         this.tags = this.tags.subList(0, 8);
      }

      if (this.items.size() > 27 - this.tags.size()) {
         this.items = this.items.subList(0, 26 - this.tags.size());
      }

      String var10001;
      int i;
      for(i = 0; i < this.items.size(); ++i) {
         CodeValue codeValue = (CodeValue)this.items.get(i);
         var10001 = codeValue.asJSON();
         itemsJSON.add("{\"item\":" + var10001 + ",\"slot\":" + i + "}");
      }

      for(i = 26; i >= 27 - this.tags.size(); --i) {
         Tag tag = (Tag)this.tags.get(26 - i);
         var10001 = tag.asJSON();
         itemsJSON.add("{\"item\":" + var10001 + ",\"slot\":" + i + "}");
      }

      json = json + String.join(",", itemsJSON);
      json = json + "]},\"action\":\"" + this.action + "\"}";
      return json;
   }

   /** @deprecated */
   @Deprecated
   public static enum Action {
      SPAWN_MOB("SpawnMob"),
      SPAWN_ITEM("SpawnItem"),
      SPAWN_RNG_ITEM("SpawnRngItem"),
      SPAWN_POTION_CLOUD("SpawnPotionCloud"),
      SPAWN_VEHICLE("SpawnVehicle"),
      SPAWN_EXP_ORB("SpawnExpOrb"),
      EXPLOSION("Explosion"),
      SPAWN_T_N_T("SpawnTNT"),
      SPAWN_FANGS("SpawnFangs"),
      FIREWORK("Firework"),
      LAUNCH_PROJ("LaunchProj"),
      LIGHTNING("Lightning"),
      FALLING_BLOCK("FallingBlock"),
      SPAWN_ARMOR_STAND("SpawnArmorStand"),
      SPAWN_CRYSTAL("SpawnCrystal"),
      SPAWN_ENDER_EYE("SpawnEnderEye"),
      SET_BLOCK("SetBlock"),
      BREAK_BLOCK("BreakBlock"),
      COPY_BLOCKS("CopyBlocks"),
      SET_BLOCK_DATA("SetBlockData"),
      TICK_BLOCK("TickBlock"),
      BONE_MEAL("BoneMeal"),
      GENERATE_TREE("GenerateTree"),
      SET_BLOCK_GROWTH("SetBlockGrowth"),
      FILL_CONTAINER("FillContainer"),
      SET_CONTAINER("SetContainer"),
      SET_ITEM_IN_SLOT("SetItemInSlot"),
      REPLACE_ITEMS("ReplaceItems"),
      REMOVE_ITEMS("RemoveItems"),
      CLEAR_ITEMS("ClearItems"),
      CLEAR_CONTAINER("ClearContainer"),
      SET_CONTAINER_NAME("SetContainerName"),
      LOCK_CONTAINER("LockContainer"),
      CHANGE_SIGN("ChangeSign"),
      SET_HEAD("SetHead"),
      CANCEL_EVENT("CancelEvent"),
      UNCANCEL_EVENT("UncancelEvent"),
      SET_EVENT_DAMAGE("SetEventDamage"),
      SET_EVENT_HEAL("SetEventHeal"),
      SET_EVENT_X_P("SetEventXP"),
      SET_EVENT_PROJ("SetEventProj"),
      SET_EVENT_SOUND("SetEventSound"),
      BLOCK_DROPS_ON("BlockDropsOn"),
      BLOCK_DROPS_OFF("BlockDropsOff"),
      SET_SC_OBJ("SetScObj"),
      CREATE_HOLOGRAM("CreateHologram"),
      REMOVE_HOLOGRAM("RemoveHologram"),
      WEB_REQUEST("WebRequest"),
      DISCORD_WEBHOOK("DiscordWebhook");

      private static final Map values = new HashMap();
      private int value;
      private final String jsonValue;

      private Action(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public static Action valueOf(int type) {
         return (Action)values.get(type);
      }

      public int getValue() {
         return this.value;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }

      static {
         Action[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Action type = var0[var2];
            type.value = values.size();
            values.put(type.getValue(), type);
         }

      }
   }
}

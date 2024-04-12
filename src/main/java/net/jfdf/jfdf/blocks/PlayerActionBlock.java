package net.jfdf.jfdf.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Tag;

public class PlayerActionBlock implements CodeBlock {
   private List items = new ArrayList();
   private List tags = new ArrayList();
   private final String action;
   private final PlayerSelection selection;

   /** @deprecated */
   @Deprecated
   public PlayerActionBlock(Action action, PlayerSelection selection) {
      this.action = action.getJSONValue();
      this.selection = selection;
   }

   public PlayerActionBlock(String action, PlayerSelection selection) {
      this.action = action;
      this.selection = selection;
   }

   /** @deprecated */
   @Deprecated
   public static PlayerActionBlock New(Action action, PlayerSelection selection) {
      return new PlayerActionBlock(action, selection);
   }

   public static PlayerActionBlock New(String action, PlayerSelection selection) {
      return new PlayerActionBlock(action, selection);
   }

   public PlayerActionBlock SetItems(List items) {
      this.items = items;
      return this;
   }

   public PlayerActionBlock SetItems(CodeValue... items) {
      this.items = Arrays.asList(items);
      return this;
   }

   public PlayerActionBlock SetTags(Tag... tags) {
      this.tags = Arrays.asList(tags);
      Tag[] var2 = tags;
      int var3 = tags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Tag tag = var2[var4];
         tag.setAction(this.action);
         tag.setBlock("player_action");
      }

      return this;
   }

   public String asJSON() {
      String json = "{\"id\":\"block\",\"block\":\"player_action\",\"args\":{\"items\":[";
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
      json = json + "]},\"action\":\"" + this.action + (this.selection != PlayerActionBlock.PlayerSelection.CURRENT_SELECTION ? "\",\"target\":\"" + this.selection.getJSONValue() : "") + "\"}";
      return json;
   }

   /** @deprecated */
   @Deprecated(
      forRemoval = true
   )
   public static enum Action {
      GIVE_ITEMS("GiveItems"),
      SET_ITEMS("SetItems"),
      SET_SLOT_ITEM("SetSlotItem"),
      SET_EQUIPMENT("SetEquipment"),
      SET_ARMOR("SetArmor"),
      REPLACE_ITEMS("ReplaceItems"),
      REMOVE_ITEMS("RemoveItems"),
      CLEAR_ITEMS("ClearItems"),
      CLEAR_INV("ClearInv"),
      GIVE_RNG_ITEM("GiveRngItem"),
      SET_CURSOR_ITEM("SetCursorItem"),
      SAVE_INV("SaveInv"),
      LOAD_INV("LoadInv"),
      SET_ITEM_COOLDOWN("SetItemCooldown"),
      SEND_MESSAGE("SendMessage"),
      SEND_DIALOGUE("SendDialogue"),
      SEND_HOVER("SendHover"),
      CLEAR_CHAT("ClearChat"),
      SEND_TITLE("SendTitle"),
      ACTION_BAR("ActionBar"),
      OPEN_BOOK("OpenBook"),
      BOSS_BAR("BossBar"),
      REMOVE_BOSS_BAR("RemoveBossBar"),
      SEND_ADVANCEMENT("SendAdvancement"),
      SET_TAB_LIST_INFO("SetTabListInfo"),
      PLAY_SOUND("PlaySound"),
      STOP_SOUND("StopSound"),
      PLAY_SOUND_SEQ("PlaySoundSeq"),
      SHOW_INV("ShowInv"),
      EXPAND_INV("ExpandInv"),
      SET_MENU_ITEM("SetMenuItem"),
      SET_INV_NAME("SetInvName"),
      ADD_INV_ROW("AddInvRow"),
      REMOVE_INV_ROW("RemoveInvRow"),
      CLOSE_INV("CloseInv"),
      OPEN_BLOCK_INV("OpenBlockInv"),
      DAMAGE("Damage"),
      HEAL("Heal"),
      SET_HEALTH("SetHealth"),
      SET_MAX_HEALTH("SetMaxHealth"),
      SET_ABSORPTION("SetAbsorption"),
      SET_FOOD_LEVEL("SetFoodLevel"),
      SET_SATURATION("SetSaturation"),
      GIVE_EXP("GiveExp"),
      SET_EXP("SetExp"),
      GIVE_EFFECT("GiveEffect"),
      REMOVE_EFFECT("RemoveEffect"),
      CLEAR_EFFECTS("ClearEffects"),
      SET_SLOT("SetSlot"),
      SET_ATK_SPEED("SetAtkSpeed"),
      SET_FIRE_TICKS("SetFireTicks"),
      SET_AIR_TICKS("SetAirTicks"),
      SET_INVUL_TICKS("SetInvulTicks"),
      SET_FALL_DISTANCE("SetFallDistance"),
      SET_SPEED("SetSpeed"),
      SET_COLLIDABLE("SetCollidable"),
      SET_ALLOW_FLIGHT("SetAllowFlight"),
      SET_ALLOW_P_V_P("SetAllowPVP"),
      SET_INVENTORY_KEPT("SetInventoryKept"),
      SET_DROPS_ENABLED("SetDropsEnabled"),
      SET_GAMEMODE("SetGamemode"),
      ENABLE_BLOCKS("EnableBlocks"),
      DISABLE_BLOCKS("DisableBlocks"),
      TELEPORT("Teleport"),
      RNG_TELEPORT("RngTeleport"),
      TP_SEQUENCE("TpSequence"),
      LAUNCH_UP("LaunchUp"),
      LAUNCH_FWD("LaunchFwd"),
      LAUNCH_TOWARD("LaunchToward"),
      RIDE_ENTITY("RideEntity"),
      SET_FLYING("SetFlying"),
      SET_GLIDING("SetGliding"),
      LAUNCH_PROJ("LaunchProj"),
      PARTICLE_EFFECT("ParticleEffect"),
      SET_PLAYER_TIME("SetPlayerTime"),
      SET_PLAYER_WEATHER("SetPlayerWeather"),
      SET_COMPASS("SetCompass"),
      DISPLAY_LIGHTNING("DisplayLightning"),
      DISPLAY_BLOCK("DisplayBlock"),
      DISPLAY_FRACTURE("DisplayFracture"),
      DISPLAY_BLOCK_OPEN("DisplayBlockOpen"),
      DISPLAY_SIGN_TEXT("DisplaySignText"),
      SET_WORLD_BORDER("SetWorldBorder"),
      SHIFT_WORLD_BORDER("ShiftWorldBorder"),
      MOB_DISGUISE("MobDisguise"),
      PLAYER_DISGUISE("PlayerDisguise"),
      BLOCK_DISGUISE("BlockDisguise"),
      SET_DISGUISE_VISIBLE("SetDisguiseVisible"),
      UNDISGUISE("Undisguise"),
      SET_CHAT_TAG("SetChatTag"),
      CHAT_COLOR("ChatColor"),
      SET_NAME_COLOR("SetNameColor"),
      SET_ARROWS_STUCK("SetArrowsStuck"),
      ATTACK_ANIMATION("AttackAnimation"),
      SEND_ANIMATION("SendAnimation"),
      ROLLBACK_BLOCKS("RollbackBlocks"),
      RESPAWN("Respawn"),
      KICK("Kick");

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

   public static enum PlayerSelection {
      CURRENT_SELECTION(0, "Selection"),
      DEFAULT_PLAYER(1, "Default"),
      KILLER(2, "Killer"),
      DAMAGER(3, "Damager"),
      SHOOTER(4, "Shooter"),
      VICTIM(5, "Victim"),
      ALL_PLAYERS(6, "AllPlayers");

      private static final Map values = new HashMap();
      private final int value;
      private final String jsonValue;

      private PlayerSelection(int value, String jsonValue) {
         this.value = value;
         this.jsonValue = jsonValue;
      }

      public static PlayerSelection valueOf(int type) {
         return (PlayerSelection)values.get(type);
      }

      public int getValue() {
         return this.value;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }

      static {
         PlayerSelection[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            PlayerSelection type = var0[var2];
            values.put(type.getValue(), type);
         }

      }
   }
}

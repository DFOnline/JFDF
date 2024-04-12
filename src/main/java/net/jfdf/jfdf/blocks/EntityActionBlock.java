package net.jfdf.jfdf.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Tag;

public class EntityActionBlock implements CodeBlock {
   private List items = new ArrayList();
   private List tags = new ArrayList();
   private String action;

   /** @deprecated */
   @Deprecated
   public EntityActionBlock(Action action, EntitySelection selection) {
      this.action = action.getJSONValue();
   }

   public EntityActionBlock(String action, EntitySelection selection) {
      this.action = action;
   }

   public static EntityActionBlock New(Action action, EntitySelection selection) {
      return new EntityActionBlock(action, selection);
   }

   public EntityActionBlock SetItems(List items) {
      this.items = items;
      return this;
   }

   public EntityActionBlock SetItems(CodeValue... items) {
      this.items = Arrays.asList(items);
      return this;
   }

   public EntityActionBlock SetTags(Tag... tags) {
      this.tags = Arrays.asList(tags);
      Tag[] var2 = tags;
      int var3 = tags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Tag tag = var2[var4];
         tag.setAction(this.action);
         tag.setBlock("entity_action");
      }

      return this;
   }

   public String asJSON() {
      String json = "{\"id\":\"block\",\"block\":\"entity_action\",\"args\":{\"items\":[";
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
   @Deprecated(
      forRemoval = true
   )
   public static enum Action {
      HEAL("Heal"),
      SET_HEALTH("SetHealth"),
      SET_ABSORPTION("SetAbsorption"),
      SET_MAX_HEALTH("SetMaxHealth"),
      DAMAGE("Damage"),
      SET_FIRE_TICKS("SetFireTicks"),
      SET_INVUL_TICKS("SetInvulTicks"),
      GIVE_EFFECT("GiveEffect"),
      REMOVE_EFFECT("RemoveEffect"),
      CLEAR_EFFECTS("ClearEffects"),
      SET_AGE("SetAge"),
      SET_FALL_DISTANCE("SetFallDistance"),
      SET_ANGER_TICKS("SetAngerTicks"),
      SET_CREEPER_FUSE("SetCreeperFuse"),
      SET_CREEPER_POWER("SetCreeperPower"),
      SET_CLOUD_RADIUS("SetCloudRadius"),
      SET_VILLAGER_EXP("SetVillagerExp"),
      SET_WITHER_SHIELD("SetWitherShield"),
      SET_HORSE_JUMP("SetHorseJump"),
      SET_PICKUP_DELAY("SetPickupDelay"),
      SET_NAME("SetName"),
      SET_NAME_VISIBLE("SetNameVisible"),
      SET_A_I("SetAI"),
      SET_SILENCED("SetSilenced"),
      SET_GRAVITY("SetGravity"),
      SET_DEATH_DROPS("SetDeathDrops"),
      SET_COLLIDABLE("SetCollidable"),
      SET_INVULNERABLE("SetInvulnerable"),
      SET_MOB_SITTING("SetMobSitting"),
      SET_BABY("SetBaby"),
      SET_SIZE("SetSize"),
      SET_SHEEP_SHEARED("SetSheepSheared"),
      SET_SADDLE("SetSaddle"),
      SET_CARRYING_CHEST("SetCarryingChest"),
      CREEPER_CHARGED("CreeperCharged"),
      IGNITE_CREEPER("IgniteCreeper"),
      TAME("Tame"),
      END_CRYSTAL_BEAM("EndCrystalBeam"),
      SET_PANDA_GENE("SetPandaGene"),
      SET_PROFESSION("SetProfession"),
      SET_ITEM_OWNER("SetItemOwner"),
      SET_PROJ_SOURCE("SetProjSource"),
      TELEPORT("Teleport"),
      TP_SEQUENCE("TpSequence"),
      LAUNCH_UP("LaunchUp"),
      LAUNCH_FWD("LaunchFwd"),
      LAUNCH_TOWARD("LaunchToward"),
      RIDE_ENTITY("RideEntity"),
      ATTACH_LEAD("AttachLead"),
      MOVE_TO("MoveTo"),
      SET_TARGET("SetTarget"),
      SET_ROTATION("SetRotation"),
      MOB_DISGUISE("MobDisguise"),
      PLAYER_DISGUISE("PlayerDisguise"),
      BLOCK_DISGUISE("BlockDisguise"),
      UNDISGUISE("Undisguise"),
      SET_GLOWING("SetGlowing"),
      SET_DYE_COLOR("SetDyeColor"),
      SET_FISH_PATTERN("SetFishPattern"),
      SET_RABBIT_TYPE("SetRabbitType"),
      SET_CAT_TYPE("SetCatType"),
      MOOSHROOM_TYPE("MooshroomType"),
      SET_FOX_TYPE("SetFoxType"),
      SET_PARROT_COLOR("SetParrotColor"),
      SET_HORSE_PATTERN("SetHorsePattern"),
      SET_LLAMA_COLOR("SetLlamaColor"),
      SET_VILLAGER_BIOME("SetVillagerBiome"),
      SNOWMAN_PUMPKIN("SnowmanPumpkin"),
      SET_ENDERMAN_BLOCK("SetEndermanBlock"),
      SET_MINECART_BLOCK("SetMinecartBlock"),
      PROJECTILE_ITEM("ProjectileItem"),
      SEND_ANIMATION("SendAnimation"),
      ATTACK_ANIMATION("AttackAnimation"),
      SET_POSE("SetPose"),
      ARMOR_STAND_TAGS("ArmorStandTags"),
      REMOVE("Remove"),
      SET_EQUIPMENT("SetEquipment"),
      SET_ARMOR("SetArmor"),
      LAUNCH_PROJ("LaunchProj"),
      SHEEP_EAT("SheepEat"),
      SHEAR_SHEEP("ShearSheep"),
      EXPLODE_CREEPER("ExplodeCreeper"),
      SET_CUSTOM_TAG("SetCustomTag"),
      GET_CUSTOM_TAG("GetCustomTag"),
      REMOVE_CUSTOM_TAG("RemoveCustomTag");

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

   public static enum EntitySelection {
      CURRENT_SELECTION(0, "Selection"),
      DEFAULT_ENTITY(1, "Default"),
      KILLER(2, "Killer"),
      DAMAGER(3, "Damager"),
      SHOOTER(4, "Shooter"),
      VICTIM(5, "Victim"),
      PROJECTILE(6, "Projectile"),
      ALL_ENTITIES(7, "AllEntities"),
      ALL_MOBS(7, "AllMobs"),
      LAST_MOB_SPAWNED(8, "LastMob"),
      LAST_ENTITY_SPAWNED(8, "LastEntity");

      private static final Map values = new HashMap();
      private final int value;
      private final String jsonValue;

      private EntitySelection(int value, String jsonValue) {
         this.value = value;
         this.jsonValue = jsonValue;
      }

      public static EntitySelection valueOf(int type) {
         return (EntitySelection)values.get(type);
      }

      public int getValue() {
         return this.value;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }

      static {
         EntitySelection[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            EntitySelection type = var0[var2];
            values.put(type.getValue(), type);
         }

      }
   }
}

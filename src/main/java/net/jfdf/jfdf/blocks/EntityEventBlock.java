package net.jfdf.jfdf.blocks;

import java.util.HashMap;
import java.util.Map;

public class EntityEventBlock implements CodeHeader {
   private String event;

   public EntityEventBlock(String event) {
      this.event = event;
   }

   public String asJSON() {
      return "{\"id\":\"block\",\"block\":\"entity_event\",\"action\":\"" + this.event + "\"}";
   }

   public String getTemplateName() {
      return "Event آ» " + this.event;
   }

   public String getTemplateNameWithColors() {
      return "§e§lEvent §6آ» §e" + this.event + " Event";
   }

   public static enum Event {
      ENTITY_DAMAGE_ENTITY("EntityDmgEntity"),
      ENTITY_KILL_ENTITY("EntityKillEntity"),
      ENTITY_TAKE_DAMAGE("EntityDmg"),
      PROJECTILE_DAMAGE_ENTITY("ProjDmgEntity"),
      PROJECTILE_KILL_ENTITY("ProjKillEntity"),
      ENTITY_DEATH("EntityDeath"),
      VECHILE_TAKE_DAMAGE("VechileDamage"),
      BLOCK_FALL("BlockFall"),
      FALLING_BLOCK_LAND("FallingBlockLand");

      private static final Map values = new HashMap();
      private int value;
      private final String name;
      private final String jsonValue;

      private Event(String jsonValue) {
         this.name = String.join(" ", jsonValue.split("(?=\\p{Upper})"));
         this.jsonValue = jsonValue;
      }

      public static Event valueOf(int type) {
         return (Event)values.get(type);
      }

      public int getValue() {
         return this.value;
      }

      public String getName() {
         return this.name;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }

      static {
         Event[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Event type = var0[var2];
            type.value = values.size();
            values.put(type.getValue(), type);
         }

      }
   }
}

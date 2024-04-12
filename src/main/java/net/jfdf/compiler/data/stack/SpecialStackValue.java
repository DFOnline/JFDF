package net.jfdf.compiler.data.stack;

import net.jfdf.jfdf.values.CodeValue;

public class SpecialStackValue implements IStackValue {
   private final SpecialValueType type;

   public SpecialStackValue(SpecialValueType type) {
      this.type = type;
   }

   public SpecialValueType getType() {
      return this.type;
   }

   public CodeValue getTransformedValue() {
      throw new IllegalStateException("Couldn't get transformed special value.");
   }

   public String getDescriptor() {
      return null;
   }

   public static enum SpecialValueType {
      SYSTEM_OUT,
      SYSTEM_ERR,
      PLAYER_CURRENT_SELECTION,
      PLAYER_DEFAULT,
      PLAYER_KILLER,
      PLAYER_DAMAGER,
      PLAYER_SHOOTER,
      PLAYER_VICTIM,
      PLAYER_ALL_PLAYERS,
      ENTITY_CURRENT_SELECTION,
      ENTITY_DEFAULT,
      ENTITY_KILLER,
      ENTITY_DAMAGER,
      ENTITY_SHOOTER,
      ENTITY_VICTIM,
      ENTITY_PROJECTILE,
      ENTITY_ALL_ENTITIES,
      ENTITY_ALL_MOBS,
      ENTITY_LAST_ENTITY_SPAWNED,
      ENTITY_LAST_MOB_SPAWNED;
   }
}

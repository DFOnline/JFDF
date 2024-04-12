package net.jfdf.jfdf.values;

import java.util.HashMap;
import java.util.Map;

public class Projectile extends Item implements IProjectile {
   public Projectile(ProjectileType projectileType) {
      super(projectileType.getItemID());
   }

   public Projectile(PotionProjectileType potionProjectileType, IPotion potion) {
      super(potionProjectileType.getItemID());
   }

   public static enum ProjectileType {
      ARROW("arrow"),
      EXPERIENCE_BOTTLE("experience_bottle"),
      EGG("egg"),
      ENDER_PEARL("ender_pearl"),
      EYE_OF_ENDER("ender_eye"),
      FIREBALL("fire_charge"),
      SNOWBALL("snowball"),
      TRIDENT("trident");

      private static final Map values = new HashMap();
      private int value;
      private final String itemId;

      private ProjectileType(String itemId) {
         this.itemId = itemId;
      }

      public static ProjectileType valueOf(int type) {
         return (ProjectileType)values.get(type);
      }

      public int getValue() {
         return this.value;
      }

      public String getItemID() {
         return this.itemId;
      }

      static {
         ProjectileType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            ProjectileType type = var0[var2];
            type.value = values.size();
            values.put(type.getValue(), type);
         }

      }
   }

   public static enum PotionProjectileType {
      LINGERING_POTION("lingering_potion"),
      SPLASH_POTION("splash_potion");

      private static final Map values = new HashMap();
      private int value;
      private final String itemId;

      private PotionProjectileType(String itemId) {
         this.itemId = itemId;
      }

      public static PotionProjectileType valueOf(int type) {
         return (PotionProjectileType)values.get(type);
      }

      public int getValue() {
         return this.value;
      }

      public String getItemID() {
         return this.itemId;
      }

      static {
         PotionProjectileType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            PotionProjectileType type = var0[var2];
            type.value = values.size();
            values.put(type.getValue(), type);
         }

      }
   }
}

package net.jfdf.addon.interaction.npe.villager;

import java.util.List;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;

public interface IVillagerVariant {
   default void setVillagerProfession(VillagerProfession profession) {
   }

   default void setVillagerBiome(VillagerBiome biome) {
   }

   public static class MethodHandler {
      public static void setVillagerProfession(List stack) {
         INumber profession = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         if (profession instanceof Number && ((Number)profession).isConstant()) {
            String value = ((Number)profession).getValue();
            Entity var10000 = Entity.getCurrentSelection();
            Tags.Profession var10001;
            switch (value) {
               case "0":
                  var10001 = Tags.Profession.UNEMPLOYED;
                  break;
               case "1":
                  var10001 = Tags.Profession.ARMORER;
                  break;
               case "2":
                  var10001 = Tags.Profession.BUTCHER;
                  break;
               case "3":
                  var10001 = Tags.Profession.CARTOGRAPHER;
                  break;
               case "4":
                  var10001 = Tags.Profession.CLERIC;
                  break;
               case "5":
                  var10001 = Tags.Profession.FARMER;
                  break;
               case "6":
                  var10001 = Tags.Profession.FISHERMAN;
                  break;
               case "7":
                  var10001 = Tags.Profession.FLETCHER;
                  break;
               case "8":
                  var10001 = Tags.Profession.LEATHERWORKER;
                  break;
               case "9":
                  var10001 = Tags.Profession.LIBRARIAN;
                  break;
               case "10":
                  var10001 = Tags.Profession.MASON;
                  break;
               case "11":
                  var10001 = Tags.Profession.NITWIT;
                  break;
               case "12":
                  var10001 = Tags.Profession.SHEPHERD;
                  break;
               case "13":
                  var10001 = Tags.Profession.TOOLSMITH;
                  break;
               case "14":
                  var10001 = Tags.Profession.WEAPONSMITH;
                  break;
               default:
                  throw new IllegalStateException("Unknown Villager Profession ID: " + value);
            }

            var10000.SetProfession(var10001);
         } else {
            VillagerProfession[] var2 = IVillagerVariant.VillagerProfession.values();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               VillagerProfession value = var2[var4];
               If.Variable.Equals(profession, new CodeValue[]{(new Number()).Set(value.getId())}, false);
               Entity.getCurrentSelection().SetProfession(Tags.Profession.valueOf(value.name()));
               If.End();
            }

         }
      }

      public static void setVillageBiome(List stack) {
         INumber biome = (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
         if (biome instanceof Number && ((Number)biome).isConstant()) {
            String value = ((Number)biome).getValue();
            Entity var10000 = Entity.getCurrentSelection();
            Tags.Biome var10001;
            switch (value) {
               case "0":
                  var10001 = Tags.Biome.DESERT;
                  break;
               case "1":
                  var10001 = Tags.Biome.JUNGLE;
                  break;
               case "2":
                  var10001 = Tags.Biome.PLAINS;
                  break;
               case "3":
                  var10001 = Tags.Biome.SAVANNA;
                  break;
               case "4":
                  var10001 = Tags.Biome.SNOW;
                  break;
               case "5":
                  var10001 = Tags.Biome.SWAMP;
                  break;
               case "6":
                  var10001 = Tags.Biome.TAIGA;
                  break;
               default:
                  throw new IllegalStateException("Unknown Villager Biome ID: " + value);
            }

            var10000.SetVillagerBiome(var10001);
         } else {
            VillagerBiome[] var2 = IVillagerVariant.VillagerBiome.values();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               VillagerBiome value = var2[var4];
               If.Variable.Equals(biome, new CodeValue[]{(new Number()).Set(value.getId())}, false);
               Entity.getCurrentSelection().SetVillagerBiome(Tags.Biome.valueOf(value.name()));
               If.End();
            }

         }
      }
   }

   public static enum VillagerBiome {
      DESERT(0),
      JUNGLE(1),
      PLAINS(2),
      SAVANNA(3),
      SNOW(4),
      SWAMP(5),
      TAIGA(6);

      private final int id;

      private VillagerBiome(int id) {
         this.id = id;
      }

      public int getId() {
         return this.id;
      }
   }

   public static enum VillagerProfession {
      UNEMPLOYED(0),
      ARMORER(1),
      BUTCHER(2),
      CARTOGRAPHER(3),
      CLERIC(4),
      FARMER(5),
      FISHERMAN(6),
      FLETCHER(7),
      LEATHERWORKER(8),
      LIBRARIAN(9),
      MASON(10),
      NITWIT(11),
      SHEPHERD(12),
      TOOLSMITH(13),
      WEAPONSMITH(14);

      private final int id;

      private VillagerProfession(int id) {
         this.id = id;
      }

      public int getId() {
         return this.id;
      }
   }
}

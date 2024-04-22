package net.jfdf.addon.interaction.npe.villager;

import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tags;

import java.util.List;

public interface IVillagerVariant {
    /**
     * Sets villager's profession, this
     * would affect how the villager looks,
     * it might also modify villager's trades.
     *
     * @param profession New Profession
     */
    public default void setVillagerProfession(VillagerProfession profession) {}

    /**
     * Sets villager's biome, this would
     * affect how the villager looks, the
     * look would be changed depending on
     * the biome.
     *
     * @param biome New Biome
     */
    public default void setVillagerBiome(VillagerBiome biome) {}

    enum VillagerProfession {
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

        VillagerProfession(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    enum VillagerBiome {
        DESERT(0),
        JUNGLE(1),
        PLAINS(2),
        SAVANNA(3),
        SNOW(4),
        SWAMP(5),
        TAIGA(6);

        private final int id;

        VillagerBiome(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    class MethodHandler {
        public static void setVillagerProfession(List<IStackValue> stack) {
            INumber profession = (INumber) stack.remove(stack.size() - 1).getTransformedValue();

            if(profession instanceof Number && ((Number) profession).isConstant()) {
                String value = ((Number) profession).getValue();

                Entity.getCurrentSelection().SetProfession(
                        switch(value) {
                            case "0" -> Tags.Profession.UNEMPLOYED;
                            case "1" -> Tags.Profession.ARMORER;
                            case "2" -> Tags.Profession.BUTCHER;
                            case "3" -> Tags.Profession.CARTOGRAPHER;
                            case "4" -> Tags.Profession.CLERIC;
                            case "5" -> Tags.Profession.FARMER;
                            case "6" -> Tags.Profession.FISHERMAN;
                            case "7" -> Tags.Profession.FLETCHER;
                            case "8" -> Tags.Profession.LEATHERWORKER;
                            case "9" -> Tags.Profession.LIBRARIAN;
                            case "10" -> Tags.Profession.MASON;
                            case "11" -> Tags.Profession.NITWIT;
                            case "12" -> Tags.Profession.SHEPHERD;
                            case "13" -> Tags.Profession.TOOLSMITH;
                            case "14" -> Tags.Profession.WEAPONSMITH;
                            default -> throw new IllegalStateException("Unknown Villager Profession ID: " + value);
                        }
                );

                return;
            }

            for (VillagerProfession value : VillagerProfession.values()) {
                If.Variable.Equals(profession, new CodeValue[] { new Number().Set(value.getId()) }, false);
                    Entity.getCurrentSelection().SetProfession(Tags.Profession.valueOf(value.name()));
                If.End();
            }
        }

        public static void setVillageBiome(List<IStackValue> stack) {
            INumber biome = (INumber) stack.remove(stack.size() - 1).getTransformedValue();

            if(biome instanceof Number && ((Number) biome).isConstant()) {
                String value = ((Number) biome).getValue();

                Entity.getCurrentSelection().SetVillagerBiome(
                        switch(value) {
                            case "0" -> Tags.Biome.DESERT;
                            case "1" -> Tags.Biome.JUNGLE;
                            case "2" -> Tags.Biome.PLAINS;
                            case "3" -> Tags.Biome.SAVANNA;
                            case "4" -> Tags.Biome.SNOW;
                            case "5" -> Tags.Biome.SWAMP;
                            case "6" -> Tags.Biome.TAIGA;
                            default -> throw new IllegalStateException("Unknown Villager Biome ID: " + value);
                        }
                );

                return;
            }

            for (VillagerBiome value : VillagerBiome.values()) {
                If.Variable.Equals(biome, new CodeValue[] { new Number().Set(value.getId()) }, false);
                    Entity.getCurrentSelection().SetVillagerBiome(Tags.Biome.valueOf(value.name()));
                If.End();
            }
        }
    }
}

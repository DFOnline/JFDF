package net.jfdf.addon.interaction.npe.zombie;

import net.jfdf.addon.interaction.npe.EntityType;
import net.jfdf.addon.interaction.npe.LivingNPE;
import net.jfdf.addon.interaction.npe.villager.IVillagerVariant;

@EntityType(
   eggId = "zombie_villager_spawn_egg"
)
public class ZombieVillager extends LivingNPE implements IZombieVariant, IVillagerVariant {
}

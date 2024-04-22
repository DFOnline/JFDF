package net.jfdf.addon.interaction.npe.zombie;

import net.jfdf.addon.interaction.npe.EntityType;
import net.jfdf.addon.interaction.npe.property.IHasBabyVariant;
import net.jfdf.addon.interaction.npe.LivingNPE;

@EntityType(eggId = "zombie_spawn_egg")
public class Zombie extends LivingNPE implements IZombieVariant, IHasBabyVariant {}

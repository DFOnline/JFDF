package net.jfdf.jfdf.mangement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.IItem;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IPotion;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.IVector;
import net.jfdf.jfdf.values.Tag;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Variable;

public class Entity {
   private final EntityActionBlock.EntitySelection selection;

   public Entity(EntityActionBlock.EntitySelection selection) {
      this.selection = selection;
   }

   public static Entity getCurrentSelection() {
      return new Entity(EntityActionBlock.EntitySelection.CURRENT_SELECTION);
   }

   public static Entity getDefaultEntity() {
      return new Entity(EntityActionBlock.EntitySelection.DEFAULT_ENTITY);
   }

   public static Entity getKiller() {
      return new Entity(EntityActionBlock.EntitySelection.KILLER);
   }

   public static Entity getDamager() {
      return new Entity(EntityActionBlock.EntitySelection.DAMAGER);
   }

   public static Entity getShooter() {
      return new Entity(EntityActionBlock.EntitySelection.SHOOTER);
   }

   public static Entity getVictim() {
      return new Entity(EntityActionBlock.EntitySelection.VICTIM);
   }

   public static Entity getProjectile() {
      return new Entity(EntityActionBlock.EntitySelection.PROJECTILE);
   }

   public static Entity getAllEntities() {
      return new Entity(EntityActionBlock.EntitySelection.ALL_ENTITIES);
   }

   public static Entity getAllMobs() {
      return new Entity(EntityActionBlock.EntitySelection.ALL_MOBS);
   }

   public static Entity getLastEntitySpawned() {
      return new Entity(EntityActionBlock.EntitySelection.LAST_ENTITY_SPAWNED);
   }

   public static Entity getLastMobSpawned() {
      return new Entity(EntityActionBlock.EntitySelection.LAST_MOB_SPAWNED);
   }

   public void SetParrotColor(Tags.ParrotColor parrotColorTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetParrotColor", this.selection)).SetItems((List)items).SetTags(new Tag("Parrot Color", parrotColorTag.getJSONValue())));
   }

   public void Remove() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("Remove", this.selection)).SetItems((List)items));
   }

   public void SetVelocity(IVector newVelocity, Tags.AddtoCurrentVelocity addtoCurrentVelocityTag) {
      List items = new ArrayList();
      items.add(newVelocity);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetVelocity", this.selection)).SetItems((List)items).SetTags(new Tag("Add to Current Velocity", addtoCurrentVelocityTag.getJSONValue())));
   }

   public void SetGlowSquidDark(INumber ticks) {
      List items = new ArrayList();
      items.add(ticks);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetGlowSquidDark", this.selection)).SetItems((List)items));
   }

   public void Damage(INumber damageToInflict) {
      List items = new ArrayList();
      items.add(damageToInflict);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("Damage", this.selection)).SetItems((List)items));
   }

   public void SetSheepSheared(Tags.Sheared shearedTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetSheepSheared", this.selection)).SetItems((List)items).SetTags(new Tag("Sheared", shearedTag.getJSONValue())));
   }

   public void SetMobSitting(Tags.IsSitting isSittingTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetMobSitting", this.selection)).SetItems((List)items).SetTags(new Tag("Is Sitting", isSittingTag.getJSONValue())));
   }

   public void SetAxolotlColor(Tags.AxolotlColor axolotlColorTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetAxolotlColor", this.selection)).SetItems((List)items).SetTags(new Tag("Axolotl Color", axolotlColorTag.getJSONValue())));
   }

   public void SendAnimation(Tags.AnimationType animationTypeTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SendAnimation", this.selection)).SetItems((List)items).SetTags(new Tag("Animation Type", animationTypeTag.getJSONValue())));
   }

   public void SetHorsePattern(Tags.HorseMarkings horseMarkingsTag, Tags.HorseColor horseColorTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetHorsePattern", this.selection)).SetItems((List)items).SetTags(new Tag("Horse Markings", horseMarkingsTag.getJSONValue()), new Tag("Horse Color", horseColorTag.getJSONValue())));
   }

   public void Heal(INumber amountToHeal) {
      List items = new ArrayList();
      items.add(amountToHeal);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("Heal", this.selection)).SetItems((List)items));
   }

   public void SetAI(Tags.AI aITag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetAI", this.selection)).SetItems((List)items).SetTags(new Tag("AI", aITag.getJSONValue())));
   }

   public void SetRiptiding(Tags.Riptiding riptidingTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetRiptiding", this.selection)).SetItems((List)items).SetTags(new Tag("Riptiding", riptidingTag.getJSONValue())));
   }

   public void SetProjSource(IText uuidOfShooter) {
      List items = new ArrayList();
      items.add(uuidOfShooter);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetProjSource", this.selection)).SetItems((List)items));
   }

   public void SetFoxLeaping(Tags.Leaping leapingTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetFoxLeaping", this.selection)).SetItems((List)items).SetTags(new Tag("Leaping", leapingTag.getJSONValue())));
   }

   public void SetPandaGene(Tags.GeneType geneTypeTag, Tags.SetGene setGeneTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetPandaGene", this.selection)).SetItems((List)items).SetTags(new Tag("Gene Type", geneTypeTag.getJSONValue()), new Tag("Set Gene", setGeneTag.getJSONValue())));
   }

   public void SetDyeColor(Tags.Dye dyeTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetDyeColor", this.selection)).SetItems((List)items).SetTags(new Tag("Dye", dyeTag.getJSONValue())));
   }

   public void LaunchUp(INumber launchPower, Tags.AddtoCurrentVelocity addtoCurrentVelocityTag) {
      List items = new ArrayList();
      items.add(launchPower);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("LaunchUp", this.selection)).SetItems((List)items).SetTags(new Tag("Add to Current Velocity", addtoCurrentVelocityTag.getJSONValue())));
   }

   public void SetMaxHealth(INumber maximumHealth, Tags.HealMobtoMaxHealth healMobtoMaxHealthTag) {
      List items = new ArrayList();
      items.add(maximumHealth);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetMaxHealth", this.selection)).SetItems((List)items).SetTags(new Tag("Heal Mob to Max Health", healMobtoMaxHealthTag.getJSONValue())));
   }

   public void SetAge(INumber age, Tags.AgeLock ageLockTag) {
      List items = new ArrayList();
      items.add(age);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetAge", this.selection)).SetItems((List)items).SetTags(new Tag("Age Lock", ageLockTag.getJSONValue())));
   }

   public void SetFishingTime(INumber waitTime) {
      List items = new ArrayList();
      items.add(waitTime);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetFishingTime", this.selection)).SetItems((List)items));
   }

   public void EndCrystalBeam(ILocation target) {
      List items = new ArrayList();
      if (target != null) {
         items.add(target);
      }

      CodeManager.instance.addCodeBlock((new EntityActionBlock("EndCrystalBeam", this.selection)).SetItems((List)items));
   }

   public void SetProfession(Tags.Profession professionTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetProfession", this.selection)).SetItems((List)items).SetTags(new Tag("Profession", professionTag.getJSONValue())));
   }

   public void SetArmsRaised(Tags.ArmsRaised armsRaisedTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetArmsRaised", this.selection)).SetItems((List)items).SetTags(new Tag("Arms Raised", armsRaisedTag.getJSONValue())));
   }

   public void ClearPotions() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("ClearPotions", this.selection)).SetItems((List)items));
   }

   public void ArmorStandParts(Tags.BasePlate basePlateTag, Tags.Arms armsTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("ArmorStandParts", this.selection)).SetItems((List)items).SetTags(new Tag("Base Plate", basePlateTag.getJSONValue()), new Tag("Arms", armsTag.getJSONValue())));
   }

   public void SetInvulnerable(Tags.Invulnerable invulnerableTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetInvulnerable", this.selection)).SetItems((List)items).SetTags(new Tag("Invulnerable", invulnerableTag.getJSONValue())));
   }

   public void SetPickupDelay(INumber delay) {
      List items = new ArrayList();
      items.add(delay);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetPickupDelay", this.selection)).SetItems((List)items));
   }

   public void SetTarget(IText targetNameOrUuid) {
      List items = new ArrayList();
      items.add(targetNameOrUuid);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetTarget", this.selection)).SetItems((List)items));
   }

   public void SetCreeperPower(INumber power) {
      List items = new ArrayList();
      items.add(power);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetCreeperPower", this.selection)).SetItems((List)items));
   }

   public void SetMarker(Tags.Marker markerTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetMarker", this.selection)).SetItems((List)items).SetTags(new Tag("Marker", markerTag.getJSONValue())));
   }

   public void RemoveCustomTag(IText tagName) {
      List items = new ArrayList();
      items.add(tagName);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("RemoveCustomTag", this.selection)).SetItems((List)items));
   }

   public void SetNameVisible(Tags.NameTagVisible nameTagVisibleTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetNameVisible", this.selection)).SetItems((List)items).SetTags(new Tag("Name Tag Visible", nameTagVisibleTag.getJSONValue())));
   }

   public void SetInvulTicks(INumber ticks) {
      List items = new ArrayList();
      items.add(ticks);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetInvulTicks", this.selection)).SetItems((List)items));
   }

   public void SetAbsorption(INumber absorptionHealth) {
      List items = new ArrayList();
      items.add(absorptionHealth);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetAbsorption", this.selection)).SetItems((List)items));
   }

   public void _SetPose_(Tags.Pose poseTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock(" SetPose ", this.selection)).SetItems((List)items).SetTags(new Tag("Pose", poseTag.getJSONValue())));
   }

   public void SetRearing(Tags.Rearing rearingTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetRearing", this.selection)).SetItems((List)items).SetTags(new Tag("Rearing", rearingTag.getJSONValue())));
   }

   public void CreeperCharged(Tags.Charged chargedTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("CreeperCharged", this.selection)).SetItems((List)items).SetTags(new Tag("Charged", chargedTag.getJSONValue())));
   }

   public void SetFireTicks(INumber ticks) {
      List items = new ArrayList();
      items.add(ticks);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetFireTicks", this.selection)).SetItems((List)items));
   }

   public void SetCloudRadius(INumber radius, INumber shrinkingSpeed) {
      List items = new ArrayList();
      items.add(radius);
      if (shrinkingSpeed != null) {
         items.add(shrinkingSpeed);
      }

      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetCloudRadius", this.selection)).SetItems((List)items));
   }

   public void SetGravity(Tags.Gravity gravityTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetGravity", this.selection)).SetItems((List)items).SetTags(new Tag("Gravity", gravityTag.getJSONValue())));
   }

   public void SetName(IText customName, Tags.HideNameTag hideNameTagTag) {
      List items = new ArrayList();
      if (customName != null) {
         items.add(customName);
      }

      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetName", this.selection)).SetItems((List)items).SetTags(new Tag("Hide Name Tag", hideNameTagTag.getJSONValue())));
   }

   public void Jump() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("Jump", this.selection)).SetItems((List)items));
   }

   public void SetWitherInvul(INumber ticks) {
      List items = new ArrayList();
      items.add(ticks);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetWitherInvul", this.selection)).SetItems((List)items));
   }

   public void SetGliding(Tags.Gliding glidingTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetGliding", this.selection)).SetItems((List)items).SetTags(new Tag("Gliding", glidingTag.getJSONValue())));
   }

   public void SetRotation(INumber pitch, INumber yaw) {
      List items = new ArrayList();
      items.add(pitch);
      items.add(yaw);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetRotation", this.selection)).SetItems((List)items));
   }

   public void SetFishPattern(Tags.Pattern patternTag, Tags.BodyColor bodyColorTag, Tags.PatternColor patternColorTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetFishPattern", this.selection)).SetItems((List)items).SetTags(new Tag("Pattern", patternTag.getJSONValue()), new Tag("Body Color", bodyColorTag.getJSONValue()), new Tag("Pattern Color", patternColorTag.getJSONValue())));
   }

   public void RideEntity(IText nameOrUuidOf) {
      List items = new ArrayList();
      items.add(nameOrUuidOf);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("RideEntity", this.selection)).SetItems((List)items));
   }

   public void SetEndermanBlock(CodeValue blockToHold) {
      List items = new ArrayList();
      items.add(blockToHold);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetEndermanBlock", this.selection)).SetItems((List)items));
   }

   public void Teleport(ILocation newPosition, Tags.KeepCurrentRotation keepCurrentRotationTag) {
      List items = new ArrayList();
      items.add(newPosition);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("Teleport", this.selection)).SetItems((List)items).SetTags(new Tag("Keep Current Rotation", keepCurrentRotationTag.getJSONValue())));
   }

   public void LaunchToward(ILocation launchDestination, INumber launchPower, Tags.IgnoreDistance ignoreDistanceTag, Tags.AddtoCurrentVelocity addtoCurrentVelocityTag) {
      List items = new ArrayList();
      items.add(launchDestination);
      if (launchPower != null) {
         items.add(launchPower);
      }

      CodeManager.instance.addCodeBlock((new EntityActionBlock("LaunchToward", this.selection)).SetItems((List)items).SetTags(new Tag("Ignore Distance", ignoreDistanceTag.getJSONValue()), new Tag("Add to Current Velocity", addtoCurrentVelocityTag.getJSONValue())));
   }

   public void SetArmor(IItem... armorToSet) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(armorToSet));
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetArmor", this.selection)).SetItems((List)items));
   }

   public void GetCustomTag(Variable variableToSet, IText tagName) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(tagName);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("GetCustomTag", this.selection)).SetItems((List)items));
   }

   public void SetCatType(Tags.SkinType skinTypeTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetCatType", this.selection)).SetItems((List)items).SetTags(new Tag("Skin Type", skinTypeTag.getJSONValue())));
   }

   public void SetSaddle(Tags.Saddle saddleTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetSaddle", this.selection)).SetItems((List)items).SetTags(new Tag("Saddle", saddleTag.getJSONValue())));
   }

   public void SetBulletTarget(IText targetUuid) {
      List items = new ArrayList();
      if (targetUuid != null) {
         items.add(targetUuid);
      }

      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetBulletTarget", this.selection)).SetItems((List)items));
   }

   public void LaunchProj(IItem projectileToLaunch, ILocation launchPoint, IText projectileName, INumber speed, INumber inaccuracy) {
      List items = new ArrayList();
      items.add(projectileToLaunch);
      if (launchPoint != null) {
         items.add(launchPoint);
      }

      if (projectileName != null) {
         items.add(projectileName);
      }

      if (speed != null) {
         items.add(speed);
      }

      if (inaccuracy != null) {
         items.add(inaccuracy);
      }

      CodeManager.instance.addCodeBlock((new EntityActionBlock("LaunchProj", this.selection)).SetItems((List)items));
   }

   public void SetDragonPhase(Tags.Phase phaseTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetDragonPhase", this.selection)).SetItems((List)items).SetTags(new Tag("Phase", phaseTag.getJSONValue())));
   }

   public void SetLlamaColor(Tags.LlamaColor llamaColorTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetLlamaColor", this.selection)).SetItems((List)items).SetTags(new Tag("Llama Color", llamaColorTag.getJSONValue())));
   }

   public void SetVillagerBiome(Tags.Biome biomeTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetVillagerBiome", this.selection)).SetItems((List)items).SetTags(new Tag("Biome", biomeTag.getJSONValue())));
   }

   public void SetCreeperFuse(INumber fuseTicks) {
      List items = new ArrayList();
      items.add(fuseTicks);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetCreeperFuse", this.selection)).SetItems((List)items));
   }

   public void SetBaby(Tags.Baby babyTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetBaby", this.selection)).SetItems((List)items).SetTags(new Tag("Baby", babyTag.getJSONValue())));
   }

   public void MooshroomType(Tags.MooshroomVariant mooshroomVariantTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("MooshroomType", this.selection)).SetItems((List)items).SetTags(new Tag("Mooshroom Variant", mooshroomVariantTag.getJSONValue())));
   }

   public void SetInvisible(Tags.Invisible invisibleTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetInvisible", this.selection)).SetItems((List)items).SetTags(new Tag("Invisible", invisibleTag.getJSONValue())));
   }

   public void SheepEat() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SheepEat", this.selection)).SetItems((List)items));
   }

   public void SetCatResting(Tags.Resting restingTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetCatResting", this.selection)).SetItems((List)items).SetTags(new Tag("Resting", restingTag.getJSONValue())));
   }

   public void GivePotion(IPotion[] effect, Tags.EffectParticles effectParticlesTag, Tags.OverwriteEffect overwriteEffectTag) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(effect));
      CodeManager.instance.addCodeBlock((new EntityActionBlock("GivePotion", this.selection)).SetItems((List)items).SetTags(new Tag("Effect Particles", effectParticlesTag.getJSONValue()), new Tag("Overwrite Effect", overwriteEffectTag.getJSONValue())));
   }

   public void Tame(IText ownerNameOrUuid) {
      List items = new ArrayList();
      items.add(ownerNameOrUuid);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("Tame", this.selection)).SetItems((List)items));
   }

   public void SetGlowing(Tags.Glowing glowingTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetGlowing", this.selection)).SetItems((List)items).SetTags(new Tag("Glowing", glowingTag.getJSONValue())));
   }

   public void SetGoatScreaming(Tags.Screams screamsTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetGoatScreaming", this.selection)).SetItems((List)items).SetTags(new Tag("Screams", screamsTag.getJSONValue())));
   }

   public void SetHealth(INumber currentHealth) {
      List items = new ArrayList();
      items.add(currentHealth);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetHealth", this.selection)).SetItems((List)items));
   }

   public void MobDisguise(IItem mobDisguise, IText disguiseName) {
      List items = new ArrayList();
      items.add(mobDisguise);
      if (disguiseName != null) {
         items.add(disguiseName);
      }

      CodeManager.instance.addCodeBlock((new EntityActionBlock("MobDisguise", this.selection)).SetItems((List)items));
   }

   public void BlockDisguise(CodeValue blockDisguise, IText nameOfDisguise) {
      List items = new ArrayList();
      items.add(blockDisguise);
      if (nameOfDisguise != null) {
         items.add(nameOfDisguise);
      }

      CodeManager.instance.addCodeBlock((new EntityActionBlock("BlockDisguise", this.selection)).SetItems((List)items));
   }

   public void SetMinecartBlock(CodeValue blockToShow, INumber blockOffset) {
      List items = new ArrayList();
      items.add(blockToShow);
      if (blockOffset != null) {
         items.add(blockOffset);
      }

      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetMinecartBlock", this.selection)).SetItems((List)items));
   }

   public void FoxSleeping(Tags.Sleeping sleepingTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("FoxSleeping", this.selection)).SetItems((List)items).SetTags(new Tag("Sleeping", sleepingTag.getJSONValue())));
   }

   public void SetCollidable(Tags.Collision collisionTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetCollidable", this.selection)).SetItems((List)items).SetTags(new Tag("Collision", collisionTag.getJSONValue())));
   }

   public void ArmorStandPose(IVector direction, Tags.ArmorStandPart armorStandPartTag) {
      List items = new ArrayList();
      items.add(direction);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("ArmorStandPose", this.selection)).SetItems((List)items).SetTags(new Tag("Armor Stand Part", armorStandPartTag.getJSONValue())));
   }

   public void LaunchFwd(INumber launchPower, Tags.LaunchAxis launchAxisTag, Tags.AddtoCurrentVelocity addtoCurrentVelocityTag) {
      List items = new ArrayList();
      items.add(launchPower);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("LaunchFwd", this.selection)).SetItems((List)items).SetTags(new Tag("Launch Axis", launchAxisTag.getJSONValue()), new Tag("Add to Current Velocity", addtoCurrentVelocityTag.getJSONValue())));
   }

   public void SetFallDistance(INumber fallDistance) {
      List items = new ArrayList();
      items.add(fallDistance);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetFallDistance", this.selection)).SetItems((List)items));
   }

   public void MoveToLoc(ILocation targetLocation, INumber walkSpeed) {
      List items = new ArrayList();
      items.add(targetLocation);
      if (walkSpeed != null) {
         items.add(walkSpeed);
      }

      CodeManager.instance.addCodeBlock((new EntityActionBlock("MoveToLoc", this.selection)).SetItems((List)items));
   }

   public void SetEquipment(IItem itemToSet, Tags.EquipmentSlot equipmentSlotTag) {
      List items = new ArrayList();
      if (itemToSet != null) {
         items.add(itemToSet);
      }

      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetEquipment", this.selection)).SetItems((List)items).SetTags(new Tag("Equipment Slot", equipmentSlotTag.getJSONValue())));
   }

   public void AttackAnimation(Tags.AnimationArm animationArmTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("AttackAnimation", this.selection)).SetItems((List)items).SetTags(new Tag("Animation Arm", animationArmTag.getJSONValue())));
   }

   public void SetSilenced(Tags.Silenced silencedTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetSilenced", this.selection)).SetItems((List)items).SetTags(new Tag("Silenced", silencedTag.getJSONValue())));
   }

   public void SetBeeNectar(Tags.HasNectar hasNectarTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetBeeNectar", this.selection)).SetItems((List)items).SetTags(new Tag("Has Nectar", hasNectarTag.getJSONValue())));
   }

   public void AttachLead(IText leadHolderUuid) {
      List items = new ArrayList();
      items.add(leadHolderUuid);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("AttachLead", this.selection)).SetItems((List)items));
   }

   public void SnowmanPumpkin(Tags.Pumpkin pumpkinTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SnowmanPumpkin", this.selection)).SetItems((List)items).SetTags(new Tag("Pumpkin", pumpkinTag.getJSONValue())));
   }

   public void SetCustomTag(IText tagName, INumber tagValue) {
      List items = new ArrayList();
      items.add(tagName);
      items.add(tagValue);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetCustomTag", this.selection)).SetItems((List)items));
   }

   public void RemovePotion(IPotion... effect) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(effect));
      CodeManager.instance.addCodeBlock((new EntityActionBlock("RemovePotion", this.selection)).SetItems((List)items));
   }

   public void ShearSheep() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("ShearSheep", this.selection)).SetItems((List)items));
   }

   public void ArmorStandSlots(Tags.EquipmentSlot equipmentSlotTag, Tags.Interactions interactionsTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("ArmorStandSlots", this.selection)).SetItems((List)items).SetTags(new Tag("Equipment Slot", equipmentSlotTag.getJSONValue()), new Tag("Interactions", interactionsTag.getJSONValue())));
   }

   public void SetRabbitType(Tags.SkinType skinTypeTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetRabbitType", this.selection)).SetItems((List)items).SetTags(new Tag("Skin Type", skinTypeTag.getJSONValue())));
   }

   public void SetSize(INumber size) {
      List items = new ArrayList();
      items.add(size);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetSize", this.selection)).SetItems((List)items));
   }

   public void PlayerDisguise(IText disguisePlayerName, IItem disguiseSkin) {
      List items = new ArrayList();
      items.add(disguisePlayerName);
      if (disguiseSkin != null) {
         items.add(disguiseSkin);
      }

      CodeManager.instance.addCodeBlock((new EntityActionBlock("PlayerDisguise", this.selection)).SetItems((List)items));
   }

   public void SetAngry(Tags.Angry angryTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetAngry", this.selection)).SetItems((List)items).SetTags(new Tag("Angry", angryTag.getJSONValue())));
   }

   public void SetItem(IItem newItem) {
      List items = new ArrayList();
      items.add(newItem);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetItem", this.selection)).SetItems((List)items));
   }

   public void Explode() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("Explode", this.selection)).SetItems((List)items));
   }

   public void Undisguise() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("Undisguise", this.selection)).SetItems((List)items));
   }

   public void SetDeathDrops(Tags.HasDeathDrops hasDeathDropsTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetDeathDrops", this.selection)).SetItems((List)items).SetTags(new Tag("Has Death Drops", hasDeathDropsTag.getJSONValue())));
   }

   public void SetPersistent(Tags.Persistent persistentTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetPersistent", this.selection)).SetItems((List)items).SetTags(new Tag("Persistent", persistentTag.getJSONValue())));
   }

   public void SetVillagerExp(INumber experience) {
      List items = new ArrayList();
      items.add(experience);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetVillagerExp", this.selection)).SetItems((List)items));
   }

   public void IgniteCreeper() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("IgniteCreeper", this.selection)).SetItems((List)items));
   }

   public void SetCelebrating(Tags.Celebrate celebrateTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetCelebrating", this.selection)).SetItems((List)items).SetTags(new Tag("Celebrate", celebrateTag.getJSONValue())));
   }

   public void ProjectileItem(IItem displayItem) {
      List items = new ArrayList();
      items.add(displayItem);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("ProjectileItem", this.selection)).SetItems((List)items));
   }

   public void SetHorseJump(INumber strength) {
      List items = new ArrayList();
      items.add(strength);
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetHorseJump", this.selection)).SetItems((List)items));
   }

   public void SetCarryingChest(Tags.CarryingChest carryingChestTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetCarryingChest", this.selection)).SetItems((List)items).SetTags(new Tag("Carrying Chest", carryingChestTag.getJSONValue())));
   }

   public void SetFoxType(Tags.FoxType foxTypeTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new EntityActionBlock("SetFoxType", this.selection)).SetItems((List)items).SetTags(new Tag("Fox Type", foxTypeTag.getJSONValue())));
   }
}

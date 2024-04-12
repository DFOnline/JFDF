package net.jfdf.jfdf.mangement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.jfdf.jfdf.blocks.GameActionBlock;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.IItem;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IPotion;
import net.jfdf.jfdf.values.ISound;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Tag;
import net.jfdf.jfdf.values.Tags;

public class Game {
   public static void SetFurnaceSpeed(ILocation furnaceLocation, INumber ticks) {
      List items = new ArrayList();
      items.add(furnaceLocation);
      items.add(ticks);
      CodeManager.instance.addCodeBlock((new GameActionBlock("SetFurnaceSpeed")).SetItems((List)items));
   }

   public static void BlockDropsOn() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new GameActionBlock("BlockDropsOn")).SetItems((List)items));
   }

   public static void FillContainer(ILocation containerLocation, IItem... item) {
      List items = new ArrayList();
      items.add(containerLocation);
      items.addAll(Arrays.asList(item));
      CodeManager.instance.addCodeBlock((new GameActionBlock("FillContainer")).SetItems((List)items));
   }

   public static void BreakBlock(ILocation... block) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(block));
      CodeManager.instance.addCodeBlock((new GameActionBlock("BreakBlock")).SetItems((List)items));
   }

   public static void BoneMeal(ILocation[] block, INumber numberOfUses, Tags.ShowParticles showParticlesTag) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(block));
      if (numberOfUses != null) {
         items.add(numberOfUses);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("BoneMeal")).SetItems((List)items).SetTags(new Tag("Show Particles", showParticlesTag.getJSONValue())));
   }

   public static void DebugStackTrace() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new GameActionBlock("DebugStackTrace")).SetItems((List)items));
   }

   public static void FallingBlock(ILocation blockLocation, CodeValue blockMaterial, IText[] blockData, Tags.HurtHitEntities hurtHitEntitiesTag, Tags.ReformonImpact reformonImpactTag) {
      List items = new ArrayList();
      items.add(blockLocation);
      items.add(blockMaterial);
      if (blockData != null) {
         items.addAll(Arrays.asList(blockData));
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("FallingBlock")).SetItems((List)items).SetTags(new Tag("Hurt Hit Entities", hurtHitEntitiesTag.getJSONValue()), new Tag("Reform on Impact", reformonImpactTag.getJSONValue())));
   }

   public static void DiscordWebhook(IText webhookUrl, IText messageContent) {
      List items = new ArrayList();
      items.add(webhookUrl);
      items.add(messageContent);
      CodeManager.instance.addCodeBlock((new GameActionBlock("DiscordWebhook")).SetItems((List)items));
   }

   public static void ChangeSign(ILocation signLocation, INumber lineNumber, IText newText) {
      List items = new ArrayList();
      items.add(signLocation);
      items.add(lineNumber);
      if (newText != null) {
         items.add(newText);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("ChangeSign")).SetItems((List)items));
   }

   public static void TickBlock(ILocation[] block, INumber numberOfTicks) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(block));
      if (numberOfTicks != null) {
         items.add(numberOfTicks);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("TickBlock")).SetItems((List)items));
   }

   public static void WebRequest(IText urlToRequest, IText contentBody, Tags.RequestMethod requestMethodTag, Tags.ContentType contentTypeTag) {
      List items = new ArrayList();
      items.add(urlToRequest);
      if (contentBody != null) {
         items.add(contentBody);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("WebRequest")).SetItems((List)items).SetTags(new Tag("Request Method", requestMethodTag.getJSONValue()), new Tag("Content Type", contentTypeTag.getJSONValue())));
   }

   public static void ClearScBoard() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new GameActionBlock("ClearScBoard")).SetItems((List)items));
   }

   public static void HideSidebar() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new GameActionBlock("HideSidebar")).SetItems((List)items));
   }

   public static void ReplaceItems(ILocation containerLocation, IItem[] item, IItem itemToReplaceWith, INumber amountOfItemsTo) {
      List items = new ArrayList();
      items.add(containerLocation);
      if (item != null) {
         items.addAll(Arrays.asList(item));
      }

      items.add(itemToReplaceWith);
      if (amountOfItemsTo != null) {
         items.add(amountOfItemsTo);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("ReplaceItems")).SetItems((List)items));
   }

   public static void SetEventProj(IItem projectileToLaunch) {
      List items = new ArrayList();
      if (projectileToLaunch != null) {
         items.add(projectileToLaunch);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SetEventProj")).SetItems((List)items));
   }

   public static void Explosion(ILocation explosion, INumber explosionPower) {
      List items = new ArrayList();
      items.add(explosion);
      if (explosionPower != null) {
         items.add(explosionPower);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("Explosion")).SetItems((List)items));
   }

   public static void SpawnMob(IItem mobType, ILocation spawnLocation, INumber health, IText customName, IPotion[] effect, IItem... equipment) {
      List items = new ArrayList();
      items.add(mobType);
      items.add(spawnLocation);
      if (health != null) {
         items.add(health);
      }

      if (customName != null) {
         items.add(customName);
      }

      if (effect != null) {
         items.addAll(Arrays.asList(effect));
      }

      if (equipment != null) {
         items.addAll(Arrays.asList(equipment));
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SpawnMob")).SetItems((List)items));
   }

   public static void SpawnEnderEye(ILocation locationToSpawnAt, ILocation destination, INumber lifespan, IText customName, Tags.EndofLifespan endofLifespanTag) {
      List items = new ArrayList();
      items.add(locationToSpawnAt);
      if (destination != null) {
         items.add(destination);
      }

      if (lifespan != null) {
         items.add(lifespan);
      }

      if (customName != null) {
         items.add(customName);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SpawnEnderEye")).SetItems((List)items).SetTags(new Tag("End of Lifespan", endofLifespanTag.getJSONValue())));
   }

   public static void ShowSidebar() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new GameActionBlock("ShowSidebar")).SetItems((List)items));
   }

   public static void SpawnPotionCloud(ILocation spawnLocation, IPotion[] effectToApply, INumber radius, INumber duration) {
      List items = new ArrayList();
      items.add(spawnLocation);
      if (effectToApply != null) {
         items.addAll(Arrays.asList(effectToApply));
      }

      if (radius != null) {
         items.add(radius);
      }

      if (duration != null) {
         items.add(duration);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SpawnPotionCloud")).SetItems((List)items));
   }

   public static void SetBlockData(ILocation[] location, IText... blockData) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(location));
      items.addAll(Arrays.asList(blockData));
      CodeManager.instance.addCodeBlock((new GameActionBlock("SetBlockData")).SetItems((List)items));
   }

   public static void LaunchProj(IItem projectileToLaunch, ILocation launchPoint, IText customName, INumber speed, INumber inaccuracy) {
      List items = new ArrayList();
      items.add(projectileToLaunch);
      items.add(launchPoint);
      if (customName != null) {
         items.add(customName);
      }

      if (speed != null) {
         items.add(speed);
      }

      if (inaccuracy != null) {
         items.add(inaccuracy);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("LaunchProj")).SetItems((List)items));
   }

   public static void Firework(IItem fireworkRocket, ILocation spawnLocation, Tags.Instant instantTag, Tags.Movement movementTag) {
      List items = new ArrayList();
      items.add(fireworkRocket);
      items.add(spawnLocation);
      CodeManager.instance.addCodeBlock((new GameActionBlock("Firework")).SetItems((List)items).SetTags(new Tag("Instant", instantTag.getJSONValue()), new Tag("Movement", movementTag.getJSONValue())));
   }

   public static void SetBlock(CodeValue blockToSet, ILocation[] blockLocation, IText... blockData) {
      List items = new ArrayList();
      items.add(blockToSet);
      items.addAll(Arrays.asList(blockLocation));
      if (blockData != null) {
         items.addAll(Arrays.asList(blockData));
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock(" SetBlock ")).SetItems((List)items));
   }

   public static void SetEventDamage(INumber newDamageAmount) {
      List items = new ArrayList();
      items.add(newDamageAmount);
      CodeManager.instance.addCodeBlock((new GameActionBlock("SetEventDamage")).SetItems((List)items));
   }

   public static void SetBlockGrowth(ILocation blockLocation, INumber growthStage, Tags.GrowthUnit growthUnitTag) {
      List items = new ArrayList();
      items.add(blockLocation);
      if (growthStage != null) {
         items.add(growthStage);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SetBlockGrowth")).SetItems((List)items).SetTags(new Tag("Growth Unit", growthUnitTag.getJSONValue())));
   }

   public static void SetContainerName(ILocation containerLocation, IText name) {
      List items = new ArrayList();
      items.add(containerLocation);
      items.add(name);
      CodeManager.instance.addCodeBlock((new GameActionBlock("SetContainerName")).SetItems((List)items));
   }

   public static void SpawnItem(IItem[] item, ILocation spawnLocation, IText customName, Tags.ApplyItemMotion applyItemMotionTag) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(item));
      items.add(spawnLocation);
      if (customName != null) {
         items.add(customName);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SpawnItem")).SetItems((List)items).SetTags(new Tag("Apply Item Motion", applyItemMotionTag.getJSONValue())));
   }

   public static void SetHead(ILocation headLocation, IItem head) {
      List items = new ArrayList();
      items.add(headLocation);
      items.add(head);
      CodeManager.instance.addCodeBlock((new GameActionBlock("SetHead")).SetItems((List)items));
   }

   public static void RemoveItems(ILocation containerLocation, IItem... item) {
      List items = new ArrayList();
      items.add(containerLocation);
      items.addAll(Arrays.asList(item));
      CodeManager.instance.addCodeBlock((new GameActionBlock("RemoveItems")).SetItems((List)items));
   }

   public static void ShulkerBullet(ILocation spawnLocation) {
      List items = new ArrayList();
      items.add(spawnLocation);
      CodeManager.instance.addCodeBlock((new GameActionBlock("ShulkerBullet")).SetItems((List)items));
   }

   public static void SetRegion(CodeValue blockToSet, ILocation corner1, ILocation corner2, IText blockData) {
      List items = new ArrayList();
      items.add(blockToSet);
      items.add(corner1);
      items.add(corner2);
      if (blockData != null) {
         items.add(blockData);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SetRegion")).SetItems((List)items));
   }

   public static void SetContainer(ILocation containerLocation, IItem... item) {
      List items = new ArrayList();
      items.add(containerLocation);
      items.addAll(Arrays.asList(item));
      CodeManager.instance.addCodeBlock((new GameActionBlock("SetContainer")).SetItems((List)items));
   }

   public static void SetItemInSlot(ILocation containerLocation, IItem itemToSet, INumber slot) {
      List items = new ArrayList();
      items.add(containerLocation);
      if (itemToSet != null) {
         items.add(itemToSet);
      }

      items.add(slot);
      CodeManager.instance.addCodeBlock((new GameActionBlock("SetItemInSlot")).SetItems((List)items));
   }

   public static void CloneRegion(ILocation corner1, ILocation corner2, ILocation positionToCopyFrom, ILocation positionToPasteTo, Tags.IgnoreAir ignoreAirTag, Tags.CloneBlockEntities cloneBlockEntitiesTag) {
      List items = new ArrayList();
      items.add(corner1);
      items.add(corner2);
      items.add(positionToCopyFrom);
      items.add(positionToPasteTo);
      CodeManager.instance.addCodeBlock((new GameActionBlock("CloneRegion")).SetItems((List)items).SetTags(new Tag("Ignore Air", ignoreAirTag.getJSONValue()), new Tag("Clone Block Entities", cloneBlockEntitiesTag.getJSONValue())));
   }

   public static void UncancelEvent() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new GameActionBlock("UncancelEvent")).SetItems((List)items));
   }

   public static void SpawnTNT(ILocation spawnLocation, INumber tntPower, INumber fuseDuration, IText customName) {
      List items = new ArrayList();
      items.add(spawnLocation);
      if (tntPower != null) {
         items.add(tntPower);
      }

      if (fuseDuration != null) {
         items.add(fuseDuration);
      }

      if (customName != null) {
         items.add(customName);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SpawnTNT")).SetItems((List)items));
   }

   public static void SpawnArmorStand(ILocation spawnLocation, IText customName, IItem[] equipment, Tags.Visibility visibilityTag) {
      List items = new ArrayList();
      items.add(spawnLocation);
      if (customName != null) {
         items.add(customName);
      }

      if (equipment != null) {
         items.addAll(Arrays.asList(equipment));
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SpawnArmorStand")).SetItems((List)items).SetTags(new Tag("Visibility", visibilityTag.getJSONValue())));
   }

   public static void SpawnExpOrb(ILocation spawnLocation, INumber experienceAmount, IText customName) {
      List items = new ArrayList();
      items.add(spawnLocation);
      if (experienceAmount != null) {
         items.add(experienceAmount);
      }

      if (customName != null) {
         items.add(customName);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SpawnExpOrb")).SetItems((List)items));
   }

   public static void SetEventHeal(INumber newHealingAmount) {
      List items = new ArrayList();
      items.add(newHealingAmount);
      CodeManager.instance.addCodeBlock((new GameActionBlock("SetEventHeal")).SetItems((List)items));
   }

   public static void GenerateTree(ILocation treeLocation, Tags.TreeType treeTypeTag) {
      List items = new ArrayList();
      items.add(treeLocation);
      CodeManager.instance.addCodeBlock((new GameActionBlock("GenerateTree")).SetItems((List)items).SetTags(new Tag("Tree Type", treeTypeTag.getJSONValue())));
   }

   public static void ClearContainer(ILocation containerLocation) {
      List items = new ArrayList();
      items.add(containerLocation);
      CodeManager.instance.addCodeBlock((new GameActionBlock("ClearContainer")).SetItems((List)items));
   }

   public static void SetScObj(IText objectiveName) {
      List items = new ArrayList();
      items.add(objectiveName);
      CodeManager.instance.addCodeBlock((new GameActionBlock("SetScObj")).SetItems((List)items));
   }

   public static void CancelEvent() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new GameActionBlock("CancelEvent")).SetItems((List)items));
   }

   public static void SpawnCrystal(ILocation spawnLocation, IText customName, Tags.ShowBottom showBottomTag) {
      List items = new ArrayList();
      items.add(spawnLocation);
      if (customName != null) {
         items.add(customName);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SpawnCrystal")).SetItems((List)items).SetTags(new Tag("Show Bottom", showBottomTag.getJSONValue())));
   }

   public static void SpawnFangs(ILocation spawnLocation, IText customName) {
      List items = new ArrayList();
      items.add(spawnLocation);
      if (customName != null) {
         items.add(customName);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SpawnFangs")).SetItems((List)items));
   }

   public static void SetEventSound(ISound newSound) {
      List items = new ArrayList();
      items.add(newSound);
      CodeManager.instance.addCodeBlock((new GameActionBlock("SetEventSound")).SetItems((List)items));
   }

   public static void SetCampfireItem(ILocation campfireLocation, IItem campfireItem, INumber cookingTime, Tags.CampfireSlot campfireSlotTag) {
      List items = new ArrayList();
      items.add(campfireLocation);
      items.add(campfireItem);
      if (cookingTime != null) {
         items.add(cookingTime);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SetCampfireItem")).SetItems((List)items).SetTags(new Tag("Campfire Slot", campfireSlotTag.getJSONValue())));
   }

   public static void SetEventXP(INumber experience) {
      List items = new ArrayList();
      items.add(experience);
      CodeManager.instance.addCodeBlock((new GameActionBlock("SetEventXP")).SetItems((List)items));
   }

   public static void LockContainer(ILocation containerLocation, IText lockKey) {
      List items = new ArrayList();
      items.add(containerLocation);
      if (lockKey != null) {
         items.add(lockKey);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("LockContainer")).SetItems((List)items));
   }

   public static void SpawnVehicle(IItem vehicleType, ILocation spawnLocation, IText customName) {
      List items = new ArrayList();
      items.add(vehicleType);
      items.add(spawnLocation);
      if (customName != null) {
         items.add(customName);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SpawnVehicle")).SetItems((List)items));
   }

   public static void Lightning(ILocation impactLocation) {
      List items = new ArrayList();
      items.add(impactLocation);
      CodeManager.instance.addCodeBlock((new GameActionBlock("Lightning")).SetItems((List)items));
   }

   public static void RemoveScore(IText scoreName) {
      List items = new ArrayList();
      items.add(scoreName);
      CodeManager.instance.addCodeBlock((new GameActionBlock("RemoveScore")).SetItems((List)items));
   }

   public static void SetScore(IText scoreName, INumber scoreValue) {
      List items = new ArrayList();
      items.add(scoreName);
      if (scoreValue != null) {
         items.add(scoreValue);
      }

      CodeManager.instance.addCodeBlock((new GameActionBlock("SetScore")).SetItems((List)items));
   }

   public static void BlockDropsOff() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new GameActionBlock("BlockDropsOff")).SetItems((List)items));
   }

   public static void ClearItems(ILocation containerLocation, IItem... item) {
      List items = new ArrayList();
      items.add(containerLocation);
      items.addAll(Arrays.asList(item));
      CodeManager.instance.addCodeBlock((new GameActionBlock("ClearItems")).SetItems((List)items));
   }

   public static void SignColor(ILocation signLocation, Tags.Glowing glowing, Tags.TextColor textColor) {
      CodeManager.instance.addCodeBlock((new GameActionBlock("SignColor")).SetItems(signLocation).SetTags(new Tag("Glowing", glowing.getJSONValue()), new Tag("Text Color", textColor.getJSONValue())));
   }
}

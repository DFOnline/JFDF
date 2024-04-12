package net.jfdf.jfdf.mangement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.jfdf.jfdf.blocks.PlayerActionBlock;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.IItem;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IParticle;
import net.jfdf.jfdf.values.IPotion;
import net.jfdf.jfdf.values.ISound;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.IVector;
import net.jfdf.jfdf.values.Tag;
import net.jfdf.jfdf.values.Tags;

public class Player {
   private final PlayerActionBlock.PlayerSelection selection;

   public Player(PlayerActionBlock.PlayerSelection selection) {
      this.selection = selection;
   }

   public static Player getCurrentSelection() {
      return new Player(PlayerActionBlock.PlayerSelection.CURRENT_SELECTION);
   }

   public static Player getDefaultPlayer() {
      return new Player(PlayerActionBlock.PlayerSelection.DEFAULT_PLAYER);
   }

   public static Player getKiller() {
      return new Player(PlayerActionBlock.PlayerSelection.KILLER);
   }

   public static Player getDamager() {
      return new Player(PlayerActionBlock.PlayerSelection.DAMAGER);
   }

   public static Player getShooter() {
      return new Player(PlayerActionBlock.PlayerSelection.SHOOTER);
   }

   public static Player getVictim() {
      return new Player(PlayerActionBlock.PlayerSelection.VICTIM);
   }

   public static Player getAllPlayers() {
      return new Player(PlayerActionBlock.PlayerSelection.ALL_PLAYERS);
   }

   private List toList(CodeValue[] array) {
      List list = Arrays.asList(array);
      list.remove((Object)null);
      return list;
   }

   public void SetHotbar(IItem... item) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(item));
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetHotbar", this.selection)).SetItems((List)items));
   }

   public void SetReducedDebug(Tags.ReducedDebugInfoEnabled reducedDebugInfoEnabledTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetReducedDebug", this.selection)).SetItems((List)items).SetTags(new Tag("Reduced Debug Info Enabled", reducedDebugInfoEnabledTag.getJSONValue())));
   }

   public void CloseInv() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("CloseInv", this.selection)).SetItems((List)items));
   }

   public void GiveItems(IItem[] item, INumber amountToGive) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(item));
      if (amountToGive != null) {
         items.add(amountToGive);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("GiveItems", this.selection)).SetItems((List)items));
   }

   public void BossBar(IText title, INumber currentHealth, INumber maximumHealth, Tags.BarSlot barSlotTag, Tags.BarStyle barStyleTag, Tags.SkyEffect skyEffectTag, Tags.BarColor barColorTag) {
      List items = new ArrayList();
      if (title != null) {
         items.add(title);
      }

      if (currentHealth != null) {
         items.add(currentHealth);
      }

      if (maximumHealth != null) {
         items.add(maximumHealth);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("BossBar", this.selection)).SetItems((List)items).SetTags(new Tag("Bar Slot", barSlotTag.getJSONValue()), new Tag("Bar Style", barStyleTag.getJSONValue()), new Tag("Sky Effect", skyEffectTag.getJSONValue()), new Tag("Bar Color", barColorTag.getJSONValue())));
   }

   public void ParticleSphere(IParticle effect, ILocation centerLocation, INumber diameter) {
      List items = new ArrayList();
      items.add(effect);
      items.add(centerLocation);
      if (diameter != null) {
         items.add(diameter);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ParticleSphere", this.selection)).SetItems((List)items));
   }

   public void SetAtkSpeed(INumber attackSpeed) {
      List items = new ArrayList();
      if (attackSpeed != null) {
         items.add(attackSpeed);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetAtkSpeed", this.selection)).SetItems((List)items));
   }

   public void SetVelocity(IVector newVelocity, Tags.AddtoCurrentVelocity addtoCurrentVelocityTag) {
      List items = new ArrayList();
      items.add(newVelocity);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetVelocity", this.selection)).SetItems((List)items).SetTags(new Tag("Add to Current Velocity", addtoCurrentVelocityTag.getJSONValue())));
   }

   public void Particle(IParticle[] effect, ILocation effectLocation) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(effect));
      items.add(effectLocation);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("Particle", this.selection)).SetItems((List)items));
   }

   public void AddInvRow(IItem[] itemsToDisplay, Tags.NewRowPosition newRowPositionTag) {
      List items = new ArrayList();
      if (itemsToDisplay != null) {
         items.addAll(Arrays.asList(itemsToDisplay));
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("AddInvRow", this.selection)).SetItems((List)items).SetTags(new Tag("New Row Position", newRowPositionTag.getJSONValue())));
   }

   public void DisplayLightning(ILocation strikeLocation) {
      List items = new ArrayList();
      items.add(strikeLocation);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("DisplayLightning", this.selection)).SetItems((List)items));
   }

   public void Damage(INumber damageToInflict, IText damageSource) {
      List items = new ArrayList();
      items.add(damageToInflict);
      if (damageSource != null) {
         items.add(damageSource);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("Damage", this.selection)).SetItems((List)items));
   }

   public void SetExp(INumber experienceToSet, Tags.SetExperience setExperienceTag) {
      List items = new ArrayList();
      items.add(experienceToSet);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetExp", this.selection)).SetItems((List)items).SetTags(new Tag("Set Experience", setExperienceTag.getJSONValue())));
   }

   public void SendAnimation(Tags.AnimationType animationTypeTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SendAnimation", this.selection)).SetItems((List)items).SetTags(new Tag("Animation Type", animationTypeTag.getJSONValue())));
   }

   public void SetXPProg(INumber progress) {
      List items = new ArrayList();
      items.add(progress);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetXPProg", this.selection)).SetItems((List)items));
   }

   public void SetInventory(IItem... item) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(item));
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetInventory", this.selection)).SetItems((List)items));
   }

   public void TpSequence(ILocation[] locationsTo, INumber teleportDelay) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(locationsTo));
      if (teleportDelay != null) {
         items.add(teleportDelay);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("TpSequence", this.selection)).SetItems((List)items));
   }

   public void Heal(INumber amountToHeal) {
      List items = new ArrayList();
      items.add(amountToHeal);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("Heal", this.selection)).SetItems((List)items));
   }

   public void SetSpawnPoint(ILocation theNewSpawnLocation) {
      List items = new ArrayList();
      items.add(theNewSpawnLocation);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetSpawnPoint", this.selection)).SetItems((List)items));
   }

   public void ParticleSpiral(IParticle effect, ILocation baseLocation, INumber length, INumber diameter, INumber effectCount, INumber rotations) {
      List items = new ArrayList();
      items.add(effect);
      items.add(baseLocation);
      if (length != null) {
         items.add(length);
      }

      if (diameter != null) {
         items.add(diameter);
      }

      if (effectCount != null) {
         items.add(effectCount);
      }

      if (rotations != null) {
         items.add(rotations);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ParticleSpiral", this.selection)).SetItems((List)items));
   }

   public void SetInventoryKept(Tags.InventoryKept inventoryKeptTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetInventoryKept", this.selection)).SetItems((List)items).SetTags(new Tag("Inventory Kept", inventoryKeptTag.getJSONValue())));
   }

   public void SetAllowFlight(Tags.AllowFlight allowFlightTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetAllowFlight", this.selection)).SetItems((List)items).SetTags(new Tag("Allow Flight", allowFlightTag.getJSONValue())));
   }

   public void LaunchUp(INumber launchPower, Tags.AddtoCurrentVelocity addtoCurrentVelocityTag) {
      List items = new ArrayList();
      items.add(launchPower);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("LaunchUp", this.selection)).SetItems((List)items).SetTags(new Tag("Add to Current Velocity", addtoCurrentVelocityTag.getJSONValue())));
   }

   public void SetMaxHealth(INumber maximumHealth, Tags.HealPlayertoMaxHealth healPlayertoMaxHealthTag) {
      List items = new ArrayList();
      items.add(maximumHealth);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetMaxHealth", this.selection)).SetItems((List)items).SetTags(new Tag("Heal Player to Max Health", healPlayertoMaxHealthTag.getJSONValue())));
   }

   public void RemoveBossBar(INumber bossBarPosition) {
      List items = new ArrayList();
      items.add(bossBarPosition);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock(" RemoveBossBar ", this.selection)).SetItems((List)items));
   }

   public void AdventureMode() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("AdventureMode", this.selection)).SetItems((List)items));
   }

   public void ForceFlight(Tags.FlightMode flightModeTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ForceFlight", this.selection)).SetItems((List)items).SetTags(new Tag("Flight Mode", flightModeTag.getJSONValue())));
   }

   public void LoadInv() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("LoadInv", this.selection)).SetItems((List)items));
   }

   public void SpectatorMode() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SpectatorMode", this.selection)).SetItems((List)items));
   }

   public void ClearPotions() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ClearPotions", this.selection)).SetItems((List)items));
   }

   public void Kick() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("Kick", this.selection)).SetItems((List)items));
   }

   public void SetTabListInfo(IText[] headerfooterText, Tags.PlayerListField playerListFieldTag) {
      List items = new ArrayList();
      if (headerfooterText != null) {
         items.addAll(Arrays.asList(headerfooterText));
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetTabListInfo", this.selection)).SetItems((List)items).SetTags(new Tag("Player List Field", playerListFieldTag.getJSONValue())));
   }

   public void SpectateTarget(IText targetUuid) {
      List items = new ArrayList();
      items.add(targetUuid);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SpectateTarget", this.selection)).SetItems((List)items));
   }

   public void SurvivalMode() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SurvivalMode", this.selection)).SetItems((List)items));
   }

   public void SetBossBar(IText title, INumber currentHealth, INumber maximumHealth, INumber bossBarPosition, Tags.BarStyle barStyleTag, Tags.SkyEffect skyEffectTag, Tags.BarColor barColorTag) {
      List items = new ArrayList();
      if (title != null) {
         items.add(title);
      }

      if (currentHealth != null) {
         items.add(currentHealth);
      }

      if (maximumHealth != null) {
         items.add(maximumHealth);
      }

      if (bossBarPosition != null) {
         items.add(bossBarPosition);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock(" SetBossBar ", this.selection)).SetItems((List)items).SetTags(new Tag("Bar Style", barStyleTag.getJSONValue()), new Tag("Sky Effect", skyEffectTag.getJSONValue()), new Tag("Bar Color", barColorTag.getJSONValue())));
   }

   public void SetSkin(IItem playerHead) {
      List items = new ArrayList();
      items.add(playerHead);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetSkin", this.selection)).SetItems((List)items));
   }

   public void SetStatus(IText gameStatus) {
      List items = new ArrayList();
      items.add(gameStatus);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetStatus", this.selection)).SetItems((List)items));
   }

   public void SetInvulTicks(INumber ticks) {
      List items = new ArrayList();
      items.add(ticks);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetInvulTicks", this.selection)).SetItems((List)items));
   }

   public void SetCursorItem(IItem itemToSet) {
      List items = new ArrayList();
      if (itemToSet != null) {
         items.add(itemToSet);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetCursorItem", this.selection)).SetItems((List)items));
   }

   public void SetAbsorption(INumber absorptionHealth) {
      List items = new ArrayList();
      items.add(absorptionHealth);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetAbsorption", this.selection)).SetItems((List)items));
   }

   public void SetFireTicks(INumber ticks) {
      List items = new ArrayList();
      items.add(ticks);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetFireTicks", this.selection)).SetItems((List)items));
   }

   public void RemoveInvRow(INumber rowsToRemove, Tags.RowtoRemove rowtoRemoveTag) {
      List items = new ArrayList();
      if (rowsToRemove != null) {
         items.add(rowsToRemove);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("RemoveInvRow", this.selection)).SetItems((List)items).SetTags(new Tag("Row to Remove", rowtoRemoveTag.getJSONValue())));
   }

   public void DisableBlocks(CodeValue... blocksToDisallow) {
      List items = new ArrayList();
      if (blocksToDisallow != null) {
         items.addAll(Arrays.asList(blocksToDisallow));
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("DisableBlocks", this.selection)).SetItems((List)items));
   }

   public void ClearInv(Tags.ClearMode clearModeTag, Tags.ClearCraftingAndCursor clearCraftingAndCursorTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ClearInv", this.selection)).SetItems((List)items).SetTags(new Tag("Clear Mode", clearModeTag.getJSONValue()), new Tag("Clear Crafting and Cursor", clearCraftingAndCursorTag.getJSONValue())));
   }

   public void SetFreezeTicks(INumber ticks) {
      List items = new ArrayList();
      items.add(ticks);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetFreezeTicks", this.selection)).SetItems((List)items));
   }

   public void SetGliding(Tags.Gliding glidingTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetGliding", this.selection)).SetItems((List)items).SetTags(new Tag("Gliding", glidingTag.getJSONValue())));
   }

   public void SetRotation(INumber pitch, INumber yaw) {
      List items = new ArrayList();
      items.add(pitch);
      items.add(yaw);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetRotation", this.selection)).SetItems((List)items));
   }

   public void ParticleCircle(IParticle effect, ILocation centerLocation, INumber diameter) {
      List items = new ArrayList();
      items.add(effect);
      items.add(centerLocation);
      if (diameter != null) {
         items.add(diameter);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ParticleCircle", this.selection)).SetItems((List)items));
   }

   public void ClearItems(IItem... item) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(item));
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ClearItems", this.selection)).SetItems((List)items));
   }

   public void DisplayBlock(CodeValue blockToDisplay, ILocation blockLocation, ILocation endOfRegion, IText... blockData) {
      List items = new ArrayList();
      items.add(blockToDisplay);
      items.add(blockLocation);
      if (endOfRegion != null) {
         items.add(endOfRegion);
      }

      if (blockData != null) {
         items.addAll(Arrays.asList(blockData));
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("DisplayBlock", this.selection)).SetItems((List)items));
   }

   public void RideEntity(IText nameOrUuidOf) {
      List items = new ArrayList();
      items.add(nameOrUuidOf);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("RideEntity", this.selection)).SetItems((List)items));
   }

   public void SetFlying(Tags.Flying flyingTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetFlying", this.selection)).SetItems((List)items).SetTags(new Tag("Flying", flyingTag.getJSONValue())));
   }

   public void RmWorldBorder() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("RmWorldBorder", this.selection)).SetItems((List)items));
   }

   public void DisplayBlockOpen(ILocation blockLocation, Tags.ContainerState containerStateTag) {
      List items = new ArrayList();
      items.add(blockLocation);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("DisplayBlockOpen", this.selection)).SetItems((List)items).SetTags(new Tag("Container State", containerStateTag.getJSONValue())));
   }

   public void SendAdvancement(IText advancementName, IItem advancementIcon, Tags.ToastType toastTypeTag) {
      List items = new ArrayList();
      items.add(advancementName);
      items.add(advancementIcon);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SendAdvancement", this.selection)).SetItems((List)items).SetTags(new Tag("Toast Type", toastTypeTag.getJSONValue())));
   }

   public void Teleport(ILocation newPosition, Tags.KeepCurrentRotation keepCurrentRotationTag) {
      List items = new ArrayList();
      items.add(newPosition);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("Teleport", this.selection)).SetItems((List)items).SetTags(new Tag("Keep Current Rotation", keepCurrentRotationTag.getJSONValue())));
   }

   public void SetAllowPVP(Tags.PVP pVPTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetAllowPVP", this.selection)).SetItems((List)items).SetTags(new Tag("PVP", pVPTag.getJSONValue())));
   }

   public void SetMenuItem(INumber slot, IItem itemToSet) {
      List items = new ArrayList();
      items.add(slot);
      if (itemToSet != null) {
         items.add(itemToSet);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetMenuItem", this.selection)).SetItems((List)items));
   }

   public void LaunchToward(ILocation launchDestination, INumber launchPower, Tags.AddtoCurrentVelocity addtoCurrentVelocityTag, Tags.IgnoreDistance ignoreDistanceTag) {
      List items = new ArrayList();
      items.add(launchDestination);
      if (launchPower != null) {
         items.add(launchPower);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("LaunchToward", this.selection)).SetItems((List)items).SetTags(new Tag("Add to Current Velocity", addtoCurrentVelocityTag.getJSONValue()), new Tag("Ignore Distance", ignoreDistanceTag.getJSONValue())));
   }

   public void SetArmor(IItem... armorToSet) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(armorToSet));
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetArmor", this.selection)).SetItems((List)items));
   }

   public void DisplayGateway(ILocation gatewayLocation, Tags.AnimationType animationTypeTag) {
      List items = new ArrayList();
      items.add(gatewayLocation);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("DisplayGateway", this.selection)).SetItems((List)items).SetTags(new Tag("Animation Type", animationTypeTag.getJSONValue())));
   }

   public void SetDisguiseVisible(Tags.DisguiseVisible disguiseVisibleTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetDisguiseVisible", this.selection)).SetItems((List)items).SetTags(new Tag("Disguise Visible", disguiseVisibleTag.getJSONValue())));
   }

   public void SetArrowsStuck(INumber arrowCount) {
      List items = new ArrayList();
      if (arrowCount != null) {
         items.add(arrowCount);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetArrowsStuck", this.selection)).SetItems((List)items));
   }

   public void GiveExp(INumber experienceToGive, Tags.GiveExperience giveExperienceTag) {
      List items = new ArrayList();
      items.add(experienceToGive);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("GiveExp", this.selection)).SetItems((List)items).SetTags(new Tag("Give Experience", giveExperienceTag.getJSONValue())));
   }

   public void FaceLocation(ILocation locationToFace) {
      List items = new ArrayList();
      items.add(locationToFace);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("FaceLocation", this.selection)).SetItems((List)items));
   }

   public void ReplaceItems(IItem[] item, IItem itemToReplaceWith, INumber amountOfItemsTo) {
      List items = new ArrayList();
      if (item != null) {
         items.addAll(Arrays.asList(item));
      }

      items.add(itemToReplaceWith);
      if (amountOfItemsTo != null) {
         items.add(amountOfItemsTo);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ReplaceItems", this.selection)).SetItems((List)items));
   }

   public void ActionBar(IText[] messageToSend, Tags.TextValueMerging textValueMergingTag) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(messageToSend));
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ActionBar", this.selection)).SetItems((List)items).SetTags(new Tag("Text Value Merging", textValueMergingTag.getJSONValue())));
   }

   public void SetChatTag(IText[] chatTag) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(chatTag));
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetChatTag", this.selection)).SetItems((List)items));
   }

   public void SendMessage(CodeValue[] messageToSend, Tags.AlignmentMode alignmentModeTag, Tags.TextValueMerging textValueMergingTag) {
      List items = new ArrayList();
      if (messageToSend != null) {
         items.addAll(Arrays.asList(messageToSend));
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SendMessage", this.selection)).SetItems((List)items).SetTags(new Tag("Alignment Mode", alignmentModeTag.getJSONValue()), new Tag("Text Value Merging", textValueMergingTag.getJSONValue())));
   }

   public void ShiftWorldBorder(INumber newRadius, INumber blocksPerSecond) {
      List items = new ArrayList();
      items.add(newRadius);
      if (blocksPerSecond != null) {
         items.add(blocksPerSecond);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ShiftWorldBorder", this.selection)).SetItems((List)items));
   }

   public void SetSlotItem(IItem itemToSet, INumber slotToSet) {
      List items = new ArrayList();
      if (itemToSet != null) {
         items.add(itemToSet);
      }

      items.add(slotToSet);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetSlotItem", this.selection)).SetItems((List)items));
   }

   public void PlaySoundSeq(ISound[] soundsToPlay, INumber soundDelay, ILocation playbackLocation) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(soundsToPlay));
      if (soundDelay != null) {
         items.add(soundDelay);
      }

      if (playbackLocation != null) {
         items.add(playbackLocation);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("PlaySoundSeq", this.selection)).SetItems((List)items));
   }

   public void ParticleLineA(IParticle effect, ILocation startLocation, ILocation endLocation, INumber effectSpacing, INumber animationDuration) {
      List items = new ArrayList();
      items.add(effect);
      items.add(startLocation);
      items.add(endLocation);
      if (effectSpacing != null) {
         items.add(effectSpacing);
      }

      if (animationDuration != null) {
         items.add(animationDuration);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ParticleLineA", this.selection)).SetItems((List)items));
   }

   public void DisplaySignText(ILocation signLocation, IText[] textLine, Tags.TextColor textColorTag) {
      List items = new ArrayList();
      items.add(signLocation);
      if (textLine != null) {
         items.addAll(Arrays.asList(textLine));
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("DisplaySignText", this.selection)).SetItems((List)items).SetTags(new Tag("Text Color", textColorTag.getJSONValue())));
   }

   public void SetSpeed(INumber movementSpeed, Tags.SpeedType speedTypeTag) {
      List items = new ArrayList();
      items.add(movementSpeed);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetSpeed", this.selection)).SetItems((List)items).SetTags(new Tag("Speed Type", speedTypeTag.getJSONValue())));
   }

   public void ExpandInv(IItem... itemsToDisplay) {
      List items = new ArrayList();
      if (itemsToDisplay != null) {
         items.addAll(Arrays.asList(itemsToDisplay));
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ExpandInv", this.selection)).SetItems((List)items));
   }

   public void SetInvName(IText inventoryName) {
      List items = new ArrayList();
      items.add(inventoryName);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetInvName", this.selection)).SetItems((List)items));
   }

   public void LaunchProj(IItem projectileTo, ILocation launchPoint, IText projectileName, INumber speed, INumber inaccuracy) {
      List items = new ArrayList();
      items.add(projectileTo);
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

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("LaunchProj", this.selection)).SetItems((List)items));
   }

   public void SetItemCooldown(IItem itemTypeToAffect, INumber cooldownInTicks) {
      List items = new ArrayList();
      items.add(itemTypeToAffect);
      items.add(cooldownInTicks);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetItemCooldown", this.selection)).SetItems((List)items));
   }

   public void SetPlayerWeather(Tags.Weather weatherTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetPlayerWeather", this.selection)).SetItems((List)items).SetTags(new Tag("Weather", weatherTag.getJSONValue())));
   }

   public void SendHover(IText messageToSendInChat, IText messageToSeeOnHover) {
      List items = new ArrayList();
      items.add(messageToSendInChat);
      items.add(messageToSeeOnHover);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SendHover", this.selection)).SetItems((List)items));
   }

   public void SetAirTicks(INumber breathTicks) {
      List items = new ArrayList();
      items.add(breathTicks);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetAirTicks", this.selection)).SetItems((List)items));
   }

   public void DisplayPickup(IText entityUuid, IText collectorUuid) {
      List items = new ArrayList();
      items.add(entityUuid);
      items.add(collectorUuid);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("DisplayPickup", this.selection)).SetItems((List)items));
   }

   public void ParticleCuboidA(IParticle effect, ILocation corner1, ILocation corner2, INumber effectSpacing, INumber animationDuration, Tags.FillType fillTypeTag) {
      List items = new ArrayList();
      items.add(effect);
      items.add(corner1);
      items.add(corner2);
      if (effectSpacing != null) {
         items.add(effectSpacing);
      }

      if (animationDuration != null) {
         items.add(animationDuration);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ParticleCuboidA", this.selection)).SetItems((List)items).SetTags(new Tag("Fill Type", fillTypeTag.getJSONValue())));
   }

   public void ChatColor(IText newChatColor) {
      List items = new ArrayList();
      items.add(newChatColor);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ChatColor", this.selection)).SetItems((List)items));
   }

   public void SetWorldBorder(ILocation centerPosition, INumber radiusInBlocks, INumber warningDistance) {
      List items = new ArrayList();
      items.add(centerPosition);
      items.add(radiusInBlocks);
      if (warningDistance != null) {
         items.add(warningDistance);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetWorldBorder", this.selection)).SetItems((List)items));
   }

   public void SetPlayerTime(INumber daylightTicks) {
      List items = new ArrayList();
      items.add(daylightTicks);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetPlayerTime", this.selection)).SetItems((List)items));
   }

   public void GivePotion(IPotion[] effect, Tags.ShowIcon showIconTag, Tags.OverwriteEffect overwriteEffectTag, Tags.EffectParticles effectParticlesTag) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(effect));
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("GivePotion", this.selection)).SetItems((List)items).SetTags(new Tag("Show Icon", showIconTag.getJSONValue()), new Tag("Overwrite Effect", overwriteEffectTag.getJSONValue()), new Tag("Effect Particles", effectParticlesTag.getJSONValue())));
   }

   public void PlaySound(ISound[] soundToPlay, ILocation playbackLocation, Tags.SoundSource soundSourceTag) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(soundToPlay));
      if (playbackLocation != null) {
         items.add(playbackLocation);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("PlaySound", this.selection)).SetItems((List)items).SetTags(new Tag("Sound Source", soundSourceTag.getJSONValue())));
   }

   public void SetCompass(ILocation newTarget) {
      List items = new ArrayList();
      items.add(newTarget);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetCompass", this.selection)).SetItems((List)items));
   }

   public void RemoveItems(IItem... item) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(item));
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("RemoveItems", this.selection)).SetItems((List)items));
   }

   public void RngTeleport(ILocation[] locationsTo, Tags.KeepCurrentRotation keepCurrentRotationTag) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(locationsTo));
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("RngTeleport", this.selection)).SetItems((List)items).SetTags(new Tag("Keep Current Rotation", keepCurrentRotationTag.getJSONValue())));
   }

   public void BoostElytra(IItem firework) {
      List items = new ArrayList();
      items.add(firework);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("BoostElytra", this.selection)).SetItems((List)items));
   }

   public void SaveInv() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SaveInv", this.selection)).SetItems((List)items));
   }

   public void OpenBook(IItem bookItem) {
      List items = new ArrayList();
      items.add(bookItem);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("OpenBook", this.selection)).SetItems((List)items));
   }

   public void SetHealth(INumber currentHealth) {
      List items = new ArrayList();
      items.add(currentHealth);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetHealth", this.selection)).SetItems((List)items));
   }

   public void MobDisguise(IItem mobDisguise, IText disguiseName) {
      List items = new ArrayList();
      items.add(mobDisguise);
      if (disguiseName != null) {
         items.add(disguiseName);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("MobDisguise", this.selection)).SetItems((List)items));
   }

   public void BlockDisguise(CodeValue blockDisguise, IText nameOfDisguise) {
      List items = new ArrayList();
      items.add(blockDisguise);
      if (nameOfDisguise != null) {
         items.add(nameOfDisguise);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("BlockDisguise", this.selection)).SetItems((List)items));
   }

   public void EnableBlocks(CodeValue... blocksToAllow) {
      List items = new ArrayList();
      if (blocksToAllow != null) {
         items.addAll(Arrays.asList(blocksToAllow));
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("EnableBlocks", this.selection)).SetItems((List)items));
   }

   public void WalkSpeed(INumber ofNormal) {
      List items = new ArrayList();
      items.add(ofNormal);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("WalkSpeed", this.selection)).SetItems((List)items));
   }

   public void OpenBlockInv(ILocation containerLocation) {
      List items = new ArrayList();
      items.add(containerLocation);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("OpenBlockInv", this.selection)).SetItems((List)items));
   }

   public void SetCollidable(Tags.Collision collisionTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetCollidable", this.selection)).SetItems((List)items).SetTags(new Tag("Collision", collisionTag.getJSONValue())));
   }

   public void LaunchFwd(INumber launchPower, Tags.AddtoCurrentVelocity addtoCurrentVelocityTag, Tags.LaunchAxis launchAxisTag) {
      List items = new ArrayList();
      items.add(launchPower);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("LaunchFwd", this.selection)).SetItems((List)items).SetTags(new Tag("Add to Current Velocity", addtoCurrentVelocityTag.getJSONValue()), new Tag("Launch Axis", launchAxisTag.getJSONValue())));
   }

   public void ParticleCircleA(IParticle effect, ILocation centerLocation, INumber diameter, INumber animationDuration) {
      List items = new ArrayList();
      items.add(effect);
      items.add(centerLocation);
      if (diameter != null) {
         items.add(diameter);
      }

      if (animationDuration != null) {
         items.add(animationDuration);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ParticleCircleA", this.selection)).SetItems((List)items));
   }

   public void SetFallDistance(INumber fallDistance) {
      List items = new ArrayList();
      items.add(fallDistance);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetFallDistance", this.selection)).SetItems((List)items));
   }

   public void CreativeMode() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("CreativeMode", this.selection)).SetItems((List)items));
   }

   public void SetEquipment(IItem itemToSet, Tags.EquipmentSlot equipmentSlotTag) {
      List items = new ArrayList();
      if (itemToSet != null) {
         items.add(itemToSet);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetEquipment", this.selection)).SetItems((List)items).SetTags(new Tag("Equipment Slot", equipmentSlotTag.getJSONValue())));
   }

   public void AttackAnimation(Tags.AnimationArm animationArmTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("AttackAnimation", this.selection)).SetItems((List)items).SetTags(new Tag("Animation Arm", animationArmTag.getJSONValue())));
   }

   public void SetDropsEnabled(Tags.SpawnDeathDrops spawnDeathDropsTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetDropsEnabled", this.selection)).SetItems((List)items).SetTags(new Tag("Spawn Death Drops", spawnDeathDropsTag.getJSONValue())));
   }

   public void RemovePotion(IPotion... effect) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(effect));
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("RemovePotion", this.selection)).SetItems((List)items));
   }

   public void DisplayHologram(ILocation displayLocation, IText textToDisplay) {
      List items = new ArrayList();
      items.add(displayLocation);
      items.add(textToDisplay);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("DisplayHologram", this.selection)).SetItems((List)items));
   }

   public void DisplayFracture(ILocation[] block, INumber fractureLevel, Tags.OverwritePreviousFracture overwritePreviousFractureTag) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(block));
      if (fractureLevel != null) {
         items.add(fractureLevel);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("DisplayFracture", this.selection)).SetItems((List)items).SetTags(new Tag("Overwrite Previous Fracture", overwritePreviousFractureTag.getJSONValue())));
   }

   public void SetEntityHidden(IText entityUuid, Tags.Hidden hiddenTag) {
      List items = new ArrayList();
      items.add(entityUuid);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetEntityHidden", this.selection)).SetItems((List)items).SetTags(new Tag("Hidden", hiddenTag.getJSONValue())));
   }

   public void ShowInv(IItem... itemsToDisplay) {
      List items = new ArrayList();
      if (itemsToDisplay != null) {
         items.addAll(Arrays.asList(itemsToDisplay));
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ShowInv", this.selection)).SetItems((List)items));
   }

   public void SetSlot(INumber newSlot) {
      List items = new ArrayList();
      items.add(newSlot);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetSlot", this.selection)).SetItems((List)items));
   }

   public void ParticleRay(IParticle effect, ILocation rayLocation, IVector rayVector, INumber effectSpacing) {
      List items = new ArrayList();
      items.add(effect);
      items.add(rayLocation);
      items.add(rayVector);
      if (effectSpacing != null) {
         items.add(effectSpacing);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ParticleRay", this.selection)).SetItems((List)items));
   }

   public void SetFoodLevel(INumber foodLevel) {
      List items = new ArrayList();
      items.add(foodLevel);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetFoodLevel", this.selection)).SetItems((List)items));
   }

   public void PlayerDisguise(IText disguisePlayerName, IItem disguiseSkin) {
      List items = new ArrayList();
      items.add(disguisePlayerName);
      if (disguiseSkin != null) {
         items.add(disguiseSkin);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("PlayerDisguise", this.selection)).SetItems((List)items));
   }

   public void ParticleCuboid(IParticle effect, ILocation corner1, ILocation corner2, INumber effectSpacing, Tags.FillType fillTypeTag) {
      List items = new ArrayList();
      items.add(effect);
      items.add(corner1);
      items.add(corner2);
      if (effectSpacing != null) {
         items.add(effectSpacing);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ParticleCuboid", this.selection)).SetItems((List)items).SetTags(new Tag("Fill Type", fillTypeTag.getJSONValue())));
   }

   public void SendMessageSeq(IText[] messagesToSend, INumber messageDelayTicks) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(messagesToSend));
      if (messageDelayTicks != null) {
         items.add(messageDelayTicks);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SendMessageSeq", this.selection)).SetItems((List)items));
   }

   public void SetSaturation(INumber saturationLevel) {
      List items = new ArrayList();
      items.add(saturationLevel);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetSaturation", this.selection)).SetItems((List)items));
   }

   public void Undisguise() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("Undisguise", this.selection)).SetItems((List)items));
   }

   public void ParticleSpiralA(IParticle effect, ILocation baseLocation, INumber length, INumber diameter, INumber particleCount, INumber rotations, INumber animationDuration) {
      List items = new ArrayList();
      items.add(effect);
      items.add(baseLocation);
      if (length != null) {
         items.add(length);
      }

      if (diameter != null) {
         items.add(diameter);
      }

      if (particleCount != null) {
         items.add(particleCount);
      }

      if (rotations != null) {
         items.add(rotations);
      }

      if (animationDuration != null) {
         items.add(animationDuration);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ParticleSpiralA", this.selection)).SetItems((List)items));
   }

   public void SendTitle(IText titleText, IText subtitleText, INumber titleDuration, INumber fadeInLength, INumber fadeOutLength) {
      List items = new ArrayList();
      items.add(titleText);
      if (subtitleText != null) {
         items.add(subtitleText);
      }

      if (titleDuration != null) {
         items.add(titleDuration);
      }

      if (fadeInLength != null) {
         items.add(fadeInLength);
      }

      if (fadeOutLength != null) {
         items.add(fadeOutLength);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SendTitle", this.selection)).SetItems((List)items));
   }

   public void InstantRespawn(Tags.InstantRespawn instantRespawnTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("InstantRespawn", this.selection)).SetItems((List)items).SetTags(new Tag("Instant Respawn", instantRespawnTag.getJSONValue())));
   }

   public void StopSound(ISound[] soundsToStop, Tags.SoundSource soundSourceTag) {
      List items = new ArrayList();
      if (soundsToStop != null) {
         items.addAll(Arrays.asList(soundsToStop));
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("StopSound", this.selection)).SetItems((List)items).SetTags(new Tag("Sound Source", soundSourceTag.getJSONValue())));
   }

   public void SetNameColor(IText nameColor) {
      List items = new ArrayList();
      items.add(nameColor);
      CodeManager.instance.addCodeBlock((new PlayerActionBlock("SetNameColor", this.selection)).SetItems((List)items));
   }

   public void ParticleLine(IParticle effect, ILocation startLocation, ILocation endLocation, INumber effectSpacing) {
      List items = new ArrayList();
      items.add(effect);
      items.add(startLocation);
      items.add(endLocation);
      if (effectSpacing != null) {
         items.add(effectSpacing);
      }

      CodeManager.instance.addCodeBlock((new PlayerActionBlock("ParticleLine", this.selection)).SetItems((List)items));
   }
}

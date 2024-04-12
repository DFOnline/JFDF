package net.jfdf.jfdf.mangement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.jfdf.jfdf.blocks.BracketBlock;
import net.jfdf.jfdf.blocks.ElseBlock;
import net.jfdf.jfdf.blocks.EntityActionBlock;
import net.jfdf.jfdf.blocks.IfEntityBlock;
import net.jfdf.jfdf.blocks.IfGameBlock;
import net.jfdf.jfdf.blocks.IfPlayerBlock;
import net.jfdf.jfdf.blocks.IfVariableBlock;
import net.jfdf.jfdf.blocks.PlayerActionBlock;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.IEntityType;
import net.jfdf.jfdf.values.IItem;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IPotion;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Item;
import net.jfdf.jfdf.values.Location;
import net.jfdf.jfdf.values.Tag;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Text;

public class If {
   public static void Else() {
      CodeManager.instance.addCodeBlock(new BracketBlock(true, false));
      CodeManager.instance.addCodeBlock(new ElseBlock());
      CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
   }

   public static void End() {
      CodeManager.instance.addCodeBlock(new BracketBlock(true, false));
   }

   public static class Game {
      private static List toList(CodeValue[] array) {
         List list = Arrays.asList(array);
         list.remove((Object)null);
         return list;
      }

      public static void BlockEquals(ILocation checkLocation, Item[] blocksToCheckFor, Text[] blockData, boolean inverseIf) {
         List items = new ArrayList();
         items.add(checkLocation);
         if (blocksToCheckFor != null) {
            items.addAll(toList(blocksToCheckFor));
         }

         if (blockData != null) {
            items.addAll(toList(blockData));
         }

         CodeManager.instance.addCodeBlock((new IfGameBlock(IfGameBlock.Type.BLOCK_EQUALS, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void BlockIsPowered(ILocation[] checkLocations, Tags.RedstonePowerMode redstonePowerMode, boolean inverseIf) {
         List items = new ArrayList(toList(checkLocations));
         CodeManager.instance.addCodeBlock((new IfGameBlock(IfGameBlock.Type.BLOCK_POWERED, inverseIf)).SetItems((List)items).SetTags(new Tag("Redstone Power Mode", redstonePowerMode.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void ContainerHasItem(ILocation containerLocation, IItem[] itemsToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         items.add(containerLocation);
         items.addAll(toList(itemsToCheckFor));
         CodeManager.instance.addCodeBlock((new IfGameBlock(IfGameBlock.Type.CONTAINER_HAS_ITEM, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void ContainerHasRoomForItem(ILocation containerLocation, IItem[] itemsToCheck, boolean inverseIf) {
         List items = new ArrayList();
         items.add(containerLocation);
         if (itemsToCheck != null) {
            items.addAll(toList(itemsToCheck));
         }

         CodeManager.instance.addCodeBlock((new IfGameBlock(IfGameBlock.Type.CONTAINER_HAS_ALL_ITEMS, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void SignContainsText(ILocation signLocation, IText[] textToCheckFor, Tags.SignLine signLine, Tags.CheckMode checkMode, boolean inverseIf) {
         List items = new ArrayList();
         items.add(signLocation);
         items.addAll(toList(textToCheckFor));
         CodeManager.instance.addCodeBlock((new IfGameBlock(IfGameBlock.Type.SIGN_CONTAINS_TEXT, inverseIf)).SetItems((List)items).SetTags(new Tag("Sign Line", signLine.getJSONValue()), new Tag("Check Mode", checkMode.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void GameHasPlayer(IText nameOrUUID, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfGameBlock(IfGameBlock.Type.HAS_PLAYER, inverseIf)).SetItems(nameOrUUID));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void EventBlockEquals(IItem[] blocksToCheckFor, boolean inverseIf) {
         List items = new ArrayList(toList(blocksToCheckFor));
         CodeManager.instance.addCodeBlock((new IfGameBlock(IfGameBlock.Type.EVENT_BLOCK_EQUALS, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void EventItemEquals(IItem[] itemsToCheckFor, Tags.ComparisonMode eventItemComparisonMode, boolean inverseIf) {
         List items = new ArrayList(toList(itemsToCheckFor));
         CodeManager.instance.addCodeBlock((new IfGameBlock(IfGameBlock.Type.EVENT_ITEM_EQUALS, inverseIf)).SetItems((List)items).SetTags(new Tag("Comparison Mode", eventItemComparisonMode.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void CommandEquals(IText[] textsToCheckFor, Tags.CheckMode checkMode, Tags.IgnoreCase ignoreCase, boolean inverseIf) {
         List items = new ArrayList(toList(textsToCheckFor));
         CodeManager.instance.addCodeBlock((new IfGameBlock(IfGameBlock.Type.COMMAND_EQUALS, inverseIf)).SetItems((List)items).SetTags(new Tag("Check Mode", checkMode.getJSONValue()), new Tag("Ignore Case", ignoreCase.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void CommandArgumentEquals(IText[] textsToCheckFor, INumber argumentNumber, Tags.IgnoreCase ignoreCase, boolean inverseIf) {
         List items = new ArrayList(toList(textsToCheckFor));
         items.add(argumentNumber);
         CodeManager.instance.addCodeBlock((new IfGameBlock(IfGameBlock.Type.COMMAND_ARGUMENT_EQUALS, inverseIf)).SetItems((List)items).SetTags(new Tag("Ignore Case", ignoreCase.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void EventIsCancelled(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfGameBlock(IfGameBlock.Type.IS_EVENT_CANCELLED, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }
   }

   public static class Variable {
      private static List toList(CodeValue[] array) {
         List list = Arrays.asList(array);
         list.remove((Object)null);
         return list;
      }

      public static void Equals(CodeValue variableToCheck, CodeValue[] variablesToCompareTo, boolean inverseIf) {
         List items = new ArrayList();
         items.add(variableToCheck);
         items.addAll(toList(variablesToCompareTo));
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.EQUALS, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void NotEquals(CodeValue variableToCheck, CodeValue[] variablesToCompareTo, boolean inverseIf) {
         List items = new ArrayList();
         items.add(variableToCheck);
         items.addAll(toList(variablesToCompareTo));
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.NOT_EQUALS, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void GreaterThan(INumber numberToCheck, INumber numberToCompareTo, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.GREATER_THAN, inverseIf)).SetItems(numberToCheck, numberToCompareTo));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void GreaterThanOrEqualTo(INumber numberToCheck, INumber numberToCompareTo, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.GREATER_THAN_OR_EQUAL_TO, inverseIf)).SetItems(numberToCheck, numberToCompareTo));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void LessThan(INumber numberToCheck, INumber numberToCompareTo, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.LESS_THAN, inverseIf)).SetItems(numberToCheck, numberToCompareTo));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void LessThanOrEqualTo(INumber numberToCheck, INumber numberToCompareTo, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.LESS_THAN_OR_EQUAL_TO, inverseIf)).SetItems(numberToCheck, numberToCompareTo));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void IsWithinRange(INumber checkValue, INumber minimumValue, INumber maximumValue, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.IN_RANGE, inverseIf)).SetItems(checkValue, minimumValue, maximumValue));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void IsWithinRange(ILocation checkValue, Location minimumValue, Location maximumValue, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.IN_RANGE, inverseIf)).SetItems(checkValue, minimumValue, maximumValue));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void IsNear(INumber checkValue, INumber centerValue, INumber rangeValue, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.IS_NEAR, inverseIf)).SetItems(checkValue, centerValue, rangeValue));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void LocIsNear(ILocation locationToCheck, ILocation[] locationsToCompareTo, INumber rangeValue, Tags.Shape shape, boolean inverseIf) {
         List items = new ArrayList();
         items.add(locationToCheck);
         items.addAll(Arrays.asList(locationsToCompareTo));
         items.add(rangeValue);
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.LOC_IS_NEAR, inverseIf)).SetItems((List)items).SetTags(new Tag("Shape", shape.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void TextMatches(IText textOrExpressionToMatch, IText[] textToCompare, Tags.RegularExpressions regularExpressions, Tags.IgnoreCase ignoreCase, boolean inverseIf) {
         List items = new ArrayList();
         items.add(textOrExpressionToMatch);
         items.addAll(toList(textToCompare));
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.TEXT_MATCHES, inverseIf)).SetItems((List)items).SetTags(new Tag("Regular Expressions", regularExpressions.getJSONValue()), new Tag("Ignore Case", ignoreCase.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void TextContains(IText textToCheck, IText[] textToCheckFor, Tags.IgnoreCase ignoreCase, boolean inverseIf) {
         List items = new ArrayList();
         items.add(textToCheck);
         items.addAll(toList(textToCheckFor));
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.TEXT_CONTAINS, inverseIf)).SetItems((List)items).SetTags(new Tag("Ignore Case", ignoreCase.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void TextStartsWith(IText textToCheck, IText[] textToCheckFor, Tags.IgnoreCase ignoreCase, boolean inverseIf) {
         List items = new ArrayList();
         items.add(textToCheck);
         items.addAll(toList(textToCheckFor));
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.TEXT_STARTS_WITH, inverseIf)).SetItems((List)items).SetTags(new Tag("Ignore Case", ignoreCase.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void TextEndsWith(IText textToCheck, IText[] textToCheckFor, Tags.IgnoreCase ignoreCase, boolean inverseIf) {
         List items = new ArrayList();
         items.add(textToCheck);
         items.addAll(toList(textToCheckFor));
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.TEXT_ENDS_WITH, inverseIf)).SetItems((List)items).SetTags(new Tag("Ignore Case", ignoreCase.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void Exists(net.jfdf.jfdf.values.Variable variableToCheck, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.EXISTS, inverseIf)).SetItems(variableToCheck));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void IsType(CodeValue variableToCheck, Tags.VariableType variableType, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.VARIABLE_IS_TYPE, inverseIf)).SetItems(variableToCheck).SetTags(new Tag("Variable Type", variableType.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void ItemEquals(IItem itemToCheck, IItem[] itemsToCompareTo, Tags.ComparisonMode comparisonMode, boolean inverseIf) {
         List items = new ArrayList();
         items.add(itemToCheck);
         items.addAll(toList(itemsToCompareTo));
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.ITEM_EQUALS, inverseIf)).SetItems((List)items).SetTags(new Tag("Comparison Mode", comparisonMode.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void ItemHasCustomTag(IItem itemToCheck, IText tagName, IText tagValue, boolean inverseIf) {
         List items = new ArrayList();
         items.add(itemToCheck);
         items.add(tagName);
         if (tagValue != null) {
            items.add(tagValue);
         }

         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.ITEM_HAS_TAG, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void ItemHasCustomTag(IItem itemToCheck, IText tagName, INumber tagValue, boolean inverseIf) {
         List items = new ArrayList();
         items.add(itemToCheck);
         items.add(tagName);
         if (tagValue != null) {
            items.add(tagValue);
         }

         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.ITEM_HAS_TAG, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void ListContainsValue(net.jfdf.jfdf.values.Variable listToCheckIn, CodeValue[] variableToFind, boolean inverseIf) {
         List items = new ArrayList();
         items.add(listToCheckIn);
         items.addAll(toList(variableToFind));
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.LIST_CONTAINS_VALUE, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void ListValueEquals(net.jfdf.jfdf.values.Variable listToCheckIn, INumber indexToCheckAt, CodeValue[] variableToCompareTo, boolean inverseIf) {
         List items = new ArrayList();
         items.add(listToCheckIn);
         items.add(indexToCheckAt);
         items.addAll(toList(variableToCompareTo));
         CodeManager.instance.addCodeBlock((new IfVariableBlock(IfVariableBlock.Type.LIST_VALUE_EQUALS, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void DictHasKey(net.jfdf.jfdf.values.Variable dictionary, IText key, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfVariableBlock("DictHasKey", inverseIf)).SetItems(dictionary, key));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public static void DictValueEquals(net.jfdf.jfdf.values.Variable dictionary, IText key, CodeValue[] values, boolean inverseIf) {
         List items = new ArrayList();
         items.add(dictionary);
         items.add(key);
         items.addAll(toList(values));
         CodeManager.instance.addCodeBlock((new IfVariableBlock("DictValueEquals", inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }
   }

   public static class Entity {
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

      private List toList(CodeValue[] array) {
         List list = Arrays.asList(array);
         list.remove((Object)null);
         return list;
      }

      public void IsType(IEntityType entityType, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfEntityBlock(IfEntityBlock.Type.IS_TYPE, this.selection, inverseIf)).SetItems(entityType));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void NameEquals(IText[] nameToCheckFor, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfEntityBlock(IfEntityBlock.Type.NAME_EQUALS, this.selection, inverseIf)).SetItems((CodeValue[])nameToCheckFor));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsGrounded(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfEntityBlock(IfEntityBlock.Type.IS_GROUNDED, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void StandingOn(IItem[] blockToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         if (blockToCheckFor != null) {
            items.addAll(this.toList(blockToCheckFor));
         }

         CodeManager.instance.addCodeBlock((new IfEntityBlock(IfEntityBlock.Type.IS_STANDING_ON, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsNear(ILocation[] centerLocation, INumber range, Tags.IgnoreY_Axis ignoreYAxis, boolean inverseIf) {
         List items = new ArrayList(this.toList(centerLocation));
         if (range != null) {
            items.add(range);
         }

         CodeManager.instance.addCodeBlock((new IfEntityBlock(IfEntityBlock.Type.IS_NEAR, this.selection, inverseIf)).SetItems((List)items).SetTags(new Tag("Ignore Y-Axis", ignoreYAxis.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsRiding(IText[] entityType, Tags.CompareTextTo compareTextTo, boolean inverseIf) {
         List items = new ArrayList();
         if (entityType != null) {
            items.addAll(this.toList(entityType));
         }

         CodeManager.instance.addCodeBlock((new IfEntityBlock(IfEntityBlock.Type.IS_RIDING, this.selection, inverseIf)).SetItems((List)items).SetTags(new Tag("Compare Text To", compareTextTo.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsMob(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfEntityBlock(IfEntityBlock.Type.IS_MOB, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsProjectile(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfEntityBlock(IfEntityBlock.Type.IS_PROJECTILE, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsVechile(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfEntityBlock(IfEntityBlock.Type.IS_VECHILE, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsItem(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfEntityBlock(IfEntityBlock.Type.IS_ITEM, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void Exists(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfEntityBlock(IfEntityBlock.Type.EXISTS, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void HasCustomTag(IText tagName, IText tagValue, boolean inverseIf) {
         List items = new ArrayList();
         items.add(tagName);
         if (tagValue != null) {
            items.add(tagValue);
         }

         CodeManager.instance.addCodeBlock((new IfEntityBlock(IfEntityBlock.Type.HAS_CUSTOM_TAG, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void HasCustomTag(IText tagName, INumber tagValue, boolean inverseIf) {
         List items = new ArrayList();
         items.add(tagName);
         if (tagValue != null) {
            items.add(tagValue);
         }

         CodeManager.instance.addCodeBlock((new IfEntityBlock(IfEntityBlock.Type.HAS_CUSTOM_TAG, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }
   }

   public static class Player {
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
         List list = new ArrayList(Arrays.asList(array));
         list.remove((Object)null);
         return list;
      }

      public void IsSneaking(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfPlayerBlock(IfPlayerBlock.Type.IS_SNEAKING, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsSprinting(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfPlayerBlock(IfPlayerBlock.Type.IS_SPRINTING, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsGliding(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfPlayerBlock(IfPlayerBlock.Type.IS_GLIDING, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsFlying(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfPlayerBlock(IfPlayerBlock.Type.IS_FLYING, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsGrounded(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfPlayerBlock(IfPlayerBlock.Type.IS_GROUNDED, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsSwimming(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfPlayerBlock(IfPlayerBlock.Type.IS_SWIMMING, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsBlocking(boolean inverseIf) {
         CodeManager.instance.addCodeBlock(new IfPlayerBlock(IfPlayerBlock.Type.IS_BLOCKING, this.selection, inverseIf));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsLookingAt(IItem[] blocksToCheckFor, Tags.FluidMode fluidMode, boolean inverseIf) {
         List items = new ArrayList();
         if (blocksToCheckFor != null) {
            items.addAll(this.toList(blocksToCheckFor));
         }

         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.IS_LOOKING_AT, this.selection, inverseIf)).SetItems((List)items).SetTags(new Tag("Fluid Mode", fluidMode.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void StandingOn(IItem[] blockToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         if (blockToCheckFor != null) {
            items.addAll(this.toList(blockToCheckFor));
         }

         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.STANDING_ON, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsNear(ILocation[] centerLocation, INumber range, Tags.IgnoreY_Axis ignoreYAxis, boolean inverseIf) {
         List items = new ArrayList(this.toList(centerLocation));
         if (range != null) {
            items.add(range);
         }

         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.IS_NEAR, this.selection, inverseIf)).SetItems((List)items).SetTags(new Tag("Ignore Y-Axis", ignoreYAxis.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void InWorldBorder(ILocation locationToCheck, boolean inverseIf) {
         List items = new ArrayList();
         if (locationToCheck != null) {
            items.add(locationToCheck);
         }

         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.IN_WORLD_BORDER, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsHolding(IItem[] itemsToCheckFor, Tags.HandSlot handSlot, boolean inverseIf) {
         List items = new ArrayList();
         if (itemsToCheckFor != null) {
            items.addAll(this.toList(itemsToCheckFor));
         }

         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.IS_HOLDING, this.selection, inverseIf)).SetItems((List)items).SetTags(new Tag("Hand Slot", handSlot.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void HasItem(IItem[] itemsToCheckFor, Tags.CheckMode checkMode, boolean inverseIf) {
         List items = new ArrayList(this.toList(itemsToCheckFor));
         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.HAS_ITEM, this.selection, inverseIf)).SetItems((List)items).SetTags(new Tag("Check Mode", checkMode.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsWearing(IItem[] itemsToCheckFor, boolean inverseIf) {
         List items = new ArrayList(this.toList(itemsToCheckFor));
         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.IS_WEARING, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsUsingItem(IItem[] itemsToCheck, boolean inverseIf) {
         List items = new ArrayList();
         if (itemsToCheck != null) {
            items.addAll(this.toList(itemsToCheck));
         }

         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.IS_USING_ITEM, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void NoItemCooldown(IItem[] itemTypesToCheck, boolean inverseIf) {
         List items = new ArrayList(this.toList(itemTypesToCheck));
         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.NO_ITEM_COOLDOWN, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void HasSlotItem(INumber[] slotsToCheck, IItem[] itemsToCheckFor, boolean inverseIf) {
         List items = new ArrayList(this.toList(slotsToCheck));
         if (itemsToCheckFor != null) {
            items.addAll(this.toList(itemsToCheckFor));
         }

         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.HAS_SLOT_ITEM, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void MenuSlotEquals(INumber[] slotIDsToCheck, IItem[] itemsToCheckFor, boolean inverseIf) {
         List items = new ArrayList(this.toList(slotIDsToCheck));
         if (itemsToCheckFor != null) {
            items.addAll(this.toList(itemsToCheckFor));
         }

         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.MENU_SLOT_EQUALS, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void CursorItem(IItem[] itemssToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         if (itemssToCheckFor != null) {
            items.addAll(this.toList(itemssToCheckFor));
         }

         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.CURSOR_ITEM, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void HasRoomForItem(IItem itemsToCheck, Tags.CheckedSlots checkedSlots, boolean inverseIf) {
         List items = new ArrayList();
         if (itemsToCheck != null) {
            items.add(itemsToCheck);
         }

         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.HAS_ROOM_FOR_ITEM, this.selection, inverseIf)).SetItems((List)items).SetTags(new Tag("Checked Slots", checkedSlots.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void NameEquals(IText[] namesToCheckFor, boolean inverseIf) {
         List items = new ArrayList(this.toList(namesToCheckFor));
         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.NAME_EQUALS, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void SlotEquals(INumber slotIDToCheck, boolean inverseIf) {
         List items = new ArrayList();
         items.add(slotIDToCheck);
         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.SLOT_EQUALS, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void HasEffect(IPotion[] potionsToCheckFor, boolean inverseIf) {
         List items = new ArrayList(this.toList(potionsToCheckFor));
         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.HAS_EFFECT, this.selection, inverseIf)).SetItems((List)items));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void IsRiding(IText[] entityType, Tags.CompareTextTo compareTextTo, boolean inverseIf) {
         List items = new ArrayList();
         if (entityType != null) {
            items.addAll(this.toList(entityType));
         }

         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.IS_RIDING, this.selection, inverseIf)).SetItems((List)items).SetTags(new Tag("Compare Text To", compareTextTo.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void InvOpen(Tags.InventoryType inventoryType, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.INV_OPEN, this.selection, inverseIf)).SetTags(new Tag("Inventory Type", inventoryType.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }

      public void HasPermission(Tags.Permission permission, boolean inverseIf) {
         CodeManager.instance.addCodeBlock((new IfPlayerBlock(IfPlayerBlock.Type.HAS_PERMISSION, this.selection, inverseIf)).SetTags(new Tag("Permission", permission.getJSONValue())));
         CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
      }
   }
}

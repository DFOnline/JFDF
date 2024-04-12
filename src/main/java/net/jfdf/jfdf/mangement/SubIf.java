package net.jfdf.jfdf.mangement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.IEntityType;
import net.jfdf.jfdf.values.IItem;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Item;
import net.jfdf.jfdf.values.Location;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Potion;
import net.jfdf.jfdf.values.Tag;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Text;

public class SubIf {
   private final SubIfType subIfType;
   private boolean inverseIf = false;
   private List items;
   private List tags;

   public SubIf(SubIfType subIfType) {
      this.subIfType = subIfType;
      this.items = new ArrayList();
      this.tags = new ArrayList();
   }

   public SubIf(SubIfType subIfType, boolean inverseIf) {
      this.subIfType = subIfType;
      this.inverseIf = inverseIf;
      this.items = new ArrayList();
      this.tags = new ArrayList();
   }

   public SubIf SetItems(List items) {
      this.items = items;
      return this;
   }

   public SubIf SetItems(CodeValue... items) {
      this.items = Arrays.asList(items);
      return this;
   }

   public SubIf SetTags(Tag... tags) {
      this.tags = Arrays.asList(tags);
      return this;
   }

   public SubIf InverseIf() {
      this.inverseIf = !this.inverseIf;
      return this;
   }

   public SubIf SetInverted(boolean inverseIf) {
      this.inverseIf = inverseIf;
      return this;
   }

   public SubIfType GetType() {
      return this.subIfType;
   }

   public List GetTags() {
      return this.tags;
   }

   public List GetItems() {
      return this.items;
   }

   public boolean IsInversed() {
      return this.inverseIf;
   }

   public static enum SubIfType {
      ENTITY_IS_TYPE("if_entity", "IsType"),
      ENTITY_NAME_EQUALS("if_entity", "ENameEquals", "NameEquals"),
      ENTITY_IS_STANDING_ON("if_entity", "EStandingOn", "StandingOn"),
      ENTITY_IS_GROUNDED("if_entity", "IsGrounded"),
      ENTITY_IS_NEAR("if_entity", "EIsNear", "IsNear"),
      ENTITY_IS_RIDING("if_entity", "IsRiding"),
      ENTITY_IS_MOB("if_entity", "IsMob"),
      ENTITY_IS_PROJECTILE("if_entity", "IsProj"),
      ENTITY_IS_VECHILE("if_entity", "IsVechile"),
      ENTITY_IS_ITEM("if_entity", "IsItem"),
      ENTITY_EXISTS("if_entity", "Exists"),
      ENTITY_HAS_CUSTOM_TAG("if_entity", "HasCustomTag"),
      GAME_BLOCK_EQUALS("if_game", "GBlockEquals", "BlockEquals"),
      GAME_BLOCK_POWERED("if_game", "BlockPowered"),
      GAME_CONTAINER_HAS_ITEM("if_game", "ContainterHas"),
      GAME_CONTAINER_HAS_ALL_ITEMS("if_game", "ContainterHasAll"),
      GAME_CONTAINER_HAS_ROOM_FOR_ITEM("if_game", "HasRoomForItem"),
      GAME_SIGN_CONTAINS_TEXT("if_game", "SignHasTxt"),
      GAME_HAS_PLAYER("if_game", "HasPlayer"),
      GAME_EVENT_BLOCK_EQUALS("if_game", "EventBlockEquals"),
      GAME_EVENT_ITEM_EQUALS("if_game", "EventItemEquals"),
      GAME_COMMAND_EQUALS("if_game", "CommandEquals"),
      GAME_COMMAND_ARGUMENT_EQUALS("if_game", "CmdArgEquals"),
      GAME_IS_EVENT_CANCELLED("if_game", "EventCancelled"),
      PLAYER_IS_SNEAKING("if_player", "IsSneaking"),
      PLAYER_IS_SPRINTING("if_player", "IsSprinting"),
      PLAYER_IS_GLIDING("if_player", "IsGliding"),
      PLAYER_IS_FLYING("if_player", "IsFlying"),
      PLAYER_IS_GROUNDED("if_player", "IsGrounded"),
      PLAYER_IS_SWIMMING("if_player", "IsSwimming"),
      PLAYER_IS_BLOCKING("if_player", "IsBlocking"),
      PLAYER_IS_LOOKING_AT("if_player", "IsLookingAt"),
      PLAYER_STANDING_ON("if_player", "PStandingOn", "StandingOn"),
      PLAYER_IS_NEAR("if_player", "PIsNear", "IsNear"),
      PLAYER_IS_IN_WORLD_BORDER("if_player", "InWorldBorder"),
      PLAYER_IS_HOLDING("if_player", "IsHolding"),
      PLAYER_HAS_ITEM("if_player", "HasItem"),
      PLAYER_IS_WEARING("if_player", "PIsWearing", "IsWearing"),
      PLAYER_IS_USING_ITEM("if_player", "IsUsingItem"),
      PLAYER_IS_ITEM_HAS_NO_COOLDOWN("if_player", "NoItemCooldown"),
      PLAYER_HAS_ITEM_IN_SLOT("if_player", "HasSlotItem"),
      PLAYER_INVENTORY_MENU_SLOT_EQUALS("if_player", "MenuSlotEquals"),
      PLAYER_CURSOR_ITEM_EQUALS("if_player", "CursorItem"),
      PLAYER_HAS_ROOM_FOR_ITEM("if_player", "HasRoomForItem"),
      PLAYER_NAME_EQUALS("if_player", "PNameEquals", "NameEquals"),
      PLAYER_HOTBAR_SLOT_EQUALS("if_player", "SlotEquals"),
      PLAYER_HAS_POTION_EFFECT("if_player", "HasEffect"),
      PLAYER_IS_RIDING_ENTITY("if_player", "IsRiding"),
      PLAYER_INVENTORY_TYPE_OPEN("if_player", "InvOpen"),
      PLAYER_HAS_PREMISSION("if_player", "HasPremission"),
      VARIABLE_EQUALS("if_var", "="),
      VARIABLE_NOT_EQUALS("if_var", "!="),
      VARIABLE_GREATER_THAN("if_var", ">"),
      VARIABLE_GREATER_THAN_OR_EQUAL_TO("if_var", ">="),
      VARIABLE_LESS_THAN("if_var", "<"),
      VARIABLE_LESS_THAN_OR_EQUAL_TO("if_var", "<="),
      VARIABLE_IN_RANGE("if_var", "InRange"),
      VARIABLE_IS_NEAR("if_var", "VIsNear", "IsNear"),
      VARIABLE_TEXT_MATCHES("if_var", "TextMatches"),
      VARIABLE_TEXT_CONTAINS("if_var", "Contains"),
      VARIABLE_TEXT_STARTS_WITH("if_var", "StartsWith"),
      VARIABLE_TEXT_ENDS_WITH("if_var", "EndsWith"),
      VARIABLE_EXISTS("if_var", "VarExists"),
      VARIABLE_VARIABLE_IS_TYPE("if_var", "VarIsType"),
      VARIABLE_ITEM_EQUALS("if_var", "VItemEquals", "ItemEquals"),
      VARIABLE_ITEM_HAS_TAG("if_var", "ItemHasTag"),
      VARIABLE_LIST_CONTAINS_VALUE("if_var", "ListContains"),
      VARIABLE_LIST_VALUE_EQUALS("if_var", "ListValueEq");

      private static final Map values = new HashMap();
      private int value;
      private final String ifBlock;
      private final String normalJsonValue;
      private final String subJsonValue;

      private SubIfType(String ifBlock, String jsonValue) {
         this.ifBlock = ifBlock;
         this.subJsonValue = jsonValue;
         this.normalJsonValue = jsonValue;
      }

      private SubIfType(String ifBlock, String subJSONValue, String normalJSONValue) {
         this.ifBlock = ifBlock;
         this.subJsonValue = subJSONValue;
         this.normalJsonValue = normalJSONValue;
      }

      public static SubIfType valueOf(int subIf) {
         return (SubIfType)values.get(subIf);
      }

      public int getValue() {
         return this.value;
      }

      public String getIfBlock() {
         return this.ifBlock;
      }

      public String getNormalJSONValue() {
         return this.normalJsonValue;
      }

      public String getSubJSONValue() {
         return this.subJsonValue;
      }

      static {
         SubIfType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            SubIfType subIf = var0[var2];
            subIf.value = values.size();
            values.put(subIf.getValue(), subIf);
         }

      }
   }

   public static class Game {
      private static List toList(CodeValue[] array) {
         List list = Arrays.asList(array);
         list.remove((Object)null);
         return list;
      }

      public static SubIf BlockEquals(ILocation checkLocation, Item[] blocksToCheckFor, Text[] blockData, boolean inverseIf) {
         List items = new ArrayList();
         items.add(checkLocation);
         if (blocksToCheckFor != null) {
            items.addAll(toList(blocksToCheckFor));
         }

         if (blockData != null) {
            items.addAll(toList(blockData));
         }

         return (new SubIf(SubIf.SubIfType.GAME_BLOCK_EQUALS)).SetItems((List)items);
      }

      public static SubIf BlockIsPowered(ILocation[] checkLocations, Tags.RedstonePowerMode redstonePowerMode, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(checkLocations));
         return (new SubIf(SubIf.SubIfType.GAME_BLOCK_POWERED)).SetItems((List)items).SetTags(new Tag("Redstone Power Mode", redstonePowerMode.getJSONValue()));
      }

      public static SubIf ContainerHasItem(ILocation containerLocation, IItem[] itemsToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         items.add(containerLocation);
         items.addAll(toList(itemsToCheckFor));
         return (new SubIf(SubIf.SubIfType.GAME_CONTAINER_HAS_ITEM)).SetItems((List)items);
      }

      public static SubIf ContainerHasRoomForItem(ILocation containerLocation, IItem[] itemsToCheck, boolean inverseIf) {
         List items = new ArrayList();
         items.add(containerLocation);
         if (itemsToCheck != null) {
            items.addAll(toList(itemsToCheck));
         }

         return (new SubIf(SubIf.SubIfType.GAME_CONTAINER_HAS_ALL_ITEMS)).SetItems((List)items);
      }

      public static SubIf SignContainsText(ILocation signLocation, IText[] textToCheckFor, Tags.SignLine signLine, Tags.CheckMode checkMode, boolean inverseIf) {
         List items = new ArrayList();
         items.add(signLocation);
         items.addAll(toList(textToCheckFor));
         return (new SubIf(SubIf.SubIfType.GAME_SIGN_CONTAINS_TEXT)).SetItems((List)items).SetTags(new Tag("Sign Line", signLine.getJSONValue()), new Tag("Check Mode", checkMode.getJSONValue()));
      }

      public static SubIf GameHasPlayer(IText nameOrUUID, boolean inverseIf) {
         return (new SubIf(SubIf.SubIfType.GAME_HAS_PLAYER)).SetItems(nameOrUUID);
      }

      public static SubIf EventBlockEquals(IItem[] blocksToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(blocksToCheckFor));
         return (new SubIf(SubIf.SubIfType.GAME_EVENT_BLOCK_EQUALS)).SetItems((List)items);
      }

      public static SubIf EventItemEquals(IItem[] itemsToCheckFor, Tags.ComparisonMode eventItemComparisonMode, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(itemsToCheckFor));
         return (new SubIf(SubIf.SubIfType.GAME_EVENT_ITEM_EQUALS)).SetItems((List)items).SetTags(new Tag("Comparison Mode", eventItemComparisonMode.getJSONValue()));
      }

      public static SubIf CommandEquals(IText[] textsToCheckFor, Tags.CheckMode checkMode, Tags.IgnoreCase ignoreCase, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(textsToCheckFor));
         return (new SubIf(SubIf.SubIfType.GAME_COMMAND_EQUALS)).SetItems((List)items).SetTags(new Tag("Check Mode", checkMode.getJSONValue()), new Tag("Ignore Case", ignoreCase.getJSONValue()));
      }

      public static SubIf CommandArgumentEquals(IText[] textsToCheckFor, INumber argumentNumber, Tags.IgnoreCase ignoreCase, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(textsToCheckFor));
         items.add(argumentNumber);
         return (new SubIf(SubIf.SubIfType.GAME_COMMAND_ARGUMENT_EQUALS)).SetItems((List)items).SetTags(new Tag("Ignore Case", ignoreCase.getJSONValue()));
      }

      public static SubIf EventIsCancelled(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.GAME_IS_EVENT_CANCELLED);
      }
   }

   public static class Variable {
      private static List toList(CodeValue[] array) {
         List list = Arrays.asList(array);
         list.remove((Object)null);
         return list;
      }

      public static SubIf Equals(CodeValue variableToCheck, CodeValue[] variablesToCompareTo) {
         List items = new ArrayList();
         items.add(variableToCheck);
         items.addAll(toList(variablesToCompareTo));
         return (new SubIf(SubIf.SubIfType.VARIABLE_EQUALS)).SetItems((List)items);
      }

      public static SubIf NotEquals(CodeValue variableToCheck, CodeValue[] variablesToCompareTo) {
         List items = new ArrayList();
         items.add(variableToCheck);
         items.addAll(toList(variablesToCompareTo));
         return (new SubIf(SubIf.SubIfType.VARIABLE_NOT_EQUALS)).SetItems((List)items);
      }

      public static SubIf GreaterThan(INumber numberToCheck, INumber numberToCompareTo) {
         return (new SubIf(SubIf.SubIfType.VARIABLE_GREATER_THAN)).SetItems(numberToCheck, numberToCompareTo);
      }

      public static SubIf GreaterThanOrEqualTo(INumber numberToCheck, INumber numberToCompareTo) {
         return (new SubIf(SubIf.SubIfType.VARIABLE_GREATER_THAN_OR_EQUAL_TO)).SetItems(numberToCheck, numberToCompareTo);
      }

      public static SubIf LessThan(INumber numberToCheck, INumber numberToCompareTo) {
         return (new SubIf(SubIf.SubIfType.VARIABLE_LESS_THAN)).SetItems(numberToCheck, numberToCompareTo);
      }

      public static SubIf LessThanOrEqualTo(INumber numberToCheck, INumber numberToCompareTo) {
         return (new SubIf(SubIf.SubIfType.VARIABLE_LESS_THAN_OR_EQUAL_TO)).SetItems(numberToCheck, numberToCompareTo);
      }

      public static SubIf IsWithinRange(INumber checkValue, INumber minimumValue, INumber maximumValue, boolean inverseIf) {
         return (new SubIf(SubIf.SubIfType.VARIABLE_IN_RANGE)).SetItems(checkValue, minimumValue, maximumValue);
      }

      public static SubIf IsWithinRange(ILocation checkValue, Location minimumValue, Location maximumValue, boolean inverseIf) {
         return (new SubIf(SubIf.SubIfType.VARIABLE_IN_RANGE)).SetItems(checkValue, minimumValue, maximumValue);
      }

      public static SubIf IsNear(INumber checkValue, INumber centerValue, INumber rangeValue, boolean inverseIf) {
         return (new SubIf(SubIf.SubIfType.VARIABLE_IS_NEAR)).SetItems(checkValue, centerValue, rangeValue);
      }

      public static SubIf IsNear(ILocation checkValue, Location centerValue, INumber rangeValue, boolean inverseIf) {
         return (new SubIf(SubIf.SubIfType.VARIABLE_IS_NEAR)).SetItems(checkValue, centerValue, rangeValue);
      }

      public static SubIf TextMatches(IText textOrExpressionToMatch, IText[] textToCompare, Tags.RegularExpressions regularExpressions, Tags.IgnoreCase ignoreCase, boolean inverseIf) {
         List items = new ArrayList();
         items.add(textOrExpressionToMatch);
         items.addAll(toList(textToCompare));
         return (new SubIf(SubIf.SubIfType.VARIABLE_TEXT_MATCHES)).SetItems((List)items).SetTags(new Tag("Regular Expressions", regularExpressions.getJSONValue()), new Tag("Ignore Case", ignoreCase.getJSONValue()));
      }

      public static SubIf TextContains(IText textToCheck, IText[] textToCheckFor, Tags.IgnoreCase ignoreCase, boolean inverseIf) {
         List items = new ArrayList();
         items.add(textToCheck);
         items.addAll(toList(textToCheckFor));
         return (new SubIf(SubIf.SubIfType.VARIABLE_TEXT_CONTAINS)).SetItems((List)items).SetTags(new Tag("Ignore Case", ignoreCase.getJSONValue()));
      }

      public static SubIf TextStartsWith(IText textToCheck, IText[] textToCheckFor, Tags.IgnoreCase ignoreCase, boolean inverseIf) {
         List items = new ArrayList();
         items.add(textToCheck);
         items.addAll(toList(textToCheckFor));
         return (new SubIf(SubIf.SubIfType.VARIABLE_TEXT_STARTS_WITH)).SetItems((List)items).SetTags(new Tag("Ignore Case", ignoreCase.getJSONValue()));
      }

      public static SubIf TextEndsWith(IText textToCheck, IText[] textToCheckFor, Tags.IgnoreCase ignoreCase, boolean inverseIf) {
         List items = new ArrayList();
         items.add(textToCheck);
         items.addAll(toList(textToCheckFor));
         return (new SubIf(SubIf.SubIfType.VARIABLE_TEXT_ENDS_WITH)).SetItems((List)items).SetTags(new Tag("Ignore Case", ignoreCase.getJSONValue()));
      }

      public static SubIf Exists(net.jfdf.jfdf.values.Variable variableToCheck) {
         return (new SubIf(SubIf.SubIfType.VARIABLE_EXISTS)).SetItems(variableToCheck);
      }

      public static SubIf IsType(CodeValue variableToCheck, Tags.VariableType variableType, boolean inverseIf) {
         return (new SubIf(SubIf.SubIfType.VARIABLE_VARIABLE_IS_TYPE)).SetItems(variableToCheck).SetTags(new Tag("Variable Type", variableType.getJSONValue()));
      }

      public static SubIf ItemEquals(IItem itemToCheck, IItem[] itemsToCompareTo, Tags.ComparisonMode comparisonMode, boolean inverseIf) {
         List items = new ArrayList();
         items.add(itemToCheck);
         items.addAll(toList(itemsToCompareTo));
         return (new SubIf(SubIf.SubIfType.VARIABLE_ITEM_EQUALS)).SetItems((List)items).SetTags(new Tag("Comparison Mode", comparisonMode.getJSONValue()));
      }

      public static SubIf ItemHasCustomTag(IItem itemToCheck, IText tagName, IText tagValue, boolean inverseIf) {
         List items = new ArrayList();
         items.add(itemToCheck);
         items.add(tagName);
         if (tagValue != null) {
            items.add(tagValue);
         }

         return (new SubIf(SubIf.SubIfType.VARIABLE_ITEM_HAS_TAG)).SetItems((List)items);
      }

      public static SubIf ItemHasCustomTag(IItem itemToCheck, IText tagName, INumber tagValue, boolean inverseIf) {
         List items = new ArrayList();
         items.add(itemToCheck);
         items.add(tagName);
         if (tagValue != null) {
            items.add(tagValue);
         }

         return (new SubIf(SubIf.SubIfType.VARIABLE_ITEM_HAS_TAG)).SetItems((List)items);
      }

      public static SubIf ListContainsValue(net.jfdf.jfdf.values.Variable listToCheckIn, CodeValue[] variableToFind) {
         List items = new ArrayList();
         items.add(listToCheckIn);
         items.addAll(toList(variableToFind));
         return (new SubIf(SubIf.SubIfType.VARIABLE_LIST_CONTAINS_VALUE)).SetItems((List)items);
      }

      public static SubIf ListValueEquals(net.jfdf.jfdf.values.Variable listToCheckIn, INumber indexToCheckAt, CodeValue[] variableToCompareTo) {
         List items = new ArrayList();
         items.add(listToCheckIn);
         items.add(indexToCheckAt);
         items.addAll(toList(variableToCompareTo));
         return (new SubIf(SubIf.SubIfType.VARIABLE_LIST_VALUE_EQUALS)).SetItems((List)items);
      }
   }

   public static class Entity {
      private static List toList(CodeValue[] array) {
         List list = Arrays.asList(array);
         list.remove((Object)null);
         return list;
      }

      public static SubIf IsType(IEntityType entityType, boolean inverseIf) {
         return (new SubIf(SubIf.SubIfType.ENTITY_IS_TYPE)).SetItems(entityType);
      }

      public static SubIf NameEquals(Text[] nameToCheckFor, boolean inverseIf) {
         return (new SubIf(SubIf.SubIfType.ENTITY_NAME_EQUALS)).SetItems((CodeValue[])nameToCheckFor);
      }

      public static SubIf IsGrounded(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.ENTITY_IS_GROUNDED);
      }

      public static SubIf StandingOn(Item[] blockToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         if (blockToCheckFor != null) {
            items.addAll(toList(blockToCheckFor));
         }

         return (new SubIf(SubIf.SubIfType.ENTITY_IS_STANDING_ON)).SetItems((List)items);
      }

      public static SubIf IsNear(Location[] centerLocation, Number range, Tags.IgnoreY_Axis ignoreYAxis, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(centerLocation));
         if (range != null) {
            items.add(range);
         }

         return (new SubIf(SubIf.SubIfType.ENTITY_IS_NEAR)).SetItems((List)items).SetTags(new Tag("Ignore Y-Axis", ignoreYAxis.getJSONValue()));
      }

      public static SubIf IsRiding(Text[] entityType, Tags.CompareTextTo compareTextTo, boolean inverseIf) {
         List items = new ArrayList();
         if (entityType != null) {
            items.addAll(toList(entityType));
         }

         return (new SubIf(SubIf.SubIfType.ENTITY_IS_RIDING)).SetItems((List)items).SetTags(new Tag("Compare Text To", compareTextTo.getJSONValue()));
      }

      public static SubIf IsMob(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.ENTITY_IS_MOB);
      }

      public static SubIf IsProjectile(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.ENTITY_IS_PROJECTILE);
      }

      public static SubIf IsVechile(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.ENTITY_IS_VECHILE);
      }

      public static SubIf IsItem(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.ENTITY_IS_ITEM);
      }

      public static SubIf Exists(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.ENTITY_EXISTS);
      }

      public static SubIf HasCustomTag(Text tagName, Text tagValue, boolean inverseIf) {
         List items = new ArrayList();
         items.add(tagName);
         if (tagValue != null) {
            items.add(tagValue);
         }

         return (new SubIf(SubIf.SubIfType.ENTITY_HAS_CUSTOM_TAG)).SetItems((List)items);
      }

      public static SubIf HasCustomTag(Text tagName, Number tagValue, boolean inverseIf) {
         List items = new ArrayList();
         items.add(tagName);
         if (tagValue != null) {
            items.add(tagValue);
         }

         return (new SubIf(SubIf.SubIfType.ENTITY_HAS_CUSTOM_TAG)).SetItems((List)items);
      }
   }

   public static class Player {
      private static List toList(CodeValue[] array) {
         List list = Arrays.asList(array);
         list.remove((Object)null);
         return list;
      }

      public static SubIf IsSneaking(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.PLAYER_IS_SNEAKING);
      }

      public static SubIf IsSprinting(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.PLAYER_IS_SPRINTING);
      }

      public static SubIf IsGliding(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.PLAYER_IS_GLIDING);
      }

      public static SubIf IsFlying(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.PLAYER_IS_FLYING);
      }

      public static SubIf IsGrounded(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.PLAYER_IS_GROUNDED);
      }

      public static SubIf IsSwimming(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.PLAYER_IS_SWIMMING);
      }

      public static SubIf IsBlocking(boolean inverseIf) {
         return new SubIf(SubIf.SubIfType.PLAYER_IS_BLOCKING);
      }

      public static SubIf IsLookingAt(Item[] blocksToCheckFor, Tags.FluidMode fluidMode, boolean inverseIf) {
         List items = new ArrayList();
         if (blocksToCheckFor != null) {
            items.addAll(toList(blocksToCheckFor));
         }

         return (new SubIf(SubIf.SubIfType.PLAYER_IS_LOOKING_AT)).SetItems((List)items).SetTags(new Tag("Fluid Mode", fluidMode.getJSONValue()));
      }

      public static SubIf StandingOn(Item[] blockToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         if (blockToCheckFor != null) {
            items.addAll(toList(blockToCheckFor));
         }

         return (new SubIf(SubIf.SubIfType.PLAYER_STANDING_ON)).SetItems((List)items);
      }

      public static SubIf IsNear(Location[] centerLocation, Number range, Tags.IgnoreY_Axis ignoreYAxis, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(centerLocation));
         if (range != null) {
            items.add(range);
         }

         return (new SubIf(SubIf.SubIfType.PLAYER_IS_NEAR)).SetItems((List)items).SetTags(new Tag("Ignore Y-Axis", ignoreYAxis.getJSONValue()));
      }

      public static SubIf InWorldBorder(Location locationToCheck, boolean inverseIf) {
         List items = new ArrayList();
         if (locationToCheck != null) {
            items.add(locationToCheck);
         }

         return (new SubIf(SubIf.SubIfType.PLAYER_IS_IN_WORLD_BORDER)).SetItems((List)items);
      }

      public static SubIf IsHolding(Item[] itemsToCheckFor, Tags.HandSlot handSlot, boolean inverseIf) {
         List items = new ArrayList();
         if (itemsToCheckFor != null) {
            items.addAll(toList(itemsToCheckFor));
         }

         return (new SubIf(SubIf.SubIfType.PLAYER_IS_HOLDING)).SetItems((List)items).SetTags(new Tag("Hand Slot", handSlot.getJSONValue()));
      }

      public static SubIf HasItem(Item[] itemsToCheckFor, Tags.CheckMode checkMode, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(itemsToCheckFor));
         return (new SubIf(SubIf.SubIfType.PLAYER_HAS_ITEM)).SetItems((List)items).SetTags(new Tag("Check Mode", checkMode.getJSONValue()));
      }

      public static SubIf IsWearing(Item[] itemsToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(itemsToCheckFor));
         return (new SubIf(SubIf.SubIfType.PLAYER_IS_WEARING)).SetItems((List)items);
      }

      public static SubIf IsUsingItem(Item[] itemsToCheck, boolean inverseIf) {
         List items = new ArrayList();
         if (itemsToCheck != null) {
            items.addAll(toList(itemsToCheck));
         }

         return (new SubIf(SubIf.SubIfType.PLAYER_IS_USING_ITEM)).SetItems((List)items);
      }

      public static SubIf NoItemCooldown(Item[] itemTypesToCheck, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(itemTypesToCheck));
         return (new SubIf(SubIf.SubIfType.PLAYER_IS_ITEM_HAS_NO_COOLDOWN)).SetItems((List)items);
      }

      public static SubIf HasSlotItem(Number[] slotsToCheck, Item[] itemsToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(slotsToCheck));
         if (itemsToCheckFor != null) {
            items.addAll(toList(itemsToCheckFor));
         }

         return (new SubIf(SubIf.SubIfType.PLAYER_HAS_ITEM_IN_SLOT)).SetItems((List)items);
      }

      public static SubIf MenuSlotEquals(Number[] slotIDsToCheck, Item[] itemsToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(slotIDsToCheck));
         if (itemsToCheckFor != null) {
            items.addAll(toList(itemsToCheckFor));
         }

         return (new SubIf(SubIf.SubIfType.PLAYER_INVENTORY_MENU_SLOT_EQUALS)).SetItems((List)items);
      }

      public static SubIf CursorItem(Item[] itemssToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         if (itemssToCheckFor != null) {
            items.addAll(toList(itemssToCheckFor));
         }

         return (new SubIf(SubIf.SubIfType.PLAYER_CURSOR_ITEM_EQUALS)).SetItems((List)items);
      }

      public static SubIf HasRoomForItem(Item itemsToCheck, Tags.CheckedSlots checkedSlots, boolean inverseIf) {
         List items = new ArrayList();
         if (itemsToCheck != null) {
            items.add(itemsToCheck);
         }

         return (new SubIf(SubIf.SubIfType.PLAYER_HAS_ROOM_FOR_ITEM)).SetItems((List)items).SetTags(new Tag("Checked Slots", checkedSlots.getJSONValue()));
      }

      public static SubIf NameEquals(Text[] namesToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(namesToCheckFor));
         return (new SubIf(SubIf.SubIfType.PLAYER_NAME_EQUALS)).SetItems((List)items);
      }

      public static SubIf SlotEquals(Number slotIDToCheck, boolean inverseIf) {
         List items = new ArrayList();
         items.add(slotIDToCheck);
         return (new SubIf(SubIf.SubIfType.PLAYER_HOTBAR_SLOT_EQUALS)).SetItems((List)items);
      }

      public static SubIf HasEffect(Potion[] potionsToCheckFor, boolean inverseIf) {
         List items = new ArrayList();
         items.addAll(toList(potionsToCheckFor));
         return (new SubIf(SubIf.SubIfType.PLAYER_HAS_POTION_EFFECT)).SetItems((List)items);
      }

      public static SubIf IsRiding(Text[] entityType, Tags.CompareTextTo compareTextTo, boolean inverseIf) {
         List items = new ArrayList();
         if (entityType != null) {
            items.addAll(toList(entityType));
         }

         return (new SubIf(SubIf.SubIfType.PLAYER_IS_RIDING_ENTITY)).SetItems((List)items).SetTags(new Tag("Compare Text To", compareTextTo.getJSONValue()));
      }

      public static SubIf InvOpen(Tags.InventoryType inventoryType, boolean inverseIf) {
         return (new SubIf(SubIf.SubIfType.PLAYER_INVENTORY_TYPE_OPEN)).SetTags(new Tag("Inventory Type", inventoryType.getJSONValue()));
      }

      public static SubIf HasPermission(Tags.Permission permission, boolean inverseIf) {
         return (new SubIf(SubIf.SubIfType.PLAYER_HAS_PREMISSION)).SetTags(new Tag("Permission", permission.getJSONValue()));
      }
   }
}

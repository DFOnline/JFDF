package net.jfdf.jfdf.mangement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.jfdf.jfdf.blocks.SelectionBlock;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Tag;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Variable;

public class Selector {
   public static void RandomPlayer(INumber selectionSize) {
      List items = new ArrayList();
      if (selectionSize != null) {
         items.add(selectionSize);
      }

      CodeManager.instance.addCodeBlock((new SelectionBlock("RandomPlayer")).SetItems((List)items));
   }

   public static void LastEntity() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new SelectionBlock("LastEntity")).SetItems((List)items));
   }

   public static void EntityName(IText... nameOrUuid) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(nameOrUuid));
      CodeManager.instance.addCodeBlock((new SelectionBlock("EntityName")).SetItems((List)items));
   }

   public static void FilterRandom(INumber selectionSize) {
      List items = new ArrayList();
      if (selectionSize != null) {
         items.add(selectionSize);
      }

      CodeManager.instance.addCodeBlock((new SelectionBlock("FilterRandom")).SetItems((List)items));
   }

   public static void PlayerName(IText... nameOrUuid) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(nameOrUuid));
      CodeManager.instance.addCodeBlock((new SelectionBlock("PlayerName")).SetItems((List)items));
   }

   public static void AllEntities() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new SelectionBlock("AllEntities")).SetItems((List)items));
   }

   public static void FilterDistance(ILocation locationTo, INumber selectionSize, Tags.IgnoreY_Axis ignoreY_AxisTag, Tags.CompareMode compareModeTag) {
      List items = new ArrayList();
      items.add(locationTo);
      if (selectionSize != null) {
         items.add(selectionSize);
      }

      CodeManager.instance.addCodeBlock((new SelectionBlock("FilterDistance")).SetItems((List)items).SetTags(new Tag("Ignore Y-Axis", ignoreY_AxisTag.getJSONValue()), new Tag("Compare Mode", compareModeTag.getJSONValue())));
   }

   public static void FilterRay(Variable getsTheEndOr, ILocation rayOrigin, INumber rayDistance, INumber rayWidth, INumber selectionSize, Tags.BlockCollision blockCollisionTag) {
      List items = new ArrayList();
      if (getsTheEndOr != null) {
         items.add(getsTheEndOr);
      }

      items.add(rayOrigin);
      items.add(rayDistance);
      if (rayWidth != null) {
         items.add(rayWidth);
      }

      if (selectionSize != null) {
         items.add(selectionSize);
      }

      CodeManager.instance.addCodeBlock((new SelectionBlock("FilterRay")).SetItems((List)items).SetTags(new Tag("Block Collision", blockCollisionTag.getJSONValue())));
   }

   public static void Reset() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new SelectionBlock("Reset")).SetItems((List)items));
   }

   public static void EventTarget(Tags.EventTarget eventTargetTag) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new SelectionBlock("EventTarget")).SetItems((List)items).SetTags(new Tag("Event Target", eventTargetTag.getJSONValue())));
   }

   public static void EntitiesCond(SubIf.SubIfType condition) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new SelectionBlock("EntitiesCond", condition)).SetItems((List)items));
   }

   public static void AllPlayers() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new SelectionBlock("AllPlayers")).SetItems((List)items));
   }

   public static void Invert() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new SelectionBlock("Invert")).SetItems((List)items));
   }

   public static void FilterCondition(SubIf.SubIfType condition) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new SelectionBlock("FilterCondition", condition)).SetItems((List)items));
   }

   public static void FilterSort(CodeValue valueTo, INumber selectionSize, Tags.SortOrder sortOrderTag) {
      List items = new ArrayList();
      items.add(valueTo);
      if (selectionSize != null) {
         items.add(selectionSize);
      }

      CodeManager.instance.addCodeBlock((new SelectionBlock("FilterSort")).SetItems((List)items).SetTags(new Tag("Sort Order", sortOrderTag.getJSONValue())));
   }

   public static void PlayersCond(SubIf.SubIfType condition) {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new SelectionBlock("PlayersCond", condition)).SetItems((List)items));
   }
}

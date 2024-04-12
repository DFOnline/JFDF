package net.jfdf.jfdf.mangement;

import java.util.ArrayList;
import java.util.List;
import net.jfdf.jfdf.blocks.BracketBlock;
import net.jfdf.jfdf.blocks.RepeatBlock;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tag;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Variable;

public class Repeat {
   public static void Forever() {
      CodeManager.instance.addCodeBlock(new RepeatBlock(RepeatBlock.Type.FOREVER));
      CodeManager.instance.addCodeBlock(new BracketBlock(false, true));
   }

   public static void While(SubIf condition) {
      CodeManager.instance.addCodeBlock((new RepeatBlock(condition.GetType(), condition.IsInversed())).SetItems(condition.GetItems()).SetTags(condition.GetTags()));
      CodeManager.instance.addCodeBlock(new BracketBlock(false, true));
   }

   public static void MultipleTimes(Variable iterationIndex, INumber amount) {
      List items = new ArrayList();
      if (iterationIndex != null) {
         items.add(iterationIndex);
      }

      items.add(amount);
      CodeManager.instance.addCodeBlock((new RepeatBlock(RepeatBlock.Type.MULTIPLE_TIMES)).SetItems((List)items));
      CodeManager.instance.addCodeBlock(new BracketBlock(false, true));
   }

   public static void OnRange(Variable iterationIndex, INumber startOfRange, INumber endOfRange, Number interval) {
      List items = new ArrayList();
      if (iterationIndex != null) {
         items.add(iterationIndex);
      }

      items.add(startOfRange);
      items.add(endOfRange);
      if (interval != null) {
         items.add(interval);
      }

      CodeManager.instance.addCodeBlock((new RepeatBlock(RepeatBlock.Type.ON_RANGE)).SetItems((List)items));
      CodeManager.instance.addCodeBlock(new BracketBlock(false, true));
   }

   public static void ForEach(Variable iterationValue, Variable listToRepeatThrough) {
      List items = new ArrayList();
      items.add(iterationValue);
      items.add(listToRepeatThrough);
      CodeManager.instance.addCodeBlock((new RepeatBlock(RepeatBlock.Type.FOREACH)).SetItems((List)items));
      CodeManager.instance.addCodeBlock(new BracketBlock(false, true));
   }

   public static void ForEachEntry(Variable key, Variable value, Variable dictionary) {
      List items = new ArrayList();
      items.add(key);
      items.add(value);
      items.add(dictionary);
      CodeManager.instance.addCodeBlock((new RepeatBlock(RepeatBlock.Type.FOREACH_ENTRY)).SetItems((List)items));
      CodeManager.instance.addCodeBlock(new BracketBlock(false, true));
   }

   public static void OnGrid(Variable iterationBlock, ILocation startOfRegion, ILocation endOfRegion) {
      List items = new ArrayList();
      items.add(iterationBlock);
      items.add(startOfRegion);
      items.add(endOfRegion);
      CodeManager.instance.addCodeBlock((new RepeatBlock(RepeatBlock.Type.ON_GRID)).SetItems((List)items));
      CodeManager.instance.addCodeBlock(new BracketBlock(false, true));
   }

   public static void Adjacently(Variable iterationBlock, ILocation startOfRegion, ILocation endOfRegion, Tags.Pattern pattern, Tags.IncludeOriginBlock includeOriginBlock, Tags.ChangeLocationRotation changeLocationRotation) {
      List items = new ArrayList();
      items.add(iterationBlock);
      items.add(startOfRegion);
      items.add(endOfRegion);
      CodeManager.instance.addCodeBlock((new RepeatBlock(RepeatBlock.Type.ON_GRID)).SetItems((List)items).SetTags(new Tag("Pattern", pattern.getJSONValue()), new Tag("Include Origin Block", includeOriginBlock.getJSONValue()), new Tag("Change Location Rotation", changeLocationRotation.getJSONValue())));
      CodeManager.instance.addCodeBlock(new BracketBlock(false, true));
   }

   public static void OnSphere(Variable iterationBlock, ILocation sphereCenter, INumber sphereRadius, INumber spherePoints, Tags.PointLocationsInwards pointLocationsInwards) {
      List items = new ArrayList();
      items.add(iterationBlock);
      items.add(sphereCenter);
      items.add(sphereRadius);
      items.add(spherePoints);
      CodeManager.instance.addCodeBlock((new RepeatBlock(RepeatBlock.Type.ON_SPHERE)).SetItems((List)items).SetTags(new Tag("Point Locations Inwards", pointLocationsInwards.getJSONValue())));
      CodeManager.instance.addCodeBlock(new BracketBlock(false, true));
   }

   public static void End() {
      CodeManager.instance.addCodeBlock(new BracketBlock(true, true));
   }
}

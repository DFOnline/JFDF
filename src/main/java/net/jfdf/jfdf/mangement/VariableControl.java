package net.jfdf.jfdf.mangement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.jfdf.jfdf.blocks.SetVariableBlock;
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
import net.jfdf.jfdf.values.Variable;

public class VariableControl {
   public static void SetParticleType(Variable variableToSet, IParticle effectTo, IText type) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (effectTo != null) {
         items.add(effectTo);
      }

      items.add(type);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetParticleType")).SetItems((List)items));
   }

   public static void SetItemEnchants(Variable variableToSet, IItem itemToChange, Variable enchantments) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.add(enchantments);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetItemEnchants")).SetItems((List)items));
   }

   public static void PurgeVars(IText[] nameToMatch, Tags.IgnoreCase ignoreCaseTag, Tags.MatchRequirement matchRequirementTag) {
      List items = new ArrayList();
      items.addAll(Arrays.asList(nameToMatch));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("PurgeVars")).SetItems((List)items).SetTags(new Tag("Ignore Case", ignoreCaseTag.getJSONValue()), new Tag("Match Requirement", matchRequirementTag.getJSONValue())));
   }

   public static void ShiftAllAxes(Variable variableToSet, ILocation locationToChange, INumber xChange, INumber yChange, INumber zChange) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (locationToChange != null) {
         items.add(locationToChange);
      }

      items.add(xChange);
      if (yChange != null) {
         items.add(yChange);
      }

      if (zChange != null) {
         items.add(zChange);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("ShiftAllAxes")).SetItems((List)items));
   }

   public static void GetParticleMat(Variable variableToSet, IParticle effectToGet) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(effectToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetParticleMat")).SetItems((List)items));
   }

   public static void SetParticleSprd(Variable variableToSet, IParticle effectToChange, INumber horizontalSpread, INumber verticalSpread) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (effectToChange != null) {
         items.add(effectToChange);
      }

      items.add(horizontalSpread);
      items.add(verticalSpread);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetParticleSprd")).SetItems((List)items));
   }

   public static void AbsoluteValue(Variable variableToSet, INumber numberInput) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (numberInput != null) {
         items.add(numberInput);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("AbsoluteValue")).SetItems((List)items));
   }

   public static void AppendValue(Variable listToAppendTo, CodeValue... value) {
      List items = new ArrayList();
      items.add(listToAppendTo);
      items.addAll(Arrays.asList(value));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("AppendValue")).SetItems((List)items));
   }

   public static void SetToRemainder(Variable variableToSet, INumber dividend, INumber divisor) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(dividend);
      items.add(divisor);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("%")).SetItems((List)items));
   }

   public static void ShiftOnVector(Variable variableToSet, ILocation locationToShift, IVector shiftVector, INumber shiftDistance, Tags.AddLocationRotation addLocationRotationTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (locationToShift != null) {
         items.add(locationToShift);
      }

      items.add(shiftVector);
      items.add(shiftDistance);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("ShiftOnVector")).SetItems((List)items).SetTags(new Tag("Add Location Rotation", addLocationRotationTag.getJSONValue())));
   }

   public static void GetItemAttribute(Variable variableToSet, IItem item, Tags.ActiveEquipmentSlot activeEquipmentSlotTag, Tags.Attribute attributeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(item);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetItemAttribute")).SetItems((List)items).SetTags(new Tag("Active Equipment Slot", activeEquipmentSlotTag.getJSONValue()), new Tag("Attribute", attributeTag.getJSONValue())));
   }

   public static void ClearDict(Variable dictionaryToClear) {
      List items = new ArrayList();
      items.add(dictionaryToClear);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("ClearDict")).SetItems((List)items));
   }

   public static void SetToSum(Variable variableToSet, CodeValue... numbersToAdd) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.addAll(Arrays.asList(numbersToAdd));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("+")).SetItems((List)items));
   }

   public static void ShiftRotation(Variable variableToSet, ILocation locationToShift, INumber rotationAmount, Tags.RotationAxis rotationAxisTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (locationToShift != null) {
         items.add(locationToShift);
      }

      items.add(rotationAmount);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("ShiftRotation")).SetItems((List)items).SetTags(new Tag("Rotation Axis", rotationAxisTag.getJSONValue())));
   }

   public static void SetToDifference(Variable variableToSet, CodeValue... numbersToSubtract) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.addAll(Arrays.asList(numbersToSubtract));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("-")).SetItems((List)items));
   }

   public static void GetItemRarity(Variable variableToSet, IItem item) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(item);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetItemRarity")).SetItems((List)items));
   }

   public static void MultiplyVector(Variable variableToSet, IVector vectorToMultiply, INumber multiplier) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (vectorToMultiply != null) {
         items.add(vectorToMultiply);
      }

      items.add(multiplier);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("MultiplyVector")).SetItems((List)items));
   }

   public static void SetToQuotient(Variable variableToSet, INumber[] numbersToDivide, Tags.DivisionMode divisionModeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.addAll(Arrays.asList(numbersToDivide));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("/")).SetItems((List)items).SetTags(new Tag("Division Mode", divisionModeTag.getJSONValue())));
   }

   public static void GetSignText(Variable variableToSet, ILocation location, Tags.SignLine signLineTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(location);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetSignText")).SetItems((List)items).SetTags(new Tag("Sign Line", signLineTag.getJSONValue())));
   }

   public static void Bitwise(Variable variableToSet, INumber operand1, INumber operand2, Tags.Operator operatorTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(operand1);
      if (operand2 != null) {
         items.add(operand2);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("Bitwise")).SetItems((List)items).SetTags(new Tag("Operator", operatorTag.getJSONValue())));
   }

   public static void ShiftOnAxis(Variable variableToSet, ILocation locationToShift, INumber shiftDistance, Tags.Coordinate coordinateTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (locationToShift != null) {
         items.add(locationToShift);
      }

      items.add(shiftDistance);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("ShiftOnAxis")).SetItems((List)items).SetTags(new Tag("Coordinate", coordinateTag.getJSONValue())));
   }

   public static void VectorBetween(Variable variableToSet, ILocation startLocation, ILocation endLocation) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(startLocation);
      items.add(endLocation);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("VectorBetween")).SetItems((List)items));
   }

   public static void GetVectorComp(Variable variableToSet, IVector vectorToGet, Tags.Component componentTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(vectorToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetVectorComp")).SetItems((List)items).SetTags(new Tag("Component", componentTag.getJSONValue())));
   }

   public static void Set(Variable variableToSet, CodeValue value) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(value);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("=")).SetItems((List)items));
   }

   public static void AddItemAttribute(Variable variableToSet, IItem item, INumber modifierAmount, Tags.ActiveEquipmentSlot activeEquipmentSlotTag, Tags.Operation operationTag, Tags.Attribute attributeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (item != null) {
         items.add(item);
      }

      items.add(modifierAmount);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("AddItemAttribute")).SetItems((List)items).SetTags(new Tag("Active Equipment Slot", activeEquipmentSlotTag.getJSONValue()), new Tag("Operation", operationTag.getJSONValue()), new Tag("Attribute", attributeTag.getJSONValue())));
   }

   public static void GetCenterLoc(Variable variableToSet, ILocation... locationsToCenter) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.addAll(Arrays.asList(locationsToCenter));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetCenterLoc")).SetItems((List)items));
   }

   public static void AlignLoc(Variable variableToSet, ILocation locationToAlign, Tags.LocationAlignmentMode alignmentModeTag, Tags.Coordinates coordinatesTag, Tags.Rotation rotationTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (locationToAlign != null) {
         items.add(locationToAlign);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("AlignLoc")).SetItems((List)items).SetTags(new Tag("Alignment Mode", alignmentModeTag.getJSONValue()), new Tag("Coordinates", coordinatesTag.getJSONValue()), new Tag("Rotation", rotationTag.getJSONValue())));
   }

   public static void GetSoundVolume(Variable variableToSet, ISound soundToGetVolumeOf) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(soundToGetVolumeOf);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetSoundVolume")).SetItems((List)items));
   }

   public static void RandomNumber(Variable variableToSet, INumber minimumNumber, INumber maximumNumber, Tags.RoundingMode roundingModeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(minimumNumber);
      items.add(maximumNumber);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("RandomNumber")).SetItems((List)items).SetTags(new Tag("Rounding Mode", roundingModeTag.getJSONValue())));
   }

   public static void Raycast(Variable variableToSet, ILocation rayOrigin, INumber rayDistance, Tags.BlockCollision blockCollisionTag, Tags.EntityCollision entityCollisionTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(rayOrigin);
      items.add(rayDistance);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("Raycast")).SetItems((List)items).SetTags(new Tag("Block Collision", blockCollisionTag.getJSONValue()), new Tag("Entity Collision", entityCollisionTag.getJSONValue())));
   }

   public static void RotateAroundVec(Variable variableToSet, IVector vectorToRotate, IVector axisVector, INumber angle, Tags.AngleUnits angleUnitsTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (vectorToRotate != null) {
         items.add(vectorToRotate);
      }

      items.add(axisVector);
      items.add(angle);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("RotateAroundVec")).SetItems((List)items).SetTags(new Tag("Angle Units", angleUnitsTag.getJSONValue())));
   }

   public static void GetParticleMotion(Variable variableToSet, IParticle effectToGet) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(effectToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetParticleMotion")).SetItems((List)items));
   }

   public static void SetParticleMotion(Variable variableToSet, IParticle effectTo, IVector particleMotion, INumber motionVariation) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (effectTo != null) {
         items.add(effectTo);
      }

      items.add(particleMotion);
      if (motionVariation != null) {
         items.add(motionVariation);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetParticleMotion")).SetItems((List)items));
   }

   public static void RepeatText(Variable variableToSet, IText textToRepeat, INumber timesToRepeat) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(textToRepeat);
      items.add(timesToRepeat);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("RepeatText")).SetItems((List)items));
   }

   public static void Average(Variable variableToSet, INumber... numbersToAverage) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.addAll(Arrays.asList(numbersToAverage));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("Average")).SetItems((List)items));
   }

   public static void WrapNumber(Variable variableToSet, INumber numberToWrap, INumber lowerBound, INumber upperBound) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (numberToWrap != null) {
         items.add(numberToWrap);
      }

      items.add(lowerBound);
      items.add(upperBound);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("WrapNumber")).SetItems((List)items));
   }

   public static void SetMapTexture(Variable variableToSet, IItem itemToChange, IText imageUrl) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.add(imageUrl);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetMapTexture")).SetItems((List)items));
   }

   public static void GetBlockData(Variable variableToSet, ILocation blockLocation, IText tagName) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(blockLocation);
      items.add(tagName);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetBlockData")).SetItems((List)items));
   }

   public static void CrossProduct(Variable variableToSet, IVector vector1, IVector vector2) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(vector1);
      items.add(vector2);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("CrossProduct")).SetItems((List)items));
   }

   public static void SetToProduct(Variable variableToSet, CodeValue... numbersToMultiply) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.addAll(Arrays.asList(numbersToMultiply));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("x")).SetItems((List)items));
   }

   public static void DotProduct(Variable variableToSet, IVector vector1, IVector vector2) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(vector1);
      items.add(vector2);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("DotProduct")).SetItems((List)items));
   }

   public static void GetPotionType(Variable variableToSet, IPotion potionToGet) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(potionToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetPotionType")).SetItems((List)items));
   }

   public static void SetItemName(Variable variableToSet, IItem itemToChange, IText... name) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.addAll(Arrays.asList(name));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetItemName")).SetItems((List)items));
   }

   public static void ListLength(Variable variableToSet, Variable listToMeasure) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(listToMeasure);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("ListLength")).SetItems((List)items));
   }

   public static void Sine(Variable variableToSet, INumber numberInput, Tags.Input inputTag, Tags.SineVariant sineVariantTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(numberInput);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("Sine")).SetItems((List)items).SetTags(new Tag("Input", inputTag.getJSONValue()), new Tag("Sine Variant", sineVariantTag.getJSONValue())));
   }

   public static void DirectionName(Variable variableToSet, IVector direction) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(direction);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("DirectionName")).SetItems((List)items));
   }

   public static void GetItemLore(Variable variableToSet, IItem itemToGetLoreOf) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(itemToGetLoreOf);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetItemLore")).SetItems((List)items));
   }

   public static void ReverseList(Variable variableToSet, Variable listToReverse) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (listToReverse != null) {
         items.add(listToReverse);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("ReverseList")).SetItems((List)items));
   }

   public static void CreateDict(Variable variableToSet, Variable keyList, Variable valueList) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (keyList != null) {
         items.add(keyList);
      }

      if (valueList != null) {
         items.add(valueList);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("CreateDict")).SetItems((List)items));
   }

   public static void FaceLocation(Variable variableToSet, ILocation locationToChange, ILocation targetLocation, Tags.FaceDirection faceDirectionTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (locationToChange != null) {
         items.add(locationToChange);
      }

      items.add(targetLocation);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("FaceLocation")).SetItems((List)items).SetTags(new Tag("Face Direction", faceDirectionTag.getJSONValue())));
   }

   public static void SetVectorLength(Variable variableToSet, IVector vectorToChange, INumber length) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (vectorToChange != null) {
         items.add(vectorToChange);
      }

      if (length != null) {
         items.add(length);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetVectorLength")).SetItems((List)items));
   }

   public static void SetPotionDur(Variable variableToSet, IPotion potionToChange, INumber duration) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (potionToChange != null) {
         items.add(potionToChange);
      }

      items.add(duration);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetPotionDur")).SetItems((List)items));
   }

   public static void NormalRandom(Variable variableToSet, INumber meanMidpoint, INumber standardDeviation, Tags.Distribution distributionTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(meanMidpoint);
      items.add(standardDeviation);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("NormalRandom")).SetItems((List)items).SetTags(new Tag("Distribution", distributionTag.getJSONValue())));
   }

   public static void SetPotionType(Variable variableToSet, IPotion potionToChange, IText type) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (potionToChange != null) {
         items.add(potionToChange);
      }

      items.add(type);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetPotionType")).SetItems((List)items));
   }

   public static void AlignVector(Variable variableToSet, IVector vectorToAlign) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (vectorToAlign != null) {
         items.add(vectorToAlign);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("AlignVector")).SetItems((List)items));
   }

   public static void SetItemDura(Variable variableToSet, IItem item, INumber itemDurability, Tags.DurabilityType durabilityTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (item != null) {
         items.add(item);
      }

      items.add(itemDurability);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetItemDura")).SetItems((List)items).SetTags(new Tag("Durability Type", durabilityTypeTag.getJSONValue())));
   }

   public static void SetBreakability(Variable variableToSet, IItem item, Tags.Breakability breakabilityTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (item != null) {
         items.add(item);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetBreakability")).SetItems((List)items).SetTags(new Tag("Breakability", breakabilityTag.getJSONValue())));
   }

   public static void SetDictValue(Variable dictionaryToAddTo, IText key, CodeValue value) {
      List items = new ArrayList();
      items.add(dictionaryToAddTo);
      items.add(key);
      items.add(value);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetDictValue")).SetItems((List)items));
   }

   public static void SetAllCoords(Variable variableToSet, ILocation locationToChange, INumber newX, INumber newY, INumber newZ, INumber newPitch, INumber newYaw, Tags.CoordinateType coordinateTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (locationToChange != null) {
         items.add(locationToChange);
      }

      items.add(newX);
      if (newY != null) {
         items.add(newY);
      }

      if (newZ != null) {
         items.add(newZ);
      }

      if (newPitch != null) {
         items.add(newPitch);
      }

      if (newYaw != null) {
         items.add(newYaw);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetAllCoords")).SetItems((List)items).SetTags(new Tag("Coordinate Type", coordinateTypeTag.getJSONValue())));
   }

   public static void RGBColor(Variable variableToSet, INumber red, INumber green, INumber blue) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(red);
      items.add(green);
      items.add(blue);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("RGBColor")).SetItems((List)items));
   }

   public static void SetCanDestroy(Variable variableToSet, IItem itemToChange, CodeValue... breakableBlocks) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.addAll(Arrays.asList(breakableBlocks));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetCanDestroy")).SetItems((List)items));
   }

   public static void SplitText(Variable variableToSet, IText textToSplit, IText splitterText) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(textToSplit);
      if (splitterText != null) {
         items.add(splitterText);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("SplitText")).SetItems((List)items));
   }

   public static void HSLColor(Variable variableToSet, INumber hue, INumber saturation, INumber lightness) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(hue);
      if (saturation != null) {
         items.add(saturation);
      }

      if (lightness != null) {
         items.add(lightness);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("HSLColor")).SetItems((List)items));
   }

   public static void RemoveListIndex(Variable listToChange, INumber... indexToRemove) {
      List items = new ArrayList();
      items.add(listToChange);
      items.addAll(Arrays.asList(indexToRemove));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("RemoveListIndex")).SetItems((List)items));
   }

   public static void Logarithm(Variable variableToSet, INumber numberInput, INumber base) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (numberInput != null) {
         items.add(numberInput);
      }

      items.add(base);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("Logarithm")).SetItems((List)items));
   }

   public static void SetItemTag(Variable variableToSet, IItem itemToChange, IText tagName, INumber tagValue) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.add(tagName);
      items.add(tagValue);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetItemTag")).SetItems((List)items));
   }

   public static void GetItemAmount(Variable variableToSet, IItem itemToGetStack) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(itemToGetStack);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetItemAmount")).SetItems((List)items));
   }

   public static void SetPotionAmp(Variable variableToSet, IPotion potionToChange, INumber amplifier) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (potionToChange != null) {
         items.add(potionToChange);
      }

      items.add(amplifier);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetPotionAmp")).SetItems((List)items));
   }

   public static void RotateAroundAxis(Variable variableToSet, IVector vectorToRotate, INumber angle, Tags.AngleUnits angleUnitsTag, Tags.Axis axisTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (vectorToRotate != null) {
         items.add(vectorToRotate);
      }

      items.add(angle);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("RotateAroundAxis")).SetItems((List)items).SetTags(new Tag("Angle Units", angleUnitsTag.getJSONValue()), new Tag("Axis", axisTag.getJSONValue())));
   }

   public static void GetItemName(Variable variableToSet, IItem itemToGetNameOf) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(itemToGetNameOf);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetItemName")).SetItems((List)items));
   }

   public static void GetItemDura(Variable variableToSet, IItem item, Tags.DurabilityType durabilityTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(item);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetItemDura")).SetItems((List)items).SetTags(new Tag("Durability Type", durabilityTypeTag.getJSONValue())));
   }

   public static void ShiftInDirection(Variable variableToSet, ILocation locationToShift, INumber shiftDistance, Tags.Direction directionTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (locationToShift != null) {
         items.add(locationToShift);
      }

      if (shiftDistance != null) {
         items.add(shiftDistance);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("ShiftInDirection")).SetItems((List)items).SetTags(new Tag("Direction", directionTag.getJSONValue())));
   }

   public static void ReplaceText(Variable variable, IText textToChange, IText textPartToReplace, IText replacement, Tags.RegularExpressions regularExpressionsTag, Tags.ReplacementType replacementTypeTag) {
      List items = new ArrayList();
      items.add(variable);
      items.add(textToChange);
      items.add(textPartToReplace);
      items.add(replacement);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("ReplaceText")).SetItems((List)items).SetTags(new Tag("Regular Expressions", regularExpressionsTag.getJSONValue()), new Tag("Replacement Type", replacementTypeTag.getJSONValue())));
   }

   public static void TextLength(Variable variableToSet, IText textToMeasure) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(textToMeasure);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("TextLength")).SetItems((List)items));
   }

   public static void SetLodestoneLoc(Variable variableToSet, IItem itemToChange, ILocation lodestoneLocation, Tags.RequireLodestoneatLocation requireLodestoneatLocationTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.add(lodestoneLocation);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetLodestoneLoc")).SetItems((List)items).SetTags(new Tag("Require Lodestone at Location", requireLodestoneatLocationTag.getJSONValue())));
   }

   public static void GetPotionAmp(Variable variableToSet, IPotion potionToGet) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(potionToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetPotionAmp")).SetItems((List)items));
   }

   public static void GetParticleAmount(Variable variableToSet, IParticle effectToGet) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(effectToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetParticleAmount")).SetItems((List)items));
   }

   public static void GetDictSize(Variable variableToSet, Variable dictionaryTo) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(dictionaryTo);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetDictSize")).SetItems((List)items));
   }

   public static void SetItemAmount(Variable variableToSet, IItem itemToChange, INumber stackSize) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.add(stackSize);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetItemAmount")).SetItems((List)items));
   }

   public static void SubtractVectors(Variable variableToSet, IVector... vectorsToSubtract) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.addAll(Arrays.asList(vectorsToSubtract));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SubtractVectors")).SetItems((List)items));
   }

   public static void SetCase(Variable variableToSet, IText textToChange, Tags.CapitalizationType capitalizationTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (textToChange != null) {
         items.add(textToChange);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetCase")).SetItems((List)items).SetTags(new Tag("Capitalization Type", capitalizationTypeTag.getJSONValue())));
   }

   public static void SetParticleColor(Variable variableToSet, IParticle effectTo, IText colorHexadecimal, INumber colorVariation) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (effectTo != null) {
         items.add(effectTo);
      }

      items.add(colorHexadecimal);
      if (colorVariation != null) {
         items.add(colorVariation);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetParticleColor")).SetItems((List)items));
   }

   public static void GetLight(Variable variableToSet, ILocation lightLocation, Tags.LightType lightTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(lightLocation);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetLight")).SetItems((List)items).SetTags(new Tag("Light Type", lightTypeTag.getJSONValue())));
   }

   public static void GetDictValues(Variable variableToSet, Variable dictionaryTo) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(dictionaryTo);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetDictValues")).SetItems((List)items));
   }

   public static void Vector(Variable variableToSet, INumber xComponent, INumber yComponent, INumber zComponent) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(xComponent);
      items.add(yComponent);
      items.add(zComponent);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("Vector")).SetItems((List)items));
   }

   public static void Distance(Variable variableToSet, ILocation location1, ILocation location2, Tags.DistanceType distanceTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(location1);
      items.add(location2);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("Distance")).SetItems((List)items).SetTags(new Tag("Distance Type", distanceTypeTag.getJSONValue())));
   }

   public static void SetItemLore(Variable variableToSet, IItem itemToChange, IText[] lore) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      if (lore != null) {
         items.addAll(Arrays.asList(lore));
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetItemLore")).SetItems((List)items));
   }

   public static void Root(Variable variableToSet, INumber numberInput, INumber rootIndex) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (numberInput != null) {
         items.add(numberInput);
      }

      if (rootIndex != null) {
         items.add(rootIndex);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("Root")).SetItems((List)items));
   }

   public static void SetParticleAmount(Variable variableToSet, IParticle effectTo, INumber particleAmount) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (effectTo != null) {
         items.add(effectTo);
      }

      items.add(particleAmount);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetParticleAmount")).SetItems((List)items));
   }

   public static void AddItemEnchant(Variable variableToSet, IItem itemToChange, IText enchantmentName, INumber enchantmentLevel) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.add(enchantmentName);
      items.add(enchantmentLevel);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("AddItemEnchant")).SetItems((List)items));
   }

   public static void GetItemType(Variable variableToSet, IItem itemToGetMaterialOf, Tags.ReturnValueType returnValueTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(itemToGetMaterialOf);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetItemType")).SetItems((List)items).SetTags(new Tag("Return Value Type", returnValueTypeTag.getJSONValue())));
   }

   public static void GetDirection(Variable variableToSet, ILocation locationToGet) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(locationToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetDirection")).SetItems((List)items));
   }

   public static void TrimText(Variable variableToSet, IText textToTrim, INumber startCharacterPosition, INumber endCharacterPosition) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (textToTrim != null) {
         items.add(textToTrim);
      }

      items.add(startCharacterPosition);
      if (endCharacterPosition != null) {
         items.add(endCharacterPosition);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("TrimText")).SetItems((List)items));
   }

   public static void GetParticleType(Variable variableToSet, IParticle effectToGet) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(effectToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetParticleType")).SetItems((List)items));
   }

   public static void GetAllBlockData(Variable variableToSet, ILocation blockLocation, Tags.HideDefault hideDefaultTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(blockLocation);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetAllBlockData")).SetItems((List)items).SetTags(new Tag("Hide Default", hideDefaultTag.getJSONValue())));
   }

   public static void GetDictKeys(Variable variableToSet, Variable dictionaryTo) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(dictionaryTo);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetDictKeys")).SetItems((List)items));
   }

   public static void SetParticleMat(Variable variableToSet, IParticle effectTo, IItem particleMaterial) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (effectTo != null) {
         items.add(effectTo);
      }

      items.add(particleMaterial);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetParticleMat")).SetItems((List)items));
   }

   public static void GetCoord(Variable variableToSet, ILocation locationToGet, Tags.Coordinate coordinateTag, Tags.CoordinateType coordinateTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(locationToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetCoord")).SetItems((List)items).SetTags(new Tag("Coordinate", coordinateTag.getJSONValue()), new Tag("Coordinate Type", coordinateTypeTag.getJSONValue())));
   }

   public static void RemoveItemTag(Variable variableToSet, IItem itemToChange, IText tagName) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.add(tagName);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("RemoveItemTag")).SetItems((List)items));
   }

   public static void SetParticleSize(Variable variableToSet, IParticle effectTo, INumber particleSize, INumber sizeVariation) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (effectTo != null) {
         items.add(effectTo);
      }

      items.add(particleSize);
      if (sizeVariation != null) {
         items.add(sizeVariation);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetParticleSize")).SetItems((List)items));
   }

   public static void GetPotionDur(Variable variableToSet, IPotion potionToGet) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(potionToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetPotionDur")).SetItems((List)items));
   }

   public static void RandomLoc(Variable variableToSet, ILocation location1, ILocation location2) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(location1);
      items.add(location2);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("RandomLoc")).SetItems((List)items));
   }

   public static void SetSoundType(Variable variableToSet, ISound soundToChange, IText soundName) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (soundToChange != null) {
         items.add(soundToChange);
      }

      items.add(soundName);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetSoundType")).SetItems((List)items));
   }

   public static void GetLodestoneLoc(Variable variableToSet, IItem compassToGetLodestone) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(compassToGetLodestone);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetLodestoneLoc")).SetItems((List)items));
   }

   public static void GetContainerName(Variable variableToSet, ILocation containerLocation) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(containerLocation);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetContainerName")).SetItems((List)items));
   }

   public static void GetParticleSprd(Variable variableToSet, IParticle effectToGet, Tags.Spread spreadTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(effectToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetParticleSprd")).SetItems((List)items).SetTags(new Tag("Spread", spreadTag.getJSONValue())));
   }

   public static void ReflectVector(Variable variableToSet, IVector vectorToReflect, IVector surfaceVector) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (vectorToReflect != null) {
         items.add(vectorToReflect);
      }

      items.add(surfaceVector);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("ReflectVector")).SetItems((List)items));
   }

   public static void GetHeadOwner(Variable variableToSet, IItem headToGetOwnerOf, Tags.TextValue textValueTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(headToGetOwnerOf);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetHeadOwner")).SetItems((List)items).SetTags(new Tag("Text Value", textValueTag.getJSONValue())));
   }

   public static void GetItemEnchants(Variable variableToSet, IItem itemToGetEnchantmentsOf) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(itemToGetEnchantmentsOf);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetItemEnchants")).SetItems((List)items));
   }

   public static void AppendDict(Variable dictionaryTo, Variable dictionary) {
      List items = new ArrayList();
      items.add(dictionaryTo);
      items.add(dictionary);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("AppendDict")).SetItems((List)items));
   }

   public static void GetMaxItemAmount(Variable variableToSet, IItem itemToGetMaximumStack) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(itemToGetMaximumStack);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetMaxItemAmount")).SetItems((List)items));
   }

   public static void GetColorChannels(Variable variableToSet, IText color, Tags.ColorChannels colorChannelsTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(color);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetColorChannels")).SetItems((List)items).SetTags(new Tag("Color Channels", colorChannelsTag.getJSONValue())));
   }

   public static void _SetDirection_(Variable variableToSet, ILocation locationToChange, IVector direction) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (locationToChange != null) {
         items.add(locationToChange);
      }

      items.add(direction);
      CodeManager.instance.addCodeBlock((new SetVariableBlock(" SetDirection ")).SetItems((List)items));
   }

   public static void SetListValue(Variable listToChange, INumber index, CodeValue valueToSet) {
      List items = new ArrayList();
      items.add(listToChange);
      items.add(index);
      items.add(valueToSet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetListValue")).SetItems((List)items));
   }

   public static void _SetItemEnchants_(Variable variableToSet, IItem itemToChange, Variable enchantments) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.add(enchantments);
      CodeManager.instance.addCodeBlock((new SetVariableBlock(" SetItemEnchants ")).SetItems((List)items));
   }

   public static void SetBookText(Variable variableToSet, IItem book, IText[] pages) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (book != null) {
         items.add(book);
      }

      items.addAll(Arrays.asList(pages));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetBookText")).SetItems((List)items));
   }

   public static void RandomValue(Variable variableToSet, CodeValue... valueSet) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.addAll(Arrays.asList(valueSet));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("RandomValue")).SetItems((List)items));
   }

   public static void SetItemType(Variable variableToSet, IItem itemToChange, IText material) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.add(material);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetItemType")).SetItems((List)items));
   }

   public static void GetSoundType(Variable variableToSet, ISound soundToGetTypeOf) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(soundToGetTypeOf);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetSoundType")).SetItems((List)items));
   }

   public static void GetListValue(Variable variableToSet, CodeValue listToGetValueOf, INumber index) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(listToGetValueOf);
      items.add(index);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetListValue")).SetItems((List)items));
   }

   public static void Tangent(Variable variableToSet, INumber numberInput, Tags.Input inputTag, Tags.TangentVariant tangentVariantTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(numberInput);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("Tangent")).SetItems((List)items).SetTags(new Tag("Input", inputTag.getJSONValue()), new Tag("Tangent Variant", tangentVariantTag.getJSONValue())));
   }

   public static void Text(Variable variableToSet, CodeValue[] textToSetTo, Tags.TextValueMerging textValueMergingTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (textToSetTo != null) {
         items.addAll(Arrays.asList(textToSetTo));
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("Text")).SetItems((List)items).SetTags(new Tag("Text Value Merging", textValueMergingTag.getJSONValue())));
   }

   public static void VoronoiNoise(Variable variableToSet, ILocation noiseLocation, INumber cellFrequency, INumber cellScatter, INumber generationSeed, Tags.CellEdgeType cellEdgeTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(noiseLocation);
      if (cellFrequency != null) {
         items.add(cellFrequency);
      }

      if (cellScatter != null) {
         items.add(cellScatter);
      }

      if (generationSeed != null) {
         items.add(generationSeed);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("VoronoiNoise")).SetItems((List)items).SetTags(new Tag("Cell Edge Type", cellEdgeTypeTag.getJSONValue())));
   }

   public static void HSBColor(Variable variableToSet, INumber hue, INumber saturation, INumber brightness) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(hue);
      if (saturation != null) {
         items.add(saturation);
      }

      if (brightness != null) {
         items.add(brightness);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("HSBColor")).SetItems((List)items));
   }

   public static void Increment(Variable variable, INumber... number) {
      List items = new ArrayList();
      items.add(variable);
      if (number != null) {
         items.addAll(Arrays.asList(number));
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("+=")).SetItems((List)items));
   }

   public static void GetItemColor(Variable variable, IItem itemToGetColorOf) {
      List items = new ArrayList();
      items.add(variable);
      items.add(itemToGetColorOf);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetItemColor")).SetItems((List)items));
   }

   public static void InsertListValue(Variable listToChange, INumber index, CodeValue valueToInsert) {
      List items = new ArrayList();
      items.add(listToChange);
      items.add(index);
      items.add(valueToInsert);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("InsertListValue")).SetItems((List)items));
   }

   public static void SetSoundVolume(Variable variableToSet, ISound soundToChange, INumber volume) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (soundToChange != null) {
         items.add(soundToChange);
      }

      items.add(volume);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetSoundVolume")).SetItems((List)items));
   }

   public static void SetCoord(Variable variableToSet, ILocation locationToChange, INumber coordinate, Tags.Coordinate coordinateTag, Tags.CoordinateType coordinateTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (locationToChange != null) {
         items.add(locationToChange);
      }

      items.add(coordinate);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetCoord")).SetItems((List)items).SetTags(new Tag("Coordinate", coordinateTag.getJSONValue()), new Tag("Coordinate Type", coordinateTypeTag.getJSONValue())));
   }

   public static void AddVectors(Variable variableToSet, IVector... vectorsToAdd) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.addAll(Arrays.asList(vectorsToAdd));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("AddVectors")).SetItems((List)items));
   }

   public static void _GetItemEnchants_(Variable variableToSet, IItem itemToGetEnchantmentsFrom) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(itemToGetEnchantmentsFrom);
      CodeManager.instance.addCodeBlock((new SetVariableBlock(" GetItemEnchants ")).SetItems((List)items));
   }

   public static void SetHeadTexture(Variable variableToSet, IItem playerHead, IText ownerName) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (playerHead != null) {
         items.add(playerHead);
      }

      items.add(ownerName);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetHeadTexture")).SetItems((List)items));
   }

   public static void PerlinNoise(Variable variableToSet, ILocation noiseLocation, INumber frequency, INumber octaves, INumber octaveFrequencyGain, INumber octaveAmplitudeGain, INumber generationSeed, Tags.FractalType fractalTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(noiseLocation);
      if (frequency != null) {
         items.add(frequency);
      }

      if (octaves != null) {
         items.add(octaves);
      }

      if (octaveFrequencyGain != null) {
         items.add(octaveFrequencyGain);
      }

      if (octaveAmplitudeGain != null) {
         items.add(octaveAmplitudeGain);
      }

      if (generationSeed != null) {
         items.add(generationSeed);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("PerlinNoise")).SetItems((List)items).SetTags(new Tag("Fractal Type", fractalTypeTag.getJSONValue())));
   }

   public static void WorleyNoise(Variable variableToSet, ILocation noiseLocation, INumber cellFrequency, INumber cellScatter, INumber generationSeed, Tags.DistanceCalculation distanceCalculationTag, Tags.CellEdgeType cellEdgeTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(noiseLocation);
      if (cellFrequency != null) {
         items.add(cellFrequency);
      }

      if (cellScatter != null) {
         items.add(cellScatter);
      }

      if (generationSeed != null) {
         items.add(generationSeed);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("WorleyNoise")).SetItems((List)items).SetTags(new Tag("Distance Calculation", distanceCalculationTag.getJSONValue()), new Tag("Cell Edge Type", cellEdgeTypeTag.getJSONValue())));
   }

   public static void SetItemColor(Variable variableToSet, IItem itemToChange, IText colorHexadecimal) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.add(colorHexadecimal);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetItemColor")).SetItems((List)items));
   }

   public static void GetParticleColor(Variable variableToSet, IParticle effectToGet) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(effectToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetParticleColor")).SetItems((List)items));
   }

   public static void SetSoundPitch(Variable variableToSet, ISound soundToChange, INumber pitch) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (soundToChange != null) {
         items.add(soundToChange);
      }

      items.add(pitch);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetSoundPitch")).SetItems((List)items));
   }

   public static void SortList(Variable variableToSet, Variable listToSort, Tags.SortOrder sortOrderTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (listToSort != null) {
         items.add(listToSort);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("SortList")).SetItems((List)items).SetTags(new Tag("Sort Order", sortOrderTag.getJSONValue())));
   }

   public static void RemoveDictEntry(Variable dictionaryToChange, IText keyToRemove, CodeValue... expectedValue) {
      List items = new ArrayList();
      items.add(dictionaryToChange);
      items.add(keyToRemove);
      if (expectedValue != null) {
         items.addAll(Arrays.asList(expectedValue));
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("RemoveDictEntry")).SetItems((List)items));
   }

   public static void FormatTime(Variable variableToSet, INumber timeToFormat, IText customFormat, Tags.Format formatTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(timeToFormat);
      if (customFormat != null) {
         items.add(customFormat);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("FormatTime")).SetItems((List)items).SetTags(new Tag("Format", formatTag.getJSONValue())));
   }

   public static void SetItemFlags(Variable variableToSet, IItem item, Tags.HidePotionEffects hidePotionEffectsTag, Tags.HideCanPlaceOn hideCanPlaceOnTag, Tags.HideCanDestroy hideCanDestroyTag, Tags.HideUnbreakable hideUnbreakableTag, Tags.HideAttributes hideAttributesTag, Tags.HideEnchantments hideEnchantmentsTag, Tags.HideColor hideColorTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (item != null) {
         items.add(item);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetItemFlags")).SetItems((List)items).SetTags(new Tag("Hide Potion Effects", hidePotionEffectsTag.getJSONValue()), new Tag("Hide Can Place On", hideCanPlaceOnTag.getJSONValue()), new Tag("Hide Can Destroy", hideCanDestroyTag.getJSONValue()), new Tag("Hide Unbreakable", hideUnbreakableTag.getJSONValue()), new Tag("Hide Attributes", hideAttributesTag.getJSONValue()), new Tag("Hide Enchantments", hideEnchantmentsTag.getJSONValue()), new Tag("Hide Color", hideColorTag.getJSONValue())));
   }

   public static void GetItemEffects(Variable variableToSet, IItem itemToGetEffectsFrom) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(itemToGetEffectsFrom);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetItemEffects")).SetItems((List)items));
   }

   public static void SetItemEffects(Variable variableToSet, IItem itemToChange, IPotion... itemEffects) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.addAll(Arrays.asList(itemEffects));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetItemEffects")).SetItems((List)items));
   }

   public static void Decrement(Variable variable, INumber... number) {
      List items = new ArrayList();
      items.add(variable);
      if (number != null) {
         items.addAll(Arrays.asList(number));
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("-=")).SetItems((List)items));
   }

   public static void GetItemTag(Variable variableToSet, IItem itemToGetTagOf, IText tagName) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(itemToGetTagOf);
      items.add(tagName);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetItemTag")).SetItems((List)items));
   }

   public static void CreateList(Variable variableToSet, CodeValue... valueList) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (valueList != null) {
         items.addAll(Arrays.asList(valueList));
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("CreateList")).SetItems((List)items));
   }

   public static void AppendList(Variable listToAppendTo, Variable... list) {
      List items = new ArrayList();
      items.add(listToAppendTo);
      items.addAll(Arrays.asList(list));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("AppendList")).SetItems((List)items));
   }

   public static void GetContainerItems(Variable variableToSet, ILocation containerLocation, Tags.IgnoreEmptySlots ignoreEmptySlotsTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(containerLocation);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetContainerItems")).SetItems((List)items).SetTags(new Tag("Ignore Empty Slots", ignoreEmptySlotsTag.getJSONValue())));
   }

   public static void ShiftToward(Variable variableToSet, ILocation locationToShift, ILocation targetLocation, INumber shiftDistance) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (locationToShift != null) {
         items.add(locationToShift);
      }

      items.add(targetLocation);
      if (shiftDistance != null) {
         items.add(shiftDistance);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("ShiftToward")).SetItems((List)items));
   }

   public static void TrimList(Variable variableToSet, Variable listToTrim, INumber startIndex, INumber endIndex) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (listToTrim != null) {
         items.add(listToTrim);
      }

      items.add(startIndex);
      if (endIndex != null) {
         items.add(endIndex);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("TrimList")).SetItems((List)items));
   }

   public static void RemoveText(Variable variable, IText textToChange, IText[] textToRemove, Tags.RegularExpressions regularExpressionsTag) {
      List items = new ArrayList();
      items.add(variable);
      if (textToChange != null) {
         items.add(textToChange);
      }

      items.addAll(Arrays.asList(textToRemove));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("RemoveText")).SetItems((List)items).SetTags(new Tag("Regular Expressions", regularExpressionsTag.getJSONValue())));
   }

   public static void Cosine(Variable variableToSet, INumber numberInput, Tags.Input inputTag, Tags.CosineVariant cosineVariantTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(numberInput);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("Cosine")).SetItems((List)items).SetTags(new Tag("Input", inputTag.getJSONValue()), new Tag("Cosine Variant", cosineVariantTag.getJSONValue())));
   }

   public static void SetVectorComp(Variable variableToSet, IVector vectorToChange, INumber component, Tags.Component componentTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (vectorToChange != null) {
         items.add(vectorToChange);
      }

      items.add(component);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetVectorComp")).SetItems((List)items).SetTags(new Tag("Component", componentTag.getJSONValue())));
   }

   public static void ParseNumber(Variable variableToSet, IText textToConvert) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (textToConvert != null) {
         items.add(textToConvert);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("ParseNumber")).SetItems((List)items));
   }

   public static void Exponent(Variable variableToSet, INumber numberInput, INumber exponent) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (numberInput != null) {
         items.add(numberInput);
      }

      if (exponent != null) {
         items.add(exponent);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("Exponent")).SetItems((List)items));
   }

   public static void GetValueIndex(Variable variableToSet, Variable listToSearchIn, CodeValue valueToSearch, Tags.SearchOrder searchOrderTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(listToSearchIn);
      items.add(valueToSearch);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetValueIndex")).SetItems((List)items).SetTags(new Tag("Search Order", searchOrderTag.getJSONValue())));
   }

   public static void RemItemEnchant(Variable variableToSet, IItem itemToChange, IText enchantmentName) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.add(enchantmentName);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("RemItemEnchant")).SetItems((List)items));
   }

   public static void GetBookText(Variable variableToSet, IItem book, INumber pageNumber) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(book);
      if (pageNumber != null) {
         items.add(pageNumber);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetBookText")).SetItems((List)items));
   }

   public static void RandomizeList(Variable variableToSet, Variable listToRandomize) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (listToRandomize != null) {
         items.add(listToRandomize);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("RandomizeList")).SetItems((List)items));
   }

   public static void ClampNumber(Variable variableToSet, INumber numberToClamp, INumber minimum, INumber maximum) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (numberToClamp != null) {
         items.add(numberToClamp);
      }

      items.add(minimum);
      items.add(maximum);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("ClampNumber")).SetItems((List)items));
   }

   public static void Round(Variable variableToSet, INumber numberToRound, INumber roundMultiple, Tags.RoundMode roundModeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (numberToRound != null) {
         items.add(numberToRound);
      }

      if (roundMultiple != null) {
         items.add(roundMultiple);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("Round")).SetItems((List)items).SetTags(new Tag("Round Mode", roundModeTag.getJSONValue())));
   }

   public static void GetSoundPitch(Variable variableToSet, ISound soundToGetPitchOr, Tags.ReturnValueType returnValueTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(soundToGetPitchOr);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetSoundPitch")).SetItems((List)items).SetTags(new Tag("Return Value Type", returnValueTypeTag.getJSONValue())));
   }

   public static void JoinText(Variable variableToSet, Variable textsToJoin, IText joiningText, IText finalJoiningText) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(textsToJoin);
      if (joiningText != null) {
         items.add(joiningText);
      }

      if (finalJoiningText != null) {
         items.add(finalJoiningText);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("JoinText")).SetItems((List)items));
   }

   public static void TranslateColors(Variable variableToSet, IText textToTranslate, Tags.TranslationType translationTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (textToTranslate != null) {
         items.add(textToTranslate);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("TranslateColors")).SetItems((List)items).SetTags(new Tag("Translation Type", translationTypeTag.getJSONValue())));
   }

   public static void GetBlockGrowth(Variable variableToSet, ILocation blockLocation, Tags.GrowthUnit growthUnitTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(blockLocation);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetBlockGrowth")).SetItems((List)items).SetTags(new Tag("Growth Unit", growthUnitTag.getJSONValue())));
   }

   public static void GetAllItemTags(Variable variableToSet, IItem itemToGetTagsFrom) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(itemToGetTagsFrom);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetAllItemTags")).SetItems((List)items));
   }

   public static void RemoveListValue(Variable listToChange, CodeValue... value) {
      List items = new ArrayList();
      items.add(listToChange);
      items.addAll(Arrays.asList(value));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("RemoveListValue")).SetItems((List)items));
   }

   public static void ShiftAllDirections(Variable variableToSet, ILocation locationToShift, INumber forwardsChange, INumber upwardsChange, INumber sidewaysChange) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (locationToShift != null) {
         items.add(locationToShift);
      }

      items.add(forwardsChange);
      if (upwardsChange != null) {
         items.add(upwardsChange);
      }

      if (sidewaysChange != null) {
         items.add(sidewaysChange);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("ShiftAllDirections")).SetItems((List)items));
   }

   public static void SetCanPlaceOn(Variable variableToSet, IItem itemToChange, CodeValue... placeableBlocks) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.addAll(Arrays.asList(placeableBlocks));
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetCanPlaceOn")).SetItems((List)items));
   }

   public static void GetBlockType(Variable variableToSet, ILocation blockLocation, Tags.ReturnValueType returnValueTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(blockLocation);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetBlockType")).SetItems((List)items).SetTags(new Tag("Return Value Type", returnValueTypeTag.getJSONValue())));
   }

   public static void GetDictValue(Variable variableToSet, Variable dictionaryTo, IText key) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(dictionaryTo);
      items.add(key);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetDictValue")).SetItems((List)items));
   }

   public static void ContainerLock(Variable variableToSet, ILocation containerLocation) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(containerLocation);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("ContainerLock")).SetItems((List)items));
   }

   public static void GetBlockPower(Variable variableToSet, ILocation blockLocation) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(blockLocation);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetBlockPower")).SetItems((List)items));
   }

   public static void GetVectorLength(Variable variableToSet, IVector vectorToGet, Tags.LengthType lengthTypeTag) {
      List items = new ArrayList();
      items.add(variableToSet);
      items.add(vectorToGet);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetVectorLength")).SetItems((List)items).SetTags(new Tag("Length Type", lengthTypeTag.getJSONValue())));
   }

   public static void SetModelData(Variable variableToSet, IItem itemToChange, INumber modelValue) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (itemToChange != null) {
         items.add(itemToChange);
      }

      items.add(modelValue);
      CodeManager.instance.addCodeBlock((new SetVariableBlock("SetModelData")).SetItems((List)items));
   }

   public static void GetParticleSize(Variable variableToSet, IParticle effectToGet) {
      List items = new ArrayList();
      items.add(variableToSet);
      if (effectToGet != null) {
         items.add(effectToGet);
      }

      CodeManager.instance.addCodeBlock((new SetVariableBlock("GetParticleSize")).SetItems((List)items));
   }
}

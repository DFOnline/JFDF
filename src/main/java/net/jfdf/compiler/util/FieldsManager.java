package net.jfdf.compiler.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.jfdf.compiler.JFDFCompiler;

public class FieldsManager {
   private static final Map fieldIndices = new HashMap();
   private static final Map fieldDefaultValues = new HashMap();

   public static void analyzeClassFields(Class class_) {
      if (!fieldIndices.containsKey(class_)) {
         JFDFCompiler.analyzeClass(class_);
         HashMap classFieldIndices;
         if (class_.getSuperclass() != Object.class && class_.getSuperclass() != Enum.class) {
            analyzeClassFields(class_.getSuperclass());
            classFieldIndices = new HashMap((Map)fieldIndices.get(class_.getSuperclass()));
         } else {
            classFieldIndices = new HashMap();
         }

         int firstFieldIndex = classFieldIndices.size();
         firstFieldIndex += !Modifier.isFinal(class_.getModifiers()) || class_.getSuperclass() != Object.class && class_.getSuperclass() != Enum.class ? 1 : 0;
         int i = 1;
         Field[] var4 = class_.getDeclaredFields();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            if (!Modifier.isStatic(field.getModifiers())) {
               classFieldIndices.put(field.getName(), firstFieldIndex + i);
               ++i;
            }
         }

         fieldIndices.put(class_, classFieldIndices);
      }
   }

   public static int getFieldIndex(Class class_, String name) {
      if (!fieldIndices.containsKey(class_)) {
         analyzeClassFields(class_);
      }

      return (Integer)((Map)fieldIndices.get(class_)).get(name);
   }

   public static boolean hasFieldWithDefaultValue(Class class_) {
      if (!fieldDefaultValues.containsKey(class_)) {
         fieldDefaultValues.put(class_, new HashMap());
      }

      return ((Map)fieldDefaultValues.get(class_)).values().stream().anyMatch(Objects::nonNull);
   }

   public static Map getClassDefaultValues(Class class_) {
      if (!fieldDefaultValues.containsKey(class_)) {
         fieldDefaultValues.put(class_, new HashMap());
      }

      return (Map)fieldDefaultValues.get(class_);
   }

   public static void setFieldDefaultValue(Class class_, String name, Object defaultValue) {
      if (!fieldDefaultValues.containsKey(class_)) {
         fieldDefaultValues.put(class_, new HashMap());
      }

      ((Map)fieldDefaultValues.get(class_)).put(name, defaultValue);
   }
}

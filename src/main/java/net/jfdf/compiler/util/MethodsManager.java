package net.jfdf.compiler.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodsManager {
   private static final Map methodInstructionDataLists = new HashMap();
   private static final List classesWithClinit = new ArrayList();

   public static void addMethodInstructions(Class class_, String methodName, String methodDescriptor, List instructionDataList) {
      if (!methodInstructionDataLists.containsKey(class_)) {
         methodInstructionDataLists.put(class_, new HashMap());
      }

      ((Map)methodInstructionDataLists.get(class_)).put(methodName + methodDescriptor, instructionDataList);
   }

   public static List getMethodInstructions(Class class_, String methodName, String methodDescriptor) {
      return (List)((Map)methodInstructionDataLists.get(class_)).get(methodName + methodDescriptor);
   }

   public static boolean hasStaticInitializer(Class class_) {
      return classesWithClinit.contains(class_);
   }

   public static void enableHasStaticInitializer(Class class_) {
      classesWithClinit.add(class_);
   }
}

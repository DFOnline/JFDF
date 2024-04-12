package net.jfdf.compiler.addon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.jfdf.jfdf.values.Variable;
import org.objectweb.asm.Handle;

public class CompilerAddons {
   private static final List addons = new ArrayList();
   private static TempVariableCallback tempVariableCallback;
   private static List instructionDataList;
   private static int instructionIndex = -1;

   public static void registerAddon(ICompilerAddon addon) {
      addons.add(addon);
   }

   public static void unregisterAddon(ICompilerAddon addon) {
      addons.remove(addon);
   }

   public static boolean publishInitClassEvent(String type, List stack) {
      Iterator var2 = addons.iterator();

      ICompilerAddon addon;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         addon = (ICompilerAddon)var2.next();
      } while(!addon.onInitClass(type, stack));

      return true;
   }

   public static boolean publishInvokeConstructorEvent(String type, String descriptor, List stack) {
      Iterator var3 = addons.iterator();

      ICompilerAddon addon;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         addon = (ICompilerAddon)var3.next();
      } while(!addon.onInvokeConstructor(type, descriptor, stack));

      return true;
   }

   public static boolean publishInvokeDynamicEvent(String name, String descriptor, Handle methodHandle, Object[] methodArgs, List stack) {
      Iterator var5 = addons.iterator();

      ICompilerAddon addon;
      do {
         if (!var5.hasNext()) {
            return false;
         }

         addon = (ICompilerAddon)var5.next();
      } while(!addon.onInvokeDynamic(name, descriptor, methodHandle, methodArgs, stack));

      return true;
   }

   public static boolean publishInvokeMemberEvent(String owner, String name, String descriptor, List stack) {
      Iterator var4 = addons.iterator();

      ICompilerAddon addon;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         addon = (ICompilerAddon)var4.next();
      } while(!addon.onInvokeMember(owner, name, descriptor, stack));

      return true;
   }

   public static boolean publishInvokeStaticEvent(String owner, String name, String descriptor, List stack) {
      Iterator var4 = addons.iterator();

      ICompilerAddon addon;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         addon = (ICompilerAddon)var4.next();
      } while(!addon.onInvokeStatic(owner, name, descriptor, stack));

      return true;
   }

   public static boolean publishGetFieldEvent(boolean isStatic, String owner, String name, String descriptor, List stack) {
      Iterator var5 = addons.iterator();

      ICompilerAddon addon;
      do {
         if (!var5.hasNext()) {
            return false;
         }

         addon = (ICompilerAddon)var5.next();
      } while(!addon.onGetField(isStatic, owner, name, descriptor, stack));

      return true;
   }

   public static IfHandler publishIfEvent(String defaultType, boolean invert, List stack) {
      Iterator var3 = addons.iterator();

      IfHandler ifHandler;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         ICompilerAddon addon = (ICompilerAddon)var3.next();
         ifHandler = addon.onIf(defaultType, invert, stack);
      } while(ifHandler == null);

      return ifHandler;
   }

   public static void setTempVariableCallback(TempVariableCallback callback) {
      tempVariableCallback = callback;
   }

   public static void setInstructionDataList(List list) {
      if (list == null) {
         instructionDataList = null;
      } else {
         instructionDataList = Collections.unmodifiableList(list);
      }
   }

   public static void setInstructionIndex(int index) {
      instructionIndex = index;
   }

   public static Variable getTempVariable() {
      return tempVariableCallback.getTempVariable();
   }

   public static List getInstructionDataList() {
      return instructionDataList;
   }

   public static int getInstructionIndex() {
      return instructionIndex;
   }

   @FunctionalInterface
   public interface TempVariableCallback {
      Variable getTempVariable();
   }
}

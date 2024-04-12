package net.jfdf.jfdf;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.jfdf.jfdf.blocks.CodeBlock;
import net.jfdf.jfdf.blocks.CodeHeader;

public class JFDFAddon {
   private static List allAddons = new ArrayList();

   private static void onStart() {
      Iterator var0 = allAddons.iterator();

      while(var0.hasNext()) {
         JFDFAddon jfdfAddon = (JFDFAddon)var0.next();

         try {
            jfdfAddon.getClass().getMethod("onStart").invoke(jfdfAddon);
         } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var3) {
         }
      }

   }

   private static void onHeaderStart() {
      Iterator var0 = allAddons.iterator();

      while(var0.hasNext()) {
         JFDFAddon jfdfAddon = (JFDFAddon)var0.next();

         try {
            jfdfAddon.getClass().getMethod("onHeaderStart").invoke(jfdfAddon);
         } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var3) {
         }
      }

   }

   private static void onHeaderEnd(CodeHeader codeHeader, List codeBlocks) {
      Iterator var2 = allAddons.iterator();

      while(var2.hasNext()) {
         JFDFAddon jfdfAddon = (JFDFAddon)var2.next();

         try {
            jfdfAddon.getClass().getMethod("onHeaderEnd", CodeHeader.class, List.class).invoke(jfdfAddon, codeHeader, codeBlocks);
         } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var5) {
         }
      }

   }

   private static void onAddCodeBlock(CodeBlock codeBlock) {
      Iterator var1 = allAddons.iterator();

      while(var1.hasNext()) {
         JFDFAddon jfdfAddon = (JFDFAddon)var1.next();

         try {
            jfdfAddon.getClass().getMethod("onAddCodeBlock", CodeBlock.class).invoke(jfdfAddon, codeBlock);
         } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var4) {
         }
      }

   }

   private static void onGetHeaderValue(String value) {
      Iterator var1 = allAddons.iterator();

      while(var1.hasNext()) {
         JFDFAddon jfdfAddon = (JFDFAddon)var1.next();

         try {
            jfdfAddon.getClass().getMethod("onAddCodeBlock", String.class).invoke(jfdfAddon, value);
         } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var4) {
         }
      }

   }

   public static void addJFDFAddons(Collection addons) {
      allAddons.addAll(addons);
   }
}

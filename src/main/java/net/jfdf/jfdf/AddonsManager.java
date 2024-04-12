package net.jfdf.jfdf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.jfdf.jfdf.blocks.CodeHeader;

public final class AddonsManager {
   private static final List addons = new ArrayList();

   public static void registerAddon(IBlocksAddon addon) {
      addons.add(addon);
   }

   public static void unregisterAddon(IBlocksAddon addon) {
      addons.remove(addon);
   }

   public static Map publishPreGenerateLine(CodeHeader codeHeader, List blocks) {
      Map result = new LinkedHashMap();
      Iterator var3 = addons.iterator();

      while(var3.hasNext()) {
         IBlocksAddon addon = (IBlocksAddon)var3.next();
         Map addonResult = addon.onPreGenerateLine(codeHeader, blocks);
         if (addonResult != null) {
            result.putAll(addonResult);
         }
      }

      return result;
   }
}

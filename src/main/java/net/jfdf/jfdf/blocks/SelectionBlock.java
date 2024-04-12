package net.jfdf.jfdf.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.jfdf.jfdf.mangement.SubIf;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Tag;

public class SelectionBlock implements CodeBlock {
   private List items = new ArrayList();
   private List tags = new ArrayList();
   private final String selection;
   private boolean inverseIf = false;
   private SubIf.SubIfType ifBlock = null;

   /** @deprecated */
   @Deprecated
   public SelectionBlock(Selection selection) {
      this.selection = selection.getJSONValue();
      if (selection.getValue() >= 18 && selection.getValue() <= 21) {
         throw new IllegalArgumentException("You can't put if selection without if !");
      }
   }

   /** @deprecated */
   @Deprecated
   public SelectionBlock(Selection selection, SubIf.SubIfType ifBlock) {
      this.selection = selection.getJSONValue();
      this.ifBlock = ifBlock;
      if (selection.getValue() < 18 || selection.getValue() > 21) {
         throw new IllegalArgumentException("You can't put normal selection with if !");
      }
   }

   public SelectionBlock(String selection) {
      this.selection = selection;
   }

   public SelectionBlock(String selection, SubIf.SubIfType ifBlock) {
      this.selection = selection;
      this.ifBlock = ifBlock;
   }

   public SelectionBlock SetItems(List items) {
      this.items = items;
      return this;
   }

   public SelectionBlock SetItems(CodeValue... items) {
      this.items = Arrays.asList(items);
      return this;
   }

   public SelectionBlock SetTags(Tag... tags) {
      this.tags = Arrays.asList(tags);
      Tag[] var2 = tags;
      int var3 = tags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Tag tag = var2[var4];
         if (this.ifBlock == null) {
            tag.setBlock("select_obj");
            tag.setAction(this.selection);
         } else {
            tag.setBlock(this.ifBlock.getIfBlock());
            tag.setAction(this.ifBlock.getNormalJSONValue());
         }
      }

      return this;
   }

   public String asJSON() {
      String json = "{\"id\":\"block\",\"block\":\"select_obj\",\"args\":{\"items\":[";
      List itemsJSON = new ArrayList();
      if (this.tags.size() > 9) {
         this.tags = this.tags.subList(0, 8);
      }

      if (this.items.size() > 27 - this.tags.size()) {
         this.items = this.items.subList(0, 26 - this.tags.size());
      }

      String var10001;
      int i;
      for(i = 0; i < this.items.size(); ++i) {
         CodeValue codeValue = (CodeValue)this.items.get(i);
         var10001 = codeValue.asJSON();
         itemsJSON.add("{\"item\":" + var10001 + ",\"slot\":" + i + "}");
      }

      for(i = 26; i >= 27 - this.tags.size(); --i) {
         Tag tag = (Tag)this.tags.get(26 - i);
         var10001 = tag.asJSON();
         itemsJSON.add("{\"item\":" + var10001 + ",\"slot\":" + i + "}");
      }

      json = json + String.join(",", itemsJSON);
      json = json + "]},\"action\":\"" + this.selection + "\"";
      if (this.ifBlock != null) {
         json = json + ",\"subAction\":\"" + this.ifBlock.getSubJSONValue() + "\"" + (this.inverseIf ? ",\"inverted\":\"NOT\"" : "");
      }

      json = json + "}";
      return json;
   }

   /** @deprecated */
   @Deprecated
   public static enum Selection {
      DEFAULT_PLAYER("DefaultPlayer"),
      DEFAULT_ENTITY("DefaultEntity"),
      RANDOM_PLAYER("RandomPlayer"),
      RANDOM_MOB("RandomMob"),
      RANDOM_ENTITY("RandomEntity"),
      ALL_PLAYERS("AllPlayers"),
      ALL_MOBS("AllMobs"),
      ALL_ENTITIES("AllEntities"),
      LAST_SPAWNED_MOB("LastMob"),
      LAST_SPAWNED_ENTITY("LastEntity"),
      KILLER("Killer"),
      DAMAGER("Damager"),
      SHOOTER("Shooter"),
      VICTIM("Victim"),
      PROJECTILE("Projectile"),
      PLAYER_NAME("PlayerName"),
      MOBS_NAME("MobName"),
      ENTITIES_NAME("EntityName"),
      PLAYERS_CONDITION("PlayersCond"),
      MOBS_CONDITION("MobsCond"),
      ENTITIES_CONDITION("EntitiesCond"),
      FILTER_SELECTION_CONDITION("FilterSelect"),
      FILTER_SELECTION_RANDOM("RandomSelected"),
      NONE("None");

      private static final Map values = new HashMap();
      private int value;
      private final String jsonValue;

      private Selection(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public static Selection valueOf(int Selection) {
         return (Selection)values.get(Selection);
      }

      public int getValue() {
         return this.value;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }

      static {
         Selection[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Selection selection = var0[var2];
            selection.value = values.size();
            values.put(selection.getValue(), selection);
         }

      }
   }
}

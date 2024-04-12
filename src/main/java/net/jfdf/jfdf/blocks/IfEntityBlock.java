package net.jfdf.jfdf.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Tag;

public class IfEntityBlock implements CodeBlock {
   private List items = new ArrayList();
   private List tags = new ArrayList();
   private EntityActionBlock.EntitySelection entitySelection;
   private boolean inverseIf = false;
   private Type type;

   public IfEntityBlock(Type type, EntityActionBlock.EntitySelection entitySelection) {
      this.type = type;
      this.entitySelection = entitySelection;
   }

   public IfEntityBlock(Type type, EntityActionBlock.EntitySelection entitySelection, boolean inverseIf) {
      this.type = type;
      this.entitySelection = entitySelection;
      this.inverseIf = inverseIf;
   }

   public IfEntityBlock SetItems(List items) {
      this.items = items;
      return this;
   }

   public IfEntityBlock SetItems(CodeValue... items) {
      this.items = Arrays.asList(items);
      return this;
   }

   public IfEntityBlock SetTags(Tag... tags) {
      this.tags = Arrays.asList(tags);
      Tag[] var2 = tags;
      int var3 = tags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Tag tag = var2[var4];
         tag.setBlock("if_entity");
         tag.setAction(this.type.getJSONValue());
      }

      return this;
   }

   public String asJSON() {
      String json = "{\"id\":\"block\",\"block\":\"if_entity\",\"args\":{\"items\":[";
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
      json = json + "]},\"action\":\"" + this.type.getJSONValue() + "\",\"selection\":\"" + this.entitySelection.getJSONValue() + "\"" + (this.inverseIf ? ",\"inverted\":\"NOT\"" : "") + "}";
      return json;
   }

   public static enum Type {
      IS_TYPE("IsType"),
      NAME_EQUALS("NameEquals"),
      IS_STANDING_ON("StandingOn"),
      IS_GROUNDED("IsGrounded"),
      IS_NEAR("IsNear"),
      IS_RIDING("IsRiding"),
      IS_MOB("IsMob"),
      IS_PROJECTILE("IsProj"),
      IS_VECHILE("IsVechile"),
      IS_ITEM("IsItem"),
      EXISTS("Exists"),
      HAS_CUSTOM_TAG("HasCustomTag");

      private static final Map values = new HashMap();
      private int value;
      private final String jsonValue;

      private Type(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public static Type valueOf(int type) {
         return (Type)values.get(type);
      }

      public int getValue() {
         return this.value;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }

      static {
         Type[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Type type = var0[var2];
            type.value = values.size();
            values.put(type.getValue(), type);
         }

      }
   }
}

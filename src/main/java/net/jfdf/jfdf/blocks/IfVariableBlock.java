package net.jfdf.jfdf.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Tag;

public class IfVariableBlock implements CodeBlock {
   private List items = new ArrayList();
   private List tags = new ArrayList();
   private boolean inverseIf = false;
   private final String type;

   public IfVariableBlock(Type type) {
      this.type = type.getJSONValue();
   }

   public IfVariableBlock(Type type, boolean inverseIf) {
      this.type = type.getJSONValue();
      this.inverseIf = inverseIf;
   }

   public IfVariableBlock(String type, boolean inverseIf) {
      this.type = type;
      this.inverseIf = inverseIf;
   }

   public IfVariableBlock SetItems(List items) {
      this.items = items;
      return this;
   }

   public IfVariableBlock SetItems(CodeValue... items) {
      this.items = Arrays.asList(items);
      return this;
   }

   public IfVariableBlock SetTags(Tag... tags) {
      this.tags = Arrays.asList(tags);
      Tag[] var2 = tags;
      int var3 = tags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Tag tag = var2[var4];
         tag.setBlock("if_var");
         tag.setAction(this.type);
      }

      return this;
   }

   public String asJSON() {
      String json = "{\"id\":\"block\",\"block\":\"if_var\",\"args\":{\"items\":[";
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
      json = json + "]},\"action\":\"" + this.type + (this.inverseIf ? "\",\"inverted\":\"NOT\"" : "\"") + "}";
      return json;
   }

   public static enum Type {
      EQUALS("="),
      NOT_EQUALS("!="),
      GREATER_THAN(">"),
      GREATER_THAN_OR_EQUAL_TO(">="),
      LESS_THAN("<"),
      LESS_THAN_OR_EQUAL_TO("<="),
      IN_RANGE("InRange"),
      IS_NEAR("IsNear"),
      LOC_IS_NEAR("LocIsNear"),
      TEXT_MATCHES("TextMatches"),
      TEXT_CONTAINS("Contains"),
      TEXT_STARTS_WITH("StartsWith"),
      TEXT_ENDS_WITH("EndsWith"),
      EXISTS("VarExists"),
      VARIABLE_IS_TYPE("VarIsType"),
      ITEM_EQUALS("ItemEquals"),
      ITEM_HAS_TAG("ItemHasTag"),
      LIST_CONTAINS_VALUE("ListContains"),
      LIST_VALUE_EQUALS("ListValueEq");

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

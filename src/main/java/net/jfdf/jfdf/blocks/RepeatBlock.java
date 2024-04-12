package net.jfdf.jfdf.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.jfdf.jfdf.mangement.SubIf;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Tag;

public class RepeatBlock implements CodeBlock {
   private List items = new ArrayList();
   private List tags = new ArrayList();
   private final Type repeatType;
   private boolean inverseIf = false;
   private String ifType = null;

   public RepeatBlock(Type repeatType) {
      this.repeatType = repeatType;
   }

   public RepeatBlock(SubIf.SubIfType ifBlock) {
      this.inverseIf = false;
      this.ifType = ifBlock.getSubJSONValue();
      this.repeatType = RepeatBlock.Type.WHILE;
   }

   public RepeatBlock(SubIf.SubIfType ifBlock, boolean inverseIf) {
      this.ifType = ifBlock.getSubJSONValue();
      this.repeatType = RepeatBlock.Type.WHILE;
      this.inverseIf = inverseIf;
   }

   public RepeatBlock(String ifType, boolean inverseIf) {
      this.ifType = ifType;
      this.repeatType = RepeatBlock.Type.WHILE;
      this.inverseIf = inverseIf;
   }

   public RepeatBlock SetItems(List items) {
      this.items = items;
      return this;
   }

   public RepeatBlock SetItems(CodeValue... items) {
      this.items = Arrays.asList(items);
      return this;
   }

   public RepeatBlock SetTags(List tags) {
      this.tags = tags;
      String actionValue = this.repeatType.getJSONValue();
      Iterator var3 = tags.iterator();

      while(var3.hasNext()) {
         Tag tag = (Tag)var3.next();
         if (this.ifType == null) {
            tag.setBlock("repeat");
            tag.setAction(actionValue);
         }
      }

      return this;
   }

   public RepeatBlock SetTags(Tag... tags) {
      this.tags = Arrays.asList(tags);
      String actionValue = this.repeatType.getJSONValue();
      Tag[] var3 = tags;
      int var4 = tags.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Tag tag = var3[var5];
         if (this.ifType == null) {
            tag.setBlock("repeat");
            tag.setAction(actionValue);
         }
      }

      return this;
   }

   public String asJSON() {
      String actionValue = this.repeatType.getJSONValue();
      String json = "{\"id\":\"block\",\"block\":\"repeat\",\"args\":{\"items\":[";
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

      for(i = 26; i < this.tags.size(); --i) {
         Tag tag = (Tag)this.tags.get(i);
         var10001 = tag.asJSON();
         itemsJSON.add("{\"item\":" + var10001 + ",\"slot\":" + i + "}");
      }

      json = json + String.join(",", itemsJSON);
      json = json + "]},\"action\":\"" + actionValue + "\"";
      if (actionValue.equals("While")) {
         json = json + ",\"subAction\":\"" + this.ifType + "\"" + (this.inverseIf ? ",\"inverted\":\"NOT\"" : "");
      }

      json = json + "}";
      return json;
   }

   public static enum Type {
      FOREVER("Forever"),
      WHILE("While"),
      MULTIPLE_TIMES("Multiple"),
      ON_RANGE("Range"),
      FOREACH("ForEach"),
      FOREACH_ENTRY("ForEachEntry"),
      ON_GRID("Grid"),
      ADJACENTLY("Adjacent"),
      ON_SPHERE("SPHERE");

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

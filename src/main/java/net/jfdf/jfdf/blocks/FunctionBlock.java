package net.jfdf.jfdf.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Tag;

public class FunctionBlock implements CodeHeader {
   private List items = new ArrayList();
   private List tags = new ArrayList();
   private final String name;

   public FunctionBlock(String name) {
      this.name = name;
   }

   public FunctionBlock SetTags(Tag... tags) {
      this.tags = Arrays.asList(tags);
      Tag[] var2 = tags;
      int var3 = tags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Tag tag = var2[var4];
         tag.setAction("dynamic");
         tag.setBlock("func");
      }

      return this;
   }

   public List GetItems() {
      return new ArrayList(this.items);
   }

   public FunctionBlock SetItems(CodeValue... items) {
      this.items = Arrays.asList(items);
      return this;
   }

   public FunctionBlock SetItems(List items) {
      this.items = items;
      return this;
   }

   public String asJSON() {
      String json = "{\"id\":\"block\",\"block\":\"func\",\"args\":{\"items\":[";
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
      json = json + "]},\"data\":\"" + this.name + "\"}";
      return json;
   }

   public String getName() {
      return this.name;
   }

   public String getTemplateName() {
      return "Function آ» " + this.name;
   }

   public String getTemplateNameWithColors() {
      return "§b§lFunction §3آ» §b" + this.name;
   }
}

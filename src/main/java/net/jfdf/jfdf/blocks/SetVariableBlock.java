package net.jfdf.jfdf.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Tag;

public class SetVariableBlock implements CodeBlock {
   public List items = new ArrayList();
   public List tags = new ArrayList();
   public String type;

   public SetVariableBlock(String type) {
      this.type = type;
   }

   public static SetVariableBlock New(String type) {
      return new SetVariableBlock(type);
   }

   public SetVariableBlock SetItems(List items) {
      this.items = items;
      return this;
   }

   public SetVariableBlock SetItems(CodeValue... items) {
      this.items = Arrays.asList(items);
      return this;
   }

   public SetVariableBlock SetTags(Tag... tags) {
      this.tags = Arrays.asList(tags);
      Tag[] var2 = tags;
      int var3 = tags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Tag tag = var2[var4];
         tag.setBlock("set_var");
         tag.setAction(this.type);
      }

      return this;
   }

   public String asJSON() {
      String json = "{\"id\":\"block\",\"block\":\"set_var\",\"args\":{\"items\":[";
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

      for(i = 0; i < this.tags.size(); ++i) {
         Tag tag = (Tag)this.tags.get(i);
         var10001 = tag.asJSON();
         itemsJSON.add("{\"item\":" + var10001 + ",\"slot\":" + (26 - i) + "}");
      }

      json = json + String.join(",", itemsJSON);
      json = json + "]},\"action\":\"" + this.type + "\"}";
      return json;
   }
}

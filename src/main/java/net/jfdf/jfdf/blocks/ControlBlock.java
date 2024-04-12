package net.jfdf.jfdf.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.Tag;

public class ControlBlock implements CodeBlock {
   private List items = new ArrayList();
   private List tags = new ArrayList();
   private final String action;

   /** @deprecated */
   @Deprecated
   public ControlBlock(Action action) {
      this.action = action.getJSONValue();
   }

   public ControlBlock(String action) {
      this.action = action;
   }

   /** @deprecated */
   @Deprecated
   public static ControlBlock New(Action action) {
      return new ControlBlock(action);
   }

   public static ControlBlock New(String action) {
      return new ControlBlock(action);
   }

   public ControlBlock SetItems(CodeValue... items) {
      this.items = Arrays.asList(items);
      return this;
   }

   public ControlBlock SetItems(List items) {
      this.items = items;
      return this;
   }

   public ControlBlock SetTags(Tag... tags) {
      this.tags = Arrays.asList(tags);
      Tag[] var2 = tags;
      int var3 = tags.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Tag tag = var2[var4];
         tag.setAction(this.action);
         tag.setBlock("control");
      }

      return this;
   }

   public String asJSON() {
      String json = "{\"id\":\"block\",\"block\":\"control\",\"args\":{\"items\":[";
      if (this.items.size() >= 1 || this.tags.size() >= 1) {
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
      }

      json = json + "]},\"action\":\"" + this.action + "\"}";
      return json;
   }

   public String getAction() {
      return this.action;
   }

   /** @deprecated */
   @Deprecated
   public static enum Action {
      WAIT(0, "Wait"),
      RETURN(1, "Return"),
      END_SEQUENCE(2, "End"),
      SKIP_ITREATION(3, "Skip"),
      STOP_REPEATING(4, "StopRepeat");

      private static final Map values = new HashMap();
      private final int value;
      private final String jsonValue;

      private Action(int value, String jsonValue) {
         this.value = value;
         this.jsonValue = jsonValue;
      }

      public static Action valueOf(int type) {
         return (Action)values.get(type);
      }

      public int getValue() {
         return this.value;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }

      static {
         Action[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Action type = var0[var2];
            values.put(type.getValue(), type);
         }

      }
   }
}

package net.jfdf.addon.splitter;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.jfdf.jfdf.blocks.CodeBlock;

public class Scope implements CodeBlock {
   protected List<CodeBlock> content;
   protected int totalLength;

   public Scope(List content) {
      this.content = content;
      this.totalLength = 2;
      Iterator var2 = content.iterator();

      while(var2.hasNext()) {
         CodeBlock block = (CodeBlock)var2.next();
         if (block instanceof Scope) {
            this.totalLength += ((Scope)block).totalLength;
         } else {
            this.totalLength += 2;
         }
      }

   }

   public String asJSON() {
      return (String)this.content.stream().map(CodeBlock::asJSON).collect(Collectors.joining(","));
   }

   public List getContent() {
      return this.content;
   }
}

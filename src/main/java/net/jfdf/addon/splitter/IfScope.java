package net.jfdf.addon.splitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.jfdf.jfdf.blocks.CodeBlock;

public class IfScope extends Scope {
   private int elseIndex;

   public IfScope(CodeBlock ifBlock, CodeBlock ifStart, List content, CodeBlock[] elseBlocks, List elseContent, CodeBlock ifEnd) {
      super(new ArrayList());
      this.elseIndex = -1;
      if (elseBlocks != null ^ elseContent != null) {
         throw new IllegalStateException("Both of else blocks and else content should be null or not null.");
      } else {
         this.totalLength = 4;
         if (elseBlocks != null) {
            if (elseBlocks.length != 3) {
               throw new IllegalStateException("Else blocks array should have 3 blocks only.");
            }

            this.totalLength += 4;
         }

         this.content.add(ifBlock);
         this.content.add(ifStart);
         this.content.addAll(content);
         if (elseBlocks != null) {
            this.elseIndex = this.content.size();
            this.content.addAll(Arrays.asList(elseBlocks));
            this.content.addAll(elseContent);
         }

         this.content.add(ifEnd);
         Iterator var7 = content.iterator();

         CodeBlock block;
         while(var7.hasNext()) {
            block = (CodeBlock)var7.next();
            if (block instanceof Scope) {
               this.totalLength += ((Scope)block).totalLength;
            } else {
               this.totalLength += 2;
            }
         }

         if (elseContent != null) {
            var7 = elseContent.iterator();

            while(var7.hasNext()) {
               block = (CodeBlock)var7.next();
               if (block instanceof Scope) {
                  this.totalLength += ((Scope)block).totalLength;
               } else {
                  this.totalLength += 2;
               }
            }
         }

      }
   }

   public IfScope(CodeBlock ifBlock, CodeBlock ifStart, List content, CodeBlock ifEnd) {
      this(ifBlock, ifStart, content, (CodeBlock[])null, (List)null, ifEnd);
   }

   public List getContent() {
      return this.elseIndex != -1 ? this.content.subList(2, this.elseIndex) : this.content.subList(2, this.content.size() - 1);
   }

   public List getElseContent() {
      return this.content.subList(this.elseIndex + 3, this.content.size() - 1);
   }

   public List getStart() {
      return this.content.subList(0, 2);
   }

   public List getElse() {
      return this.content.subList(this.elseIndex, this.elseIndex + 3);
   }

   public CodeBlock getEnd() {
      return (CodeBlock)this.content.get(this.content.size() - 1);
   }

   public int getMinimumLength() {
      return this.elseIndex == -1 ? 6 : 12;
   }

   public boolean hasElse() {
      return this.elseIndex != -1;
   }
}

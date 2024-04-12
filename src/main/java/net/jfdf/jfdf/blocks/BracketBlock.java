package net.jfdf.jfdf.blocks;

public class BracketBlock implements CodeBlock {
   public boolean isClose;
   public boolean isRepeat;

   public BracketBlock(boolean isClose, boolean isRepeat) {
      this.isClose = isClose;
      this.isRepeat = isRepeat;
   }

   public String asJSON() {
      String var10000 = this.isClose ? "close" : "open";
      return "{\"id\":\"bracket\",\"direct\":\"" + var10000 + "\",\"type\":\"" + (this.isRepeat ? "repeat" : "norm") + "\"}";
   }
}

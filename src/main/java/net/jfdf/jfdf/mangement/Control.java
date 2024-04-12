package net.jfdf.jfdf.mangement;

import java.util.ArrayList;
import java.util.List;
import net.jfdf.jfdf.blocks.ControlBlock;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Tag;
import net.jfdf.jfdf.values.Tags;

public class Control {
   public static void StopRepeat() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new ControlBlock("StopRepeat")).SetItems((List)items));
   }

   public static void Return() {
      CodeManager.instance.addCodeBlock(new ControlBlock("Return"));
   }

   public static void ReturnNTimes(INumber returnTimes) {
      CodeManager.instance.addCodeBlock((new ControlBlock("ReturnNTimes")).SetItems(returnTimes));
   }

   public static void Skip() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new ControlBlock("Skip")).SetItems((List)items));
   }

   public static void End() {
      List items = new ArrayList();
      CodeManager.instance.addCodeBlock((new ControlBlock("End")).SetItems((List)items));
   }

   public static void Wait(INumber waitDuration, Tags.TimeUnit timeUnitTag) {
      List items = new ArrayList();
      if (waitDuration != null) {
         items.add(waitDuration);
      }

      CodeManager.instance.addCodeBlock((new ControlBlock("Wait")).SetItems((List)items).SetTags(new Tag("Time Unit", timeUnitTag.getJSONValue())));
   }
}

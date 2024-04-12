package net.jfdf.transformer;

import java.io.PrintStream;
import net.jfdf.jfdf.mangement.Player;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Text;

/** @deprecated */
@Deprecated(
   forRemoval = true
)
public class JFDFSysOut {
   public static void println(PrintStream printStream, Object object) {
      if (object instanceof CodeValue) {
         if (printStream == System.out) {
            Player.getCurrentSelection().SendMessage(new IText[]{(IText)object}, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.ADD_SPACES);
         } else {
            if (printStream != System.err) {
               throw new IllegalStateException("Illegal PrintStream (Isn't System.out or System.err)");
            }

            Player.getCurrentSelection().SendMessage(new IText[]{(new Text()).Set("§c"), (IText)object}, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.ADD_SPACES);
         }
      } else {
         printStream.println(object);
      }

   }
}

package net.jfdf.compiler.addon;

import java.util.List;
import org.objectweb.asm.Handle;

public interface ICompilerAddon {
   default boolean onInitClass(String type, List stack) {
      return false;
   }

   default boolean onInvokeConstructor(String type, String descriptor, List stack) {
      return false;
   }

   default boolean onInvokeDynamic(String name, String descriptor, Handle methodHandle, Object[] methodArgs, List stack) {
      return false;
   }

   default boolean onInvokeMember(String owner, String name, String descriptor, List stack) {
      return false;
   }

   default boolean onInvokeStatic(String owner, String name, String descriptor, List stack) {
      return false;
   }

   default boolean onGetField(boolean isStatic, String owner, String name, String descriptor, List stack) {
      return false;
   }

   default IfHandler onIf(String defaultType, boolean invert, List stack) {
      return null;
   }
}

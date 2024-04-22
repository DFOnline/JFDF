package net.jfdf.compiler.util;

import net.jfdf.compiler.library.References;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.INumber;

public class ReferenceUtils {
    public static boolean isReferenceDescriptor(String descriptor) {
        return descriptor != null
                && (descriptor.startsWith("[") || (descriptor.startsWith("L")
                    && !descriptor.startsWith("Lnet/jfdf/jfdf/values/"))
                    && !descriptor.equals("Ljava/lang/String;"));
    }

    public static void incrementIfReference(String descriptor, CodeValue pointer) {
        if(pointer instanceof INumber && isReferenceDescriptor(descriptor)) References.incrementRefCount((INumber) pointer);
    }

    public static void decrementIfReference(String descriptor, CodeValue pointer) {
        if(pointer instanceof INumber && isReferenceDescriptor(descriptor)) References.decrementRefCount((INumber) pointer);
    }
}

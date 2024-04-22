package net.jfdf.addon.interaction.npe.property;

import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.Tags;

import java.util.List;

public interface IAgeable {
    /**
     * Sets entity's age.
     *
     * @param age New Age
     */
    public default void setAge(int age) {}

    class MethodHandler {
        public static void setAge(List<IStackValue> stack) {
            INumber age = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
            Entity.getCurrentSelection().SetAge(age, Tags.AgeLock.DON_T_CHANGE);
        }
    }
}

package net.jfdf.addon.interaction.npe.villager;

import net.jfdf.addon.interaction.npe.EntityType;
import net.jfdf.addon.interaction.npe.property.IAgeable;
import net.jfdf.addon.interaction.npe.LivingNPE;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.values.INumber;

import java.util.List;

@EntityType(eggId = "villager_spawn_egg")
public class Villager extends LivingNPE implements IVillagerVariant, IAgeable {
    /**
     * Sets villager's experience, which
     * affects villager's level.
     *
     * <table style="width: 100%;">
     *     <caption>Required experiences for levels</caption>
     *     <thead>
     *         <tr><th scope="row">Experience</th>
     *         <th scope="row">Level</th></tr>
     *     </thead>
     *     <tbody>
     *         <tr><th scope="row">0</th>
     *             <td>Novice</td></tr>
     *         <tr><th scope="row">10</th>
     *             <td>Apprentice</td></tr>
     *         <tr><th scope="row">70</th>
     *             <td>Journeyman</td></tr>
     *         <tr><th scope="row">150</th>
     *             <td>Expert</td></tr>
     *         <tr><th scope="row">250</th>
     *             <td>Master</td></tr>
     *     </tbody>
     * </table>
     *
     * @param experience New Experience
     */
    public void setVillagerExperience(int experience) {}

    public static class MethodHandler {
        public void setVillagerExperience(List<IStackValue> stack) {
            INumber experience = (INumber) stack.remove(stack.size() - 1).getTransformedValue();
            Entity.getCurrentSelection().SetVillagerExp(experience);
        }
    }
}

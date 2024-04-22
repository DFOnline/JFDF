package net.jfdf.test.interaction;

import net.jfdf.addon.collections.CollectionsAddon;
import net.jfdf.addon.interaction.InteractionAddon;
import net.jfdf.addon.loc.LocAddon;
import net.jfdf.addon.random.RandomAddon;
import net.jfdf.addon.string.StringAddon;
import net.jfdf.compiler.EasyConfigurableCompiler;
import net.jfdf.compiler.addon.CompilerAddons;

public class InteractionCompiler extends EasyConfigurableCompiler {
    public static void main(String[] args) {
        EasyConfigurableCompiler compiler = new InteractionCompiler();
        compiler.compileAndSend("Interaction Test");
    }

    @Override
    protected void configure() {
        CompilerAddons.registerAddon(new InteractionAddon());
        CompilerAddons.registerAddon(new StringAddon());
        CompilerAddons.registerAddon(new CollectionsAddon());
        CompilerAddons.registerAddon(new RandomAddon());
        CompilerAddons.registerAddon(new LocAddon());
    }
}

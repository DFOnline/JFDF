package net.jfdf.test.sample;

import net.jfdf.addon.interaction.InteractionAddon;
import net.jfdf.addon.math.MathAddon;
import net.jfdf.compiler.EasyConfigurableCompiler;
import net.jfdf.compiler.addon.CompilerAddons;

public class SampleCompiler extends EasyConfigurableCompiler {
    public static void main(String[] args) {
        EasyConfigurableCompiler compiler = new SampleCompiler();
        compiler.compileAndSend("Sample Example");
    }

    @Override
    protected void configure() {
        CompilerAddons.registerAddon(new InteractionAddon());
    }

    @Override
    protected boolean shouldSendLine(LineStarterType lineStarterType, String name) {
//        return isStandardLine(lineStarterType, name) || isInitLine(lineStarterType, name); // Only send the library code
        return !isStandardLine(lineStarterType, name) && !isInitLine(lineStarterType, name); // Only send the test code
    }
}

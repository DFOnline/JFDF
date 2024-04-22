package net.jfdf.test.fib;

import net.jfdf.compiler.EasyConfigurableCompiler;

public class FibonacciCompiler extends EasyConfigurableCompiler {
    public static void main(String[] args) {
        EasyConfigurableCompiler compiler = new FibonacciCompiler();
        compiler.compileAndSend("Fibonacci Example");
    }

    @Override
    protected void configure() {}

    @Override
    protected boolean shouldSendLine(LineStarterType lineStarterType, String name) {
        return !isStandardLine(lineStarterType, name) && !isInitLine(lineStarterType, name);
    }
}

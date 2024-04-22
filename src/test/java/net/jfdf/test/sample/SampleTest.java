package net.jfdf.test.sample;

import net.jfdf.addon.interaction.Plot;
import net.jfdf.addon.interaction.player.Player;
import net.jfdf.jfdf.blocks.PlayerEventBlock;
import net.jfdf.jfdf.mangement.PlayerEvent;
import net.jfdf.jfdf.values.Component;
import net.jfdf.jfdf.values.Sound;
import net.jfdf.jfdf.values.Variable;

public class SampleTest {
    @PlayerEvent(eventType = PlayerEventBlock.Event.JOIN)
    public static void onJoin() {
        Variable output = new Variable("output", Variable.Scope.LINE);
        Player player = Plot.getActiveEvent().getPlayer();
        player.playSound(new Sound("Player Hurt"));

        System.out.println(new Component().Set("Hello, World!"));
        
    }
}

package net.jfdf.test.interaction;

import net.jfdf.addon.interaction.Plot;
import net.jfdf.addon.interaction.npe.zombie.Zombie;
import net.jfdf.addon.interaction.player.Player;
import net.jfdf.addon.loc.Loc;
import net.jfdf.jfdf.blocks.PlayerEventBlock;
import net.jfdf.jfdf.mangement.PlayerEvent;
import net.jfdf.jfdf.values.Location;

import java.util.ArrayList;
import java.util.List;

public class InteractionTest {
    @PlayerEvent(eventType = PlayerEventBlock.Event.JOIN)
    public static void onJoin() {
        Player p = Plot.getActiveEvent().getPlayer();
        Zombie zombie = Plot.getWorld().spawnMob(new Location(10.0f, 50.0f, 10.0f), Zombie.class);

        List<String> list = new ArrayList<>();

        list.add("1st Message");
        list.add("2nd Message");
        list.add("3rd Message");

        String test = "A B";
        String[] result = test.split(" ");

        for (String s : result) {
            p.sendMessage(s);
        }

        for (String message : list) {
            p.sendMessage(message);
        }

        zombie.setBaby(true);
        zombie.setName("Fancy Zombie");

        Loc loc = new Loc(0, 0, 0);
        loc = loc.add(new Loc(10, 10.3, 10))
                .alignToCenter(Loc.AlignAxes.ALL_AXES, false);

        System.out.println(loc);
    }
}

package com.ethan.twfaith.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Divine extends BukkitRunnable {
    // This repeating tasks makes sure players affected by Divine Intervention keep gliding without an elytra.
    Player player;

    public Divine(Player player){
        this.player = player;
    }

    @Override
    public void run() {
        player.setGliding(true);
    }
}

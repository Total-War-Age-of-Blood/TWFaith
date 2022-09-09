package com.ethan.twfaith.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Divine extends BukkitRunnable {
    Player player;

    public Divine(Player player){
        this.player = player;
    }

    @Override
    public void run() {
        player.setGliding(true);
    }
}

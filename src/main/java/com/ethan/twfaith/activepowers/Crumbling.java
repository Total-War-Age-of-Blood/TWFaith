package com.ethan.twfaith.activepowers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class Crumbling implements Listener {
    // TODO implement crumbling

    @EventHandler
    public void onArmorDamage(PlayerItemDamageEvent event){
        Player player = event.getPlayer();
    }
}

package com.ethan.twfaith.data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class BossBars implements Listener {

    public static HashMap<UUID, BossBar> boss_bar_map = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
        // If the player is a god, generate their stamina bar when they log in. The stamina bar will be updated by the
        // Stamina Checker.
        if (player_data.getIn_faith() && player_data.getLeader()){
            BossBar boss_bar = Bukkit.createBossBar(ChatColor.YELLOW + "Stamina", BarColor.YELLOW, BarStyle.SEGMENTED_10);
            boss_bar.setProgress(1);
            boss_bar.addPlayer(player);
            boss_bar_map.put(player.getUniqueId(), boss_bar);
        }
    }
}

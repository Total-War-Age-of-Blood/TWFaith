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
        PlayerData playerData = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        // Fixing a bug where ledBy is null
        if (playerData.getLedBy() == null){playerData.setLedBy(player.getUniqueId());}
        // If the player is a god, generate their stamina bar when they log in. The stamina bar will be updated by the
        // Stamina Checker.
        if (playerData.isInFaith() && playerData.isLeader() && playerData.staminaBarEnabled){
            BossBar bossBar = Bukkit.createBossBar(ChatColor.YELLOW + "Stamina", BarColor.YELLOW, BarStyle.SEGMENTED_10);
            bossBar.setProgress(1);
            bossBar.addPlayer(player);
            boss_bar_map.put(player.getUniqueId(), bossBar);
        }
    }
}

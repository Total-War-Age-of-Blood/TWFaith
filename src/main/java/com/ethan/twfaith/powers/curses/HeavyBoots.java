package com.ethan.twfaith.powers.curses;

import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


import java.util.LinkedHashSet;
import java.util.Objects;

public class HeavyBoots implements Listener {

    public static LinkedHashSet<String> heavy_boots = new LinkedHashSet<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player  = event.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());

        boolean was_victim = player_data.isHeavy_boots_victim();
        // Checks if player should be a victim
        boolean heavy_victim = false;
        for (String heavy_caster : heavy_boots){
            if (player.getLocation().distance(Objects.requireNonNull(Bukkit.getPlayer(heavy_caster)).getLocation()) <= 30){
                heavy_victim = true;
                break;
            }
        }
        if (heavy_victim && player.getInventory().getBoots() != null){player.setWalkSpeed(0.1F);}
        else{player.setWalkSpeed(.2F);}

        // Notifies the player when their victimhood is toggled.
        if (was_victim != heavy_victim){
            if (was_victim){
                player.sendMessage(ChatColor.GREEN + "Your boots are no longer heavy.");
            } else{player.sendMessage(ChatColor.RED + "Your boots weigh you down!");}
        }

        player_data.setHeavy_boots_victim(heavy_victim);
        PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);

    }
}

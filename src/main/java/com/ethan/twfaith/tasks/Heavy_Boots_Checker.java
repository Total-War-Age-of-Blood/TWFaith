package com.ethan.twfaith.tasks;

import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.UUID;

public class Heavy_Boots_Checker extends BukkitRunnable {
    public static LinkedHashSet<UUID> heavy_boots = new LinkedHashSet<>();
    public static LinkedHashSet<UUID> wearing_boots = new LinkedHashSet<>();
    public static LinkedHashSet<UUID> not_wearing_boots = new LinkedHashSet<>();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
            boolean was_victim = player_data.isHeavy_boots_victim();
            // Checks if player should be a victim
            boolean heavy_victim = false;
            for (UUID heavy_caster : heavy_boots){
                Player heavy_player = Bukkit.getPlayer(heavy_caster);
                if (!player.getWorld().equals(heavy_player.getWorld())){continue;}
                if (player.getLocation().distance(Objects.requireNonNull(heavy_player).getLocation()) <= 30 && !heavy_player.getUniqueId().equals(player.getUniqueId())){
                    heavy_victim = true;
                    break;
                }
            }

            // Notifies the player when their victimhood is toggled.
            if (was_victim != heavy_victim){
                if (was_victim){
                    player.sendMessage(ChatColor.GREEN + "Your boots are no longer heavy.");
                    if (wearing_boots.contains(player.getUniqueId())){
                        player.setWalkSpeed(Math.min(player.getWalkSpeed() * 2F, 1F));
                    }
                    player_data.setHeavy_boots_victim(false);
                    PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);
                    return;
                } else{
                    player.sendMessage(ChatColor.RED + "Your boots weigh you down!");
                    if (player.getInventory().getBoots() == null){
                        not_wearing_boots.add(player.getUniqueId());
                        wearing_boots.remove(player.getUniqueId());
                    }else{
                        wearing_boots.add(player.getUniqueId());
                        not_wearing_boots.remove(player.getUniqueId());
                        player.setWalkSpeed(Math.max(player.getWalkSpeed() / 2F, 0F));
                    }
                }
            }

            // I had to rework this area to use relative speed to make it compatible with other plugins.
            // To avoid a progress wipe on my server from adding some booleans to PlayerData, I am making my own
            // booleans with static LinkedHashSets.
            if (heavy_victim && player.getInventory().getBoots() != null && !wearing_boots.contains(player.getUniqueId())){
                player.setWalkSpeed(player.getWalkSpeed() / 2F);
                wearing_boots.add(player.getUniqueId());
                not_wearing_boots.remove(player.getUniqueId());
            } else if (heavy_victim && player.getInventory().getBoots() == null && !not_wearing_boots.contains(player.getUniqueId())) {
                wearing_boots.remove(player.getUniqueId());
                not_wearing_boots.add(player.getUniqueId());
                player.setWalkSpeed(Math.min(player.getWalkSpeed() * 2F, 1F));
                player.sendMessage("You feel lighter without boots");
            }
            player_data.setHeavy_boots_victim(heavy_victim);
            PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);
        }
    }
}

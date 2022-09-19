package com.ethan.twfaith.tasks;

import com.ethan.twfaith.data.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StaminaChecker extends BukkitRunnable {

    @Override
    public void run() {
        // This method will run every second and deduct or give stamina from each player based on their active powers.
        for (Player player : Bukkit.getOnlinePlayers()){
            PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
            Faith faith = FaithHashMap.player_faith_hashmap.get(player.getUniqueId());

            double stamina = player_data.getStamina();
            double max_stamina = player_data.getMax_stamina();
            // TODO make all of the stamina costs and stamina regen configurable
            // subtract the stamina from the active powers
            if (player_data.isPowerful_flock_active()){
                stamina -= 11;
            }
            if (player_data.isHells_fury_active()){
                stamina -= 12;
            }
            if (player_data.isTerrain_bonus_active()){
                stamina -= 11;
            }
            if (player_data.isCrumbling_active()){
                stamina -= 11;
            }
            if (player_data.isHeavy_boots_active()){
                stamina -= 11;
            }
            if (player_data.isIntoxicate_active()){
                stamina -= 11;
            }
            if (player_data.isLions_heart_active()){
                stamina -= 11;
            }
            if (player_data.isInsidious_active()){
                stamina -= 11;
            }

            // add the stamina that should have regenerated

            stamina += 10;

            // If the current stamina is negative, turn off all powers and send player a notification.
            // Also, set stamina to 0. If calculated stamina is above max, set to max.
            // Otherwise, set stamina to new calculated stamina.

            if (stamina < 0){
                player_data.setPowerful_flock_active(false);
                player_data.setHells_fury_active(false);
                player_data.setSummon_god_active(false);
                // TODO make terrain bonus deduct stamina based on length of active terrain powers list
                // TODO fix bug where invisibility effect remains permanently when insidious is disabled by StaminaChecker
                player_data.setTerrain_bonus_active(false);
                player_data.setCrumbling_active(false);
                player_data.setHeavy_boots_active(false);
                player_data.setIntoxicate_active(false);
                player_data.setLions_heart_active(false);
                player_data.setSavior_active(false);
                player_data.setInsidious_active(false);
                player_data.setExplosive_landing_active(false);
                player_data.setStamina(0);
                player.sendMessage(ChatColor.RED + "Your energy runs out, and your powers fade.");

            }else player_data.setStamina(Math.min(stamina, max_stamina));

            if (stamina > max_stamina){stamina = max_stamina;}

            // Update the player's Boss Bar
            if (player_data.getLeader() && player_data.getIn_faith()){
                BossBar boss_bar = BossBars.boss_bar_map.get(player.getUniqueId());
                boss_bar.setProgress(stamina / max_stamina);
            }

            PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);
        }
    }
}

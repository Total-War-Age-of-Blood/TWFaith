package com.ethan.twfaith.tasks;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.Util;
import com.ethan.twfaith.data.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class StaminaChecker extends BukkitRunnable {

    @Override
    public void run() {
        // This method will run every second and deduct or give stamina from each player based on their active powers.
        for (Player player : Bukkit.getOnlinePlayers()){
            PlayerData playerData = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
            if (!playerData.isLeader() || !playerData.isInFaith()){continue;}
            Faith faith = FaithHashMap.playerFaithHashmap.get(playerData.getLedBy());

            double stamina = playerData.getStamina();
            double maxStamina = playerData.getMaxStamina();
            // subtract the stamina from the active powers
            if (playerData.isPowerfulFlockActive()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("flock-stamina");
            }
            if (playerData.isHellsFuryActive()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("hells-stamina");
            }
            // Because multiple terrain effects can be active at once, we deduct 11 stamina for each terrain bonus
            // in the active terrain bonus list.
            if (faith.getTerrainActivePowers().size() > 0){
                stamina -= (TWFaith.getPlugin().getConfig().getInt("terrain-stamina") * faith.getTerrainActivePowers().size());
            }
            if (playerData.isCrumblingActive()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("crumbling-stamina");
            }
            if (playerData.isHeavyBootsActive()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("heavy-stamina");
            }
            if (playerData.isIntoxicateActive()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("intoxicate-stamina");
            }
            if (playerData.isLionsHeartActive()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("lions-heart-stamina");
            }
            if (playerData.isInsidiousActive() && player.isSneaking()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("insidious-stamina");
            }

            // add the stamina that should have regenerated

            stamina += TWFaith.getPlugin().getConfig().getInt("base-stamina-regen");

            // If the current stamina is negative, turn off all powers and send player a notification.
            // Also, set stamina to 0. If calculated stamina is above max, set to max.
            // Otherwise, set stamina to new calculated stamina.
            if (stamina < 0){
                Util.clearBlessings(faith);
                Util.removeSheen(player);
                playerData.setPowerfulFlockActive(false);
                playerData.setHellsFuryActive(false);
                playerData.setSummonGodActive(false);
                playerData.setTerrainBonusActive(false);
                faith.getTerrainActivePowers().clear();
                playerData.setCrumblingActive(false);
                playerData.setHeavyBootsActive(false);
                playerData.setIntoxicateActive(false);
                playerData.setLionsHeartActive(false);
                playerData.setSaviorActive(false);
                playerData.setInsidiousActive(false);
                playerData.setExplosiveLandingActive(false);
                playerData.setStamina(0);
                player.sendMessage(ChatColor.RED + "Your energy runs out, and your powers fade.");
            }else playerData.setStamina(Math.min(stamina, maxStamina));

            if (stamina > maxStamina){stamina = maxStamina;}
            if (stamina < 0){stamina = 0;}

            // Update the player's Boss Bar
            if (playerData.isLeader() && playerData.isInFaith()){
                BossBar bossBar = BossBars.boss_bar_map.get(player.getUniqueId());
                bossBar.setProgress(stamina / maxStamina);
            }
            PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), playerData);
        }
    }
}

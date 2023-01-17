package com.ethan.twfaith.tasks;

import com.ethan.twfaith.TWFaith;
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

public class StaminaChecker extends BukkitRunnable {

    @Override
    public void run() {
        // This method will run every second and deduct or give stamina from each player based on their active powers.
        for (Player player : Bukkit.getOnlinePlayers()){
            PlayerData player_data = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
            if (!player_data.getLeader() || !player_data.getIn_faith()){continue;}
            Faith faith = FaithHashMap.playerFaithHashmap.get(player_data.getLed_by());

            double stamina = player_data.getStamina();
            double max_stamina = player_data.getMax_stamina();
            // subtract the stamina from the active powers
            if (player_data.isPowerful_flock_active()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("flock-stamina");
            }
            if (player_data.isHells_fury_active()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("hells-stamina");
            }
            // Because multiple terrain effects can be active at once, we deduct 11 stamina for each terrain bonus
            // in the active terrain bonus list.
            if (faith.getTerrainActivePowers().size() > 0){
                stamina -= (TWFaith.getPlugin().getConfig().getInt("terrain-stamina") * faith.getTerrainActivePowers().size());
            }
            if (player_data.isCrumbling_active()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("crumbling-stamina");
            }
            if (player_data.isHeavy_boots_active()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("heavy-stamina");
            }
            if (player_data.isIntoxicate_active()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("intoxicate-stamina");
            }
            if (player_data.isLions_heart_active()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("lions-heart-stamina");
            }
            if (player_data.isInsidious_active()){
                stamina -= TWFaith.getPlugin().getConfig().getInt("insidious-stamina");
            }

            // add the stamina that should have regenerated

            stamina += TWFaith.getPlugin().getConfig().getInt("base-stamina-regen");

            // If the current stamina is negative, turn off all powers and send player a notification.
            // Also, set stamina to 0. If calculated stamina is above max, set to max.
            // Otherwise, set stamina to new calculated stamina.
            if (stamina < 0){
                player_data.setPowerfulFlockActive(false);
                player_data.setHellsFuryActive(false);
                player_data.setSummonGodActive(false);
                player_data.setTerrainBonusActive(false);
                faith.getTerrainActivePowers().clear();
                player_data.setCrumblingActive(false);
                player_data.setHeavyBootsActive(false);
                player_data.setIntoxicate_active(false);
                player_data.setLionsHeartActive(false);
                player_data.setSaviorActive(false);
                player_data.setInsidiousActive(false);
                player_data.setExplosiveLandingActive(false);
                player_data.setStamina(0);
                player.sendMessage(ChatColor.RED + "Your energy runs out, and your powers fade.");

                // Remove enchantment sheen for all powers
                for (int x = 0; x < 9; x++){
                    if (player.getInventory().getItem(x) == null){continue;}
                    ItemStack item = player.getInventory().getItem(x);
                    if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING)){
                        continue;
                    }
                    ItemMeta item_meta = item.getItemMeta();
                    item_meta.removeEnchant(Enchantment.DURABILITY);
                    item.setItemMeta(item_meta);
                }

            }else player_data.setStamina(Math.min(stamina, max_stamina));

            if (stamina > max_stamina){stamina = max_stamina;}
            if (stamina < 0){stamina = 0;}

            // Update the player's Boss Bar
            if (player_data.getLeader() && player_data.getIn_faith()){
                BossBar boss_bar = BossBars.boss_bar_map.get(player.getUniqueId());
                boss_bar.setProgress(stamina / max_stamina);
            }

            PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), player_data);
        }
    }
}

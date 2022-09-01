package com.ethan.twfaith.customevents;

import com.ethan.twfaith.activepowers.Flood;
import com.ethan.twfaith.activepowers.Taunt;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class UsePowers implements Listener {

    // When player right clicks, check if the item in main hand has the NBT tag for a god power. If it does, activate the power.
    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        Player player = e.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getDisplayName());
        ItemStack held_item = e.getPlayer().getInventory().getItemInMainHand();
        if (!held_item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING)){return;}
        e.setCancelled(true);
            // TODO fix players without perms being able to activate abilities using the special terracotta
            switch (held_item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING)){
                case "Lion's Heart":
                    if (player_data.isLions_heart_active()){
                        player_data.setLions_heart_active(false);
                        player.sendMessage(ChatColor.RED + "Lions Heart Deactivated");
                    }else {player_data.setLions_heart_active(true);
                    player.sendMessage(ChatColor.GREEN + "Lion's Heart Activated");}
                    break;
                case "Savior":
                    if (player_data.isSavior_active()){
                        player_data.setSavior_active(false);
                        player.sendMessage(ChatColor.RED + "Savior Deactivated");
                    }else {player_data.setSavior_active(true);
                        player.sendMessage(ChatColor.GREEN + "Savior Activated");}
                    break;
                case "Taunt":
                    Taunt taunt = new Taunt();
                    taunt.tauntTrigger(player);
                    player.sendMessage(ChatColor.GOLD + "You taunt your enemies!");
                    break;
                case "Insidious":
                    if (player_data.isInsidious_active()){
                        player_data.setInsidious_active(false);
                        player.sendMessage(ChatColor.RED + "Insidious Deactivated");
                    }else {player_data.setInsidious_active(true);
                        player.sendMessage(ChatColor.GREEN + "Insidious Activated");}
                    break;
                case "Explosive Landing":
                    if (player_data.isExplosive_landing_active()){
                        player_data.setExplosive_landing_active(false);
                        player.sendMessage(ChatColor.RED + "Explosive Landing Deactivated");
                    }else {player_data.setExplosive_landing_active(true);
                        player.sendMessage(ChatColor.GREEN + "Explosive Landing Activated");}
                    break;
                case "Flood":
                    Flood flood = new Flood();
                    flood.floodTrigger(player);
                    player.sendMessage(ChatColor.DARK_BLUE + "The area floods with water.");
                    break;
            }
            PlayerHashMap.player_data_hashmap.put(player.getDisplayName(), player_data);
    }

    // TODO Only allow power to be held or given to the hot bar.
    // TODO Delete power items when they are dropped.
}

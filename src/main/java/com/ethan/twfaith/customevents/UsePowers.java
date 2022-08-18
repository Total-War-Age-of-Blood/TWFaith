package com.ethan.twfaith.customevents;

import com.ethan.twfaith.commands.FloodCommand;
import com.ethan.twfaith.commands.Taunt;
import com.ethan.twfaith.data.PlayerData;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UsePowers implements Listener {

    // When player right clicks, check if the item in main hand has the NBT tag for a god power. If it does, activate the power.
    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        Player player =(Player) e.getPlayer();
        ItemStack held_item = e.getPlayer().getInventory().getItemInMainHand();
        if (!held_item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING)){return;}
        e.setCancelled(true);
        try{
            File player_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "PlayerData");
            File player_file = new File(player_folder, e.getPlayer().getUniqueId() + ".json");
            FileReader player_file_reader = new FileReader(player_file);
            Gson gson = new Gson();
            PlayerData player_data = gson.fromJson(player_file_reader, PlayerData.class);

            switch (held_item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING)){
                case "lionsheart":
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
                    FloodCommand flood = new FloodCommand();
                    flood.floodTrigger(player);
                    player.sendMessage(ChatColor.DARK_BLUE + "The area floods with water.");
                    break;
            }
            FileWriter player_data_writer = new FileWriter(player_file, false);
            gson.toJson(player_data, player_data_writer);
            player_data_writer.flush();
            player_data_writer.close();
        }catch (IOException exception){exception.printStackTrace();}
    }

    // TODO Only allow power to be held or given to the hot bar.
    // TODO Delete power items when they are dropped.
}

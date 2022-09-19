package com.ethan.twfaith.powers.curses;

import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;


import java.util.*;

// TODO when hot bar restriction is added for powers, make sure powers only get shuffled to the hot bar.
public class Discombobulate implements Listener {
    public static LinkedHashSet<String> discombobulators = new LinkedHashSet<>();

    public void discombobulateTrigger(Player player){
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());

        // Because this is a triggered power, not a toggle power, we check and deduct stamina here
        if (player_data.getStamina() < 10){
            player.sendMessage(ChatColor.RED + "Not enough stamina.");
            return;
        }
        player_data.setStamina(player_data.getStamina() - 10);

        // See who is in range of the power
        for (Player heathen : Bukkit.getOnlinePlayers()){
            PlayerData heathen_data = PlayerHashMap.player_data_hashmap.get(heathen.getUniqueId());
            if (heathen.getLocation().distance(player.getLocation()) <= 30 && !heathen_data.getFaith().equals(player_data.getFaith())){

                // Clear the inventory of affected players and record their items for redistribution
                List<ItemStack> items = new ArrayList<>();
                for (ItemStack item : heathen.getInventory().getStorageContents()){
                    if (item != null){
                        items.add(item);
                    }
                }

                // Return the items to random spots in the inventory
                Collections.shuffle(items);
                int list_size = items.size();
                ItemStack[] inventory = items.toArray(new ItemStack[list_size]);
                heathen.getInventory().setStorageContents(inventory);

            }
        }
    }
}

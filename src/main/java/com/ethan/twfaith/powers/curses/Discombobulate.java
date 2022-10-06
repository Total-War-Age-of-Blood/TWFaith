package com.ethan.twfaith.powers.curses;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;


import java.util.*;

public class Discombobulate implements Listener {
    public void discombobulateTrigger(Player player){
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());

        // Because this is a triggered power, not a toggle power, we check and deduct stamina here
        if (player_data.getStamina() < TWFaith.getPlugin().getConfig().getInt("discombobulate-stamina")){
            player.sendMessage(ChatColor.RED + "Not enough stamina.");
            return;
        }
        player_data.setStamina(player_data.getStamina() - TWFaith.getPlugin().getConfig().getInt("discombobulate-stamina"));

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

                // Shuffle items to randomize them
                Collections.shuffle(items);

                List<Integer> indices = new ArrayList<>();
                // Bring the power blocks to the front of the list, so they will get put in the hotbar.
                // Because we can't modify the items list while iterating over it to check for power blocks, we have
                // to record the indices of the power blocks to a second list and iterate through that.
                for (ItemStack item : items){
                    if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING)){
                        continue;
                    }
                    indices.add(items.indexOf(item));
                }
                // Iterating through the list of indices to move items marked as power blocks to the front of the list.
                for (int index : indices){
                    ItemStack item = items.get(index);
                    items.remove(item);
                    items.add(0, item);
                }

                // Return the items to random spots in the inventory
                int list_size = items.size();
                ItemStack[] inventory = items.toArray(new ItemStack[list_size]);
                heathen.getInventory().setStorageContents(inventory);

            }
        }
    }
}

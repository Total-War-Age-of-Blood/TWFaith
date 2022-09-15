package com.ethan.twfaith.powers.blessings;

import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class Mana implements Listener {
    public void onManaTrigger(Player player, PlayerData player_data){
        for (Player nearby : Bukkit.getOnlinePlayers()){
            PlayerData nearby_data = PlayerHashMap.player_data_hashmap.get(nearby.getUniqueId());
            if(!player_data.getLed_by().equals(nearby_data.getLed_by()) || player.getLocation().distance(nearby.getLocation()) > 30){continue;}
            ItemStack mana = new ItemStack(Material.BREAD, 16);
            ItemMeta mana_meta = mana.getItemMeta();
            assert mana_meta != null;
            mana_meta.setDisplayName(ChatColor.GOLD + "Mana");
            mana_meta.setLore(Collections.singletonList("A gift from a god. May be imbued with special properties."));
            mana.setItemMeta(mana_meta);
            nearby.getInventory().addItem(mana);
        }
    }
}

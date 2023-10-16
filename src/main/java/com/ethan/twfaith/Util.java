package com.ethan.twfaith;

import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class Util {
    public static void clearBlessings(Faith faith){
        for (UUID followerId : faith.getFollowers()){
            PlayerData followerData = PlayerHashMap.playerDataHashMap.get(followerId);
            if (followerData != null){
                if (followerData.isHellsFuryActive()){
                    followerData.setHellsFuryActive(false);
                }
                if (followerData.isPowerfulFlockActive()){
                    followerData.setPowerfulFlockActive(false);
                    followerData.setInFlock(false);
                }
                if (followerData.isTerrainBonusActive()){
                    followerData.setTerrainBonusActive(false);
                }
            }
        }
    }

    public static void clearBlessings(PlayerData followerData){
        if (followerData.isHellsFuryActive()){
            followerData.setHellsFuryActive(false);
        }
        if (followerData.isPowerfulFlockActive()){
            followerData.setPowerfulFlockActive(false);
            followerData.setInFlock(false);
        }
        if (followerData.isTerrainBonusActive()){
            followerData.setTerrainBonusActive(false);
        }
    }

    // Removes enchantment sheen for all held powers
    public static void removeSheen(Player player){
        // Remove enchantment sheen for all powers
        for (int x = 0; x < 9; x++){
            if (player.getInventory().getItem(x) == null){continue;}
            ItemStack item = player.getInventory().getItem(x);
            if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING)){
                continue;
            }
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.removeEnchant(Enchantment.DURABILITY);
            item.setItemMeta(itemMeta);
        }
    }
}

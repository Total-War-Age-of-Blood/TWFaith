package com.ethan.twfaith.customevents;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class GodDeath implements Listener {
    @EventHandler
    public void onGodDeath(PlayerDeathEvent event){
        // Removes power items when god dies
        Player player = event.getEntity();
        PlayerData player_data = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        if (!player_data.isLeader()){return;}
        List<ItemStack> power_items = new ArrayList<>();
        for (ItemStack item : event.getDrops()){
            if (!item.hasItemMeta()){continue;}
            if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(TWFaith.getPlugin(), "Power"), PersistentDataType.STRING)){continue;}
            power_items.add(item);
        }
        for (ItemStack item : power_items){
            event.getDrops().remove(item);
        }
    }
}

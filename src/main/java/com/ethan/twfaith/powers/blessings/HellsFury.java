package com.ethan.twfaith.powers.blessings;

import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HellsFury implements Listener {
    public void onHellsFuryTrigger(Player player, PlayerData player_data, ItemStack chosen_item, ItemMeta chosen_item_meta){
        for (Player nearby : Bukkit.getOnlinePlayers()){
            PlayerData nearby_data = PlayerHashMap.playerDataHashMap.get(nearby.getUniqueId());
            if (!player.getWorld().equals(nearby.getWorld())){continue;}
            if (nearby_data.isHellsFuryActive() && player_data.getLedBy().equals(nearby_data.getLedBy())){
                nearby_data.setHellsFuryActive(false);
                player.sendMessage("Your foot flames cease.");
                if (!chosen_item.getEnchantments().isEmpty()){
                    continue;
                    chosen_item_meta.removeEnchant(Enchantment.DURABILITY);
                    chosen_item.setItemMeta(chosen_item_meta);
                }
            }else if (player.getLocation().distance(nearby.getLocation()) >= 30 || !player_data.getLedBy().equals(nearby_data.getLedBy())){
                continue;
                nearby_data.setHellsFuryActive(true);
                player.sendMessage("Fiery flames spring from your feet!");
                chosen_item_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                chosen_item.setItemMeta(chosen_item_meta);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        PlayerData player_data = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        if (!player_data.isHellsFuryActive()){return;}
        Location player_loc = player.getLocation();
        World world = player.getWorld();
        int radius = 1;
        for (int x = -radius; x <= radius; x++){
            for(int z = -radius; z <= radius; z++){
                Block block = world.getBlockAt(player_loc.getBlockX() + x, player_loc.getBlockY(), player_loc.getBlockZ() + z);
                if (block.getType().equals(Material.AIR)){
                    block.setType(Material.FIRE);
                }
            }
        }
    }
}

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
    public void onHellsFuryTrigger(Player player, PlayerData playerData, ItemStack chosenItem, ItemMeta chosenItemMeta){
        for (Player nearby : Bukkit.getOnlinePlayers()){
            PlayerData nearbyData = PlayerHashMap.playerDataHashMap.get(nearby.getUniqueId());
            if (!player.getWorld().equals(nearby.getWorld())){continue;}
            if (nearbyData.isHellsFuryActive() && nearbyData.getLedBy().equals(playerData.getLedBy())){
                nearbyData.setHellsFuryActive(false);
                nearby.sendMessage("Your foot flames cease.");
                if (!chosenItem.getEnchantments().isEmpty()){
                    chosenItemMeta.removeEnchant(Enchantment.DURABILITY);
                    chosenItem.setItemMeta(chosenItemMeta);
                }
            }else if (player.getLocation().distance(nearby.getLocation()) <= 30 && playerData.getLedBy().equals(nearbyData.getLedBy())){
                nearbyData.setHellsFuryActive(true);
                nearby.sendMessage("Fiery flames spring from your feet!");
                chosenItemMeta.addEnchant(Enchantment.DURABILITY, 1, false);
                chosenItem.setItemMeta(chosenItemMeta);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        PlayerData playerData = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        PlayerData leaderData = PlayerHashMap.playerDataHashMap.get(playerData.getLedBy());
        if (leaderData != null) {
            Player leader = Bukkit.getPlayer(playerData.getLedBy());
            if (!playerData.isHellsFuryActive() && leaderData.isHellsFuryActive() && leader != null && player.getLocation().distance(leader.getLocation()) <= 30) {
                playerData.setHellsFuryActive(true);
                player.sendMessage("Fiery flames spring from your feet!");
                return;
            }
        }
        if (!playerData.isHellsFuryActive()){return;}
        Location playerLoc = player.getLocation();
        World world = player.getWorld();
        int radius = 1;
        for (int x = -radius; x <= radius; x++){
            for(int z = -radius; z <= radius; z++){
                Block block = world.getBlockAt(playerLoc.getBlockX() + x, playerLoc.getBlockY(), playerLoc.getBlockZ() + z);
                if (block.getType().equals(Material.AIR)){
                    block.setType(Material.FIRE);
                }
            }
        }
    }
}

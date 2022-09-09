package com.ethan.twfaith.powers;

import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class HellsFury implements Listener {
    public void onHellsFuryTrigger(Player player, PlayerData player_data){
        for (Player nearby : Bukkit.getOnlinePlayers()){
            PlayerData nearby_data = PlayerHashMap.player_data_hashmap.get(nearby.getDisplayName());
            if (player.getLocation().distance(nearby.getLocation()) >= 30 || !player_data.getLed_by().equals(nearby_data.getLed_by())){continue;}
            if (nearby_data.isHells_fury_active()){
                nearby_data.setHells_fury_active(false);
                player.sendMessage("Your foot flames cease.");
            }else{
                nearby_data.setHells_fury_active(true);
                player.sendMessage("Fiery flames spring from your feet!");
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getDisplayName());
        if (!player_data.isHells_fury_active()){return;}
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

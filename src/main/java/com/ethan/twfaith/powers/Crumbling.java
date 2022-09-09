package com.ethan.twfaith.powers;

import com.ethan.twfaith.customevents.UsePowers;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class Crumbling implements Listener {
    // Marks crumbling victims
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getDisplayName());
        boolean is_crumbling = false;
        for (String crumbler : UsePowers.crumblers){
            if (player.getLocation().distance(Objects.requireNonNull(Bukkit.getPlayer(crumbler)).getLocation()) <= 30){is_crumbling = true;}
        }
        player_data.setCrumbling_victim(is_crumbling);
        PlayerHashMap.player_data_hashmap.put(player.getDisplayName(), player_data);
    }

    // The crumbling effect
    @EventHandler
    public void onArmorDamage(PlayerItemDamageEvent event){
        Player player = event.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getDisplayName());
        // System.out.println(event.getItem());
        if (!player_data.isCrumbling_victim()){
            // System.out.println("Not crumbling victim");
            return;}
        // System.out.println(event.getDamage());
        event.setDamage(event.getDamage()*10);
        // System.out.println(event.getDamage());
    }
}

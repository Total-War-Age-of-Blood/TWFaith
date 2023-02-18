package com.ethan.twfaith.powers.curses;

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
        PlayerData player_data = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        boolean is_crumbling = false;
        for (String crumbler : UsePowers.crumblers){
            Player crumbler_player = Bukkit.getPlayer(crumbler);
            if (!player.getWorld().equals(crumbler_player.getWorld())){continue;}
            if (player.getLocation().distance(Objects.requireNonNull(crumbler_player).getLocation()) <= 30){is_crumbling = true;}
        }
        player_data.setCrumblingVictim(is_crumbling);
        PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), player_data);
    }

    // The crumbling effect
    @EventHandler
    public void onArmorDamage(PlayerItemDamageEvent event){
        Player player = event.getPlayer();
        PlayerData player_data = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        // System.out.println(event.getItem());
        if (!player_data.isCrumblingVictim()){
            // System.out.println("Not crumbling victim");
            return;}
        // System.out.println(event.getDamage());
        event.setDamage(event.getDamage()*10);
        // System.out.println(event.getDamage());
    }
}

package com.ethan.twfaith.powers.curses;

import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.LinkedHashSet;
import java.util.Objects;

public class Intoxicate implements Listener {
    public static LinkedHashSet<String> intoxicators = new LinkedHashSet<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());

        boolean intox_victim = player_data.isIntoxicate_victim();

        boolean intoxicated = false;
        for (String intox : intoxicators){
            if (player.getLocation().distance(Objects.requireNonNull(Bukkit.getPlayer(intox)).getLocation()) <= 30 && !player.equals(Bukkit.getPlayer(intox))){
                intoxicated = true;
            }
        }

        if (intoxicated && !intox_victim){
            player.addPotionEffect(PotionEffectType.CONFUSION.createEffect(Integer.MAX_VALUE, 0));
            player_data.setIntoxicate_victim(true);
            player.sendMessage(ChatColor.RED + "The air here is intoxicating!");
        } else if(!intoxicated && intox_victim){
            player.removePotionEffect(PotionEffectType.CONFUSION);
            player_data.setIntoxicate_victim(false);
            player.sendMessage(ChatColor.GREEN + "Fresh air clears your lungs.");
        }

        PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);
    }
}
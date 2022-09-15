package com.ethan.twfaith.powers.blessings;

import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class PowerfulFlock implements Listener {
    // When multiple players of the same faith are close to each other, they become stronger
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());

        // Make sure the leader is online and has the power active
        if (Bukkit.getPlayer(player_data.getLed_by()) == null){return;}
        Player leader = Bukkit.getPlayer(player_data.getLed_by());
        assert leader != null;
        PlayerData leader_data = PlayerHashMap.player_data_hashmap.get(leader.getUniqueId());

        double nearby_friends = 0;
        for (Player friend : Bukkit.getOnlinePlayers()){
            PlayerData friend_data = PlayerHashMap.player_data_hashmap.get(friend.getUniqueId());
            if (player.getLocation().distance(friend.getLocation()) >= 30 || !player_data.getLed_by().equals(friend_data.getLed_by()) || player.getUniqueId().equals(friend.getUniqueId())){continue;}
            nearby_friends ++;
        }

        if (!leader_data.isPowerful_flock_active()){
            if (player_data.isIn_flock()){
                player_data.setIn_flock(false);
                Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() - nearby_friends);
                player.sendMessage("Your god has stopped Powerful Flock");
            }
            return;
        }

        // Stuff that only happens when the player changes flock state
        if (nearby_friends > 0 && !player_data.isIn_flock()){
            player_data.setIn_flock(true);
            player.sendMessage("The flock gives you strength");
            // To avoid the complications of using potion effects, we are instead changing the max health attribute of the player
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20 + nearby_friends);
            player_data.setNearby_friends(nearby_friends);
        } else if (nearby_friends < 1 && player_data.isIn_flock()){
            player_data.setIn_flock(false);
            player.sendMessage("You stray from the flock, losing your strength");
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20);
            player_data.setNearby_friends(nearby_friends);
        } else if (nearby_friends > 0 && player_data.isIn_flock() && nearby_friends != player_data.getNearby_friends()){
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20 + (nearby_friends));
            player_data.setNearby_friends(nearby_friends);
        }
    }
}

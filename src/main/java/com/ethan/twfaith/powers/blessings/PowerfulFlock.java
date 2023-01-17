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
        PlayerData playerData = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());

        // Make sure the leader is online and has the power active
        if (Bukkit.getPlayer(playerData.getLed_by()) == null){return;}
        Player leader = Bukkit.getPlayer(playerData.getLed_by());
        assert leader != null;
        PlayerData leaderData = PlayerHashMap.playerDataHashMap.get(leader.getUniqueId());

        double nearbyFriends = 0;
        for (Player friend : Bukkit.getOnlinePlayers()){
            PlayerData friend_data = PlayerHashMap.playerDataHashMap.get(friend.getUniqueId());
            if (!player.getWorld().equals(friend.getWorld())){continue;}
            if (player.getLocation().distance(friend.getLocation()) >= 30 || !playerData.getLed_by().equals(friend_data.getLed_by()) || player.getUniqueId().equals(friend.getUniqueId())){continue;}
            nearbyFriends ++;
        }

        if (!leaderData.isPowerful_flock_active()){
            if (playerData.isInFlock()){
                playerData.setInFlock(false);
                Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() - nearbyFriends);
                player.sendMessage("Your god has stopped Powerful Flock");
            }
            return;
        }

        // Stuff that only happens when the player changes flock state
        if (nearbyFriends > 0 && !playerData.isInFlock()){
            playerData.setInFlock(true);
            player.sendMessage("The flock gives you strength");
            // To avoid the complications of using potion effects, we are instead changing the max health attribute of the player
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20 + nearbyFriends);
            playerData.setNearby_friends(nearbyFriends);
        } else if (nearbyFriends < 1 && playerData.isInFlock()){
            playerData.setInFlock(false);
            player.sendMessage("You stray from the flock, losing your strength");
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20);
            playerData.setNearby_friends(nearbyFriends);
        } else if (nearbyFriends > 0 && playerData.isInFlock() && nearbyFriends != playerData.getNearby_friends()){
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20 + (nearbyFriends));
            playerData.setNearby_friends(nearbyFriends);
        }
    }
}

package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class JoinFaith {
    public void JoinFaith(PlayerData playerData, Player player, String[] args){

        if (playerData.isInFaith()){
        player.sendMessage(ChatColor.RED + "You must leave your current faith before joining a new faith.");
        return;
    }

        if (playerData.isLeader()){
            player.sendMessage(ChatColor.RED + "You are already the leader of a faith. Disband your faith to join another.");
            return;
        }

        for (Faith faith : FaithHashMap.playerFaithHashmap.values()){
            List<UUID> invited_players = faith.getInvitedPlayers();
            List<UUID> followers = faith.getFollowers();
            System.out.println("file detected");
            if (faith.getFaithName().equals(args[1]) && faith.getInvitedPlayers().contains(player.getUniqueId()) && !followers.contains(player.getUniqueId())){
                invited_players.remove(player.getUniqueId());
                followers.add(player.getUniqueId());
                player.sendMessage("You have become a follower of " + faith.getFaithName());
                faith.setInvitedPlayers(invited_players);
                faith.setFollowers(followers);

                if (Objects.requireNonNull(Bukkit.getPlayer(faith.getLeader())).isOnline()){
                    Objects.requireNonNull(Bukkit.getPlayer(faith.getLeader())).sendMessage(player.getDisplayName() + " has become a follower of " + faith.getFaithName());
                }


                playerData.setFaith(args[1]);
                playerData.setInFaith(true);
                playerData.setLedBy(faith.getLeader());
                PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), playerData);
                FaithHashMap.playerFaithHashmap.put(faith.getLeader(), faith);
            }
        }
    }
}

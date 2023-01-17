package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.Faith;
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
    public void JoinFaith(PlayerData playerData, Player player, File faithFolder, String[]args){

        if (playerData.getIn_faith()){
        player.sendMessage(ChatColor.RED + "You must leave your current faith before joining a new faith.");
        return;
    }

        if (playerData.getLeader()){
            player.sendMessage(ChatColor.RED + "You are already the leader of a faith. Disband your faith to join another.");
            return;
        }

        for (File faith_file : Objects.requireNonNull(faithFolder.listFiles())){
            try{
                Reader faith_reader = new FileReader(faith_file);
                Faith readFaith = TWFaith.getGson().fromJson(faith_reader, Faith.class);
                List<UUID> invited_players = readFaith.getInvitedPlayers();
                List<UUID> followers = readFaith.getFollowers();
                System.out.println("file detected");
                if (readFaith.getFaithName().equals(args[1]) && readFaith.getInvitedPlayers().contains(player.getUniqueId()) && !followers.contains(player.getUniqueId())){
                    invited_players.remove(player.getUniqueId());
                    followers.add(player.getUniqueId());
                    player.sendMessage("You have become a follower of " + readFaith.getFaithName());
                    readFaith.setInvitedPlayers(invited_players);
                    readFaith.setFollowers(followers);

                    if (Objects.requireNonNull(Bukkit.getPlayer(readFaith.getLeader())).isOnline()){
                        Objects.requireNonNull(Bukkit.getPlayer(readFaith.getLeader())).sendMessage(player.getDisplayName() + " has become a follower of " + readFaith.getFaithName());
                    }

                    Writer writer = new FileWriter(faith_file, false);
                    TWFaith.getGson().toJson(readFaith, writer);
                    writer.flush();
                    writer.close();

                    playerData.setFaith(args[1]);
                    playerData.setInFaith(true);
                    playerData.setLedBy(readFaith.getLeader());
                    PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), playerData);
                }

            }catch(IOException e){e.printStackTrace();}
        }
    }
}

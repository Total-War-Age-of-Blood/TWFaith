package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.List;
import java.util.UUID;

public class LeaveFaith {
    public void leaveFaith(PlayerData playerData, Player player, File faithFolder){
        if (playerData.isLeader()){
        player.sendMessage(ChatColor.RED + "A leader cannot leave the faith. You must disband it.");
        return;
    }
        try{
            if (playerData.isInFaith()){
                // Removing the player from the faith data file
                File faith_file = new File (faithFolder, playerData.getLedBy() + ".json");
                Reader faith_reader = new FileReader(faith_file);
                Faith read_faith = TWFaith.getGson().fromJson(faith_reader, Faith.class);
                List<UUID> followers = read_faith.getFollowers();
                followers.remove(player.getUniqueId());
                Writer faith_data_writer = new FileWriter(faith_file, false);
                TWFaith.getGson().toJson(read_faith, faith_data_writer);
                faith_data_writer.flush();
                faith_data_writer.close();

                // Removing the faith from the player data file
                playerData.setInFaith(false);
                playerData.setLeader(false);
                playerData.setFaith("");
                playerData.setLedBy(null);
                PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), playerData);

                player.sendMessage("You have left " + read_faith.getFaithName());
            }
        }catch (IOException e){e.printStackTrace();}}
}

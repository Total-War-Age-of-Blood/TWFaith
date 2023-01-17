package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.UUID;

public class RenameFaith {
    public void RenameFaith(File senderFaithFile, String[] args, Player player){

        try{
            if (senderFaithFile.exists()){
                Reader faith_reader = new FileReader(senderFaithFile);
                Faith read_faith = TWFaith.getGson().fromJson(faith_reader, Faith.class);
                read_faith.setFaithName(args[1]);

                Writer writer = new FileWriter(senderFaithFile, false);
                TWFaith.getGson().toJson(read_faith, writer);
                writer.flush();
                writer.close();

                PlayerData leader_player_data = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
                leader_player_data.setFaith(args[1]);
                PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), leader_player_data);

                for (UUID unique_player : read_faith.getFollowers()){
                    Player follower = Bukkit.getPlayer(unique_player);
                    assert follower != null;
                    PlayerData follower_data = PlayerHashMap.playerDataHashMap.get(follower.getUniqueId());
                    follower_data.setFaith(args[1]);
                    PlayerHashMap.playerDataHashMap.put(follower.getUniqueId(), follower_data);
                }

                player.sendMessage("Renamed faith to " + args[1]);

            }else{player.sendMessage(ChatColor.RED + "ERROR: Could not rename faith. Are you sure you are the" +
                    " leader of a faith?");}
        }catch(IOException e){e.printStackTrace();}
    }
}

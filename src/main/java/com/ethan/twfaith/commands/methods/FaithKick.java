package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerProfile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class FaithKick {
    public void FaithKick(PlayerData playerData, Player player, String[] args){
        // Only leaders can use this command
        if (!playerData.getLeader()){
            player.sendMessage(ChatColor.RED + "Must be leader to kick follower.");
            return;
        }

        // If the kicked player is not online, search the list of offline players. If there is still no result,
        // tell the player he typed it wrong.
        Player kicked = Bukkit.getPlayer(args[1]);
        if (kicked == null){
            boolean found_offline = false;
            for (OfflinePlayer offline : Bukkit.getOfflinePlayers()){
                if (!offline.getName().equals(args[1])){continue;}
                // If the player is offline, we have to change the data in the JSON file.
                PlayerProfile kicked_profile = offline.getPlayerProfile();
                UUID kicked_uuid = kicked_profile.getUniqueId();
                File kicked_folder = new File(TWFaith.getPlugin().getDataFolder(), "PlayerData");
                File kicked_file = new File(kicked_folder, kicked_uuid + ".json");
                try{
                    FileReader kicked_reader = new FileReader(kicked_file);
                    PlayerData kicked_data = TWFaith.getGson().fromJson(kicked_reader, PlayerData.class);

                    kicked_data.setLedBy(kicked_uuid);
                    kicked_data.setFaith("");
                    kicked_data.setInFaith(false);

                    FileWriter kicked_writer = new FileWriter(kicked_file, false);
                    TWFaith.getGson().toJson(kicked_data, kicked_writer);
                    kicked_writer.flush();
                    kicked_writer.close();
                }catch (IOException exception){return;}
                Faith faith = FaithHashMap.playerFaithHashmap.get(player.getUniqueId());
                List<UUID> followers = faith.getFollowers();
                followers.remove(kicked_uuid);
                player.sendMessage("Kicked " + args[1]+ " from " + playerData.getFaith() + ".");
                return;
            }
            if (!found_offline){
                player.sendMessage(ChatColor.RED + "Invalid Username");
                return;
            }
        }

        PlayerData kicked_data = PlayerHashMap.playerDataHashMap.get(kicked.getUniqueId());
        Faith faith = FaithHashMap.playerFaithHashmap.get(player.getUniqueId());

        // Kicking the player
        if (kicked_data.getLed_by().equals(playerData.getLed_by()) && !kicked_data.getLeader()){
            kicked_data.setLedBy(kicked.getUniqueId());
            kicked_data.setFaith("");
            kicked_data.setInFaith(false);
            List<UUID> followers = faith.getFollowers();
            followers.remove(kicked.getUniqueId());
            faith.setFollowers(followers);
            player.sendMessage("Kicked " + kicked.getDisplayName() + " from " + playerData.getFaith() + ".");
            kicked.sendMessage("You have been kicked from " + playerData.getFaith() + ".");
        } else if(!kicked_data.getLed_by().equals(playerData.getLed_by())){
            player.sendMessage(ChatColor.RED + "That player is not in your faction.");
        } else{
            player.sendMessage(ChatColor.RED + "You cannot kick yourself.");
        }

    }
}

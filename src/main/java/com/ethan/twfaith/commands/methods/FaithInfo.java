package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
import com.ethan.twfaith.guis.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FaithInfo {
    public void faithInfo(String arg, Player player){
        // Player passes String of faith they want info on
        // String is converted to faith data, which is referenced to create a message about the faith
        // Message is then sent to player

        Faith faith = null;
        boolean faithFound = false;
        for (Faith religion : FaithHashMap.playerFaithHashmap.values()){
            if (!religion.getFaithName().equalsIgnoreCase(arg)){
                continue;
            }
            faithFound = true;
            faith = religion;
        }
        if (!faithFound){
            player.sendMessage(ChatColor.RED + "Faith does not exist.");
            return;
        }

        // Constructing message
        List<String> messages = new ArrayList<>();

        // Faith name
        messages = Util.addCenteredMessage(ChatColor.GOLD + faith.getFaithName(), messages);
        // Leader
        messages.add(ChatColor.RED + "Leader: " + Bukkit.getOfflinePlayer(faith.getLeader()).getName() + ChatColor.WHITE);
        // List of followers
        List<String> followers = new ArrayList<>();
        for (UUID follower : faith.getFollowers()){
            String name = ChatColor.stripColor(Bukkit.getOfflinePlayer(follower).getName());
            followers.add(name);
        }
        messages.add(ChatColor.DARK_GREEN + "Followers: " + ChatColor.GREEN + String.join(", ", followers) + ChatColor.WHITE);
        System.out.println(messages);

        // Sending out the messages
        player.sendMessage(String.join("\n", messages));

    }
}

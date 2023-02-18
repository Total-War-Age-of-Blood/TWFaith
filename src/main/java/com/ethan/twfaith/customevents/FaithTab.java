package com.ethan.twfaith.customevents;

import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class FaithTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> onlinePlayers = new ArrayList<>();
        for (Player online : Bukkit.getOnlinePlayers()){
            onlinePlayers.add(ChatColor.stripColor(online.getDisplayName()));
        }
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("balance", "create", "list", "invite", "disband", "join", "leave", "summon", "powers", "upgrade", "rename", "kick", "accept", "info"), new ArrayList<>());
        }else if (args.length == 2){
            if (args[0].equals("invite")){
                return StringUtil.copyPartialMatches(args[1], Arrays.asList("add"), new ArrayList<>());
            }
            if (args[0].equals("kick")){
                return StringUtil.copyPartialMatches(args[1], onlinePlayers, new ArrayList<>());
            }
            if (args[0].equals("info")){
                List<String> faithNames = new ArrayList<>();
                for (Faith faith : FaithHashMap.playerFaithHashmap.values()){
                    faithNames.add(faith.getFaithName());
                }
                return StringUtil.copyPartialMatches(args[1], faithNames, new ArrayList<>());
            }
        }else if (args.length == 3){
            if (args[1].equals("add")){
                return StringUtil.copyPartialMatches(args[2], onlinePlayers, new ArrayList<>());
            }
        }

        return new ArrayList<>();
    }
}

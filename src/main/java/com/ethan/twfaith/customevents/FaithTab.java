package com.ethan.twfaith.customevents;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class FaithTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> online_players = new ArrayList<>();
        for (Player online : Bukkit.getOnlinePlayers()){
            online_players.add(online.getDisplayName());
        }
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("balance", "create", "list", "invite", "disband", "join", "leave", "summon", "powers", "upgrade", "rename", "kick", "accept"), new ArrayList<>());
        }else if (args.length == 2){
            if (args[0].equals("invite")){
                return StringUtil.copyPartialMatches(args[1], Arrays.asList("add"), new ArrayList<>());
            }
            if (args[0].equals("kick")){
                return StringUtil.copyPartialMatches(args[1], online_players, new ArrayList<>());
            }
        }else if (args.length == 3){
            if (args[1].equals("add")){
                return StringUtil.copyPartialMatches(args[2], online_players, new ArrayList<>());
            }
        }

        return new ArrayList<>();
    }
}

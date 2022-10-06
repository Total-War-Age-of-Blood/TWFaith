package com.ethan.twfaith.customevents;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.*;

public class FaithTab implements TabCompleter {
    // TODO make sure everything is included in TabComplete
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("balance", "create", "list", "invite", "disband", "join", "leave", "summon", "powers", "upgrade", "rename", "kick"), new ArrayList<>());
        }else if (args.length == 2 && Objects.equals(args[0], "invite")){
            return StringUtil.copyPartialMatches(args[0], Collections.singletonList("add"), new ArrayList<>());
        }

        return new ArrayList<>();
    }
}

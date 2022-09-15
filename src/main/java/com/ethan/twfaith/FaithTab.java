package com.ethan.twfaith;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FaithTab implements TabCompleter {
    // TODO make sure everything is included in TabComplete
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("create", "list", "invite", "disband", "join", "leave", "summon", "powers", "upgrade"), new ArrayList<>());
        }

        return new ArrayList<>();
    }
}

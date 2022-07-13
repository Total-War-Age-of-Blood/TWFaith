package com.ethan.twfaith.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class pray implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        if (sender instanceof Player){
// TODO finish pray command
            // pray command should increase the number of faith points of the faith by one
            // pray command should have a configurable cool down
            // pray command should send player a message so they know it worked
        }
        return false;
    }
}

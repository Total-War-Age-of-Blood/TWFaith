package com.ethan.twfaith.commands;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.FaithHashMap;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.concurrent.TimeUnit;

public class Pray implements CommandExecutor {

    // TODO make Pray part of FaithCommand
    private TWFaith twFaith;

    public Pray(TWFaith twFaith) {
        this.twFaith = twFaith;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
            // Check if the player is a leader because gods cannot pray to themselves
            if (player_data.getLeader()){
                player.sendMessage(ChatColor.RED + "You cannot pray to yourself.");
                return true;
            }
            Faith faith = FaithHashMap.player_faith_hashmap.get(player_data.getLed_by());
            // Check if the player is on prayer cooldown
            long last_prayer = player_data.getLast_prayer();
            long current_time = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());
            if (player_data.getIn_faith() && (current_time - last_prayer) > twFaith.getConfig().getLong("pray-cool-down")){

                // Giving god a faith point
                faith.setFaith_points(faith.getFaith_points() + 1);

                // Setting player cooldown and giving player a faith point
                player_data.setLast_prayer(current_time);
                player_data.setFaith_points(player_data.getFaith_points() + 1);

                PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);
                FaithHashMap.player_faith_hashmap.put(player.getUniqueId(), faith);

                player.sendMessage("You pray to your god.");

            } else if(!((current_time - last_prayer) > twFaith.getConfig().getLong("pray-cool-down"))){
                player.sendMessage("Your prayer is still on cool-down for " + (twFaith.getConfig().getLong("pray-cool-down") - (current_time - last_prayer)) + " hours.");
            }


        }
        return false;
    }
}

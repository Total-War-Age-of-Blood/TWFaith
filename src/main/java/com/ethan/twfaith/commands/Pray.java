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
            PlayerData playerData = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
            // Check if the player is a leader because gods cannot pray to themselves
            if (playerData.isLeader()){
                player.sendMessage(ChatColor.RED + "You cannot pray to yourself.");
                return true;
            }
            Faith faith = FaithHashMap.playerFaithHashmap.get(playerData.getLedBy());
            // Check if the player is on prayer cooldown
            long lastPrayer = playerData.getLastPrayer();
            long currentTime = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());
            if (playerData.isInFaith() && (currentTime - lastPrayer) > twFaith.getConfig().getLong("pray-cool-down")){

                // Giving god a faith point
                faith.setFaithPoints(faith.getFaithPoints() + 1);

                // Setting player cooldown and giving player a faith point
                playerData.setLastPrayer(currentTime);
                playerData.setFaithPoints(playerData.getFaithPoints() + 1);

                PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), playerData);
                FaithHashMap.playerFaithHashmap.put(player.getUniqueId(), faith);

                player.sendMessage("You pray to your god.");

            } else if(!((currentTime - lastPrayer) > twFaith.getConfig().getLong("pray-cool-down"))){
                player.sendMessage("Your prayer is still on cool-down for " + (twFaith.getConfig().getLong("pray-cool-down") - (currentTime - lastPrayer)) + " hours.");
            }


        }
        return false;
    }
}

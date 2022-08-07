package com.ethan.twfaith.commands;

import com.ethan.twfaith.files.PlayerData;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import java.io.*;
import java.util.UUID;

public class taunt implements CommandExecutor, Listener {
    // TODO implement command cool-down.
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){return true;}
        Player player = (Player) sender;
        File player_data_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "PlayerData");
        File player_data_file = new File(player_data_folder, player.getUniqueId() + "json.");
        Gson gson = new Gson();
        try{
            Reader player_data_reader = new FileReader(player_data_file);
            PlayerData player_data = gson.fromJson(player_data_reader, PlayerData.class);
            if (!player_data.getLeader() || player_data.getTaunt() < 1){return true;}
            for (Player heathen : Bukkit.getOnlinePlayers()){
                if (heathen.getLocation().distance(player.getLocation()) < 30){
                    File heathen_data_file = new File(player_data_file, heathen.getUniqueId() + ".json");
                    Reader heathen_data_reader = new FileReader(heathen_data_file);
                    PlayerData heathen_data = gson.fromJson(heathen_data_reader, PlayerData.class);
                    if (!UUID.fromString(heathen_data.getLed_by()).equals(player_data.getUuid())){
                        heathen_data.setTaunted(true);
                        heathen_data.setTaunter(player.getUniqueId());
                        Writer heathen_data_writer = new FileWriter(heathen_data_file, false);
                        gson.toJson(heathen_data, heathen_data_writer);
                        heathen_data_writer.flush();
                        heathen_data_writer.close();
                    }
                }
            }
        }catch(IOException exception){exception.printStackTrace();}
        return false;
    }

    // Applies and removes the taunt effects based on distance from taunter
    @EventHandler
    public void onTauntEvent(PlayerMoveEvent e){
        Player player = e.getPlayer();
        File player_data_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "PlayerData");
        File player_data_file = new File(player_data_folder, player.getUniqueId() + "json.");
        Gson gson = new Gson();
        try{
            Reader player_data_reader = new FileReader(player_data_file);
            PlayerData player_data = gson.fromJson(player_data_reader, PlayerData.class);
            if (!player_data.isTaunted()){return;}
            if (player.getLocation().distance(Bukkit.getPlayer(player_data.getTaunter()).getLocation()) <= 30){
                player.addPotionEffect(PotionEffectType.HUNGER.createEffect(Integer.MAX_VALUE, 0));
            }else{
                player_data.setTaunted(false);
                player_data.setTaunter(null);
                FileWriter player_data_writer = new FileWriter(player_data_file, false);
                gson.toJson(player_data, player_data_writer);
                player_data_writer.flush();
                player_data_writer.close();
                player.removePotionEffect(PotionEffectType.HUNGER);
            }
        }catch(IOException exception){exception.printStackTrace();}

        // TODO add player glow for taunter
    }

    // Removes taunt when taunter is hit by taunted
    @EventHandler
    public void onTauntHitEvent(EntityDamageEvent e){
        // TODO set isTaunted to false and remove hunger when taunter is hit by taunted
    }
}

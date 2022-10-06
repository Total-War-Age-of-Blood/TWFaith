package com.ethan.twfaith.commands;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.PlayerData;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Pray implements CommandExecutor {

    // TODO make Pray part of FaithCommand
    private TWFaith twFaith;

    public Pray(TWFaith twFaith) {
        this.twFaith = twFaith;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        if (sender instanceof Player){
            PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
            Gson gson = new Gson();
            try {
                long last_prayer = player_data.getLast_prayer();
                long current_time = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());
                if (player_data.getIn_faith() && (current_time - last_prayer) > twFaith.getConfig().getLong("pray-cool-down")){
                    File player_faith_folder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "Faiths");
                    File player_faith_file = new File(player_faith_folder, player_data.getLed_by() + ".json");
                    Reader player_faith_reader = new FileReader(player_faith_file);
                    Faith player_faith = gson.fromJson(player_faith_reader, Faith.class);
                    player_faith.setFaith_points(player_faith.getFaith_points() + 1);

                    Writer player_faith_writer = new FileWriter(player_faith_file, false);
                    gson.toJson(player_faith, player_faith_writer);
                    player_faith_writer.flush();
                    player_faith_writer.close();

                    player_data.setLast_prayer(current_time);
                    player_data.setFaith_points(player_data.getFaith_points() + 1);

                    PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);

                    player.sendMessage("You pray to your god.");

                } else if(!((current_time - last_prayer) > twFaith.getConfig().getLong("pray-cool-down"))){
                    player.sendMessage("Your prayer is still on cool-down for " + (twFaith.getConfig().getLong("pray-cool-down") - (current_time - last_prayer)) + " hours.");
                }
            } catch (IOException e) {e.printStackTrace();}


        }
        return false;
    }
}

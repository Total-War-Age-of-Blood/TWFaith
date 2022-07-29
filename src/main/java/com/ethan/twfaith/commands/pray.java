package com.ethan.twfaith.commands;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.files.Faith;
import com.ethan.twfaith.files.PlayerData;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class pray implements CommandExecutor {

    private TWFaith twFaith;

    public pray(TWFaith twFaith) {
        this.twFaith = twFaith;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        if (sender instanceof Player){
            UUID player_id = player.getUniqueId();
            File player_data_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "PlayerData");
            File player_data_file = new File(player_data_folder, player_id + ".json");
            Gson gson = new Gson();
            try {
                Reader player_data_reader = new FileReader(player_data_file);
                PlayerData player_data = gson.fromJson(player_data_reader, PlayerData.class);
                long last_prayer = player_data.getLast_prayer();
                long current_time = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());
                if (player_data.getIn_faith() && (current_time - last_prayer) > twFaith.getConfig().getLong("pray-cool-down")){
                    File player_faith_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Faiths");
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

                    Writer player_data_writer = new FileWriter(player_data_file, false);
                    gson.toJson(player_data, player_data_writer);
                    player_data_writer.flush();
                    player_data_writer.close();

                    player.sendMessage("You pray to your god.");

                } else if(!((current_time - last_prayer) > twFaith.getConfig().getLong("pray-cool-down"))){
                    player.sendMessage("Your prayer is still on cool-down for " + (twFaith.getConfig().getLong("pray-cool-down") - (current_time - last_prayer)) + "hours.");
                }
            } catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}


        }
        return false;
    }
}

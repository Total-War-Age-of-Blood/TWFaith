package com.ethan.twfaith.data;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PlayerHashMap implements Listener {
    // Creates the Hashmap for managing PlayerData
    public static HashMap<UUID, PlayerData> player_data_hashmap = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        File player_folder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "PlayerData");
        String[] file_list = player_folder.list();
        boolean found_player = false;
        // Iterate through the "Players" folder to see if player has a PlayerData file
        // If player has existing file, load it to the HashMap
        // If player does not have existing file, create new file and load to HashMap
        assert file_list != null;
        for (String file_name : file_list) {
            if (!file_name.equals(player.getUniqueId() + ".json")) {continue;}
            System.out.println("Player has file on record.");
            File player_file = new File(player_folder, e.getPlayer().getUniqueId() + ".json");
            Gson gson = new Gson();
            try{
                System.out.println("Putting PlayerData in HashMap.");
                FileReader player_file_reader = new FileReader(player_file);
                PlayerData player_data = gson.fromJson(player_file_reader, PlayerData.class);
                player_data_hashmap.put(player.getUniqueId(), player_data);
            }catch (IOException exception){exception.printStackTrace();}
            found_player = true;
        }

        if (found_player){return;}
        PlayerData player_data = new PlayerData(e.getPlayer().getUniqueId(), e.getPlayer().getUniqueId(), "", false, false,
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, false, e.getPlayer().getUniqueId(),
                false, false, false, false,
                false, false, false, false, false,
                false, false, false, false, false,
                0, 0, 0, 0, false, false, false, false, 0, false);
        try {
            // Check to see if the necessary directories already exist.
            File player_data_folder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "PlayerData");
            if (!player_data_folder.exists()) {player_data_folder.mkdir();}

            File file = new File(player_data_folder, e.getPlayer().getUniqueId() + ".json");
            if (!file.exists()) {file.createNewFile();}

            Gson gson = new Gson();
            Writer writer = new FileWriter(file, false);
            gson.toJson(player_data, writer);
            writer.flush();
            writer.close();

        } catch (Exception ex) {ex.printStackTrace();}

        // Add the player's data to the hashmap
        player_data_hashmap.put(e.getPlayer().getUniqueId(), player_data);
        System.out.println("Player " + e.getPlayer().getDisplayName() + " added to HashMap.");
    }

    // Save data from HashMap to file when player quits, then remove player from HashMap
    // TODO set active powers to false when player leaves
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Gson gson = new Gson();
        try{
            File player_folder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "PlayerData");
            File player_file = new File(player_folder, player.getUniqueId() + ".json");
            FileWriter player_data_writer = new FileWriter(player_file, false);
            PlayerData player_data = player_data_hashmap.get(player.getUniqueId());
            gson.toJson(player_data, player_data_writer);
            player_data_writer.flush();
            player_data_writer.close();
            System.out.println("Player File Saved!");
        }catch (IOException exception){exception.printStackTrace();}

        // Remove player from HashMap
        player_data_hashmap.remove(player.getUniqueId());
        System.out.println(player.getDisplayName() + " has been removed from HashMap.");
    }
}

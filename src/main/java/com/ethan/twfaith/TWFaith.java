package com.ethan.twfaith;

import com.ethan.twfaith.commands.FaithCommand;
import com.ethan.twfaith.activepowers.Flood;
import com.ethan.twfaith.commands.Pray;
import com.ethan.twfaith.activepowers.Taunt;
import com.ethan.twfaith.customevents.UsePowers;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.UniquePlayers;
import com.ethan.twfaith.guis.*;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public final class TWFaith extends JavaPlugin implements Listener {
    // TODO add cool powers that are unlocked with faith points
    // TODO Replace hard-coded values with configurable ones where applicable
    // TODO See if its possible to load all of the PlayerData and Faiths files on startup and save on shutdown to
    //  decrease the amount of reading disk required. Persistent Data Containers may be the solution as they have the
    //  same capacity as JSON and can be applied to the Player object
    public List<UUID> unique_player_list = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        // Primary config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Event Listeners
        Bukkit.getPluginManager().registerEvents(new FaithUpgrade(), this);
        Bukkit.getPluginManager().registerEvents(new Blessings(), this);
        Bukkit.getPluginManager().registerEvents(new GodPowers(), this);
        Bukkit.getPluginManager().registerEvents(new Curses(), this);
        Bukkit.getPluginManager().registerEvents(new Taunt(), this);
        Bukkit.getPluginManager().registerEvents(new Flood(), this);
        Bukkit.getPluginManager().registerEvents(new SelectPowers(), this);
        Bukkit.getPluginManager().registerEvents(new UsePowers(), this);

        // Plugin Commands
        getCommand("faith").setExecutor(new FaithCommand());
        getCommand("pray").setExecutor(new Pray(this));

        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder();

        // Loads the unique players list when the server starts
        try{
            getDataFolder().mkdir();
            File file = new File(getDataFolder(), "unique_players.json");
            if (!file.exists()){
                file.createNewFile();
            }

            Gson gson = new Gson();
            Reader reader = new FileReader(file);
            UniquePlayers read_unique_players = gson.fromJson(reader, UniquePlayers.class);
            unique_player_list.addAll(read_unique_players.getUnique_player_list());
        }catch(IOException exception){exception.printStackTrace();}
    }

    // When players join the server for the first time, they get added to the unique players list
    // The unique players list is used to check for religion files that share a UUID with a player
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if (!unique_player_list.contains(e.getPlayer().getUniqueId())){
            unique_player_list.add(e.getPlayer().getUniqueId());
            PlayerData player_data = new PlayerData(e.getPlayer().getUniqueId(), e.getPlayer().getUniqueId(), "", false, false,
                    0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, false, e.getPlayer().getUniqueId(),
                    false, false, false, false,
                    false, false, false, false, false);
            try{
                File player_data_folder = new File(getDataFolder(), "PlayerData");
                if (!player_data_folder.exists()){player_data_folder.mkdir();}

                File file = new File(player_data_folder, e.getPlayer().getUniqueId() + ".json");
                if (!file.exists()){file.createNewFile();}

                Gson gson = new Gson();
                Writer writer = new FileWriter(file, false);
                gson.toJson(player_data, writer);
                writer.flush();
                writer.close();

            } catch (Exception ex) {ex.printStackTrace();}
        }

        System.out.println("A player joined the server");
        System.out.println(unique_player_list);
        e.getPlayer().sendMessage(String.valueOf(unique_player_list));
        UniquePlayers unique_players = new UniquePlayers(unique_player_list);
        try{
            getDataFolder().mkdir();
            File file = new File(getDataFolder(), "unique_players.json");
            if (!file.exists()){file.createNewFile();}

            Gson gson = new Gson();
            Writer writer = new FileWriter(file, false);
            gson.toJson(unique_players, writer);
            writer.flush();
            writer.close();
        }catch(IOException exception){System.out.println(exception);}
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

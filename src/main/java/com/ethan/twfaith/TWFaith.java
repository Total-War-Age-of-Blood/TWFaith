package com.ethan.twfaith;

import com.ethan.twfaith.commands.FaithCommand;
import com.ethan.twfaith.activepowers.Flood;
import com.ethan.twfaith.commands.Pray;
import com.ethan.twfaith.activepowers.Taunt;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.customevents.UsePowers;
import com.ethan.twfaith.guis.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Objects;

public final class TWFaith extends JavaPlugin implements Listener {
    // TODO add cool powers that are unlocked with faith points
    // TODO Replace hard-coded values with configurable ones where applicable
    // TODO Load PlayerData into hashmap when a player joins
    //  and save it to file when player leaves or when server shuts down


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
        Bukkit.getPluginManager().registerEvents(new PlayerHashMap(), this);

        // Plugin Commands
        Objects.requireNonNull(getCommand("faith")).setExecutor(new FaithCommand());
        Objects.requireNonNull(getCommand("pray")).setExecutor(new Pray(this));

        Bukkit.getPluginManager().registerEvents(this, this);

        Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder();

        // Check to see if the necessary directories already exist.
        File player_data_folder = new File(getDataFolder(), "PlayerData");
        if (!player_data_folder.exists()) {player_data_folder.mkdir();}
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package com.ethan.twfaith;

import com.ethan.twfaith.data.FaithHashMap;
import com.ethan.twfaith.powers.*;
import com.ethan.twfaith.commands.FaithCommand;
import com.ethan.twfaith.commands.Pray;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.customevents.UsePowers;
import com.ethan.twfaith.guis.*;
import com.ethan.twfaith.powers.TerrainBonus;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Objects;

public final class TWFaith extends JavaPlugin implements Listener {
    private static TWFaith plugin;

    // TODO Move upgrades from PlayerData class to Faith class
    // TODO add cool powers that are unlocked with faith points
    // TODO Replace hard-coded values with configurable ones where applicable
    // TODO Load PlayerData into hashmap when a player joins
    //  and save it to file when player leaves or when server shuts down

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // Primary config
        if(!getDataFolder().exists()){getDataFolder().mkdir();}
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Event Listeners
        Bukkit.getPluginManager().registerEvents(new FaithUpgrade(), this);
        Bukkit.getPluginManager().registerEvents(new Blessings(), this);
        Bukkit.getPluginManager().registerEvents(new GodPowers(), this);
        Bukkit.getPluginManager().registerEvents(new Curses(), this);
        Bukkit.getPluginManager().registerEvents(new SummonGod(), this);
        Bukkit.getPluginManager().registerEvents(new Taunt(), this);
        Bukkit.getPluginManager().registerEvents(new Flood(), this);
        Bukkit.getPluginManager().registerEvents(new SelectPowers(), this);
        Bukkit.getPluginManager().registerEvents(new UsePowers(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerHashMap(), this);
        Bukkit.getPluginManager().registerEvents(new Crumbling(), this);
        Bukkit.getPluginManager().registerEvents(new HeavyBoots(), this);
        Bukkit.getPluginManager().registerEvents(new Intoxicate(), this);
        Bukkit.getPluginManager().registerEvents(new Discombobulate(), this);
        Bukkit.getPluginManager().registerEvents(new Entangle(), this);
        Bukkit.getPluginManager().registerEvents(new SummonGod(), this);
        Bukkit.getPluginManager().registerEvents(new PowerfulFlock(), this);
        Bukkit.getPluginManager().registerEvents(new HellsFury(), this);
        Bukkit.getPluginManager().registerEvents(new DivineIntervention(), this);
        Bukkit.getPluginManager().registerEvents(new Mana(), this);
        Bukkit.getPluginManager().registerEvents(new TerrainBonus(), this);
        Bukkit.getPluginManager().registerEvents(new com.ethan.twfaith.guis.TerrainBonus(), this);

        // Plugin Commands
        Objects.requireNonNull(getCommand("faith")).setExecutor(new FaithCommand());
        Objects.requireNonNull(getCommand("pray")).setExecutor(new Pray(this));

        Bukkit.getPluginManager().registerEvents(this, this);

        // Check to see if the necessary directories already exist.
        File player_data_folder = new File(getDataFolder(), "PlayerData");
        if (!player_data_folder.exists()) {player_data_folder.mkdir();}

        // Load Faiths data from files
        FaithHashMap player_faith_hashmap = new FaithHashMap();
        player_faith_hashmap.loadFaiths();
    }

    @Override
    public void onDisable() {
        // Save Faiths data to files
        FaithHashMap player_faith_hashmap = new FaithHashMap();
        player_faith_hashmap.saveFaiths();
    }

    public static TWFaith getPlugin() {
        return plugin;
    }
}

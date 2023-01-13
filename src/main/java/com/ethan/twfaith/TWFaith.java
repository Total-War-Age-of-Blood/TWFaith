package com.ethan.twfaith;

import com.ethan.twfaith.customevents.FaithTab;
import com.ethan.twfaith.customevents.GodDeath;
import com.ethan.twfaith.data.BossBars;
import com.ethan.twfaith.data.FaithHashMap;
import com.ethan.twfaith.commands.FaithCommand;
import com.ethan.twfaith.commands.Pray;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.customevents.UsePowers;
import com.ethan.twfaith.guis.*;
import com.ethan.twfaith.powers.blessings.TerrainBonus;
import com.ethan.twfaith.powers.blessings.*;
import com.ethan.twfaith.powers.curses.*;
import com.ethan.twfaith.powers.godpowers.Flood;
import com.ethan.twfaith.powers.godpowers.Taunt;
import com.ethan.twfaith.tasks.EffectsGiver;
import com.ethan.twfaith.tasks.Heavy_Boots_Checker;
import com.ethan.twfaith.tasks.StaminaChecker;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.util.Objects;

public final class TWFaith extends JavaPlugin implements Listener {
    private static TWFaith plugin;
    private static Gson gson = new Gson();
    public static Gson getGson() {return gson;}

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
        Bukkit.getPluginManager().registerEvents(new Intoxicate(), this);
        Bukkit.getPluginManager().registerEvents(new Discombobulate(), this);
        Bukkit.getPluginManager().registerEvents(new Entangle(), this);
        Bukkit.getPluginManager().registerEvents(new SummonGod(), this);
        Bukkit.getPluginManager().registerEvents(new PowerfulFlock(), this);
        Bukkit.getPluginManager().registerEvents(new HellsFury(), this);
        Bukkit.getPluginManager().registerEvents(new DivineIntervention(), this);
        Bukkit.getPluginManager().registerEvents(new Mana(), this);
        Bukkit.getPluginManager().registerEvents(new TerrainBonus(), this);
        Bukkit.getPluginManager().registerEvents(new TerrainBonusUpgrade(), this);
        Bukkit.getPluginManager().registerEvents(new TerrainBonusEquip(), this);
        Bukkit.getPluginManager().registerEvents(new BossBars(), this);
        Bukkit.getPluginManager().registerEvents(new GodDeath(), this);

        // Plugin Commands
        Objects.requireNonNull(getCommand("faith")).setExecutor(new FaithCommand());
        Objects.requireNonNull(getCommand("faith")).setTabCompleter(new FaithTab());
        Objects.requireNonNull(getCommand("pray")).setExecutor(new Pray(this));

        Bukkit.getPluginManager().registerEvents(this, this);

        // Check to see if the necessary directories already exist.
        File player_data_folder = new File(getDataFolder(), "PlayerData");
        if (!player_data_folder.exists()) {player_data_folder.mkdir();}

        // Load Faiths data from files
        FaithHashMap player_faith_hashmap = new FaithHashMap();
        player_faith_hashmap.loadFaiths();

        // Timer Tasks for Cooldowns and Stamina
        BukkitTask stamina_checker = new StaminaChecker().runTaskTimer(this, 0, 20);
        BukkitTask effects_giver = new EffectsGiver().runTaskTimer(this, 0, 20);
        BukkitTask heavy_boots_checker = new Heavy_Boots_Checker().runTaskTimer(this, 0, 20);

    }

    @Override
    public void onDisable() {
        // Save Faiths data to files
        FaithHashMap player_faith_hashmap = new FaithHashMap();
        player_faith_hashmap.saveFaiths();

        // Save PlayerData to files
        for (PlayerData player_data : PlayerHashMap.player_data_hashmap.values()){
            Player player = Bukkit.getPlayer(player_data.getUuid());
            // Switching off the player's powers
            player_data.setExplosive_landing_active(false);
            player_data.setInsidious_active(false);
            player_data.setSavior_active(false);
            player_data.setCrumbling_active(false);
            player_data.setHeavy_boots_active(false);
            player_data.setLions_heart_active(false);
            player_data.setTerrain_bonus_active(false);
            player_data.setSummon_god_active(false);
            player_data.setHells_fury_active(false);
            player_data.setPowerful_flock_active(false);
            player_data.setIn_flock(false);
            player_data.setIntoxicate_victim(false);
            player_data.setCrumbling_victim(false);
            player_data.setDiscombobulate_victim(false);
            player_data.setEntangle_victim(false);
            player_data.setHeavy_boots_victim(false);

            // Saving data from HashMap to File
            try{
                File player_folder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "PlayerData");
                File player_file = new File(player_folder, player.getUniqueId() + ".json");
                FileWriter player_data_writer = new FileWriter(player_file, false);
                gson.toJson(player_data, player_data_writer);
                player_data_writer.flush();
                player_data_writer.close();
                System.out.println("Player File Saved!");
            }catch (IOException exception){exception.printStackTrace();}
        }
    }

    public static TWFaith getPlugin() {
        return plugin;
    }
}

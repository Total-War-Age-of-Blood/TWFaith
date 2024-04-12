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
import com.ethan.twfaith.tasks.AutoSave;
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
    public static TWFaith getPlugin() {
        return plugin;
    }
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
        File playerData = new File(getDataFolder(), "PlayerData");
        if (!playerData.exists()) {playerData.mkdir();}

        // Load Faiths data from files
        FaithHashMap playerFaithHashmap = new FaithHashMap();
        playerFaithHashmap.loadFaiths();

        // Timer Tasks for Cooldowns and Stamina
        BukkitTask staminaChecker = new StaminaChecker().runTaskTimer(this, 0, 20);
        BukkitTask effectsGiver = new EffectsGiver().runTaskTimer(this, 0, 20);
        BukkitTask heavyBootsChecker = new Heavy_Boots_Checker().runTaskTimer(this, 0, 20);
        int autoSavePeriod = this.getConfig().getInt("auto-save");
        autoSavePeriod = autoSavePeriod * 20 * 60;
        BukkitTask autoSave = new AutoSave().runTaskTimer(this, autoSavePeriod, autoSavePeriod);
    }

    @Override
    public void onDisable() {
        // Save Faiths data to files
        FaithHashMap playerFaithHashmap = new FaithHashMap();
        playerFaithHashmap.saveFaiths();

        // Save PlayerData to files
        for (PlayerData playerData : PlayerHashMap.playerDataHashMap.values()){
            Player player = Bukkit.getPlayer(playerData.getUuid());
            // Switching off the player's powers
            playerData.setExplosiveLandingActive(false);
            playerData.setInsidiousActive(false);
            playerData.setSaviorActive(false);
            playerData.setCrumblingActive(false);
            playerData.setHeavyBootsActive(false);
            playerData.setLionsHeartActive(false);
            playerData.setTerrainBonusActive(false);
            playerData.setSummonGodActive(false);
            playerData.setHellsFuryActive(false);
            playerData.setPowerfulFlockActive(false);
            playerData.setInFlock(false);
            playerData.setIntoxicateVictim(false);
            playerData.setCrumblingVictim(false);
            playerData.setDiscombobulateVictim(false);
            playerData.setEntangleVictim(false);
            playerData.setHeavyBootsVictim(false);

            // Saving data from HashMap to File
            try{
                File player_folder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "PlayerData");
                File player_file = new File(player_folder, playerData.getUuid() + ".json");
                FileWriter player_data_writer = new FileWriter(player_file, false);
                gson.toJson(playerData, player_data_writer);
                player_data_writer.flush();
                player_data_writer.close();
                System.out.println("Player File Saved!");
            }catch (IOException exception){exception.printStackTrace();}
        }
    }
}

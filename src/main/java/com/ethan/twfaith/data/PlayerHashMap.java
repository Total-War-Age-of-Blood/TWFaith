package com.ethan.twfaith.data;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.tasks.Heavy_Boots_Checker;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
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
    public static HashMap<UUID, PlayerData> playerDataHashMap = new HashMap<>();

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
            try{
                System.out.println("Putting PlayerData in HashMap.");
                FileReader player_file_reader = new FileReader(player_file);
                PlayerData player_data = TWFaith.getGson().fromJson(player_file_reader, PlayerData.class);
                playerDataHashMap.put(player.getUniqueId(), player_data);
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
                false, false, false, 0, 0, 0,
                0, false, false, false, false, 0, false, 100, 100, new HashMap<>());
        try {
            // Check to see if the necessary directories already exist.
            File player_data_folder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "PlayerData");
            if (!player_data_folder.exists()) {player_data_folder.mkdir();}

            File file = new File(player_data_folder, e.getPlayer().getUniqueId() + ".json");
            if (!file.exists()) {file.createNewFile();}

            Writer writer = new FileWriter(file, false);
            TWFaith.getGson().toJson(player_data, writer);
            writer.flush();
            writer.close();

        } catch (Exception ex) {ex.printStackTrace();}

        // Add the player's data to the hashmap
        playerDataHashMap.put(e.getPlayer().getUniqueId(), player_data);
        System.out.println("Player " + e.getPlayer().getDisplayName() + " added to HashMap.");
    }

    // Save data from HashMap to file when player quits, then remove player from HashMap
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        // Switching off the player's powers
        PlayerData player_data = playerDataHashMap.get(player.getUniqueId());
        player_data.setExplosiveLandingActive(false);
        player_data.setInsidiousActive(false);
        player_data.setSaviorActive(false);
        player_data.setCrumblingActive(false);
        player_data.setHeavyBootsActive(false);
        Heavy_Boots_Checker.heavy_boots.remove(player.getUniqueId());
        player_data.setLionsHeartActive(false);
        player_data.setTerrainBonusActive(false);
        player_data.setSummonGodActive(false);
        player_data.setHellsFuryActive(false);
        // We need to remove the powerful flock health bonus when the god logs off.
        if (player_data.powerful_flock_active){
            // System.out.println("Powerful flock active");
            player_data.setPowerfulFlockActive(false);
            for (Player follower : Bukkit.getOnlinePlayers()){
                PlayerData follower_data = PlayerHashMap.playerDataHashMap.get(follower.getUniqueId());
                if (follower_data.isInFlock() && follower_data.getLed_by().equals(player_data.getLed_by())){
                    // System.out.println("Follower needs to be deactivated");
                    int nearby_friends = 0;
                    for (Player nearby : Bukkit.getOnlinePlayers()){
                        PlayerData nearby_data = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
                        if (!player.getWorld().equals(nearby.getWorld())){continue;}
                        if (nearby.getLocation().distance(follower.getLocation()) <= 30 && nearby_data.getLed_by().equals(follower_data.getLed_by())){
                            nearby_friends ++;
                            // System.out.println(nearby_friends + " nearby friends");
                        }
                    }
                    Objects.requireNonNull(follower.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() - nearby_friends);
                    follower_data.setInFlock(false);
                }
            }
        }
        player_data.setInFlock(false);
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
            TWFaith.getGson().toJson(player_data, player_data_writer);
            player_data_writer.flush();
            player_data_writer.close();
            System.out.println("Player File Saved!");
        }catch (IOException exception){exception.printStackTrace();}

        // Remove player from HashMap
        playerDataHashMap.remove(player.getUniqueId());
        System.out.println(player.getDisplayName() + " has been removed from HashMap.");
    }
}

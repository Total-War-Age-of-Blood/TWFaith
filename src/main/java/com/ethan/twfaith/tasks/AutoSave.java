package com.ethan.twfaith.tasks;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.FaithHashMap;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class AutoSave extends BukkitRunnable {

    private static boolean change = false;

    public static void setChange(boolean change) {
        AutoSave.change = change;
    }

    @Override
    public void run() {
        if (!change){System.out.println("No change. Did not autoSave TWFaith");
        return;}

        FaithHashMap playerFaithHashmap = new FaithHashMap();
        playerFaithHashmap.saveFaiths();

        HashMap<UUID, PlayerData> tempPlayerDataHashMap = new HashMap<>();
        for (UUID uuid : PlayerHashMap.playerDataHashMap.keySet()){
            PlayerData playerData = new PlayerData(PlayerHashMap.playerDataHashMap.get(uuid));
            tempPlayerDataHashMap.put(uuid, playerData);
        }

        Gson gson = TWFaith.getGson();
        for (PlayerData playerData : tempPlayerDataHashMap.values()){
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

        change = false;
        System.out.println("AutoSaved TWFaith");
    }
}

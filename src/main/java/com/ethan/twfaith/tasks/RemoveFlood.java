package com.ethan.twfaith.tasks;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.Flood;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;


import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.UUID;

public class RemoveFlood extends BukkitRunnable implements Listener {
    UUID uuid;
    List<Block> blockList;
    public RemoveFlood(UUID uuid, List<Block> block_list) {
        this.uuid = uuid;
        this.blockList = block_list;
    }

    @Override
    public void run() {
        System.out.println("Removing Flood");
        for (Block block : blockList){
            // System.out.println(block.getType());
            if (block.getType().equals(Material.WATER)){
                //System.out.println("Draining water");
                block.setType(Material.AIR, false);
            }
        }
        // Iterate through the flood files to figure out which one we are removing and then delete the file.
        try{
            File flood_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Floods");
            File[] floodFiles = flood_folder.listFiles();
            for (File file : floodFiles){
                FileReader floodReader = new FileReader(file);
                Flood floodData = TWFaith.getGson().fromJson(floodReader, Flood.class);
                if (floodData.getUuid().equals(uuid)){
                    file.delete();
                    break;
                }
            }
        } catch (Exception e) {e.printStackTrace();}
    }
}

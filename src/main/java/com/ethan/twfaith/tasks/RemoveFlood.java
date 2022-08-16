package com.ethan.twfaith.tasks;

import com.ethan.twfaith.data.Flood;
import com.google.gson.Gson;
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
    List<Block> block_list;
    public RemoveFlood(UUID uuid, List<Block> block_list) {
        this.uuid = uuid;
        this.block_list = block_list;
    }

    @Override
    public void run() {
        System.out.println("Removing Flood");
        for (Block block : block_list){
            // System.out.println(block.getType());
            if (block.getType().equals(Material.WATER)){
                //System.out.println("Draining water");
                block.setType(Material.AIR, false);
            }
        }
        // Iterate through the flood files to figure out which one we are removing and then delete the file.
        try{
            File flood_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Floods");
            File[] flood_files = flood_folder.listFiles();
            for (File file : flood_files){
                FileReader flood_reader = new FileReader(file);
                Gson gson = new Gson();
                Flood flood_data = gson.fromJson(flood_reader, Flood.class);
                if (flood_data.getUuid().equals(uuid)){
                    file.delete();
                    break;
                }
            }
        } catch (Exception e) {e.printStackTrace();}
    }
}

package com.ethan.twfaith.powers.godpowers;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.tasks.RemoveFlood;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Flood implements Listener {
    List<Block> block_list = new ArrayList<>();
    ArrayList<ArrayList<Integer>> block_coordinates = new ArrayList<>();

    @EventHandler
    public void onWaterFlow(BlockFromToEvent event){
        if (!event.getBlock().getType().equals(Material.WATER)){return;}
        // System.out.println("Water flow detected.");
        try{
            // Because it is hard to get a unique identifier from the BlockFromToEvent that would also be given to a
            // Flood object, we are going to iterate through the Floods directory and test all block lists.
            File flood_folder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "Floods");
            File[] flood_files = flood_folder.listFiles();
            for (File flood : flood_files){
                FileReader flood_reader = new FileReader(flood);
                Gson gson = new Gson();
                com.ethan.twfaith.data.Flood flood_data = gson.fromJson(flood_reader, com.ethan.twfaith.data.Flood.class);

                ArrayList<Integer> event_block = new ArrayList<>();
                event_block.add(event.getBlock().getX());
                event_block.add(event.getBlock().getZ());

                if (flood_data.getBlock_coordinates().contains(event_block)){
                    event.setCancelled(true);
                    // System.out.println("Water Flow Cancelled");
                    break;
                }
            }
        } catch (FileNotFoundException e) {e.printStackTrace();}
        // If the flood files folder is empty, it will throw a lot of null pointer exceptions at the console.
        // My solution is to just ignore them.
        catch (NullPointerException ignored){}
    }

    public void floodTrigger(Player player){
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
        Gson gson = new Gson();
        try{
            if (player_data.getFlood() < 1){return;}
            Location player_location = player.getLocation();
            World world = player_location.getWorld();
            int radius = 30;
            // We have to loop through all the x and z values within the radius, looking for the block with the highest
            // y value. Then, we replace the block above that one with water.
            for (int x = -radius; x < radius; x++) {
                for (int z = -radius; z < radius; z++) {
                    assert world != null;
                    Block block = world.getHighestBlockAt(player_location.getBlockX()+x, player_location.getBlockZ()+z);
                    Block air_block = world.getBlockAt(block.getX(), block.getY() + 1, block.getZ());
                    air_block.setType(Material.WATER, false);
                    block_list.add(air_block);

                    ArrayList<Integer> coordinates = new ArrayList<>();
                    coordinates.add(air_block.getX());
                    coordinates.add(air_block.getZ());
                    block_coordinates.add(coordinates);
                }
            }

            // Storing the block list for the flood in a JSON file so the EventHandler that stops water flowing in the
            // zone can reference it.
            com.ethan.twfaith.data.Flood flood = new com.ethan.twfaith.data.Flood(player.getUniqueId(), block_coordinates);
            File flood_folder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "Floods");
            if (!flood_folder.exists()){flood_folder.mkdir();}
            File flood_file = new File(flood_folder, player.getUniqueId() + ".json");
            if (!flood_file.exists()){flood_file.createNewFile();}

            FileWriter flood_writer = new FileWriter(flood_file);
            gson.toJson(flood, flood_writer);
            flood_writer.flush();
            flood_writer.close();

            // We use a BukkitTask to replace the water with air after a period of time
            BukkitTask remove_flood = new RemoveFlood(player.getUniqueId(), block_list).runTaskLater(TWFaith.getPlugin(TWFaith.class), 200);
        }catch (IOException exception){exception.printStackTrace();}
    }
}

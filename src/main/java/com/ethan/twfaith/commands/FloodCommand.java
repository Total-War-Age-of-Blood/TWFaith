package com.ethan.twfaith.commands;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.Flood;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.tasks.RemoveFlood;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FloodCommand implements CommandExecutor, Listener {
    List<Block> block_list = new ArrayList<>();
    ArrayList<ArrayList<Integer>> block_coordinates = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){return true;}
        Player player = (Player) sender;
        File player_data_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(),
                "PlayerData");
        File player_data_file = new File(player_data_folder, player.getUniqueId() + ".json");
        Gson gson = new Gson();
        try{
            Reader player_data_reader = new FileReader(player_data_file);
            PlayerData player_data = gson.fromJson(player_data_reader, PlayerData.class);
            if (player_data.getFlood() < 1){return true;}
            Location player_location = player.getLocation();
            World world = player_location.getWorld();
            int radius = 30;
            // We have to loop through all the x and z values within the radius, looking for the block with the highest
            // y value. Then, we replace the block above that one with water.
            for (int x = -radius; x < radius; x++) {
                for (int z = -radius; z < radius; z++) {
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
            Flood flood = new Flood(player.getUniqueId(), block_coordinates);
            File flood_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Floods");
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
        return false;
    }

    @EventHandler
    public void onWaterFlow(BlockFromToEvent event){
        if (!event.getBlock().getType().equals(Material.WATER)){return;}
        //System.out.println("Water flow detected.");
        try{
            // Because it is hard to get a unique identifier from the BlockFromToEvent that would also be given to a
            // Flood object, we are going to iterate through the Floods directory and test all block lists.
            File flood_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Floods");
            File[] flood_files = flood_folder.listFiles();
            for (File flood : flood_files){
                FileReader flood_reader = new FileReader(flood);
                Gson gson = new Gson();
                Flood flood_data = gson.fromJson(flood_reader, Flood.class);

                ArrayList<Integer> event_block = new ArrayList<>();
                event_block.add(event.getBlock().getX());
                event_block.add(event.getBlock().getZ());

                if (flood_data.getBlock_coordinates().contains(event_block)){
                    event.setCancelled(true);
                    System.out.println("Water Flow Cancelled");
                    break;
                }
            }
        } catch (FileNotFoundException e) {e.printStackTrace();}
    }
}

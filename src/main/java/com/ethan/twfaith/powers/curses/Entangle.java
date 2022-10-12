package com.ethan.twfaith.powers.curses;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Entangle implements Listener {

    ArrayList<Biome> OAK = new ArrayList<>(Arrays.asList(Biome.WINDSWEPT_HILLS, Biome.WINDSWEPT_GRAVELLY_HILLS, Biome.WINDSWEPT_FOREST, Biome.PLAINS,
            Biome.SUNFLOWER_PLAINS, Biome.FOREST, Biome.FLOWER_FOREST, Biome.SWAMP, Biome.JUNGLE, Biome.SPARSE_JUNGLE,
            Biome.BAMBOO_JUNGLE, Biome.MEADOW, Biome.WINDSWEPT_SAVANNA, Biome.WOODED_BADLANDS));
    ArrayList<Biome> SPRUCE = new ArrayList<>(Arrays.asList(Biome.SNOWY_PLAINS, Biome.SNOWY_TAIGA, Biome.GROVE, Biome.WINDSWEPT_HILLS,
            Biome.WINDSWEPT_GRAVELLY_HILLS, Biome.WINDSWEPT_FOREST, Biome.TAIGA, Biome.OLD_GROWTH_PINE_TAIGA, Biome.OLD_GROWTH_SPRUCE_TAIGA));
    ArrayList<Biome> DARK_OAK = new ArrayList<>(Collections.singletonList(Biome.DARK_FOREST));
    ArrayList<Biome> BIRCH = new ArrayList<>(Arrays.asList(Biome.BIRCH_FOREST, Biome.OLD_GROWTH_BIRCH_FOREST, Biome.FOREST, Biome.MEADOW));
    ArrayList<Biome> JUNGLE = new ArrayList<>(Arrays.asList(Biome.BAMBOO_JUNGLE, Biome.JUNGLE, Biome.SPARSE_JUNGLE));
    ArrayList<Biome> ACACIA = new ArrayList<>(Arrays.asList(Biome.SAVANNA, Biome.SAVANNA_PLATEAU, Biome.WINDSWEPT_SAVANNA));
    public void onEntangleTrigger(Player player, PlayerData player_data){

        if (player_data.getStamina() < TWFaith.getPlugin().getConfig().getInt("entangle-stamina")){
            player.sendMessage(ChatColor.RED + "Not enough stamina.");
            return;
        }
        player_data.setStamina(player_data.getStamina() - TWFaith.getPlugin().getConfig().getInt("entangle-stamina"));

        for (Player heathen : Bukkit.getOnlinePlayers()){
            PlayerData heathen_data = PlayerHashMap.player_data_hashmap.get(heathen.getUniqueId());
            if (!player.getWorld().equals(heathen.getWorld())){continue;}
            if (heathen.getLocation().distance(player.getLocation()) > 30 || heathen_data.getFaith().equals(player_data.getFaith())){continue;}
            Location heathen_loc = heathen.getLocation();
            // We find the coordinates we need to place the blocks by looping a 1 block radius around the player.
            // If we ever want the cage walls to be farther than 1 block away from the player, but only 1 block thick,
            // we will need to create a new loop that only finds the border of the radius.
            int radius = 1;
            for (int x = -radius; x <= radius; x++){
                for (int z = -radius; z <= radius; z++){
                    if (x == 0 && z == 0){continue;}
                    Block block = heathen.getWorld().getBlockAt(heathen_loc.getBlockX() + x, heathen_loc.getBlockY(), heathen_loc.getBlockZ() + z);
                    World world = block.getWorld();
                    // Because entangle makes trees sprout from the ground to surround enemies, it would be cool to have the type of trees
                    // match the biome the players are in. Also, it should not work in biomes that cannot spawn trees.
                    List<TreeType> possible_trees = new ArrayList<>();
                    if (OAK.contains(block.getBiome())){
                        possible_trees.add(TreeType.TREE);
                    }
                    if (SPRUCE.contains(block.getBiome())){
                        possible_trees.add(TreeType.TALL_REDWOOD);
                    }
                    if (DARK_OAK.contains(block.getBiome())){
                        possible_trees.add(TreeType.DARK_OAK);
                    }
                    if (BIRCH.contains(block.getBiome())){
                        possible_trees.add(TreeType.BIRCH);
                    }
                    if (JUNGLE.contains(block.getBiome())){
                        possible_trees.add(TreeType.JUNGLE);
                    }
                    if (ACACIA.contains(block.getBiome())){
                        possible_trees.add(TreeType.ACACIA);
                    }

                    // We will randomly choose one of the valid tree types by shuffling the list
                    // and generating the tree that is first in the list
                    Collections.shuffle(possible_trees);
                    if (possible_trees.size() == 0){continue;}
                    boolean generate = world.generateTree(block.getLocation(), possible_trees.get(0));
                    System.out.println(generate);
                }
            }
        }
    }
}

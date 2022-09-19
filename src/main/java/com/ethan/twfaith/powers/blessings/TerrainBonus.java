package com.ethan.twfaith.powers.blessings;

import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TerrainBonus implements Listener {
    ArrayList<Biome> SNOWY = new ArrayList<>(Arrays.asList(Biome.SNOWY_PLAINS, Biome.SNOWY_TAIGA, Biome.SNOWY_BEACH, Biome.SNOWY_SLOPES, Biome.ICE_SPIKES, Biome.GROVE, Biome.JAGGED_PEAKS, Biome.FROZEN_PEAKS));
    ArrayList<Biome> COLD = new ArrayList<>(Arrays.asList(Biome.WINDSWEPT_HILLS, Biome.WINDSWEPT_GRAVELLY_HILLS, Biome.WINDSWEPT_FOREST, Biome.TAIGA, Biome.OLD_GROWTH_PINE_TAIGA, Biome.OLD_GROWTH_SPRUCE_TAIGA, Biome.STONY_SHORE));
    ArrayList<Biome> TEMPERATE = new ArrayList<>(Arrays.asList(Biome.PLAINS, Biome.SUNFLOWER_PLAINS, Biome.FOREST, Biome.FLOWER_FOREST,
            Biome.BIRCH_FOREST, Biome.OLD_GROWTH_BIRCH_FOREST, Biome.DARK_FOREST, Biome.SWAMP, Biome.MANGROVE_SWAMP, Biome.JUNGLE,
            Biome.SPARSE_JUNGLE, Biome.BAMBOO_JUNGLE, Biome.BEACH, Biome.MUSHROOM_FIELDS, Biome.MEADOW, Biome.STONY_PEAKS));
    ArrayList<Biome> WARM = new ArrayList<>(Arrays.asList(Biome.DESERT, Biome.SAVANNA, Biome.SAVANNA_PLATEAU, Biome.WINDSWEPT_SAVANNA, Biome.BADLANDS, Biome.WOODED_BADLANDS, Biome.ERODED_BADLANDS));
    ArrayList<Biome> AQUATIC = new ArrayList<>(Arrays.asList(Biome.RIVER, Biome.FROZEN_RIVER, Biome.WARM_OCEAN,
            Biome.LUKEWARM_OCEAN, Biome.DEEP_LUKEWARM_OCEAN, Biome.OCEAN, Biome.COLD_OCEAN, Biome.DEEP_COLD_OCEAN, Biome.DEEP_OCEAN, Biome.FROZEN_OCEAN, Biome.DEEP_FROZEN_OCEAN));
    ArrayList<Biome> CAVE = new ArrayList<>(Arrays.asList(Biome.DEEP_DARK, Biome.DRIPSTONE_CAVES, Biome.LUSH_CAVES));
    ArrayList<Biome> NETHER = new ArrayList<>(Arrays.asList(Biome.NETHER_WASTES, Biome.SOUL_SAND_VALLEY, Biome.CRIMSON_FOREST, Biome.WARPED_FOREST, Biome.BASALT_DELTAS));
    ArrayList<Biome> END = new ArrayList<>(Arrays.asList(Biome.THE_END, Biome.SMALL_END_ISLANDS, Biome.END_MIDLANDS, Biome.END_HIGHLANDS, Biome.END_BARRENS));

    public void terrainToggle(String display_name, Player player){
        Faith faith_data = FaithHashMap.player_faith_hashmap.get(player.getUniqueId());
        List<String> terrain_active_powers = faith_data.getTerrain_active_powers();
        // If the power was already listed as active, remove it from the list to deactivate it.
        for (String power : terrain_active_powers){
            if (power.equals(display_name)){
                terrain_active_powers.remove(power);
                faith_data.setTerrain_active_powers(terrain_active_powers);
                System.out.println(power);
                return;
            }
        }
        // If the power was not already listed, add it to the list to activate it.
        terrain_active_powers.add(display_name);
        faith_data.setTerrain_active_powers(terrain_active_powers);
    }

    // TODO it would be better for performance to replace this event with a repeating task
    @EventHandler
    public void onTerrainTrigger(PlayerMoveEvent event){
        Player player = event.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
        if (!player_data.getIn_faith()){return;}
        Faith faith = FaithHashMap.player_faith_hashmap.get(player.getUniqueId());

        // if (!faith.isTerrain_bonus_active()){return;}

        // First, get the biome the player is in. Then, see if there are any powers that apply to the biome.
        // Finally, apply those powers to the player.
        Biome biome = player.getLocation().getBlock().getBiome();
        String biome_group = biomeSearch(biome);
        for (String power : faith.getTerrain_active_powers()){
            if (power.contains(biome_group)){
                if (power.contains("Strength")){
                    player.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(100, 0));
                }
                if (power.contains("Haste")){
                    player.addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(100, 0));
                }
                if (power.contains("Speed")){
                    player.addPotionEffect(PotionEffectType.SPEED.createEffect(100, 0));
                }
                if (power.contains("Resistance") && !power.contains("Fire")){
                    player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(100, 0));
                }
                if (power.contains("Fire Resistance")){
                    player.addPotionEffect(PotionEffectType.FIRE_RESISTANCE.createEffect(100, 0));
                    System.out.println("Fire Resistance");
                }
                if (power.contains("Water Breathing")){
                    player.addPotionEffect(PotionEffectType.WATER_BREATHING.createEffect(100, 0));
                }
                if (power.contains("Dolphin's Grace")){
                    player.addPotionEffect(PotionEffectType.DOLPHINS_GRACE.createEffect(100, 0));
                }
            }
        }
    }

    public String biomeSearch(Biome biome){
        for (Biome snowy : SNOWY){
            if (snowy.name().equals(biome.name())){
                return "Snowy";
            }
        }
        for (Biome cold : COLD){
            if (cold.name().equals(biome.name())){
                return "Cold";
            }
        }
        for (Biome temperate : TEMPERATE){
            if (temperate.name().equals(biome.name())){
                return "Temperate";
            }
        }
        for (Biome warm : WARM){
            if (warm.name().equals(biome.name())){
                return "Warm";
            }
        }
        for (Biome aquatic : AQUATIC){
            if (aquatic.name().equals(biome.name())){
                return "Aquatic";
            }
        }
        for (Biome cave : CAVE){
            if (cave.name().equals(biome.name())){
                return "Cave";
            }
        }
        for (Biome nether : NETHER){
            if (nether.name().equals(biome.name())){
                return "Nether";
            }

        }
        for (Biome end : END){
            if (end.name().equals(biome.name())){
                return "End";
            }
        }
        return null;
    }
}

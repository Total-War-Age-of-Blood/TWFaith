package com.ethan.twfaith.customevents;

import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.powers.blessings.DivineIntervention;
import com.ethan.twfaith.powers.blessings.HellsFury;
import com.ethan.twfaith.powers.blessings.Mana;
import com.ethan.twfaith.powers.blessings.TerrainBonus;
import com.ethan.twfaith.powers.curses.Discombobulate;
import com.ethan.twfaith.powers.curses.Entangle;
import com.ethan.twfaith.powers.curses.HeavyBoots;
import com.ethan.twfaith.powers.curses.Intoxicate;
import com.ethan.twfaith.powers.godpowers.Flood;
import com.ethan.twfaith.powers.godpowers.Taunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.LinkedHashSet;
import java.util.Objects;

public class UsePowers implements Listener {

    public static LinkedHashSet<String> crumblers = new LinkedHashSet<>();

    // TODO rewrite the Persistent Data system to allow for the Terrain bonus information
    //  to be easily accessed without a ton of cases in the switch statement
    // When player right clicks, check if the item in main hand has the NBT tag for a god power. If it does, activate the power.
    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        Player player = e.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
        ItemStack held_item = e.getPlayer().getInventory().getItemInMainHand();
        boolean is_power;
        // Using Objects.requireNonNull is not enough to stop the console from getting spammed. To prevent console spam,
        // you have to catch the error.
        try{
            is_power = Objects.requireNonNull(held_item.getItemMeta()).getPersistentDataContainer().has(new NamespacedKey(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")), "Power"), PersistentDataType.STRING);
        }catch (NullPointerException exception){return;}
        if (!is_power){return;}
        e.setCancelled(true);
            switch (Objects.requireNonNull(held_item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")), "Power"), PersistentDataType.STRING))){
                case "Summon God":
                    if (player_data.isSummon_god_active()){
                        player_data.setSummon_god_active(false);
                        player.sendMessage(ChatColor.RED + "Summon God Deactivated");
                    }else{
                        player_data.setSummon_god_active(true);
                        player.sendMessage(ChatColor.GREEN + "Summon God Activated");
                    }
                    break;
                case "Powerful Flock":
                    if (player_data.isPowerful_flock_active()){
                        player_data.setPowerful_flock_active(false);
                        player.sendMessage(ChatColor.RED + "Powerful Flock Deactivated");
                    }else{
                        player_data.setPowerful_flock_active(true);
                        player.sendMessage(ChatColor.GREEN + "Powerful Flock Activated");
                    }
                    break;
                case "Hell's Fury":
                    HellsFury fury = new HellsFury();
                    fury.onHellsFuryTrigger(player, player_data);
                    break;
                case "Divine Intervention":
                    DivineIntervention divine = new DivineIntervention();
                    divine.onDivineTrigger(player, player_data);
                    player.sendMessage("Divine Intervention Activated");
                    break;
                case "Mana":
                    Mana mana = new Mana();
                    mana.onManaTrigger(player, player_data);
                    break;
                case "Lion's Heart":
                    if (player_data.isLions_heart_active()){
                        player_data.setLions_heart_active(false);
                        player.sendMessage(ChatColor.RED + "Lions Heart Deactivated");
                    }else {player_data.setLions_heart_active(true);
                    player.sendMessage(ChatColor.GREEN + "Lion's Heart Activated");}
                    break;
                case "Savior":
                    if (player_data.isSavior_active()){
                        player_data.setSavior_active(false);
                        player.sendMessage(ChatColor.RED + "Savior Deactivated");
                    }else {player_data.setSavior_active(true);
                        player.sendMessage(ChatColor.GREEN + "Savior Activated");}
                    break;
                case "Taunt":
                    Taunt taunt = new Taunt();
                    taunt.tauntTrigger(player);
                    player.sendMessage(ChatColor.GOLD + "You taunt your enemies!");
                    break;
                case "Insidious":
                    if (player_data.isInsidious_active()){
                        player_data.setInsidious_active(false);
                        player.sendMessage(ChatColor.RED + "Insidious Deactivated");
                    }else {player_data.setInsidious_active(true);
                        player.sendMessage(ChatColor.GREEN + "Insidious Activated");}
                    break;
                case "Explosive Landing":
                    if (player_data.isExplosive_landing_active()){
                        player_data.setExplosive_landing_active(false);
                        player.sendMessage(ChatColor.RED + "Explosive Landing Deactivated");
                    }else {player_data.setExplosive_landing_active(true);
                        player.sendMessage(ChatColor.GREEN + "Explosive Landing Activated");}
                    break;
                case "Flood":
                    Flood flood = new Flood();
                    flood.floodTrigger(player);
                    player.sendMessage(ChatColor.DARK_BLUE + "The area floods with water.");
                    break;
                case "Crumbling":
                    if (player_data.isCrumbling_active()){
                        player_data.setCrumbling_active(false);
                        crumblers.remove(player.getDisplayName());
                        player.sendMessage(ChatColor.RED + "Crumbling Deactivated");
                    }else{
                        player_data.setCrumbling_active(true);
                        crumblers.add(player.getDisplayName());
                        player.sendMessage(ChatColor.GREEN + "Crumbling Activated");
                        System.out.println(crumblers);
                    }
                    break;
                case "Heavy Boots":
                    if (player_data.isHeavy_boots_active()){
                        player_data.setHeavy_boots_active(false);
                        HeavyBoots.heavy_boots.remove(player.getDisplayName());
                        player.sendMessage(ChatColor.RED + "Heavy Boots Deactivated");
                    }else{
                        player_data.setHeavy_boots_active(true);
                        HeavyBoots.heavy_boots.add(player.getDisplayName());
                        player.sendMessage(ChatColor.GREEN + "Heavy Boots Activated");
                    }
                    break;
                case "Intoxicate":
                    if (player_data.isIntoxicate_active()){
                        player_data.setIntoxicate_active(false);
                        Intoxicate.intoxicators.remove(player.getDisplayName());
                        player.sendMessage(ChatColor.RED + "Intoxicate Deactivated");
                    }else{
                        player_data.setIntoxicate_active(true);
                        Intoxicate.intoxicators.add(player.getDisplayName());
                        player.sendMessage(ChatColor.GREEN + "Intoxicate Activated");
                    }
                    break;
                case "Discombobulate":
                    player_data.setDiscombobulate_active(true);
                    Discombobulate discombobulate = new Discombobulate();
                    discombobulate.discombobulateTrigger(player);
                    Discombobulate.discombobulators.add(player.getDisplayName());
                    player.sendMessage(ChatColor.GREEN + "Discombobulate Activated");
                    break;
                case "Entangle":
                    Entangle entangle = new Entangle();
                    entangle.onEntangleTrigger(player, player_data);
            }
        // The terrain bonuses are not going in the switch statement since I would have to code 56 entries.
        // This
        if (Objects.requireNonNull(held_item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey
                (Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")), "Power"), PersistentDataType.STRING)).contains("Terrain Bonus")){
            ItemStack power_block = e.getItem();
            TerrainBonus terrain_bonus = new TerrainBonus();
            assert power_block != null;
            terrain_bonus.terrainToggle(Objects.requireNonNull(power_block.getItemMeta()).getDisplayName(), e.getPlayer());
            if (player_data.isTerrain_bonus_active()){
                player_data.setTerrain_bonus_active(false);
                player.sendMessage(ChatColor.RED + "Terrain Bonus Deactivated");
            }else{
                player_data.setTerrain_bonus_active(true);
                player.sendMessage(ChatColor.GREEN + "Terrain Bonus Activated");
            }
        }

            PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);
    }

    // TODO Only allow power to be held or given to the hot bar.
    // TODO Delete power items when they are dropped.
}

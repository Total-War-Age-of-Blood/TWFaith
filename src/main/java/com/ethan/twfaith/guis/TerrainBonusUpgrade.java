package com.ethan.twfaith.guis;

import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Objects;

public class TerrainBonusUpgrade implements Listener {
    private Inventory gui;

    public void openTerrainBonusGUI(Player player){
        Faith faith_data = FaithHashMap.player_faith_hashmap.get(player.getUniqueId());
        gui = Bukkit.createInventory(null, 27, "Biome Groups");

        // Snowy Biomes
        generateGUI(Material.SNOW_BLOCK, ChatColor.WHITE, "Snowy Biomes", faith_data.getTerrain_snowy(), 0);
        // Cold Biomes
        generateGUI(Material.PODZOL, ChatColor.WHITE, "Cold Biomes", faith_data.getTerrain_cold(), 1);
        // Temperate Biomes
        generateGUI(Material.GRASS_BLOCK, ChatColor.WHITE, "Temperate Biomes", faith_data.getTerrain_temperate(), 2);
        // Warm biomes
        generateGUI(Material.SAND, ChatColor.WHITE, "Warm Biomes", faith_data.getTerrain_warm(), 3);
        // Aquatic biomes
        generateGUI(Material.WATER_BUCKET, ChatColor.WHITE, "Aquatic Biomes", faith_data.getTerrain_aquatic(), 4);
        // Cave biomes
        generateGUI(Material.DEEPSLATE, ChatColor.WHITE, "Cave Biomes", faith_data.getTerrain_cave(), 5);
        // The Nether
        generateGUI(Material.NETHERRACK, ChatColor.WHITE, "Nether", faith_data.getTerrain_nether(), 6);
        // The End
        generateGUI(Material.END_STONE, ChatColor.WHITE, "End", faith_data.getTerrain_end(), 7);

        // Powers
        // Strength
        generateGUI(Material.IRON_SWORD, ChatColor.RED, "Strength", faith_data.getTerrain_strength(), 18);
        // Haste
        generateGUI(Material.GOLDEN_PICKAXE, ChatColor.GOLD, "Haste", faith_data.getTerrain_haste(), 19);
        // Speed
        generateGUI(Material.CHAINMAIL_BOOTS, ChatColor.BLUE, "Speed", faith_data.getTerrain_speed(), 20);
        // Resistance
        generateGUI(Material.SHIELD, ChatColor.GRAY, "Resistance", faith_data.getTerrain_resistance(), 21);
        // Fire Resistance
        generateGUI(Material.FIRE_CHARGE, ChatColor.YELLOW, "Fire Resistance", faith_data.getTerrain_fire_resistance(), 22);
        // Water Breathing
        generateGUI(Material.PUFFERFISH, ChatColor.DARK_BLUE, "Water Breathing", faith_data.getTerrain_water_breathing(), 23);
        // Dolphin's Grace
        generateGUI(Material.HEART_OF_THE_SEA, ChatColor.BLUE, "Dolphin's Grace", faith_data.getTerrain_dolphins_grace(), 24);

        // Close Menu
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta item_meta = item.getItemMeta();
        assert item_meta != null;
        item_meta.setDisplayName(ChatColor.RED + "Back");
        item_meta.setLore(Collections.singletonList("Return to previous menu."));
        item.setItemMeta(item_meta);
        gui.setItem(8, item);

        player.openInventory(gui);
    }
    // TODO this may need to be reworked
    public void generateGUI(Material block, ChatColor color, String display, int data, int spot){
        ItemStack item = new ItemStack(block);
        ItemMeta item_meta = item.getItemMeta();
        assert item_meta != null;
        item_meta.setDisplayName(color + display);
        switch (data){
            case 0:
                item_meta.setLore(Collections.singletonList(ChatColor.RED + "Not Owned"));
                break;
            case 1:
                item_meta.setLore(Collections.singletonList(ChatColor.GREEN + "Owned"));
                break;
        }
        item.setItemMeta(item_meta);
        gui.setItem(spot, item);
    }

    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        // When custom event is triggered, check to see if it wants to open this gui
        // if so, open the gui
        if(e.getGui_name().equals("Biome Groups")){openTerrainBonusGUI(e.getPlayer());}
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){
        try{        if(!Objects.equals(e.getClickedInventory(), gui)){
            return;
        }}catch (NullPointerException exception){return;}

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(p.getUniqueId());
        Faith faith_data = FaithHashMap.player_faith_hashmap.get(player_data.getUuid());

        switch (e.getSlot()){
            case 0:
                faith_data.setTerrain_snowy(1);
                break;
            case 1:
                faith_data.setTerrain_cold(1);
                break;
            case 2:
                faith_data.setTerrain_temperate(1);
                break;
            case 3:
                faith_data.setTerrain_warm(1);
                break;
            case 4:
                faith_data.setTerrain_aquatic(1);
                break;
            case 5:
                faith_data.setTerrain_cave(1);
                break;
            case 6:
                faith_data.setTerrain_nether(1);
                break;
            case 7:
                faith_data.setTerrain_end(1);
                break;
            case 8:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Blessings"));
                break;
            case 18:
                faith_data.setTerrain_strength(1);
                break;
            case 19:
                faith_data.setTerrain_haste(1);
                break;
            case 20:
                faith_data.setTerrain_speed(1);
                break;
            case 21:
                faith_data.setTerrain_resistance(1);
                break;
            case 22:
                faith_data.setTerrain_fire_resistance(1);
                break;
            case 23:
                faith_data.setTerrain_water_breathing(1);
                break;
            case 24:
                faith_data.setTerrain_dolphins_grace(1);
                break;
        }
    }
}

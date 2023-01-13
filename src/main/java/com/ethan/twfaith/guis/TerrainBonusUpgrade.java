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

        if (gui == null){return;}

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        PlayerData playerData = PlayerHashMap.player_data_hashmap.get(p.getUniqueId());
        Faith faithData = FaithHashMap.player_faith_hashmap.get(p.getUniqueId());
        ItemStack item = e.getCurrentItem();

        switch (e.getSlot()){
            case 0:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_snowy(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_snowy(1);
                break;
            case 1:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_cold(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_cold(1);
                break;
            case 2:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_temperate(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_temperate(1);
                break;
            case 3:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_warm(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_warm(1);
                break;
            case 4:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_aquatic(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_aquatic(1);
                break;
            case 5:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_cave(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_cave(1);
                break;
            case 6:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_nether(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_nether(1);
                break;
            case 7:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_end(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_end(1);
                break;
            case 8:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Blessings"));
                break;
            case 18:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_strength(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_strength(1);
                break;
            case 19:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_haste(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_haste(1);
                break;
            case 20:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_speed(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_speed(1);
                break;
            case 21:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_resistance(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_resistance(1);
                break;
            case 22:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_fire_resistance(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_fire_resistance(1);
                break;
            case 23:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_water_breathing(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_water_breathing(1);
                break;
            case 24:
                if (faithPointsChecker(faithData, p, 5, faithData.getTerrain_dolphins_grace(), gui, item, e.getSlot())){return;}
                faithData.setTerrain_dolphins_grace(1);
                break;
        }
    }

    public boolean faithPointsChecker(Faith faithData, Player p, int cost, int data, Inventory gui, ItemStack item, int slot){
        if (data > 0){return true;}
        if (!(faithData.getFaithPoints() >= cost)){
            p.sendMessage(ChatColor.RED + "You need more Faith Points to purchase this upgrade.");
            return true;
        }
        System.out.println(faithData.getFaithPoints());
        System.out.println(cost);
        System.out.println(faithData.getFaithPoints() - cost);
        faithData.setFaithPoints(faithData.getFaithPoints() - cost);
        p.sendMessage(faithData.getFaithPoints() + " Faith Points remaining.");
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setLore(Collections.singletonList(ChatColor.GREEN + "Owned"));
        item.setItemMeta(item_meta);
        gui.setItem(slot, item);
        return false;
    }
}

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

public class TerrainBonus implements Listener {
    private Inventory gui;

    public void openTerrainBonusGUI(Player player){
        Faith faith_data = FaithHashMap.player_faith_hashmap.get(player.getUniqueId());
        gui = Bukkit.createInventory(null, 27, "Biome Groups");

        // Snowy Biomes
        generateGUI(Material.SNOW_BLOCK, ChatColor.WHITE, "Snowy Biomes", faith_data.getTerrain_bonuses().get("Snowy"), 0);
        // Cold Biomes
        generateGUI(Material.PODZOL, ChatColor.WHITE, "Cold Biomes", faith_data.getTerrain_bonuses().get("Cold"), 1);
        // Temperate Biomes
        generateGUI(Material.GRASS_BLOCK, ChatColor.WHITE, "Temperate Biomes", faith_data.getTerrain_bonuses().get("Temperate"), 2);
        // Warm biomes
        generateGUI(Material.SAND, ChatColor.WHITE, "Warm Biomes", faith_data.getTerrain_bonuses().get("Warm"), 3);
        // Aquatic biomes
        generateGUI(Material.WATER_BUCKET, ChatColor.WHITE, "Aquatic Biomes", faith_data.getTerrain_bonuses().get("Aquatic"), 4);
        // Cave biomes
        generateGUI(Material.DEEPSLATE, ChatColor.WHITE, "Cave Biomes", faith_data.getTerrain_bonuses().get("Cave"), 5);
        // The Nether
        generateGUI(Material.NETHERRACK, ChatColor.WHITE, "Nether", faith_data.getTerrain_bonuses().get("Nether"), 6);
        // The End
        generateGUI(Material.END_STONE, ChatColor.WHITE, "End", faith_data.getTerrain_bonuses().get("End"), 7);

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
        if(e.getGui_name().equals("Faith Upgrade")){openTerrainBonusGUI(e.getPlayer());}
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){
        try{        if(!Objects.equals(e.getClickedInventory(), gui)){
            return;
        }}catch (NullPointerException exception){return;}

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(p.getDisplayName());
        Faith faith_data = FaithHashMap.player_faith_hashmap.get(player_data.getUuid());

        switch (e.getSlot()){
            case 0:
                break;

        }
    }
}

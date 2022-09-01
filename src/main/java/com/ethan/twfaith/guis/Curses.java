package com.ethan.twfaith.guis;

import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.data.PlayerData;
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

public class Curses implements Listener {
    private Inventory gui;

    public void openCursesGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");

        // Crumbling
        generateGUI(Material.DAMAGED_ANVIL, ChatColor.GRAY, "Crumbling", "Heathen's items lose durability quickly.", 10);

        // Heavy Boots
        generateGUI(Material.DIAMOND_BOOTS, ChatColor.GRAY, "Heavy Boots", "Heathens wearing boots can neither sprint nor jump.", 11);

        // Intoxicate
        generateGUI(Material.HONEY_BOTTLE, ChatColor.LIGHT_PURPLE, "Intoxicate", "Nearby heathens gain nausea.", 12);

        // Discombobulate
        generateGUI(Material.PUFFERFISH, ChatColor.YELLOW, "Discombobulate", "Switch inventories of nearby heathens.", 13);

        // Entangle
        generateGUI(Material.VINE, ChatColor.GREEN, "Entangle", "Nearby heathens are caged.", 14);

        // Close Menu
        generateGUI(Material.BARRIER, ChatColor.RED, "Back", "Return to previous menu.", 16);

        // Frame
        ItemStack frame = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26}){
            gui.setItem(i, frame);
        }

        player.openInventory(gui);
    }

    public void generateGUI(Material material, ChatColor color, String display, String lore, int place){
        ItemStack item = new ItemStack(material);
        ItemMeta item_meta = item.getItemMeta();
        assert item_meta != null;
        item_meta.setDisplayName(color + display);
        item_meta.setLore(Collections.singletonList(lore));
        item.setItemMeta(item_meta);
        gui.setItem(place, item);
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){
        try{if(!Objects.equals(e.getClickedInventory(), gui)){
            return;
        }}catch (NullPointerException exception){return;}

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(p.getDisplayName());

        switch (e.getSlot()){
            case 10:
                player_data.setCrumbling(1);
                break;
            case 11:
                player_data.setHeavy_boots(1);
                break;
            case 12:
                player_data.setIntoxicate(1);
                break;
            case 13:
                player_data.setDiscombobulate(1);
                break;
            case 14:
                player_data.setEntangle(1);
                break;
            case 16:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Faith Upgrade"));
                break;
        }

        PlayerHashMap.player_data_hashmap.put(p.getDisplayName(), player_data);
    }

    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        if(e.getGui_name().equals("Curses")){openCursesGui(e.getPlayer());}
    }
}

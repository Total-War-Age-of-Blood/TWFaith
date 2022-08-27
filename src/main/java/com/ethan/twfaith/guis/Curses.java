package com.ethan.twfaith.guis;

import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.data.PlayerData;
import com.google.gson.Gson;
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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Curses implements Listener {
    private Inventory gui;

    public void openCursesGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");

        // Crumbling
        ItemStack crumbling = new ItemStack(Material.DAMAGED_ANVIL);
        ItemMeta crumbling_meta = crumbling.getItemMeta();
        crumbling_meta.setDisplayName("Crumbling");
        crumbling_meta.setLore(Arrays.asList("Heathen's items lose durability quickly."));
        crumbling.setItemMeta(crumbling_meta);
        gui.setItem(10, crumbling);

        // Heavy Boots
        ItemStack heavy_boots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta heavy_boots_meta = heavy_boots.getItemMeta();
        heavy_boots_meta.setDisplayName(ChatColor.GRAY + "Heavy Boots");
        heavy_boots_meta.setLore(Arrays.asList("Heathens wearing boots cannot sprint or jump."));
        heavy_boots.setItemMeta(heavy_boots_meta);
        gui.setItem(11, heavy_boots);

        // Intoxicate
        ItemStack intoxicate = new ItemStack(Material.HONEY_BOTTLE);
        ItemMeta intoxicate_meta = intoxicate.getItemMeta();
        intoxicate_meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Intoxicate");
        intoxicate_meta.setLore(Arrays.asList("Nearby heathens gain nausea."));
        intoxicate.setItemMeta(intoxicate_meta);
        gui.setItem(12, intoxicate);

        // Discombobulate
        ItemStack discombobulate = new ItemStack(Material.PUFFERFISH);
        ItemMeta discombobulate_meta = discombobulate.getItemMeta();
        discombobulate_meta.setDisplayName(ChatColor.YELLOW + "Discombobulate");
        discombobulate_meta.setLore(Arrays.asList("Switch inventories of nearby heathens."));
        discombobulate.setItemMeta(discombobulate_meta);
        gui.setItem(13, discombobulate);

        // Entangle
        ItemStack entangle = new ItemStack(Material.VINE);
        ItemMeta entangle_meta = entangle.getItemMeta();
        entangle_meta.setDisplayName(ChatColor.GREEN + "Entangle");
        entangle_meta.setLore(Arrays.asList("Nearby heathens are caged."));
        entangle.setItemMeta(entangle_meta);
        gui.setItem(14, entangle);

        // Close Menu
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta close_meta = close.getItemMeta();
        close_meta.setDisplayName(ChatColor.RED + "Back");
        close.setItemMeta(close_meta);
        gui.setItem(16, close);

        // Frame
        ItemStack frame = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26}){
            gui.setItem(i, frame);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){
        try{        if(!e.getClickedInventory().equals(gui)){
            return;
        }}catch (NullPointerException exception){return;}


        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();

        try{
            File player_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "PlayerData");
            File player_file = new File(player_folder, p.getUniqueId() + ".json");
            FileReader player_file_reader = new FileReader(player_file);
            Gson gson = new Gson();
            PlayerData player_data = gson.fromJson(player_file_reader, PlayerData.class);

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

            FileWriter player_data_writer = new FileWriter(player_file, false);
            gson.toJson(player_data, player_data_writer);
            player_data_writer.flush();
            player_data_writer.close();

        }catch (IOException exception){exception.printStackTrace();}

    }

    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        if(e.getGui_name().equals("Curses")){openCursesGui(e.getPlayer());}
    }
}


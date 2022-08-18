package com.ethan.twfaith.guis;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.data.PlayerData;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class SelectPowers implements Listener {
    private Inventory gui;
    // TODO Replace commands to activate powers with blocks that have NBT tags
    public void openSelectPowersGui(Player player, PlayerData player_data){
        gui = Bukkit.createInventory(null, 45, "Select Powers");


        // Lion's Heart
        ItemStack lions_heart = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta lions_meta = lions_heart.getItemMeta();
        lions_meta.setDisplayName(ChatColor.GOLD + "Lion's Heart");
        switch (player_data.getLions_heart()){
            case 0:
                lions_meta.setLore(Arrays.asList(ChatColor.RED + "Not Owned"));
                break;
            case 1:
                lions_meta.setLore(Arrays.asList(ChatColor.GREEN + "Owned"));
                break;
        }
        lions_heart.setItemMeta(lions_meta);
        gui.setItem(19, lions_heart);

        // Savior
        ItemStack savior = new ItemStack(Material.GOLDEN_CARROT);
        ItemMeta savior_meta = savior.getItemMeta();
        savior_meta.setDisplayName(ChatColor.RED + "Savior");
        switch (player_data.getSavior()){
            case 0:
                savior_meta.setLore(Arrays.asList(ChatColor.RED + "Not Owned"));
                break;
            case 1:
                savior_meta.setLore(Arrays.asList(ChatColor.GREEN + "Owned"));
                break;
        }
        savior.setItemMeta(savior_meta);
        gui.setItem(20, savior);

        // Taunt
        ItemStack taunt = new ItemStack(Material.DIAMOND);
        ItemMeta taunt_meta = taunt.getItemMeta();
        taunt_meta.setDisplayName(ChatColor.GOLD + "Taunt");
        switch (player_data.getTaunt()){
            case 0:
                taunt_meta.setLore(Arrays.asList(ChatColor.RED + "Not Owned"));
                break;
            case 1:
                taunt_meta.setLore(Arrays.asList(ChatColor.GREEN + "Owned"));
                break;
        }
        taunt.setItemMeta(taunt_meta);
        gui.setItem(21, taunt);

        // Insidious
        ItemStack insidious = new ItemStack(Material.ENDER_EYE);
        ItemMeta insidious_meta = insidious.getItemMeta();
        insidious_meta.setDisplayName(ChatColor.BLUE + "Insidious");
        switch (player_data.getInsidious()){
            case 0:
                insidious_meta.setLore(Arrays.asList(ChatColor.RED + "Not Owned"));
                break;
            case 1:
                insidious_meta.setLore(Arrays.asList(ChatColor.GREEN + "Owned"));
                break;
        }
        insidious.setItemMeta(insidious_meta);
        gui.setItem(22, insidious);

        // Explosive Landing
        ItemStack explosive_landing = new ItemStack(Material.TNT_MINECART);
        ItemMeta explosive_landing_meta = explosive_landing.getItemMeta();
        explosive_landing_meta.setDisplayName(ChatColor.RED + "Explosive Landing");
        switch (player_data.getExplosive_landing()){
            case 0:
                explosive_landing_meta.setLore(Arrays.asList(ChatColor.RED + "Not Owned"));
                break;
            case 1:
                explosive_landing_meta.setLore(Arrays.asList(ChatColor.GREEN + "Owned"));
                break;
        }
        explosive_landing.setItemMeta(explosive_landing_meta);
        gui.setItem(23, explosive_landing);

        // Flood Power
        ItemStack flood = new ItemStack(Material.WATER_BUCKET);
        ItemMeta flood_meta = flood.getItemMeta();
        flood_meta.setDisplayName(ChatColor.BLUE + "Flood");
        switch (player_data.getFlood()){
            case 0:
                flood_meta.setLore(Arrays.asList(ChatColor.RED + "Not Owned"));
                break;
            case 1:
                flood_meta.setLore(Arrays.asList(ChatColor.GREEN + "Owned"));
                break;
        }
        flood.setItemMeta(flood_meta);
        gui.setItem(24, flood);

        // Frame
        ItemStack frame = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        for (int number : new int[]{0, 9, 18, 27, 36, 8, 17, 26, 35, 44, 1, 2, 3, 4, 5, 6, 7, 36, 37, 38, 39, 40, 41, 42, 43}){
            gui.setItem(number, frame);

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
                case 19:
                    if (player_data.getLions_heart() > 0){
                        ItemStack lions_heart = new ItemStack(Material.RED_TERRACOTTA);
                        ItemMeta lions_meta = lions_heart.getItemMeta();
                        lions_meta.setDisplayName(ChatColor.RED + "Lion's Heart");
                        // Using PersistentDataContainer to add an NBT tag to the item denoting its power.
                        PersistentDataContainer lions_data = lions_meta.getPersistentDataContainer();
                        lions_data.set(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING, "lionsheart");
                        lions_heart.setItemMeta(lions_meta);

                        p.getInventory().addItem(lions_heart);
                    } else{p.sendMessage(ChatColor.DARK_RED + "ERROR: Player must own power to equip it");}
                    break;
                case 20:
                    if (player_data.getSavior() > 0){
                        ItemStack savior = new ItemStack(Material.MAGENTA_TERRACOTTA);
                        ItemMeta savior_meta = savior.getItemMeta();
                        savior_meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Savior");
                        PersistentDataContainer savior_data = savior_meta.getPersistentDataContainer();
                        savior_data.set(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING, "Savior");
                        savior.setItemMeta(savior_meta);

                        p.getInventory().addItem(savior);
                    } else{p.sendMessage(ChatColor.DARK_RED + "ERROR: Player must own power to equip it");}
                    break;
                case 21:
                    if (player_data.getTaunt() > 0){
                        ItemStack taunt = new ItemStack(Material.ORANGE_TERRACOTTA);
                        ItemMeta taunt_meta = taunt.getItemMeta();
                        taunt_meta.setDisplayName(ChatColor.GOLD + "Taunt");
                        PersistentDataContainer taunt_data = taunt_meta.getPersistentDataContainer();
                        taunt_data.set(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING, "Taunt");
                        taunt.setItemMeta(taunt_meta);

                        p.getInventory().addItem(taunt);
                    } else{p.sendMessage(ChatColor.DARK_RED + "ERROR: Player must own power to equip it");}
                    break;
                case 22:
                    if (player_data.getInsidious() > 0){
                        ItemStack insidious = new ItemStack(Material.BLUE_TERRACOTTA);
                        ItemMeta insidious_meta = insidious.getItemMeta();
                        insidious_meta.setDisplayName(ChatColor.BLUE + "Insidious");
                        PersistentDataContainer insidious_data = insidious_meta.getPersistentDataContainer();
                        insidious_data.set(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING, "Insidious");
                        insidious.setItemMeta(insidious_meta);

                        p.getInventory().addItem(insidious);
                    } else{p.sendMessage(ChatColor.DARK_RED + "ERROR: Player must own power to equip it");}
                    break;
                case 23:
                    if (player_data.getExplosive_landing() > 0){
                        ItemStack explosive = new ItemStack(Material.RED_TERRACOTTA);
                        ItemMeta explosive_meta = explosive.getItemMeta();
                        explosive_meta.setDisplayName(ChatColor.RED + "Explosive Landing");
                        PersistentDataContainer explosive_data = explosive_meta.getPersistentDataContainer();
                        explosive_data.set(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING, "Explosive Landing");
                        explosive.setItemMeta(explosive_meta);

                        p.getInventory().addItem(explosive);
                    } else{p.sendMessage(ChatColor.DARK_RED + "ERROR: Player must own power to equip it");}
                    break;
                case 24:
                        if (player_data.getFlood() > 0){
                            ItemStack flood = new ItemStack(Material.BLUE_TERRACOTTA);
                            ItemMeta flood_meta = flood.getItemMeta();
                            flood_meta.setDisplayName(ChatColor.BLUE + "Flood");
                            PersistentDataContainer flood_data = flood_meta.getPersistentDataContainer();
                            flood_data.set(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING, "Flood");
                            flood.setItemMeta(flood_meta);

                            p.getInventory().addItem(flood);
                        } else{p.sendMessage(ChatColor.DARK_RED + "ERROR: Player must own power to equip it");}
                    break;
                case 16:
                    Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Faith Upgrade"));
                    break;
            }
        }catch (IOException exception){exception.printStackTrace();}

    }

    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        if(e.getGui_name().equals("Select Powers")){
            try{
                File player_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "PlayerData");
                File player_file = new File(player_folder, e.getPlayer().getUniqueId() + ".json");
                FileReader player_file_reader = new FileReader(player_file);
                Gson gson = new Gson();
                PlayerData player_data = gson.fromJson(player_file_reader, PlayerData.class);
                openSelectPowersGui(e.getPlayer(), player_data);
            }catch (IOException exception){exception.printStackTrace();}
        }
    }
}


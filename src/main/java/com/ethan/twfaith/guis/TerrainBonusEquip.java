package com.ethan.twfaith.guis;

import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class TerrainBonusEquip implements Listener {
    private Inventory gui;

    public void openTerrainBonusEquip(Player player){
        Faith faith_data = FaithHashMap.player_faith_hashmap.get(player.getUniqueId());
        gui = Bukkit.createInventory(null, 27, "Equip Terrain Bonus");
        // Biome Groups
        // Snowy
        generateGUI(Material.SNOW_BLOCK, ChatColor.WHITE, "Snowy Terrain Bonus", faith_data.getTerrain_snowy(), 0);
        // Cold
        generateGUI(Material.PODZOL, ChatColor.WHITE, "Cold Terrain Bonus", faith_data.getTerrain_cold(), 1);
        // Temperate
        generateGUI(Material.GRASS_BLOCK, ChatColor.GREEN, "Temperate Terrain Bonus", faith_data.getTerrain_temperate(), 2);
        // Warm
        generateGUI(Material.SAND, ChatColor.YELLOW, "Warm Terrain Bonus", faith_data.getTerrain_warm(), 3);
        // Aquatic
        generateGUI(Material.WATER_BUCKET, ChatColor.BLUE, "Aquatic Terrain Bonus", faith_data.getTerrain_aquatic(), 4);
        // Cave
        generateGUI(Material.DEEPSLATE, ChatColor.GRAY, "Cave Terrain Bonus", faith_data.getTerrain_cave(), 5);
        // Nether
        generateGUI(Material.NETHERRACK, ChatColor.RED, "Nether Terrain Bonus", faith_data.getTerrain_nether(), 6);
        // End
        generateGUI(Material.END_STONE, ChatColor.LIGHT_PURPLE, "End Terrain Bonus", faith_data.getTerrain_end(), 7);

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
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta close_meta = close.getItemMeta();
        assert close_meta != null;
        close_meta.setDisplayName(ChatColor.RED + "Back");
        close_meta.setLore(Collections.singletonList("Return to previous menu."));
        close.setItemMeta(close_meta);
        gui.setItem(8, close);

        // Confirm
        ItemStack confirm = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta confirm_meta = (SkullMeta) confirm.getItemMeta();
        assert confirm_meta != null;
        PlayerProfile c_profile = Bukkit.getServer().createPlayerProfile(UUID.randomUUID(), null);
        try{
            c_profile.getTextures().setSkin(new URL("http://textures.minecraft.net/texture/b5a3b49beec3ab23ae0b60dab56e9cc8fa16769a25830b5d8d6c46378f54430"));
            confirm_meta.setOwnerProfile(c_profile);
        }catch (MalformedURLException e){e.printStackTrace();}
        confirm_meta.setDisplayName(ChatColor.GREEN + "Confirm");
        confirm.setItemMeta(confirm_meta);

        gui.setItem(26, confirm);

        // Open the GUI (must be at end)
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

    // This method gives the god the terracotta power block from the selection menu
    public void inventoryClickSwitch(Material block, ChatColor color, String display_name, Player p){
        ItemStack item = new ItemStack(block);
        ItemMeta item_meta = item.getItemMeta();
        assert item_meta != null;
        item_meta.setDisplayName(color + display_name);
        PersistentDataContainer item_data = item_meta.getPersistentDataContainer();
        item_data.set(new NamespacedKey(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")), "Power"), PersistentDataType.STRING, display_name);
        item.setItemMeta(item_meta);

        p.getInventory().addItem(item);
    }

    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        // When custom event is triggered, check to see if it wants to open this gui
        // if so, open the gui
        if(e.getGui_name().equals("Equip Terrain Bonus")){openTerrainBonusEquip(e.getPlayer());}
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
                enchantmentChecker(e, false, faith_data.getTerrain_snowy());
                break;
            case 1:
                enchantmentChecker(e, false, faith_data.getTerrain_cold());
                break;
            case 2:
                enchantmentChecker(e, false, faith_data.getTerrain_temperate());
                break;
            case 3:
                enchantmentChecker(e, false, faith_data.getTerrain_warm());
                break;
            case 4:
                enchantmentChecker(e, false, faith_data.getTerrain_aquatic());
                break;
            case 5:
                enchantmentChecker(e, false, faith_data.getTerrain_cave());
                break;
            case 6:
                enchantmentChecker(e, false, faith_data.getTerrain_nether());
                break;
            case 7:
                enchantmentChecker(e, false, faith_data.getTerrain_end());
                break;
            case 8:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Select Powers"));
                break;
            case 18:
                enchantmentChecker(e, true, faith_data.getTerrain_strength());
                break;
            case 19:
                enchantmentChecker(e, true, faith_data.getTerrain_haste());
                break;
            case 20:
                enchantmentChecker(e, true, faith_data.getTerrain_speed());
                break;
            case 21:
                enchantmentChecker(e, true, faith_data.getTerrain_resistance());
                break;
            case 22:
                enchantmentChecker(e, true, faith_data.getTerrain_fire_resistance());
                break;
            case 23:
                enchantmentChecker(e, true, faith_data.getTerrain_water_breathing());
                break;
            case 24:
                enchantmentChecker(e, true, faith_data.getTerrain_dolphins_grace());
                break;
            case 26:
                List<String> selections = new ArrayList<>();
                for (ItemStack item : e.getInventory()){
                    if (item != null){
                        if (!item.getEnchantments().isEmpty()){
                            selections.add(Objects.requireNonNull(item.getItemMeta()).getDisplayName());
                        }
                    }
                }

                if (selections.size() != 2){p.sendMessage(ChatColor.RED + "You must select a biome group and a power."); return;}


                String display_name = selections.get(0) + " " + selections.get(1);
                inventoryClickSwitch(Material.GREEN_TERRACOTTA, ChatColor.GREEN, display_name, p);
                break;
        }
    }

    public void enchantmentChecker(InventoryClickEvent e, boolean type, int player_data){
        // Checking to see if the player has permission to select the biome group or power.
        if (player_data < 1){e.getWhoClicked().sendMessage(ChatColor.RED + "Must own powers to select them."); return;}
        // Remove enchantment sheen from any other biome groups
        int base = 0;
        if (type){base += 18;}
        for (int x = base; x < 9 || (x > 17 && x < 25); x++){
            ItemStack biome_bonus = e.getInventory().getItem(x);
            assert biome_bonus != null;
            ItemMeta biome_meta = biome_bonus.getItemMeta();
            assert biome_meta != null;
            biome_meta.removeEnchant(Enchantment.DURABILITY);
            biome_bonus.setItemMeta(biome_meta);
            gui.setItem(x, biome_bonus);
        }

        // Get the item and give it the enchantment sheen
        ItemStack snowy_bonus = e.getInventory().getItem(e.getSlot());
        assert snowy_bonus != null;
        ItemMeta snowy_meta = snowy_bonus.getItemMeta();
        assert snowy_meta != null;
        snowy_meta.addEnchant(Enchantment.DURABILITY, 1, false);
        snowy_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        snowy_bonus.setItemMeta(snowy_meta);
        gui.setItem(e.getSlot(), snowy_bonus);
    }

}

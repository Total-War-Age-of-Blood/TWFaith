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
        generateGUI(Material.SNOW_BLOCK, ChatColor.WHITE, "Snowy Terrain Bonus", faith_data.getTerrainSnowy(), 0);
        // Cold
        generateGUI(Material.PODZOL, ChatColor.WHITE, "Cold Terrain Bonus", faith_data.getTerrainCold(), 1);
        // Temperate
        generateGUI(Material.GRASS_BLOCK, ChatColor.GREEN, "Temperate Terrain Bonus", faith_data.getTerrainTemperate(), 2);
        // Warm
        generateGUI(Material.SAND, ChatColor.YELLOW, "Warm Terrain Bonus", faith_data.getTerrainWarm(), 3);
        // Aquatic
        generateGUI(Material.WATER_BUCKET, ChatColor.BLUE, "Aquatic Terrain Bonus", faith_data.getTerrainAquatic(), 4);
        // Cave
        generateGUI(Material.DEEPSLATE, ChatColor.GRAY, "Cave Terrain Bonus", faith_data.getTerrainCave(), 5);
        // Nether
        generateGUI(Material.NETHERRACK, ChatColor.RED, "Nether Terrain Bonus", faith_data.getTerrainNether(), 6);
        // End
        generateGUI(Material.END_STONE, ChatColor.LIGHT_PURPLE, "End Terrain Bonus", faith_data.getTerrainEnd(), 7);

        // Powers
        // Strength
        generateGUI(Material.IRON_SWORD, ChatColor.RED, "Strength", faith_data.getTerrainStrength(), 18);
        // Haste
        generateGUI(Material.GOLDEN_PICKAXE, ChatColor.GOLD, "Haste", faith_data.getTerrainHaste(), 19);
        // Speed
        generateGUI(Material.CHAINMAIL_BOOTS, ChatColor.BLUE, "Speed", faith_data.getTerrainSpeed(), 20);
        // Resistance
        generateGUI(Material.SHIELD, ChatColor.GRAY, "Resistance", faith_data.getTerrainResistance(), 21);
        // Fire Resistance
        generateGUI(Material.FIRE_CHARGE, ChatColor.YELLOW, "Fire Resistance", faith_data.getTerrainFireResistance(), 22);
        // Water Breathing
        generateGUI(Material.PUFFERFISH, ChatColor.DARK_BLUE, "Water Breathing", faith_data.getTerrainWaterBreathing(), 23);
        // Dolphin's Grace
        generateGUI(Material.HEART_OF_THE_SEA, ChatColor.BLUE, "Dolphin's Grace", faith_data.getTerrainDolphinsGrace(), 24);

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
        if(e.getGuiName().equals("Equip Terrain Bonus")){openTerrainBonusEquip(e.getPlayer());}
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){
        try{        if(!Objects.equals(e.getClickedInventory(), gui)){
            return;
        }}catch (NullPointerException exception){return;}

        if (gui == null){return;}

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(p.getUniqueId());
        Faith faith_data = FaithHashMap.player_faith_hashmap.get(player_data.getUuid());

        switch (e.getSlot()){
            case 0:
                enchantmentChecker(e, false, faith_data.getTerrainSnowy());
                break;
            case 1:
                enchantmentChecker(e, false, faith_data.getTerrainCold());
                break;
            case 2:
                enchantmentChecker(e, false, faith_data.getTerrainTemperate());
                break;
            case 3:
                enchantmentChecker(e, false, faith_data.getTerrainWarm());
                break;
            case 4:
                enchantmentChecker(e, false, faith_data.getTerrainAquatic());
                break;
            case 5:
                enchantmentChecker(e, false, faith_data.getTerrainCave());
                break;
            case 6:
                enchantmentChecker(e, false, faith_data.getTerrainNether());
                break;
            case 7:
                enchantmentChecker(e, false, faith_data.getTerrainEnd());
                break;
            case 8:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Select Powers"));
                break;
            case 18:
                enchantmentChecker(e, true, faith_data.getTerrainStrength());
                break;
            case 19:
                enchantmentChecker(e, true, faith_data.getTerrainHaste());
                break;
            case 20:
                enchantmentChecker(e, true, faith_data.getTerrainSpeed());
                break;
            case 21:
                enchantmentChecker(e, true, faith_data.getTerrainResistance());
                break;
            case 22:
                enchantmentChecker(e, true, faith_data.getTerrainFireResistance());
                break;
            case 23:
                enchantmentChecker(e, true, faith_data.getTerrainWaterBreathing());
                break;
            case 24:
                enchantmentChecker(e, true, faith_data.getTerrainDolphinsGrace());
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

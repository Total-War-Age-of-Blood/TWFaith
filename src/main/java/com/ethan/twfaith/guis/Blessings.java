package com.ethan.twfaith.guis;

import com.ethan.twfaith.customevents.OpenGUIEvent;
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

import java.util.Arrays;


public class Blessings implements Listener {
    private Inventory gui;

    public void openBlessingsGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");

        // TODO add blessings to purchase and make them subtract faith points and give perms when clicked

        // Terrain Bonus
        ItemStack terrain_bonus = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta terrain_bonus_meta = terrain_bonus.getItemMeta();
        terrain_bonus_meta.setDisplayName(ChatColor.GREEN + "Terrain Bonus");
        terrain_bonus_meta.setLore(Arrays.asList("Buffs for being in favored biome."));
        terrain_bonus.setItemMeta(terrain_bonus_meta);
        gui.setItem(10, terrain_bonus);
        // TODO make Summon God's icon the player's head
        // Summon God
        ItemStack summon_god = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta summon_god_meta = summon_god.getItemMeta();
        summon_god_meta.setDisplayName(ChatColor.GOLD + "Summon God");
        summon_god_meta.setLore(Arrays.asList("Allows followers to summon god to them with tpa request."));
        summon_god.setItemMeta(summon_god_meta);
        gui.setItem(11, summon_god);

        // God Proximity Bonus
        ItemStack god_proximity_bonus = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta god_proximity_bonus_meta = god_proximity_bonus.getItemMeta();
        god_proximity_bonus_meta.setDisplayName(ChatColor.AQUA + "God Proximity Bonus");
        god_proximity_bonus_meta.setLore(Arrays.asList("Buffs for being close to god."));
        god_proximity_bonus.setItemMeta(god_proximity_bonus_meta);
        gui.setItem(12, god_proximity_bonus);

        // Name Prophets
        ItemStack name_prophets = new ItemStack(Material.BLACK_CANDLE);
        ItemMeta name_prophets_meta = name_prophets.getItemMeta();
        name_prophets_meta.setDisplayName(ChatColor.RED + "Name Prophets");
        name_prophets_meta.setLore(Arrays.asList("Your favorite followers get some of your powers."));
        name_prophets.setItemMeta(name_prophets_meta);
        gui.setItem(13, name_prophets);

        // Holy Protection
        ItemStack holy_protection = new ItemStack(Material.SHIELD);
        ItemMeta holy_protection_meta = holy_protection.getItemMeta();
        holy_protection_meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Holy Protection");
        holy_protection_meta.setLore(Arrays.asList("Buff your followers when they are defending their church."));
        holy_protection.setItemMeta(holy_protection_meta);
        gui.setItem(14, holy_protection);

        // God of Crafts
        ItemStack god_of_crafts = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta god_of_crafts_meta = god_of_crafts.getItemMeta();
        god_of_crafts_meta.setDisplayName(ChatColor.BLUE + "God of Crafts");
        god_of_crafts_meta.setLore(Arrays.asList("Increases quality of crafted tools, weapons, or armor."));
        god_of_crafts.setItemMeta(god_of_crafts_meta);
        gui.setItem(15, god_of_crafts);

        // Close Menu
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta close_meta = close.getItemMeta();
        close_meta.setDisplayName(ChatColor.RED + "Close Menu");
        close.setItemMeta(close_meta);
        gui.setItem(16, close);

        // Frame
        ItemStack frame = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
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

        switch (e.getSlot()){
            case 10:
                System.out.println("Placeholder");
                break;
            case 16:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Faith Upgrade"));
                break;
        }
    }

    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        if(e.getGui_name().equals("Blessings")){openBlessingsGui(e.getPlayer());}
    }
}

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

public class FaithUpgrade implements Listener {
    private Inventory gui;

    public void openNewGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");

        // Blessings
        ItemStack blessings = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta blessings_meta = blessings.getItemMeta();
        blessings_meta.setDisplayName(ChatColor.AQUA + "Blessings");
        blessings_meta.setLore(Arrays.asList(ChatColor.AQUA + "Bless your followers with abilities."));
        blessings.setItemMeta(blessings_meta);
        gui.setItem(10, blessings);

        // God Powers
        ItemStack god_powers = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta god_powers_meta = god_powers.getItemMeta();
        god_powers_meta.setDisplayName(ChatColor.DARK_PURPLE + "God Powers");
        god_powers_meta.setLore(Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade your personal abilities."));
        god_powers.setItemMeta(god_powers_meta);
        gui.setItem(12, god_powers);

        // Curses
        ItemStack curses = new ItemStack(Material.ENDER_EYE);
        ItemMeta curses_meta = curses.getItemMeta();
        curses_meta.setDisplayName(ChatColor.GREEN + "Curses");
        curses_meta.setLore(Arrays.asList(ChatColor.GREEN + "Curse the heathens."));
        curses.setItemMeta(curses_meta);
        gui.setItem(14, curses);

        // Close Menu
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta close_meta = close.getItemMeta();
        close_meta.setDisplayName(ChatColor.RED + "Close Menu");
        close.setItemMeta(close_meta);
        gui.setItem(16, close);

        // Frame
        ItemStack frame = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
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
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Blessings"));
                break;
            case 12:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "God Powers"));
                break;
            case 14:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Curses"));
                break;
            case 16:
                e.getWhoClicked().closeInventory();
                break;
        }
    }

    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        // When custom event is triggered, check to see if it wants to open this gui
        // if so, open the gui
        if(e.getGui_name().equals("Faith Upgrade")){openNewGui(e.getPlayer());}
    }
}

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

import java.util.Collections;
import java.util.Objects;

public class FaithUpgrade implements Listener {
    private Inventory gui;

    public void openNewGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");

        // Blessings
        generateGUI(Material.TOTEM_OF_UNDYING, ChatColor.AQUA, "Blessings", "Bless your followers with abilities.", 10);

        // God Powers
        generateGUI(Material.AMETHYST_SHARD, ChatColor.DARK_PURPLE, "God Powers", "Upgrade your personal abilities", 12);

        // Curses
        generateGUI(Material.ENDER_EYE, ChatColor.GREEN, "Curses", "Curse the heathens.", 14);

        // Close Menu
        generateGUI(Material.BARRIER, ChatColor.RED, "Close Menu", "See you later!", 16);

        // Frame
        ItemStack frame = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26}){
            gui.setItem(i, frame);
        }

        player.openInventory(gui);
    }

    public void generateGUI(Material material, ChatColor color, String display, String lore, int place){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(color + display);
        itemMeta.setLore(Collections.singletonList(lore));
        item.setItemMeta(itemMeta);
        gui.setItem(place, item);
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){
        try{        if(!Objects.equals(e.getClickedInventory(), gui)){
            return;
        }}catch (NullPointerException exception){return;}

        if (gui == null){return;}

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

    // This event is essential for GUI's because without it the plugin cannot recognize the in-game GUI as equal to the
    // gui in the file.
    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        // When custom event is triggered, check to see if it wants to open this gui
        // if so, open the gui
        if(e.getGuiName().equals("Faith Upgrade")){openNewGui(e.getPlayer());}
    }
}

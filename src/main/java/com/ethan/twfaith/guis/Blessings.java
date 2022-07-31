package com.ethan.twfaith.guis;

import com.ethan.twfaith.customevents.FaithUpgradeEvent;
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


public class Blessings implements Listener {
    private Inventory gui;

    public void openBlessingsGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");

        // TODO add blessings to purchase and make them subtract faith points and give perms when clicked


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
        if(!e.getClickedInventory().equals(gui)){
            return;
        }

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();

        switch (e.getSlot()){
            case 10:
                System.out.println("Placeholder");
                break;
            case 16:
                Bukkit.getPluginManager().callEvent(new FaithUpgradeEvent(p, "Faith Upgrade"));
                break;
        }
    }

    @EventHandler
    public void faithUpgradeEvent(FaithUpgradeEvent e){
        if(e.getGui_name().equals("Blessings")){openBlessingsGui(e.getPlayer());}
    }
}

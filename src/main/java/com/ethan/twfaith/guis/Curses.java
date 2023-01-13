package com.ethan.twfaith.guis;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
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

import java.util.Arrays;
import java.util.Objects;

public class Curses implements Listener {
    private Inventory gui;

    public void openCursesGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());

        // Crumbling
        generateGUI(Material.DAMAGED_ANVIL, ChatColor.GRAY, "Crumbling", "Heathen's items lose durability quickly.", 10, player_data.getCrumbling());

        // Heavy Boots
        generateGUI(Material.DIAMOND_BOOTS, ChatColor.GRAY, "Heavy Boots", "Heathens wearing boots are slowed down", 11, player_data.getHeavyBoots());

        // Intoxicate
        generateGUI(Material.HONEY_BOTTLE, ChatColor.LIGHT_PURPLE, "Intoxicate", "Nearby heathens gain nausea.", 12, player_data.getIntoxicate());

        // Discombobulate
        generateGUI(Material.PUFFERFISH, ChatColor.YELLOW, "Discombobulate", "Scramble inventories of nearby heathens.", 13, player_data.getDiscombobulate());

        // Entangle
        generateGUI(Material.VINE, ChatColor.GREEN, "Entangle", "Nearby heathens are caged.", 14, player_data.getEntangle());

        // Close Menu
        generateGUI(Material.BARRIER, ChatColor.RED, "Back", "Return to previous menu.", 16, 3);

        // Frame
        ItemStack frame = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26}){
            gui.setItem(i, frame);
        }

        player.openInventory(gui);
    }

    public void generateGUI(Material material, ChatColor color, String display, String lore, int place, int data){
        ItemStack item = new ItemStack(material);
        ItemMeta item_meta = item.getItemMeta();
        assert item_meta != null;
        item_meta.setDisplayName(color + display);
        switch (data){
            case 0:
                item_meta.setLore(Arrays.asList(lore, ChatColor.RED + "Not Owned"));
                break;
            case 1:
                item_meta.setLore(Arrays.asList(lore, ChatColor.GREEN + "Owned"));
                break;
        }
        item.setItemMeta(item_meta);
        gui.setItem(place, item);
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){
        try{if(!Objects.equals(e.getClickedInventory(), gui)){
            return;
        }}catch (NullPointerException exception){return;}

        if (gui == null){return;}

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        PlayerData playerData = PlayerHashMap.player_data_hashmap.get(p.getUniqueId());
        Faith faithData = FaithHashMap.player_faith_hashmap.get(p.getUniqueId());
        ItemStack item = e.getCurrentItem();

        switch (e.getSlot()){
            case 10:
                if (faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("crumbling-cost"), playerData.getCrumbling(), item, e.getSlot(), "Heathen's items lose durability quickly.")){return;}
                playerData.setCrumbling(1);
                break;
            case 11:
                if (faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("heavy-cost"), playerData.getHeavyBoots(), item, e.getSlot(), "Heathens wearing boots are slowed down.")){return;}
                playerData.setHeavy_boots(1);
                break;
            case 12:
                if (faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("intoxicate-cost"), playerData.getIntoxicate(), item, e.getSlot(), "Nearby heathens gain nausea.")){return;}
                playerData.setIntoxicate(1);
                break;
            case 13:
                if (faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("discombobulate-cost"), playerData.getDiscombobulate(), item, e.getSlot(),"Scramble inventories of nearby heathens.")){return;}
                playerData.setDiscombobulate(1);
                break;
            case 14:
                if (faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("entangle-cost"), playerData.getEntangle(), item, e.getSlot(), "Nearby heathens are caged.")){return;}
                playerData.setEntangle(1);
                break;
            case 16:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Faith Upgrade"));
                break;
        }

        PlayerHashMap.player_data_hashmap.put(p.getUniqueId(), playerData);
    }

    public boolean faithPointsChecker(Faith faithData, Player p, int cost, int data, ItemStack item, int slot, String lore){
        if (data > 0){return true;}
        Faith faith = FaithHashMap.player_faith_hashmap.get(p.getUniqueId());
        if (!(faith.getFaithPoints() >= cost)){
            p.sendMessage(ChatColor.RED + "You need more Faith Points to purchase this upgrade.");
            return true;
        }
        faithData.setFaithPoints(faithData.getFaithPoints() - cost);
        p.sendMessage(faithData.getFaithPoints() + " Faith Points remaining.");
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setLore(Arrays.asList(lore, ChatColor.GREEN + "Owned"));
        item.setItemMeta(item_meta);
        gui.setItem(slot, item);
        return false;
    }

    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        if(e.getGui_name().equals("Curses")){openCursesGui(e.getPlayer());}
    }
}

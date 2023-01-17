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

import java.util.Objects;

public class Curses implements Listener {
    private Inventory gui;

    public void openCursesGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");
        PlayerData player_data = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());

        Util util = new Util();

        // Crumbling
        util.generateGUI(Material.DAMAGED_ANVIL, ChatColor.GRAY, "Crumbling", "Heathen's items lose durability quickly.", 10, player_data.getCrumbling(), gui);

        // Heavy Boots
        util.generateGUI(Material.DIAMOND_BOOTS, ChatColor.GRAY, "Heavy Boots", "Heathens wearing boots are slowed down", 11, player_data.getHeavyBoots(), gui);

        // Intoxicate
        util.generateGUI(Material.HONEY_BOTTLE, ChatColor.LIGHT_PURPLE, "Intoxicate", "Nearby heathens gain nausea.", 12, player_data.getIntoxicate(), gui);

        // Discombobulate
        util.generateGUI(Material.PUFFERFISH, ChatColor.YELLOW, "Discombobulate", "Scramble inventories of nearby heathens.", 13, player_data.getDiscombobulate(), gui);

        // Entangle
        util.generateGUI(Material.VINE, ChatColor.GREEN, "Entangle", "Nearby heathens are caged.", 14, player_data.getEntangle(), gui);

        // Close Menu
        util.generateGUI(Material.BARRIER, ChatColor.RED, "Back", "Return to previous menu.", 16, 3, gui);

        // Frame
        ItemStack frame = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26}){
            gui.setItem(i, frame);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){
        try{if(!Objects.equals(e.getClickedInventory(), gui)){
            return;
        }}catch (NullPointerException exception){return;}

        if (gui == null){return;}

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        PlayerData playerData = PlayerHashMap.playerDataHashMap.get(p.getUniqueId());
        Faith faithData = FaithHashMap.playerFaithHashmap.get(p.getUniqueId());
        ItemStack item = e.getCurrentItem();
        Util util = new Util();

        switch (e.getSlot()){
            case 10:
                if (util.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("crumbling-cost"), playerData.getCrumbling(), item, e.getSlot(), "Heathen's items lose durability quickly.", gui)){return;}
                playerData.setCrumbling(1);
                break;
            case 11:
                if (util.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("heavy-cost"), playerData.getHeavyBoots(), item, e.getSlot(), "Heathens wearing boots are slowed down.", gui)){return;}
                playerData.setHeavy_boots(1);
                break;
            case 12:
                if (util.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("intoxicate-cost"), playerData.getIntoxicate(), item, e.getSlot(), "Nearby heathens gain nausea.", gui)){return;}
                playerData.setIntoxicate(1);
                break;
            case 13:
                if (util.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("discombobulate-cost"), playerData.getDiscombobulate(), item, e.getSlot(),"Scramble inventories of nearby heathens.", gui)){return;}
                playerData.setDiscombobulate(1);
                break;
            case 14:
                if (util.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("entangle-cost"), playerData.getEntangle(), item, e.getSlot(), "Nearby heathens are caged.", gui)){return;}
                playerData.setEntangle(1);
                break;
            case 16:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Faith Upgrade"));
                break;
        }

        PlayerHashMap.playerDataHashMap.put(p.getUniqueId(), playerData);
    }
    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        if(e.getGuiName().equals("Curses")){openCursesGui(e.getPlayer());}
    }
}

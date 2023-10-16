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
        PlayerData playerData = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        Faith faith = FaithHashMap.playerFaithHashmap.get(player.getUniqueId());

        GUIUtil GUIUtil = new GUIUtil();

        // Crumbling
        GUIUtil.generateGUI(Material.DAMAGED_ANVIL, ChatColor.GRAY, "Crumbling", "Heathen's items lose durability quickly.", "crumbling-cost",10, faith.getCrumbling(), gui);

        // Heavy Boots
        GUIUtil.generateGUI(Material.DIAMOND_BOOTS, ChatColor.GRAY, "Heavy Boots", "Heathens wearing boots are slowed down", "heavy-cost",11, faith.getHeavyBoots(), gui);

        // Intoxicate
        GUIUtil.generateGUI(Material.HONEY_BOTTLE, ChatColor.LIGHT_PURPLE, "Intoxicate", "Nearby heathens gain nausea.", "intoxicate-cost",12, faith.getIntoxicate(), gui);

        // Discombobulate
        GUIUtil.generateGUI(Material.PUFFERFISH, ChatColor.YELLOW, "Discombobulate", "Scramble inventories of nearby heathens.", "discombobulate-cost",13, faith.getDiscombobulate(), gui);

        // Entangle
        GUIUtil.generateGUI(Material.VINE, ChatColor.GREEN, "Entangle", "Nearby heathens are caged.", "entangle-cost",14, faith.getEntangle(), gui);

        // Close Menu
        GUIUtil.generateGUI(Material.BARRIER, ChatColor.RED, "Back", "Return to previous menu.", "N/A",16, 3, gui);

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
        GUIUtil GUIUtil = new GUIUtil();

        switch (e.getSlot()){
            case 10:
                if (GUIUtil.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("crumbling-cost"), faithData.getCrumbling(), item, e.getSlot(), "Heathen's items lose durability quickly.", gui)){return;}
                faithData.setCrumbling(1);
                break;
            case 11:
                if (GUIUtil.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("heavy-cost"), faithData.getHeavyBoots(), item, e.getSlot(), "Heathens wearing boots are slowed down.", gui)){return;}
                faithData.setHeavyBoots(1);
                break;
            case 12:
                if (GUIUtil.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("intoxicate-cost"), faithData.getIntoxicate(), item, e.getSlot(), "Nearby heathens gain nausea.", gui)){return;}
                faithData.setIntoxicate(1);
                break;
            case 13:
                if (GUIUtil.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("discombobulate-cost"), faithData.getDiscombobulate(), item, e.getSlot(),"Scramble inventories of nearby heathens.", gui)){return;}
                faithData.setDiscombobulate(1);
                break;
            case 14:
                if (GUIUtil.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("entangle-cost"), faithData.getEntangle(), item, e.getSlot(), "Nearby heathens are caged.", gui)){return;}
                faithData.setEntangle(1);
                break;
            case 16:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Faith Upgrade"));
                break;
        }

        FaithHashMap.playerFaithHashmap.put(p.getUniqueId(), faithData);
    }
    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        if(e.getGuiName().equals("Curses")){openCursesGui(e.getPlayer());}
    }
}

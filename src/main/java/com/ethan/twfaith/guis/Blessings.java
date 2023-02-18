package com.ethan.twfaith.guis;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import java.util.Arrays;
import java.util.Objects;


public class Blessings implements Listener {
    private Inventory gui;
// TODO implement FP cost in lore
    public void openBlessingsGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");
        Faith faith = FaithHashMap.playerFaithHashmap.get(player.getUniqueId());
        Util util = new Util();

        // Terrain Bonus
        util.generateGUI(Material.GRASS_BLOCK, ChatColor.GREEN, "Terrain Bonus", "Buffs for being in favored biome.", "terrain-cost",10, 3, gui);

        // Summon God
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.GOLD + "Summon God");
        itemMeta.setOwningPlayer(player);
        switch (faith.getSummonGod()){
            case 0:
                itemMeta.setLore(Arrays.asList("Allows followers to summon the god.", ChatColor.RED + "Not Owned", ChatColor.GOLD + "Cost: " + TWFaith.getPlugin().getConfig().getInt("summon-cost") + "FP"));
                break;
            case 1:
                itemMeta.setLore(Arrays.asList("Allows followers to summon the god.", ChatColor.GREEN + "Owned"));
                break;
        }
        item.setItemMeta(itemMeta);
        gui.setItem(11, item);

        // Hell's Fury
        util.generateGUI(Material.FLINT_AND_STEEL, ChatColor.RED, "Hell's Fury", "Followers leave a trail of fire in their wake.", "hells-cost",12, faith.getHellsFury(), gui);

        // Powerful Flock
        util.generateGUI(Material.WHITE_WOOL, ChatColor.WHITE, "Powerful Flock", "Your followers are stronger together.", "flock-cost",13, faith.getPowerfulFlock(), gui);

        // Divine Intervention
        util.generateGUI(Material.ELYTRA, ChatColor.GOLD, "Divine Intervention", "Raise your followers out of the devil's grasp.", "divine-cost",14, faith.getDivineIntervention(), gui);

        // Mana
        util.generateGUI(Material.BREAD, ChatColor.GOLD, "Mana", "Shower mana from the sky.", "mana-cost",15, faith.getMana(), gui);

        // Close Menu
        util.generateGUI(Material.BARRIER, ChatColor.RED, "Close Menu", "Return to previous menu.", "N/A",16, 3, gui);

        // Frame
        ItemStack frame = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26}){
            gui.setItem(i, frame);
        }

        player.openInventory(gui);
    }



    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){
        try{        if(!Objects.equals(e.getClickedInventory(), gui)){
            return;
        }}catch (NullPointerException exception){return;}

        if (gui == null){return;}

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        Faith faith = FaithHashMap.playerFaithHashmap.get(p.getUniqueId());
        Faith faithData = FaithHashMap.playerFaithHashmap.get(p.getUniqueId());
        ItemStack item = e.getCurrentItem();

        Util util = new Util();

        switch (e.getSlot()){
            case 10:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Biome Groups"));
                break;
            case 11:
                if (util.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("summon-cost"), faith.getSummonGod(), item, e.getSlot(), "Allows followers to summon the god.", gui)){return;}
                faith.setSummonGod(1);
                break;
            case 12:
                if (util.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("hells-cost"), faith.getHellsFury(), item, e.getSlot(), "Followers leave a trail of fire in their wake.", gui)){return;}
                faith.setHellsFury(1);
                break;
            case 13:
                if (util.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("flock-cost"), faith.getPowerfulFlock(), item, e.getSlot(), "Your followers are stronger together.", gui)){return;}
                faith.setPowerfulFlock(1);
                break;
            case 14:
                if (util.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("divine-cost"), faith.getDivineIntervention(), item, e.getSlot(), "Raise your followers out of the devil's grasp.", gui)){return;}
                faith.setDivineIntervention(1);
                break;
            case 15:
                if (util.faithPointsChecker(faithData, p, TWFaith.getPlugin().getConfig().getInt("mana-cost"), faith.getMana(), item, e.getSlot(), "Shower mana from the sky.", gui)){return;}
                faith.setMana(1);
                break;
            case 16:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Faith Upgrade"));
                break;
        }
    }
    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        if(e.getGuiName().equals("Blessings")){openBlessingsGui(e.getPlayer());}
    }
}

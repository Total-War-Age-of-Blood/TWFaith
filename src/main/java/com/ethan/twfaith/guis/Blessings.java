package com.ethan.twfaith.guis;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
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
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Objects;


public class Blessings implements Listener {
    private Inventory gui;

    public void openBlessingsGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());

        // Terrain Bonus
        generateGUI(Material.GRASS_BLOCK, ChatColor.GREEN, "Terrain Bonus", "Buffs for being in favored biome.", 10, 3);

        // Summon God
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta item_meta = (SkullMeta) item.getItemMeta();
        assert item_meta != null;
        item_meta.setDisplayName(ChatColor.GOLD + "Summon God");
        item_meta.setOwningPlayer(player);
        switch (player_data.getSummon_god()){
            case 0:
                item_meta.setLore(Arrays.asList("Allows followers to summon the god.", ChatColor.RED + "Not Owned"));
                break;
            case 1:
                item_meta.setLore(Arrays.asList("Allows followers to summon the god.", ChatColor.GREEN + "Owned"));
                break;
        }
        item.setItemMeta(item_meta);
        gui.setItem(11, item);

        // Hell's Fury
        generateGUI(Material.FLINT_AND_STEEL, ChatColor.RED, "Hell's Fury", "Followers leave a trail of fire in their wake.", 12, player_data.getHells_fury());

        // Powerful Flock
        generateGUI(Material.WHITE_WOOL, ChatColor.WHITE, "Powerful Flock", "Your followers are stronger together.", 13, player_data.getPowerful_flock());

        // Divine Intervention
        generateGUI(Material.ELYTRA, ChatColor.GOLD, "Divine Intervention", "Raise your followers out of the devil's grasp.", 14, player_data.getDivine_intervention());

        // Mana
        generateGUI(Material.BREAD, ChatColor.GOLD, "Mana", "Shower mana from the sky.", 15, player_data.getMana());

        // Close Menu
        generateGUI(Material.BARRIER, ChatColor.RED, "Close Menu", "Return to previous menu.", 16, 3);

        // Frame
        ItemStack frame = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
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
        try{        if(!Objects.equals(e.getClickedInventory(), gui)){
            return;
        }}catch (NullPointerException exception){return;}

        if (gui == null){return;}

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(p.getUniqueId());
        ItemStack item = e.getCurrentItem();

        switch (e.getSlot()){
            case 10:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Biome Groups"));
                break;
            case 11:
                if (faithPointsChecker(player_data, p, TWFaith.getPlugin().getConfig().getInt("summon-cost"), player_data.getSummon_god(), item, e.getSlot(), "Allows followers to summon the god.")){return;}
                player_data.setSummon_god(1);
                break;
            case 12:
                if (faithPointsChecker(player_data, p, TWFaith.getPlugin().getConfig().getInt("hells-cost"), player_data.getHells_fury(), item, e.getSlot(), "Followers leave a trail of fire in their wake.")){return;}
                player_data.setHells_fury(1);
                break;
            case 13:
                if (faithPointsChecker(player_data, p, TWFaith.getPlugin().getConfig().getInt("flock-cost"), player_data.getPowerful_flock(), item, e.getSlot(), "Your followers are stronger together.")){return;}
                player_data.setPowerful_flock(1);
                break;
            case 14:
                if (faithPointsChecker(player_data, p, TWFaith.getPlugin().getConfig().getInt("divine-cost"), player_data.getDivine_intervention(), item, e.getSlot(), "Raise your followers out of the devil's grasp.")){return;}
                player_data.setDivine_intervention(1);
                break;
            case 15:
                if (faithPointsChecker(player_data, p, TWFaith.getPlugin().getConfig().getInt("mana-cost"), player_data.getMana(), item, e.getSlot(), "Shower mana from the sky.")){return;}
                player_data.setMana(1);
                break;
            case 16:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Faith Upgrade"));
                break;
        }
    }

    public boolean faithPointsChecker(PlayerData player_data, Player p, int cost, int data, ItemStack item, int slot, String lore){
        if (data > 0){return true;}
        if (!(player_data.getFaith_points() >= cost)){
            p.sendMessage(ChatColor.RED + "You need more Faith Points to purchase this upgrade.");
            return true;
        }
        player_data.setFaith_points(player_data.getFaith_points() - cost);
        p.sendMessage(player_data.getFaith_points() + " Faith Points remaining.");
        ItemMeta item_meta = item.getItemMeta();
        item_meta.setLore(Arrays.asList(lore, ChatColor.GREEN + "Owned"));
        item.setItemMeta(item_meta);
        gui.setItem(slot, item);
        return false;
    }
    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        if(e.getGui_name().equals("Blessings")){openBlessingsGui(e.getPlayer());}
    }
}

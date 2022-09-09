package com.ethan.twfaith.guis;

import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
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

import java.util.Collections;
import java.util.Objects;


public class Blessings implements Listener {
    private Inventory gui;

    public void openBlessingsGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");

        // TODO add blessings to purchase and make them subtract faith points and give perms when clicked

        // Terrain Bonus
        generateGUI(Material.GRASS_BLOCK, ChatColor.GREEN, "Terrain Bonus", "Buffs for being in favored biome.", 10);

        // TODO make Summon God's icon the player's head
        // Summon God
        generateGUI(Material.TOTEM_OF_UNDYING, ChatColor.GOLD, "Summon God", "Allows followers to summon got to them with tpa request.", 11);

        // Hell's Fury
        generateGUI(Material.FLINT_AND_STEEL, ChatColor.RED, "Hell's Fury", "Followers leave a trail of fire in their wake.", 12);

        // Powerful Flock
        generateGUI(Material.WHITE_WOOL, ChatColor.WHITE, "Powerful Flock", "Your followers are stronger together.", 13);

        // Divine Intervention
        generateGUI(Material.ELYTRA, ChatColor.GOLD, "Divine Intervention", "Raise your followers out of the devil's grasp.", 14);

        // Mana
        generateGUI(Material.BREAD, ChatColor.GOLD, "Mana", "Shower mana from the sky.", 15);

        // Close Menu
        generateGUI(Material.BARRIER, ChatColor.RED, "Close Menu", "Return to previous menu.", 16);

        // Frame
        ItemStack frame = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26}){
            gui.setItem(i, frame);
        }

        player.openInventory(gui);
    }

    public void generateGUI(Material material, ChatColor color, String display, String lore, int place){
        ItemStack item = new ItemStack(material);
        ItemMeta item_meta = item.getItemMeta();
        assert item_meta != null;
        item_meta.setDisplayName(color + display);
        item_meta.setLore(Collections.singletonList(lore));
        item.setItemMeta(item_meta);
        gui.setItem(place, item);
    }

    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){
        try{        if(!Objects.equals(e.getClickedInventory(), gui)){
            return;
        }}catch (NullPointerException exception){return;}

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(p.getDisplayName());
        Faith faith_data = FaithHashMap.player_faith_hashmap.get(player_data.getUuid());

        switch (e.getSlot()){
            case 10:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Biome Groups"));
                break;
            case 11:
                player_data.setSummon_god(1);
                break;
            case 12:
                player_data.setHells_fury(1);
                break;
            case 13:
                player_data.setPowerful_flock(1);
                break;
            case 14:
                player_data.setDivine_intervention(1);
                break;
            case 15:
                player_data.setMana(1);
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

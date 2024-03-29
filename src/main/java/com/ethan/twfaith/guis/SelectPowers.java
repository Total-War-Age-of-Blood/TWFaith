package com.ethan.twfaith.guis;

import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

public class SelectPowers implements Listener {
    private Inventory gui;
    public void openSelectPowersGui(Player player, PlayerData player_data, Faith faith){
        gui = Bukkit.createInventory(null, 45, "Select Powers");

        // Blessings
        // Terrain Bonus
        ItemStack item = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta item_meta = item.getItemMeta();
        assert item_meta != null;
        item_meta.setDisplayName(ChatColor.GREEN + "Terrain Bonus");
        item.setItemMeta(item_meta);
        gui.setItem(10, item);

        // Summon God
        generateGUI(Material.WHITE_CANDLE, ChatColor.WHITE, "Summon God", faith.getSummonGod(), 11);

        // Hell's Fury
        generateGUI(Material.FLINT_AND_STEEL, ChatColor.YELLOW, "Hell's Fury", faith.getHellsFury(), 12);

        // Powerful Flock
        generateGUI(Material.WHITE_WOOL, ChatColor.RED, "Powerful Flock", faith.getPowerfulFlock(), 13);

        // Divine Intervention
        generateGUI(Material.ELYTRA, ChatColor.GOLD, "Divine Intervention", faith.getDivineIntervention(), 14);

        // Mana
        generateGUI(Material.BREAD, ChatColor.GOLD, "Mana", faith.getMana(), 15);

        // God Powers
        // Lion's Heart
        ItemStack lion_head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta lion_head_meta = (SkullMeta) lion_head.getItemMeta();

        // Giving the player head a custom texture
        PlayerProfile profile = Bukkit.getServer().createPlayerProfile(UUID.randomUUID(), null);

        try{
            profile.getTextures().setSkin(new URL("http://textures.minecraft.net/texture/9df16fb7bd6eab9a47e7306896a0b6ade7c36965db56469ddb4f93dc2aed6396"));
            assert lion_head_meta != null;
            lion_head_meta.setOwnerProfile(profile);
        }catch (MalformedURLException e){e.printStackTrace();}

        assert lion_head_meta != null;
        lion_head_meta.setDisplayName(ChatColor.DARK_RED + "Lion's Heart");
        switch (faith.getLionsHeart()){
            case 0:
                lion_head_meta.setLore(Arrays.asList("Your attacks are stronger when you are unarmored", ChatColor.RED + "Not Owned"));
                break;
            case 1:
                lion_head_meta.setLore(Arrays.asList("Your attacks are stronger when you are unarmored", ChatColor.GREEN + "Owned"));
                break;
        }
        lion_head.setItemMeta(lion_head_meta);
        gui.setItem(19, lion_head);

        // Savior
        generateGUI(Material.GOLDEN_CARROT, ChatColor.RED, "Savior", faith.getSavior(), 20);

        // Taunt
        generateGUI(Material.DIAMOND, ChatColor.GOLD, "Taunt", faith.getTaunt(), 21);

        // Insidious
        generateGUI(Material.ENDER_EYE, ChatColor.BLUE, "Insidious", faith.getInsidious(), 22);

        // Explosive Landing
        generateGUI(Material.TNT_MINECART, ChatColor.RED, "Explosive Landing", faith.getExplosiveLanding(), 23);

        // Flood Power
        generateGUI(Material.WATER_BUCKET, ChatColor.BLUE, "Flood", faith.getFlood(), 24);

        // Curses
        // Crumbling
        generateGUI(Material.DAMAGED_ANVIL, ChatColor.GRAY, "Crumbling", faith.getCrumbling(), 28);

        // Heavy Boots
        generateGUI(Material.DIAMOND_BOOTS, ChatColor.BLACK, "Heavy Boots", faith.getHeavyBoots(), 29);

        // Intoxicate
        generateGUI(Material.HONEY_BOTTLE, ChatColor.LIGHT_PURPLE, "Intoxicate", faith.getIntoxicate(), 30);

        // Discombobulate
        generateGUI(Material.PUFFERFISH, ChatColor.YELLOW, "Discombobulate", faith.getDiscombobulate(), 31);

        // Entangle
        generateGUI(Material.VINE, ChatColor.GREEN, "Entangle", faith.getEntangle(), 32);

        // Frame
        ItemStack frame = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        for (int number : new int[]{0, 9, 18, 27, 36, 8, 17, 26, 35, 44, 1, 2, 3, 4, 5, 6, 7, 36, 37, 38, 39, 40, 41, 42, 43}){
            gui.setItem(number, frame);

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
        PlayerData player_data = PlayerHashMap.playerDataHashMap.get(p.getUniqueId());
        Faith faith = FaithHashMap.playerFaithHashmap.get(p.getUniqueId());

        // Check if player has any empty hotbar slots
        Inventory player_inventory = p.getInventory();
        ItemStack[] player_inventory_contents = player_inventory.getContents();
        boolean empty_slot = false;
        for (int i = 0; i < 9; i++){
            if (player_inventory_contents[i] == null){
                empty_slot = true;
                break;
            }
        }
        // If player has no empty hotbar slots, do not give the power
        if (!empty_slot){
            p.sendMessage(ChatColor.RED + "Must have an empty hotbar slot");
            return;
        }

        switch (e.getSlot()){
            case 10:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(p, "Equip Terrain Bonus"));
                break;
            case 11:
                inventoryClickSwitch(faith.getSummonGod(), Material.WHITE_TERRACOTTA, ChatColor.WHITE, "Summon God", p);
                break;
            case 12:
                inventoryClickSwitch(faith.getHellsFury(), Material.RED_TERRACOTTA, ChatColor.RED, "Hell's Fury", p);
                break;
            case 13:
                inventoryClickSwitch(faith.getPowerfulFlock(), Material.WHITE_TERRACOTTA, ChatColor.WHITE, "Powerful Flock", p);
                break;
            case 14:
                inventoryClickSwitch(faith.getDivineIntervention(), Material.YELLOW_TERRACOTTA, ChatColor.YELLOW, "Divine Intervention", p);
                break;
            case 15:
                inventoryClickSwitch(faith.getMana(), Material.YELLOW_TERRACOTTA, ChatColor.GOLD, "Mana", p);
                break;
            case 19:
                inventoryClickSwitch(faith.getLionsHeart(), Material.RED_TERRACOTTA, ChatColor.RED, "Lion's Heart", p);
                break;
            case 20:
                inventoryClickSwitch(faith.getSavior(), Material.MAGENTA_TERRACOTTA, ChatColor.LIGHT_PURPLE, "Savior", p);
                break;
            case 21:
                inventoryClickSwitch(faith.getTaunt(), Material.ORANGE_TERRACOTTA, ChatColor.GOLD, "Taunt", p);
                break;
            case 22:
                inventoryClickSwitch(faith.getInsidious(), Material.BLUE_TERRACOTTA, ChatColor.BLUE, "Insidious", p);
                break;
            case 23:
                inventoryClickSwitch(faith.getExplosiveLanding(), Material.RED_TERRACOTTA, ChatColor.RED, "Explosive Landing", p);
                break;
            case 24:
                inventoryClickSwitch(faith.getFlood(), Material.BLUE_TERRACOTTA, ChatColor.BLUE, "Flood", p);
                break;
            case 28:
                inventoryClickSwitch(faith.getCrumbling(), Material.GRAY_TERRACOTTA, ChatColor.GRAY, "Crumbling", p);
                break;
            case 29:
                inventoryClickSwitch(faith.getHeavyBoots(), Material.BLACK_TERRACOTTA, ChatColor.BLACK, "Heavy Boots", p);
                break;
            case 30:
                inventoryClickSwitch(faith.getIntoxicate(), Material.MAGENTA_TERRACOTTA, ChatColor.LIGHT_PURPLE, "Intoxicate", p);
                break;
            case 31:
                inventoryClickSwitch(faith.getDiscombobulate(), Material.YELLOW_TERRACOTTA, ChatColor.YELLOW, "Discombobulate", p);
                break;
            case 32:
                inventoryClickSwitch(faith.getEntangle(), Material.GREEN_TERRACOTTA, ChatColor.GREEN, "Entangle", p);
                break;
        }
    }

    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        if(e.getGuiName().equals("Select Powers")){
            PlayerData player_data = PlayerHashMap.playerDataHashMap.get(e.getPlayer().getUniqueId());
            Faith faith = FaithHashMap.playerFaithHashmap.get(e.getPlayer().getUniqueId());
            openSelectPowersGui(e.getPlayer(), player_data, faith);
        }
    }

    // This method will populate the power selection GUI with the powers.
    public void generateGUI(Material block, ChatColor color, String display, int data, int spot){
        ItemStack item = new ItemStack(block);
        ItemMeta item_meta = item.getItemMeta();
        assert item_meta != null;
        item_meta.setDisplayName(color + display);
        switch (data){
            case 0:
                item_meta.setLore(Collections.singletonList(ChatColor.RED + "Not Owned"));
                break;
            case 1:
                item_meta.setLore(Collections.singletonList(ChatColor.GREEN + "Owned"));
                break;
        }
        item.setItemMeta(item_meta);
        gui.setItem(spot, item);

    }

    // This method gives the god the terracotta power block from the selection menu
    public void inventoryClickSwitch(int player_data, Material block, ChatColor color, String display_name, Player p){
        if (player_data > 0){
            ItemStack item = new ItemStack(block);
            ItemMeta item_meta = item.getItemMeta();
            assert item_meta != null;
            item_meta.setDisplayName(color + display_name);
            PersistentDataContainer item_data = item_meta.getPersistentDataContainer();
            item_data.set(new NamespacedKey(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")), "Power"), PersistentDataType.STRING, display_name);
            item_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(item_meta);

            p.getInventory().addItem(item);
        }else{p.sendMessage(ChatColor.DARK_RED + "ERROR: Player must own power to equip it");}
    }
}


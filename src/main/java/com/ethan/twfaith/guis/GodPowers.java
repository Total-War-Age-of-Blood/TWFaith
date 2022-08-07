package com.ethan.twfaith.guis;

import com.ethan.twfaith.customevents.FaithUpgradeEvent;
import com.ethan.twfaith.files.PlayerData;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.io.*;
import java.util.Arrays;
import java.util.UUID;

public class GodPowers implements Listener {
    private Inventory gui;
    // TODO Implement mechanics for powers.
    // TODO Implement gui purchasing
    public void openGodPowersGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");
        // TODO make Lion's Heart's icon a lion head
        // Lion's Heart
        ItemStack lions_heart = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta lions_heart_meta = lions_heart.getItemMeta();
        lions_heart_meta.setDisplayName(ChatColor.DARK_RED + "Lion's Heart");
        lions_heart_meta.setLore(Arrays.asList("Your attacks are stronger when you are unarmored."));
        lions_heart.setItemMeta(lions_heart_meta);
        gui.setItem(10, lions_heart);

        // Savior
        ItemStack savior = new ItemStack(Material.GOLDEN_CARROT);
        ItemMeta savior_meta = savior.getItemMeta();
        savior_meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Savior");
        savior_meta.setLore(Arrays.asList("Swap places with injured followers."));
        savior.setItemMeta(savior_meta);
        gui.setItem(11, savior);

        // Taunt
        ItemStack taunt = new ItemStack(Material.DIAMOND);
        ItemMeta taunt_meta = taunt.getItemMeta();
        taunt_meta.setDisplayName(ChatColor.GOLD + "Taunt");
        taunt_meta.setLore(Arrays.asList("Attract the attention of enemies."));
        taunt.setItemMeta(taunt_meta);
        gui.setItem(12, taunt);

        // Insidious
        ItemStack insidious = new ItemStack(Material.ENDER_EYE);
        ItemMeta insidious_meta = insidious.getItemMeta();
        insidious_meta.setDisplayName(ChatColor.BLUE + "Insidious");
        insidious_meta.setLore(Arrays.asList("Gain invisibility when standing still."));
        insidious.setItemMeta(insidious_meta);
        gui.setItem(13, insidious);

        // Explosive Landing
        ItemStack explosive_landing = new ItemStack(Material.TNT_MINECART);
        ItemMeta explosive_landing_meta = explosive_landing.getItemMeta();
        explosive_landing_meta.setDisplayName(ChatColor.DARK_RED + "Explosive Landing");
        explosive_landing_meta.setLore(Arrays.asList("Create an explosion when you hit the ground."));
        explosive_landing.setItemMeta(explosive_landing_meta);
        gui.setItem(14, explosive_landing);

        // Flood
        ItemStack flood = new ItemStack(Material.WATER_BUCKET);
        ItemMeta flood_meta = flood.getItemMeta();
        flood_meta.setDisplayName(ChatColor.DARK_BLUE + "Flood");
        flood_meta.setLore(Arrays.asList("Temporarily flood the area."));
        flood.setItemMeta(flood_meta);
        gui.setItem(15, flood);


        // Close Menu
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta close_meta = close.getItemMeta();
        close_meta.setDisplayName(ChatColor.RED + "Back");
        close.setItemMeta(close_meta);
        gui.setItem(16, close);

        // Frame
        ItemStack frame = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
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

        Player player = (Player) e.getWhoClicked();

        switch (e.getSlot()){
            case 10:
                File player_data_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "PlayerData");
                File player_data_file = new File(player_data_folder, player.getUniqueId() + ".json");
                Gson gson = new Gson();
                try{
                    FileReader player_data_reader = new FileReader(player_data_file);
                    PlayerData player_data = gson.fromJson(player_data_reader, PlayerData.class);
                    player_data.setLions_heart(1);
                    FileWriter player_data_writer = new FileWriter(player_data_file, false);
                    gson.toJson(player_data, player_data_writer);
                    player_data_writer.flush();
                    player_data_writer.close();
                    System.out.println("Lions Heart clicked");
                }catch(IOException exception){exception.printStackTrace();}
                break;
            case 11:
                player_data_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "PlayerData");
                player_data_file = new File(player_data_folder, player.getUniqueId() + ".json");
                gson = new Gson();
                try{
                    FileReader player_data_reader = new FileReader(player_data_file);
                    PlayerData player_data = gson.fromJson(player_data_reader, PlayerData.class);
                    player_data.setSavior(1);
                    FileWriter player_data_writer = new FileWriter(player_data_file, false);
                    gson.toJson(player_data, player_data_writer);
                    player_data_writer.flush();
                    player_data_writer.close();
                    System.out.println("Savior clicked");
                }catch(IOException exception){exception.printStackTrace();}
                break;
            case 16:
                Bukkit.getPluginManager().callEvent(new FaithUpgradeEvent(player, "Faith Upgrade"));
                break;
        }
    }

    @EventHandler
    public void faithUpgradeEvent(FaithUpgradeEvent e){
        if(e.getGui_name().equals("God Powers")){openGodPowersGui(e.getPlayer());}
    }

    // Event handlers for powers
    // Lions Heart
    @EventHandler
    public void lionsHeartEvent(PlayerMoveEvent e){
        Player player = e.getPlayer();
        File player_data_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "PlayerData");
        File player_data_file = new File(player_data_folder, player.getUniqueId() + ".json");
        Gson gson = new Gson();
        try{
            Reader player_data_reader = new FileReader(player_data_file);
            PlayerData player_data = gson.fromJson(player_data_reader, PlayerData.class);
            if (player_data.getLions_heart() > 0){
                int amplifier = 0;
                if (player.getEquipment().getBoots() != null){amplifier += 1;}
                if (player.getEquipment().getLeggings() != null){amplifier += 1;}
                if (player.getEquipment().getChestplate() != null){amplifier += 1;}
                if (player.getEquipment().getHelmet() != null){amplifier += 1;}
                System.out.println(amplifier);
                player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                player.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(Integer.MAX_VALUE, 3 - amplifier));
            }
        }catch(IOException exception){exception.printStackTrace();}
    }

    // Savior
    @EventHandler
    public void saviorTriggerEvent(EntityDamageEvent e){
        System.out.println("Entity Damage detected!");
        if (e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            File player_data_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "PlayerData");
            File player_data_file = new File(player_data_folder, player.getUniqueId() + ".json");
            Gson gson = new Gson();
            if (player.getHealth() - e.getDamage() > 6){return;}
            try{
                Reader player_data_reader = new FileReader(player_data_file);
                PlayerData player_data = gson.fromJson(player_data_reader, PlayerData.class);
                if (player_data.getLeader() || !player_data.getIn_faith()){return;}
                Player leader = Bukkit.getPlayer(UUID.fromString(player_data.getLed_by()));
                System.out.println("Player is in faith and is not leader.");
                if (leader.getLocation().distance(player.getLocation()) > 30){return;}
                System.out.println("Player and leader are within 30 blocks");
                if (leader.getHealth() < 10){return;}
                System.out.println("Leader has at least 5 hearts");
                File leader_data_file = new File (player_data_folder, leader.getUniqueId() + ".json");

                Reader leader_data_reader = new FileReader(leader_data_file);
                PlayerData leader_data = gson.fromJson(leader_data_reader, PlayerData.class);

                if (leader_data.getSavior() > 0){
                    System.out.println("Leader has Savior");
                    Location player_location = player.getLocation();
                    Location leader_location = leader.getLocation();
                    player.teleport(leader_location);
                    leader.teleport(player_location);
                    player.sendMessage("Savior activates!");
                    leader.sendMessage("Savior activates!");
                }
            }catch (IOException exception){exception.printStackTrace();}
        }
    }
}

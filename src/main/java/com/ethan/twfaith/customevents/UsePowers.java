package com.ethan.twfaith.customevents;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.powers.blessings.DivineIntervention;
import com.ethan.twfaith.powers.blessings.HellsFury;
import com.ethan.twfaith.powers.blessings.Mana;
import com.ethan.twfaith.powers.blessings.TerrainBonus;
import com.ethan.twfaith.powers.curses.Discombobulate;
import com.ethan.twfaith.powers.curses.Entangle;
import com.ethan.twfaith.powers.curses.Intoxicate;
import com.ethan.twfaith.powers.godpowers.Flood;
import com.ethan.twfaith.powers.godpowers.Taunt;
import com.ethan.twfaith.tasks.Heavy_Boots_Checker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.UUID;

public class UsePowers implements Listener {

    public static LinkedHashSet<String> crumblers = new LinkedHashSet<>();
    public static HashMap<UUID, Long> last_drop = new HashMap<>();
    // When player right clicks, check if the item in either hand has the NBT tag for a god power. If it does, activate the power.
    @EventHandler
    public void onRightClick(PlayerInteractEvent e){

        // Check to see if this event was fired due to dropping a power. If it was, cancel it.
        try{
            if (System.currentTimeMillis() - last_drop.get(e.getPlayer().getUniqueId()) < 20){
                e.setCancelled(true);
                return;
            }
        }catch (Exception ignored){}


        Player player = e.getPlayer();
        PlayerData player_data = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        ItemStack held_item = e.getPlayer().getInventory().getItemInMainHand();
        ItemStack other_item = player.getInventory().getItemInOffHand();

        ItemStack chosen_item = null;
        // Using Objects.requireNonNull is not enough to stop the console from getting spammed. To prevent console spam,
        // you have to catch the error.
        boolean main_null = held_item.getItemMeta() == null;
        boolean off_null = other_item.getItemMeta() == null;

        // Prevents the event from running twice when the player has an item in the offhand
        if (!main_null && !off_null && e.getHand().equals(EquipmentSlot.OFF_HAND)){return;}
        // This fixes issues with event running twice when player has no item in the offhand
        if (main_null && !off_null && e.getHand().equals(EquipmentSlot.OFF_HAND)){
            if (!other_item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING)){return;}
            chosen_item = other_item;
        }else if (!main_null){
            if (!held_item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Bukkit.getPluginManager().getPlugin("TWFaith"), "Power"), PersistentDataType.STRING)){return;}
            chosen_item = held_item;
        }else{return;}

        e.setCancelled(true);
        ItemMeta chosen_item_meta = chosen_item.getItemMeta();
            switch (Objects.requireNonNull(chosen_item_meta.getPersistentDataContainer().get(new NamespacedKey(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")), "Power"), PersistentDataType.STRING))){
                case "Summon God":
                    if (player_data.isSummonGodActive()){
                        player_data.setSummonGodActive(false);
                        player.sendMessage(ChatColor.RED + "Summon God Deactivated");
                        if (!chosen_item.getEnchantments().isEmpty()){
                            chosen_item_meta.removeEnchant(Enchantment.DURABILITY);
                            chosen_item.setItemMeta(chosen_item_meta);
                        }
                    }else{
                        player_data.setSummonGodActive(true);
                        player.sendMessage(ChatColor.GREEN + "Summon God Activated");
                        chosen_item_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                        chosen_item.setItemMeta(chosen_item_meta);
                    }
                    break;
                case "Powerful Flock":
                    if (player_data.isPowerfulFlockActive()){
                        player_data.setPowerfulFlockActive(false);
                        player.sendMessage(ChatColor.RED + "Powerful Flock Deactivated");
                        if (!chosen_item.getEnchantments().isEmpty()){
                            chosen_item_meta.removeEnchant(Enchantment.DURABILITY);
                            chosen_item.setItemMeta(chosen_item_meta);
                        }
                    }else{
                        player_data.setPowerfulFlockActive(true);
                        player.sendMessage(ChatColor.GREEN + "Powerful Flock Activated");
                        chosen_item_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                        chosen_item.setItemMeta(chosen_item_meta);
                    }
                    break;
                case "Hell's Fury":
                    HellsFury fury = new HellsFury();
                    fury.onHellsFuryTrigger(player, player_data, chosen_item, chosen_item_meta);
                    break;
                case "Divine Intervention":
                    // Check if the power is on cool down
                    if (checkCoolDown("Divine Intervention", player, TWFaith.getPlugin().getConfig().getLong("divine-cooldown"))){return;}
                    // If power is not on cool down, put power on cool down
                    player_data.getCoolDowns().put("Divine Intervention", System.currentTimeMillis() / 1000);
                    PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), player_data);
                    // Activate power
                    DivineIntervention divine = new DivineIntervention();
                    divine.onDivineTrigger(player, player_data);
                    player.sendMessage("Divine Intervention Activated");
                    break;
                case "Mana":
                    // Check if the power is on cool down
                    if (checkCoolDown("Mana", player, TWFaith.getPlugin().getConfig().getLong("mana-cooldown"))){return;}
                    // If power is not on cool down, put power on cool down
                    player_data.getCoolDowns().put("Mana", System.currentTimeMillis() / 1000);
                    PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), player_data);
                    // Activate power
                    Mana mana = new Mana();
                    mana.onManaTrigger(player, player_data);
                    break;
                case "Lion's Heart":
                    if (player_data.isLionsHeartActive()){
                        player_data.setLionsHeartActive(false);
                        player.sendMessage(ChatColor.RED + "Lions Heart Deactivated");
                        if (!chosen_item.getEnchantments().isEmpty()){
                            chosen_item_meta.removeEnchant(Enchantment.DURABILITY);
                            chosen_item.setItemMeta(chosen_item_meta);
                        }
                    }else {
                        player_data.setLionsHeartActive(true);
                        player.sendMessage(ChatColor.GREEN + "Lion's Heart Activated");
                        chosen_item_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                        chosen_item.setItemMeta(chosen_item_meta);
                    }
                    break;
                case "Savior":
                    if (player_data.isSaviorActive()){
                        player_data.setSaviorActive(false);
                        player.sendMessage(ChatColor.RED + "Savior Deactivated");
                        if (!chosen_item.getEnchantments().isEmpty()){
                            chosen_item_meta.removeEnchant(Enchantment.DURABILITY);
                            chosen_item.setItemMeta(chosen_item_meta);
                        }
                    }else {
                        player_data.setSaviorActive(true);
                        player.sendMessage(ChatColor.GREEN + "Savior Activated");
                        chosen_item_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                        chosen_item.setItemMeta(chosen_item_meta);
                    }
                    break;
                case "Taunt":
                    // Check if the power is on cool down
                    if (checkCoolDown("Taunt", player, TWFaith.getPlugin().getConfig().getLong("taunt-cooldown"))){return;}
                    // If power is not on cool down, put power on cool down
                    player_data.getCoolDowns().put("Taunt", System.currentTimeMillis() / 1000);
                    PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), player_data);
                    // Activate power
                    Taunt taunt = new Taunt();
                    taunt.tauntTrigger(player);
                    player.sendMessage(ChatColor.GOLD + "You taunt your enemies!");
                    break;
                case "Insidious":
                    if (player_data.isInsidiousActive()){
                        player_data.setInsidiousActive(false);
                        player.sendMessage(ChatColor.RED + "Insidious Deactivated");
                        if (!chosen_item.getEnchantments().isEmpty()){
                            chosen_item_meta.removeEnchant(Enchantment.DURABILITY);
                            chosen_item.setItemMeta(chosen_item_meta);
                        }
                    }else {
                        player_data.setInsidiousActive(true);
                        player.sendMessage(ChatColor.GREEN + "Insidious Activated");
                        chosen_item_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                        chosen_item.setItemMeta(chosen_item_meta);
                    }
                    break;
                case "Explosive Landing":
                    if (player_data.isExplosiveLandingActive()){
                        player_data.setExplosiveLandingActive(false);
                        player.sendMessage(ChatColor.RED + "Explosive Landing Deactivated");
                        if (!chosen_item.getEnchantments().isEmpty()){
                            chosen_item_meta.removeEnchant(Enchantment.DURABILITY);
                            chosen_item.setItemMeta(chosen_item_meta);
                        }
                    }else {
                        player_data.setExplosiveLandingActive(true);
                        player.sendMessage(ChatColor.GREEN + "Explosive Landing Activated");
                        chosen_item_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                        chosen_item.setItemMeta(chosen_item_meta);
                    }
                    break;
                case "Flood":
                    // Check if the power is on cool down
                    if (checkCoolDown("Flood", player, TWFaith.getPlugin().getConfig().getLong("flood-cooldown"))){return;}
                    // If power is not on cool down, put power on cool down
                    player_data.getCoolDowns().put("Flood", System.currentTimeMillis() / 1000);
                    PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), player_data);
                    // Activate power
                    Flood flood = new Flood();
                    flood.floodTrigger(player);
                    player.sendMessage(ChatColor.DARK_BLUE + "The area floods with water.");
                    break;
                case "Crumbling":
                    if (player_data.isCrumblingActive()){
                        player_data.setCrumblingActive(false);
                        crumblers.remove(player.getDisplayName());
                        player.sendMessage(ChatColor.RED + "Crumbling Deactivated");
                        if (!chosen_item.getEnchantments().isEmpty()){
                            chosen_item_meta.removeEnchant(Enchantment.DURABILITY);
                            chosen_item.setItemMeta(chosen_item_meta);
                        }
                    }else{
                        player_data.setCrumblingActive(true);
                        crumblers.add(player.getDisplayName());
                        player.sendMessage(ChatColor.GREEN + "Crumbling Activated");
                        chosen_item_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                        chosen_item.setItemMeta(chosen_item_meta);
                        // System.out.println(crumblers);
                    }
                    break;
                case "Heavy Boots":
                    if (player_data.isHeavyBootsActive()){
                        player_data.setHeavyBootsActive(false);
                        Heavy_Boots_Checker.heavy_boots.remove(player.getUniqueId());
                        player.sendMessage(ChatColor.RED + "Heavy Boots Deactivated");
                        if (!chosen_item.getEnchantments().isEmpty()){
                            chosen_item_meta.removeEnchant(Enchantment.DURABILITY);
                            chosen_item.setItemMeta(chosen_item_meta);
                        }
                    }else{
                        player_data.setHeavyBootsActive(true);
                        Heavy_Boots_Checker.heavy_boots.add(player.getUniqueId());
                        player.sendMessage(ChatColor.GREEN + "Heavy Boots Activated");
                        chosen_item_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                        chosen_item.setItemMeta(chosen_item_meta);
                    }
                    break;
                case "Intoxicate":
                    if (player_data.isIntoxicateActive()){
                        player_data.setIntoxicateActive(false);
                        Intoxicate.intoxicators.remove(player.getDisplayName());
                        player.sendMessage(ChatColor.RED + "Intoxicate Deactivated");
                        if (!chosen_item.getEnchantments().isEmpty()){
                            chosen_item_meta.removeEnchant(Enchantment.DURABILITY);
                            chosen_item.setItemMeta(chosen_item_meta);
                        }
                    }else{
                        player_data.setIntoxicateActive(true);
                        Intoxicate.intoxicators.add(player.getDisplayName());
                        player.sendMessage(ChatColor.GREEN + "Intoxicate Activated");
                        chosen_item_meta.addEnchant(Enchantment.DURABILITY, 1, false);
                        chosen_item.setItemMeta(chosen_item_meta);
                    }
                    break;
                case "Discombobulate":
                    // Check if the power is on cool down
                    if (checkCoolDown("Discombobulate", player, TWFaith.getPlugin().getConfig().getLong("discombobulate-cooldown"))){return;}
                    // If power is not on cool down, put power on cool down
                    player_data.getCoolDowns().put("Discombobulate", System.currentTimeMillis() / 1000);
                    PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), player_data);
                    // Activate power
                    Discombobulate discombobulate = new Discombobulate();
                    discombobulate.discombobulateTrigger(player);
                    player.sendMessage(ChatColor.GREEN + "Discombobulate Activated");
                    break;
                case "Entangle":
                    // Check if the power is on cool down
                    if (checkCoolDown("Entangle", player, TWFaith.getPlugin().getConfig().getLong("entangle-cooldown"))){return;}
                    // If power is not on cool down, put power on cool down
                    player_data.getCoolDowns().put("Entangle", System.currentTimeMillis() / 1000);
                    PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), player_data);
                    // Activate power
                    Entangle entangle = new Entangle();
                    entangle.onEntangleTrigger(player, player_data);
            }
        // The terrain bonuses are not going in the switch statement since I would have to code 56 entries.
        if (Objects.requireNonNull(chosen_item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey
                (Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")), "Power"), PersistentDataType.STRING)).contains("Terrain Bonus")){
            ItemStack power_block = e.getItem();
            TerrainBonus terrain_bonus = new TerrainBonus();
            assert power_block != null;
            terrain_bonus.terrainToggle(Objects.requireNonNull(power_block.getItemMeta()).getDisplayName(), e.getPlayer(), power_block);
        }

            PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), player_data);
    }

    // Prevents power blocks from being moved anywhere but the player's hotbar.
    @EventHandler
    public void onPowerMove(InventoryClickEvent event){
        if (!(event.getWhoClicked() instanceof Player)){return;}
        Player player = (Player) event.getWhoClicked();

        // Checks to see if the item being moved is a power item
        if (event.getCursor() == null || event.getCursor().getItemMeta() == null){return;}
        ItemStack cursor_item = event.getCursor();
        if (!Objects.requireNonNull(cursor_item.getItemMeta()).getPersistentDataContainer().has(new NamespacedKey(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")), "Power"), PersistentDataType.STRING)){return;}

        // System.out.println("Power item");

        // If the inventory is not a player inventory, cancel the event
        if (event.getClickedInventory() == null){return;}
        Inventory clicked_inventory = event.getClickedInventory();
        if (!clicked_inventory.getType().equals(InventoryType.PLAYER)){
            event.setCancelled(true);
            return;
        }

        // System.out.println("Player Inventory");

        // If the slot is not the player's hotbar, cancel the event
        if (event.getSlot() > 8){
            event.setCancelled(true);
            return;
        }
       // System.out.println("Slot less than or equal to 8");
    }

    // We have to prevent players from using drags too
    @EventHandler
    public void onPowerDrag(InventoryDragEvent event){
        if (!(event.getWhoClicked() instanceof Player)){return;}
        Player player = (Player) event.getWhoClicked();

        // Checks to see if the item being moved is a power item
        if (event.getOldCursor().getItemMeta() == null){return;}
        ItemStack old_cursor_item = event.getOldCursor();
        if (!Objects.requireNonNull(old_cursor_item.getItemMeta()).getPersistentDataContainer().has(new NamespacedKey(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")), "Power"), PersistentDataType.STRING)){return;}

        // System.out.println("Power item");

        // If the inventory is not a player inventory, cancel the event
        Inventory clicked_inventory = event.getInventory();
        if (!clicked_inventory.getType().equals(InventoryType.PLAYER)){
            event.setCancelled(true);
            return;
        }

        // System.out.println("Player Inventory");

        // If the slot is not the player's hotbar, cancel the event
        for (int i : event.getInventorySlots()){
            if (i > 8){
                event.setCancelled(true);
                return;
            }
        }
        // System.out.println("Slot less than or equal to 8");
    }

    // Deletes inactive powers when players try to drop them. This prevents gods from sharing powers with players and duping terracotta.
    // Prevents active powers from being dropped to avoid having to make another giant switch list to deactivate the specific power when dropped.
    @EventHandler
    public void onPowerDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();
        if (!Objects.requireNonNull(item.getItemMeta()).getPersistentDataContainer().has(new NamespacedKey(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")), "Power"), PersistentDataType.STRING)){return;}
        // Timestamp the drop event to prevent a PlayerInteractEvent from deactivating the power
        last_drop.put(player.getUniqueId(), System.currentTimeMillis());
        if (item.getItemMeta().hasEnchant(Enchantment.DURABILITY)){
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Cannot drop an active power.");
            return;
        }
        event.getItemDrop().remove();
    }

    public boolean checkCoolDown(String power, Player player, long cooldown){
        PlayerData player_data = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        if (player_data.getCoolDowns().get(power) == null){return false;}
        long last_use = player_data.getCoolDowns().get(power);
        // Convert to seconds
        long current = System.currentTimeMillis() / 1000;

        if (current - last_use < cooldown){
            player.sendMessage(ChatColor.RED + "You can use " + power + " again in " + (cooldown - (current - last_use)) + " seconds.");
            return true;
        }
        return false;
    }
}

package com.ethan.twfaith.guis;

import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.customevents.UsePowers;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.data.PlayerData;
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
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.profile.PlayerProfile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

public class GodPowers implements Listener {
    private Inventory gui;
    UsePowers use_powers = new UsePowers();
    // TODO Implement gui purchasing
    public void openGodPowersGui(Player player){
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");
        // Lion's Heart
        generateGUI(Material.PLAYER_HEAD, ChatColor.DARK_RED, "Lion's Heart", "Your attacks are stronger when you are unarmored.", 10);
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta item_meta = (SkullMeta) item.getItemMeta();

        // Giving the player head a custom texture
        PlayerProfile profile = Bukkit.getServer().createPlayerProfile(UUID.randomUUID(), null);

        try{
            profile.getTextures().setSkin(new URL("http://textures.minecraft.net/texture/9df16fb7bd6eab9a47e7306896a0b6ade7c36965db56469ddb4f93dc2aed6396"));
            assert item_meta != null;
            item_meta.setOwnerProfile(profile);
        }catch (MalformedURLException e){e.printStackTrace();}

        assert item_meta != null;
        item_meta.setDisplayName(ChatColor.DARK_RED + "Lion's Heart");
        item_meta.setLore(Collections.singletonList("Your attacks are stronger when you are unarmored"));
        item.setItemMeta(item_meta);
        gui.setItem(10, item);

        // Savior
        generateGUI(Material.GOLDEN_CARROT, ChatColor.LIGHT_PURPLE, "Savior", "Swap places with injured followers.", 11);

        // Taunt
        generateGUI(Material.DIAMOND, ChatColor.GOLD, "Taunt", "Attract the attention of enemies.", 12);

        // Insidious
        generateGUI(Material.ENDER_EYE, ChatColor.BLUE, "Insidious", "Gain invisibility when crouching.", 13);

        // Explosive Landing
        generateGUI(Material.TNT_MINECART, ChatColor.DARK_RED, "Explosive Landing", "Create an explosion when you hit the ground.", 14);

        // Flood
        generateGUI(Material.WATER_BUCKET, ChatColor.DARK_BLUE, "Flood", "Temporarily flood the area.", 15);

        // Close Menu
        generateGUI(Material.BARRIER, ChatColor.RED, "Back", "Return to previous menu.", 16);

        // Frame
        ItemStack frame = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
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

        Player player = (Player) e.getWhoClicked();

        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
        switch (e.getSlot()){
            case 10:
                    player_data.setLions_heart(1);
                    System.out.println("Lions Heart clicked");
                break;
            case 11:
                    player_data.setSavior(1);
                    System.out.println("Savior clicked");
                break;
            case 12:
                    player_data.setTaunt(1);
                    System.out.println("Taunt clicked");
                break;
            case 13:
                    player_data.setInsidious(1);
                    System.out.println("Insidious clicked");
                break;
            case 14:
                player_data.setExplosive_landing(1);
                System.out.println("Explosive Landing clicked");
                break;
            case 15:
                player_data.setFlood(1);
                System.out.println("Flood clicked");
                break;
            case 16:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(player, "Faith Upgrade"));
                break;
        }
       PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);
    }

    @EventHandler
    public void faithUpgradeEvent(OpenGUIEvent e){
        if(e.getGui_name().equals("God Powers")){openGodPowersGui(e.getPlayer());}
    }

    // Event handlers for powers
    // Lions Heart
    @EventHandler
    public void lionsHeartEvent(PlayerMoveEvent e){
        Player player = e.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
        // TODO Fix Lions Heart Giving level 1 strength with a full armor set
            if (player_data.getLions_heart() > 0 && player_data.isLions_heart_active()){
                int amplifier = 0;
                if (Objects.requireNonNull(player.getEquipment()).getBoots() != null){amplifier += 1;}
                if (player.getEquipment().getLeggings() != null){amplifier += 1;}
                if (player.getEquipment().getChestplate() != null){amplifier += 1;}
                if (player.getEquipment().getHelmet() != null){amplifier += 1;}
                // System.out.println(amplifier);
                player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                player.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(Integer.MAX_VALUE, 3 - amplifier));
            }
    }

    // Savior
    @EventHandler
    public void saviorTriggerEvent(EntityDamageEvent e){
        // System.out.println("Entity Damage detected!");
        if (e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
            if (player.getHealth() - e.getDamage() > 6){return;}
            if (player_data.getLeader() || !player_data.getIn_faith()){return;}
            Player leader = Bukkit.getPlayer(player_data.getLed_by());
            System.out.println("Player is in faith and is not leader.");
            assert leader != null;
            if (leader.getLocation().distance(player.getLocation()) > 30){return;}
            System.out.println("Player and leader are within 30 blocks");
            if (leader.getHealth() < 10){return;}
            System.out.println("Leader has at least 5 hearts");

            PlayerData leader_data = PlayerHashMap.player_data_hashmap.get(leader.getUniqueId());

            if (use_powers.checkCoolDown("Savior", player, 10)){return;}
            // If power is not on cool down, put power on cool down
            player_data.getCool_downs().put("Savior", System.currentTimeMillis() / 1000);
            PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);

            if (player_data.getStamina() < 10){
                player.sendMessage(ChatColor.RED + "Not enough stamina.");
                return;
            }
            player_data.setStamina(player_data.getStamina() - 10);

            if (leader_data.getSavior() > 0 && leader_data.isSavior_active()){
                System.out.println("Leader has Savior");
                Location player_location = player.getLocation();
                Location leader_location = leader.getLocation();
                player.teleport(leader_location);
                leader.teleport(player_location);
                player.sendMessage("Savior activates!");
                leader.sendMessage("Savior activates!");
            }
        }
    }

    // Insidious
    @EventHandler
    public void insidiousTriggerEvent(PlayerToggleSneakEvent e){
        Player player = e.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
        if (player_data.getInsidious() < 1 || !player_data.isInsidious_active()){return;}
        // Toggle Sneak event takes the sneak state of the player before the toggle happens
        // So we have to check if the player is standing before sneak is toggled.
        if (!player.isSneaking()){
            player.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(Integer.MAX_VALUE, 0));
        } else{player.removePotionEffect(PotionEffectType.INVISIBILITY);}
    }

    // Explosive Landing
    @EventHandler
    public void explosiveLandingTriggerEvent(EntityDamageEvent e){
        // When the player takes damage from hitting the ground, we cancel the damage and create an explosion
        if (!(e.getEntity() instanceof Player)){return;}
        if (!e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){return;}
        Player player = (Player) e.getEntity();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
        if (player_data.getExplosive_landing() < 1 || !player_data.isExplosive_landing_active()){return;}

        if (use_powers.checkCoolDown("Explosive Landing", player, 10)){return;}
        // If power is not on cool down, put power on cool down
        player_data.getCool_downs().put("Explosive Landing", System.currentTimeMillis() / 1000);
        PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);

        if (player_data.getStamina() < 10){
            player.sendMessage(ChatColor.RED + "Not enough stamina.");
            return;
        }
        player_data.setStamina(player_data.getStamina() - 10);
        float fall_distance = player.getFallDistance();
        e.setDamage(0);
        player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(100, 5));
        // Explosion size is capped to prevent server crashing.
        player.getWorld().createExplosion(player.getLocation(), Math.min(fall_distance, 100));
    }
}

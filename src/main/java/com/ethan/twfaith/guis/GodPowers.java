package com.ethan.twfaith.guis;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.customevents.UsePowers;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
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
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.profile.PlayerProfile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class GodPowers implements Listener {
    private Inventory gui;
    UsePowers use_powers = new UsePowers();
    public void openGodPowersGui(Player player){
        PlayerData playerData = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        GUIUtil GUIUtil = new GUIUtil();
        Faith faith = FaithHashMap.playerFaithHashmap.get(player.getUniqueId());
        gui = Bukkit.createInventory(null, 27, "Faith Upgrade Menu");
        // Lion's Heart
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();

        // Giving the player head a custom texture
        PlayerProfile profile = Bukkit.getServer().createPlayerProfile(UUID.randomUUID(), null);

        try{
            profile.getTextures().setSkin(new URL("http://textures.minecraft.net/texture/9df16fb7bd6eab9a47e7306896a0b6ade7c36965db56469ddb4f93dc2aed6396"));
            assert itemMeta != null;
            itemMeta.setOwnerProfile(profile);
        }catch (MalformedURLException e){e.printStackTrace();}

        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.DARK_RED + "Lion's Heart");
        switch (faith.getLionsHeart()){
            case 0:
                itemMeta.setLore(Arrays.asList("Your attacks are stronger when you are unarmored", ChatColor.RED + "Not Owned", ChatColor.GOLD + "Cost: " + TWFaith.getPlugin().getConfig().getInt("lions-heart-cost") + " FP"));
                break;
            case 1:
                itemMeta.setLore(Arrays.asList("Your attacks are stronger when you are unarmored", ChatColor.GREEN + "Owned"));
                break;
        }
        item.setItemMeta(itemMeta);
        gui.setItem(10, item);

        // Savior
        GUIUtil.generateGUI(Material.GOLDEN_CARROT, ChatColor.LIGHT_PURPLE, "Savior", "Swap places with injured followers.", "savior-cost",11, faith.getSavior(), gui);

        // Taunt
        GUIUtil.generateGUI(Material.DIAMOND, ChatColor.GOLD, "Taunt", "Attract the attention of enemies.", "taunt-cost",12, faith.getTaunt(), gui);

        // Insidious
        GUIUtil.generateGUI(Material.ENDER_EYE, ChatColor.BLUE, "Insidious", "Gain invisibility when crouching.", "insidious-cost",13, faith.getInsidious(), gui);

        // Explosive Landing
        GUIUtil.generateGUI(Material.TNT_MINECART, ChatColor.DARK_RED, "Explosive Landing", "Create an explosion when you hit the ground.", "explosive-cost",14, faith.getExplosiveLanding(), gui);

        // Flood
        GUIUtil.generateGUI(Material.WATER_BUCKET, ChatColor.DARK_BLUE, "Flood", "Temporarily flood the area.", "flood-cost",15, faith.getFlood(), gui);

        // Close Menu
        GUIUtil.generateGUI(Material.BARRIER, ChatColor.RED, "Back", "Return to previous menu.","na", 16, 3, gui);

        // Frame
        ItemStack frame = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
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

        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        Faith faithData = FaithHashMap.playerFaithHashmap.get(player.getUniqueId());
        switch (e.getSlot()){
            case 10:
                if (faithPointsChecker(faithData, player, TWFaith.getPlugin().getConfig().getInt("lions-heart-cost"), faithData.getLionsHeart(), item, e.getSlot(), "Your attacks are stronger when you are unarmored")){return;}
                faithData.setLionsHeart(1);
                // System.out.println("Lions Heart clicked");
                break;
            case 11:
                if (faithPointsChecker(faithData, player, TWFaith.getPlugin().getConfig().getInt("savior-cost"), faithData.getSavior(), item, e.getSlot(), "Swap places with injured followers.")){return;}
                faithData.setSavior(1);
                // System.out.println("Savior clicked");
                break;
            case 12:
                if (faithPointsChecker(faithData, player, TWFaith.getPlugin().getConfig().getInt("taunt-cost"), faithData.getTaunt(), item, e.getSlot(), "Attract the attention of enemies.")){return;}
                faithData.setTaunt(1);
                // System.out.println("Taunt clicked");
                break;
            case 13:
                if (faithPointsChecker(faithData, player, TWFaith.getPlugin().getConfig().getInt("insidious-cost"), faithData.getInsidious(), item, e.getSlot(), "Gain invisibility when crouching.")){return;}
                faithData.setInsidious(1);
                // System.out.println("Insidious clicked");
                break;
            case 14:
                if (faithPointsChecker(faithData, player, TWFaith.getPlugin().getConfig().getInt("explosive-cost"), faithData.getExplosiveLanding(), item, e.getSlot(), "Create an explosion when you hit the ground.")){return;}
                faithData.setExplosiveLanding(1);
                // System.out.println("Explosive Landing clicked");
                break;
            case 15:
                if (faithPointsChecker(faithData, player, TWFaith.getPlugin().getConfig().getInt("flood-cost"), faithData.getFlood(), item, e.getSlot(), "Temporarily flood the area.")){return;}
                faithData.setFlood(1);
                // System.out.println("Flood clicked");
                break;
            case 16:
                Bukkit.getPluginManager().callEvent(new OpenGUIEvent(player, "Faith Upgrade"));
                break;
        }
       FaithHashMap.playerFaithHashmap.put(player.getUniqueId(), faithData);
    }

    public boolean faithPointsChecker(Faith faithData, Player p, int cost, int data, ItemStack item, int slot, String lore){
        if (data > 0){return true;}
        if (!(faithData.getFaithPoints() >= cost)){
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
        if(e.getGuiName().equals("God Powers")){openGodPowersGui(e.getPlayer());}
    }

    // Event handlers for powers

    // Savior
    @EventHandler
    public void saviorTriggerEvent(EntityDamageEvent e){
        if (e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            PlayerData playerData = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
            if (playerData.isLeader() || !playerData.isInFaith()){return;}
            if (player.getHealth() - e.getDamage() > 6){return;}
            Player leader = Bukkit.getPlayer(playerData.getLedBy());
            System.out.println("Player is in faith and is not leader.");
            if (leader == null){return;}
            Faith faith = FaithHashMap.playerFaithHashmap.get(leader.getUniqueId());
            if (!player.getWorld().equals(leader.getWorld())){return;}
            if (leader.getLocation().distance(player.getLocation()) > TWFaith.getPlugin().getConfig().getInt("savior-radius")){return;}
            System.out.println("Player and leader are within 30 blocks");
            if (leader.getHealth() <= 9){return;}
            System.out.println("Leader has at least 5 hearts");
            PlayerData leaderData = PlayerHashMap.playerDataHashMap.get(leader.getUniqueId());
            if (use_powers.checkCoolDown("Savior", leader, TWFaith.getPlugin().getConfig().getLong("savior-cooldown"))){return;}
            if (faith.getSavior() > 0 && leaderData.isSaviorActive()){
                System.out.println("Leader has Savior");
                if (leaderData.getStamina() < TWFaith.getPlugin().getConfig().getInt("savior-stamina")){
                    leader.sendMessage(ChatColor.RED + "Not enough stamina.");
                    return;
                }
                leaderData.setStamina(leaderData.getStamina() - TWFaith.getPlugin().getConfig().getInt("savior-stamina"));
                Location player_location = player.getLocation();
                Location leader_location = leader.getLocation();
                player.teleport(leader_location);
                leader.teleport(player_location);
                player.sendMessage("Savior activates!");
                leader.sendMessage("Savior activates!");
                if (player.getHealth() - e.getDamage() <= 0){
                    e.setCancelled(true);
                    player.setHealth(1);
                }
                leaderData.getCoolDowns().put("Savior", System.currentTimeMillis() / 1000);
                PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), playerData);
                PlayerHashMap.playerDataHashMap.put(leaderData.getUuid(), leaderData);
            }
        }
    }

    // Insidious
    @EventHandler
    public void insidiousTriggerEvent(PlayerToggleSneakEvent e){
        Player player = e.getPlayer();
        PlayerData playerData = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        if (!playerData.isInFaith() || !playerData.isLeader()){return;}
        Faith faith = FaithHashMap.playerFaithHashmap.get(player.getUniqueId());
        if (faith.getInsidious() < 1 || !playerData.isInsidiousActive()){return;}
        // Toggle Sneak event takes the sneak state of the player before the toggle happens
        // So we have to check if the player is standing before sneak is toggled.
        if (!player.isSneaking()){
            player.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(40, 0));
        } else{player.removePotionEffect(PotionEffectType.INVISIBILITY);}
    }

    // Explosive Landing
    @EventHandler
    public void explosiveLandingTriggerEvent(EntityDamageEvent e){
        // When the player takes damage from hitting the ground, we cancel the damage and create an explosion
        if (!(e.getEntity() instanceof Player)){return;}
        if (!e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){return;}
        Player player = (Player) e.getEntity();
        PlayerData playerData = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        Faith faith = FaithHashMap.playerFaithHashmap.get(player.getUniqueId());
        if (faith == null){return;}
        if (faith.getExplosiveLanding() < 1 || !playerData.isExplosiveLandingActive()){return;}

        if (use_powers.checkCoolDown("Explosive Landing", player, TWFaith.getPlugin().getConfig().getLong("explosive-cooldown"))){return;}
        // If power is not on cool down, put power on cool down
        playerData.getCoolDowns().put("Explosive Landing", System.currentTimeMillis() / 1000);
        PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), playerData);

        if (playerData.getStamina() < TWFaith.getPlugin().getConfig().getInt("explosive-stamina")){
            player.sendMessage(ChatColor.RED + "Not enough stamina.");
            return;
        }
        playerData.setStamina(playerData.getStamina() - TWFaith.getPlugin().getConfig().getInt("explosive-stamina"));
        float fallDistance = player.getFallDistance();
        e.setDamage(0);
        player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(100, 5));
        // Explosion size is capped to prevent server crashing.
        player.getWorld().createExplosion(player.getLocation(), Math.min(fallDistance, TWFaith.getPlugin().getConfig().getInt("explosive-max-mag")));
    }
}

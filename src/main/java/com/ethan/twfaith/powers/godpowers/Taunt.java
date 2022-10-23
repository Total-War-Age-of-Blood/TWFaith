package com.ethan.twfaith.powers.godpowers;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class Taunt implements Listener {
    // Applies and removes the taunt effects based on distance from taunter
    @EventHandler
    public void onTauntEvent(PlayerMoveEvent e){
        Player player = e.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
            if (!player_data.isTaunted()){return;}
            if (Bukkit.getPlayer(player_data.getTaunter()) == null){return;}
            Player taunter = Bukkit.getPlayer(player_data.getTaunter());
            if (!player.getWorld().equals(taunter.getWorld())){return;}
            if (player.getLocation().distance(Objects.requireNonNull(taunter).getLocation()) <= 30){
                player.addPotionEffect(PotionEffectType.HUNGER.createEffect(80, 0));
            }else{
                player_data.setTaunted(false);
                player_data.setTaunter(player.getUniqueId());
                PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);

                player.removePotionEffect(PotionEffectType.HUNGER);
                player.sendMessage("You have escaped your taunter.");
            }
    }

    // Removes taunt when taunter is hit by taunted
    @EventHandler
    public void onTauntHitEvent(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player){
            Player taunter = (Player) e.getEntity();
            Player taunted = (Player) e.getDamager();
            PlayerData taunted_data = PlayerHashMap.player_data_hashmap.get(taunted.getUniqueId());

            if (taunted_data.getTaunter().equals(taunter.getUniqueId())){
                taunted_data.setTaunted(false);
                taunted_data.setTaunter(null);
                taunted.removePotionEffect(PotionEffectType.HUNGER);
                taunted.sendMessage("Attacked Taunter!");
                PlayerHashMap.player_data_hashmap.put(taunted.getUniqueId(), taunted_data);
            }
        }
    }
    @EventHandler
    public void leaveTauntCancel(PlayerQuitEvent e){
        Player player = e.getPlayer();
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());

        // Checking if anybody was taunted by the player who left and removing their taunt status to prevent
        // infinite taunts.
        for (Player taunted : Bukkit.getOnlinePlayers()){
            PlayerData taunted_data = PlayerHashMap.player_data_hashmap.get(taunted.getUniqueId());
            if (!taunted_data.isTaunted() || taunted_data.getTaunter() != player.getUniqueId()){continue;}
            taunted_data.setTaunted(false);
            taunted_data.setTaunter(taunted.getUniqueId());
            PlayerHashMap.player_data_hashmap.put(taunted.getUniqueId(), taunted_data);
            taunted.sendMessage("You are no longer taunted.");
        }

        if (player_data.isTaunted()){player.removePotionEffect(PotionEffectType.HUNGER);}
        player_data.setTaunter(e.getPlayer().getUniqueId());
        player_data.setTaunted(false);
        PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);
    }

    public void tauntTrigger(Player player){
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());

        if (player_data.getStamina() < TWFaith.getPlugin().getConfig().getInt("taunt-stamina")){
            player.sendMessage(ChatColor.RED + "Not enough stamina.");
            return;
        }
        player_data.setStamina(player_data.getStamina() - TWFaith.getPlugin().getConfig().getInt("taunt-stamina"));

        if (!player_data.getLeader() || player_data.getTaunt() < 1){return;}
        player.addPotionEffect(PotionEffectType.GLOWING.createEffect(3000, 0));
        for (Player heathen : Bukkit.getOnlinePlayers()){
            if (!player.getWorld().equals(heathen.getWorld())){continue;}
            if (heathen.getLocation().distance(player.getLocation()) < 30){
                PlayerData heathen_data = PlayerHashMap.player_data_hashmap.get(heathen.getUniqueId());
                if (heathen_data.getIn_faith()){
                    if (!heathen_data.getLed_by().equals(player_data.getUuid())){
                        heathen_data.setTaunted(true);
                        heathen_data.setTaunter(player.getUniqueId());
                        PlayerHashMap.player_data_hashmap.put(heathen.getUniqueId(), heathen_data);
                        heathen.sendMessage(player.getDisplayName() + " has taunted you! Hit them to stop hunger.");
                    }
                }else{
                    heathen_data.setTaunted(true);
                    heathen_data.setTaunter(player.getUniqueId());
                    PlayerHashMap.player_data_hashmap.put(heathen.getUniqueId(), heathen_data);
                    heathen.sendMessage(player.getDisplayName() + " has taunted you! Hit them to stop hunger.");
                }
            }
        }
    }

}

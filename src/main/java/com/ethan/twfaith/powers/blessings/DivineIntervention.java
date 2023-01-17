package com.ethan.twfaith.powers.blessings;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.tasks.Divine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class DivineIntervention implements Listener {

    public static HashMap<UUID, Player> divine_list = new HashMap<>();

    public void onDivineTrigger(Player player, PlayerData player_data){

        int divine_stamina = TWFaith.getPlugin().getConfig().getInt("divine-stamina");
        if (player_data.getStamina() < divine_stamina){
            player.sendMessage(ChatColor.RED + "Not enough stamina.");
            return;
        }
        player_data.setStamina(player_data.getStamina() - divine_stamina);

        for (Player nearby : Bukkit.getOnlinePlayers()){
            PlayerData nearby_data = PlayerHashMap.playerDataHashMap.get(nearby.getUniqueId());
            if (!player.getWorld().equals(nearby.getWorld())){continue;}
            if (player.getLocation().distance(nearby.getLocation()) <= 30 && nearby_data.getLed_by().equals(player_data.getLed_by())){
                // Launches player straight up
                nearby.setVelocity(new Vector(0, 5, 0));
                // Gives the player a second to react before gliding starts
                nearby.addPotionEffect(PotionEffectType.SLOW_FALLING.createEffect(1 * 20, 0));
                divine_list.put(nearby.getUniqueId(), nearby);
                // Allows player to glide to safety
                BukkitTask divine = new Divine(nearby).runTaskLater(TWFaith.getPlugin(), 20);
                nearby.sendMessage("Your god lifts you into the air.");
            }
        }
    }

    // Glide will automatically toggle itself off if the player does not have an elytra, so we have to cancel glide toggling
    // until the player touches the ground.
    @EventHandler
    public void onPlayerGlide(EntityToggleGlideEvent event){
        if (!(event.getEntity() instanceof Player)){return;}
        Player player = (Player) event.getEntity();
        if (divine_list.containsKey(event.getEntity().getUniqueId()) && !player.isOnGround()){
            event.setCancelled(true);
        }
        // Removing players from divine list when they hit the ground
        if (divine_list.containsKey(event.getEntity().getUniqueId()) && player.isOnGround()){
            divine_list.remove(event.getEntity().getUniqueId());
        }
    }
}


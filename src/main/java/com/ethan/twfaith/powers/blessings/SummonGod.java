package com.ethan.twfaith.powers.blessings;

import com.ethan.twfaith.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public class SummonGod implements Listener {
    public static HashMap<UUID, UUID> summon_requests = new HashMap<>();

    public void summonTrigger(Player player, PlayerData player_data){
        if (Bukkit.getPlayer(player_data.getLed_by()) == null){
            player.sendMessage("Your god must be online to summon them.");
            return;}
        Player god = Bukkit.getPlayer(player_data.getLed_by());
        // If we put the god's UUID as the key, the player entry will get replaced every time a new player sends a summon request.
        // This means the god will only be responding to the most recent summon request.
        assert god != null;
        summon_requests.put(god.getUniqueId(), player.getUniqueId());
        player.sendMessage("Summoning " + god.getDisplayName());
        god.sendMessage("You are being summoned by " + player.getDisplayName() + ". Use /faith accept to accept.");



    }
}

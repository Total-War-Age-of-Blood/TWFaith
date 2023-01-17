package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import com.ethan.twfaith.powers.blessings.SummonGod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FaithSummon {

    public void summonInvite(Player player, PlayerData playerData){
        if (playerData.getLeader()){
            player.sendMessage(ChatColor.RED + "Leader cannot summon self.");
        }
        SummonGod summon = new SummonGod();
        summon.summonTrigger(player, playerData);
    }

    public void summonAccept(Player player, PlayerData playerData){
        UUID requester_id = SummonGod.summon_requests.get(player.getUniqueId());
        if (Bukkit.getPlayer(requester_id) == null){return;}
        Player requester = Bukkit.getPlayer(requester_id);
        assert requester != null;
        // Handle cooldown
        checkCoolDown("Summon", player, TWFaith.getPlugin().getConfig().getLong("summon-cooldown"));
        playerData.getCool_downs().put("Summon", System.currentTimeMillis() / 1000);
        PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), playerData);
        // Handle stamina
        if (playerData.getStamina() < TWFaith.getPlugin().getConfig().getInt("summon-stamina")){
            player.sendMessage(ChatColor.RED + "Not enough stamina.");
            return;
        }
        playerData.setStamina(playerData.getStamina() - TWFaith.getPlugin().getConfig().getInt("summon-stamina"));
        requester.sendMessage(player.getDisplayName() + " has been summoned!");
        player.teleport(requester.getLocation());
        SummonGod.summon_requests.remove(player.getUniqueId());
    }

    public boolean checkCoolDown(String power, Player player, long cooldown){
        PlayerData player_data = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
        if (player_data.getCool_downs().get(power) == null){return false;}
        long last_use = player_data.getCool_downs().get(power);
        // Convert to seconds
        long current = System.currentTimeMillis() / 1000;

        if (current - last_use < cooldown){
            player.sendMessage(ChatColor.RED + "You can use " + power + " again in " + (cooldown - (current - last_use)) + " seconds.");
            return true;
        }
        return false;
    }
}

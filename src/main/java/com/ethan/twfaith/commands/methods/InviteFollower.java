package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
import com.ethan.twfaith.tasks.AutoSave;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class InviteFollower {
    public void InviteFollower(String[] args, Player player){
        if(Bukkit.getPlayerExact(args[2]) != null){
            UUID uuid = Objects.requireNonNull(Bukkit.getPlayerExact(args[2])).getUniqueId();
            Faith faith = FaithHashMap.playerFaithHashmap.get(player.getUniqueId());
            String faith_name = faith.getFaithName();
            List<UUID> invited_players = faith.getInvitedPlayers();

            // Adds the invited player to the invited players list in the faith file
            if (!invited_players.contains(uuid)){
                invited_players.add(uuid);
                FaithHashMap.playerFaithHashmap.put(player.getUniqueId(), faith);
            }
            player.sendMessage("You have invited " + args[2] + " to the faith.");
            Objects.requireNonNull(Bukkit.getPlayerExact(args[2])).sendMessage("You have been invited to the faith " + faith_name);
            AutoSave.setChange(true);
        }
    }
}

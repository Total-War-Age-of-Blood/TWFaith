package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.PlayerData;
import com.ethan.twfaith.data.PlayerHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.UUID;

public class DisbandFaith {
    public void DisbandFaith(File senderFaithFile, Player player){
        try{
            if (senderFaithFile.exists()){
                Reader faithReader = new FileReader(senderFaithFile);
                Faith readFaith = TWFaith.getGson().fromJson(faithReader, Faith.class);

                PlayerData leaderPlayerData = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
                leaderPlayerData.setLeader(false);
                leaderPlayerData.setFaith("");
                leaderPlayerData.setInFaith(false);
                leaderPlayerData.setDivine_intervention(0);
                leaderPlayerData.setHells_fury(0);
                leaderPlayerData.setMana(0);
                leaderPlayerData.setPowerful_flock(0);
                leaderPlayerData.setSummon_god(0);
                leaderPlayerData.setTerrain_bonus(0);
                leaderPlayerData.setCrumbling(0);
                leaderPlayerData.setDiscombobulate(0);
                leaderPlayerData.setEntangle(0);
                leaderPlayerData.setHeavy_boots(0);
                leaderPlayerData.setIntoxicate(0);
                leaderPlayerData.setLions_heart(0);
                leaderPlayerData.setSavior(0);
                leaderPlayerData.setInsidious(0);
                leaderPlayerData.setFlood(0);
                leaderPlayerData.setExplosive_landing(0);
                leaderPlayerData.setTaunt(0);
                leaderPlayerData.setFaith_points(0);
                PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), leaderPlayerData);

                for (UUID unique_player : readFaith.getFollowers()){
                    Player follower = Bukkit.getPlayer(unique_player);
                    assert follower != null;
                    PlayerData follower_data = PlayerHashMap.playerDataHashMap.get(follower.getUniqueId());
                    follower_data.setLeader(false);
                    follower_data.setInFaith(false);
                    follower_data.setFaith("");
                    PlayerHashMap.playerDataHashMap.put(follower.getUniqueId(), follower_data);
                }
                faithReader.close();
                senderFaithFile.delete();
                player.sendMessage("Your faith has been deleted.");
            }
        }catch(Exception e){e.printStackTrace();}
    }
}

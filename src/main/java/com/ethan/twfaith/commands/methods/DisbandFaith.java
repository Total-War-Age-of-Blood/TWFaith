package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.Faith;
import com.ethan.twfaith.data.FaithHashMap;
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
                Faith faith = FaithHashMap.playerFaithHashmap.get(player.getUniqueId());
                leaderPlayerData.setLeader(false);
                leaderPlayerData.setFaith("");
                leaderPlayerData.setInFaith(false);
                faith.setDivineIntervention(0);
                faith.setHellsFury(0);
                faith.setMana(0);
                faith.setPowerfulFlock(0);
                faith.setSummonGod(0);
                faith.setTerrainBonus(0);
                faith.setCrumbling(0);
                faith.setDiscombobulate(0);
                faith.setEntangle(0);
                faith.setHeavyBoots(0);
                faith.setIntoxicate(0);
                faith.setLionsHeart(0);
                faith.setSavior(0);
                faith.setInsidious(0);
                faith.setFlood(0);
                faith.setExplosiveLanding(0);
                faith.setTaunt(0);
                leaderPlayerData.setFaithPoints(0);
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

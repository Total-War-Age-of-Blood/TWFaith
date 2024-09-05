package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Locale;

public class CreateFaith {
    public void CreateFaith(Player player, PlayerData playerData, String[] args, File senderFaithFile){
        if (playerData.isInFaith()){
        player.sendMessage(ChatColor.RED + "You must leave your current faith before creating a new faith.");
        return;
    }

        if (playerData.isLeader()){
            player.sendMessage(ChatColor.RED + "You are already the leader of a faith.");
            return;
        }

        for (Faith faith : FaithHashMap.playerFaithHashmap.values()){
            if (!(args[1].toLowerCase().equals(faith.getFaithName().toLowerCase(Locale.ROOT)))){continue;}
            player.sendMessage(ChatColor.RED + "A faith with that name already exists.");
            return;
        }

        player.sendMessage("You have created the faith named " + args[1]);
        Faith faith = new Faith(args[1], player.getUniqueId(), 0, new ArrayList<>(), new ArrayList<>(),
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0, 0,0,0,0, false, new ArrayList<>());
        FaithHashMap.playerFaithHashmap.put(player.getUniqueId(), faith);

        // Give the player a Stamina Boss Bar
        BossBar bossBar = Bukkit.createBossBar(ChatColor.YELLOW + "Stamina", BarColor.YELLOW, BarStyle.SEGMENTED_10);
        bossBar.setProgress(1);
        bossBar.addPlayer(player);
        BossBars.boss_bar_map.put(player.getUniqueId(), bossBar);
        player.sendMessage("You can disable the stamina bar with /faith bossBar off");

        try{
            if (!senderFaithFile.exists()){senderFaithFile.createNewFile();}

            // Saving new Faith to file
            Writer writer = new FileWriter(senderFaithFile, false);
            TWFaith.getGson().toJson(faith, writer);
            writer.flush();
            writer.close();

            // Changing Founder's Player Data to reflect creating the faith

            playerData.setFaith(args[1]);
            playerData.setInFaith(true);
            playerData.setLeader(true);
            playerData.setLedBy(player.getUniqueId());

            PlayerHashMap.playerDataHashMap.put(player.getUniqueId(), playerData);

            System.out.println("Saved data!");
        }catch(IOException e){e.printStackTrace();}}
}

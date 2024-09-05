package com.ethan.twfaith.commands;

import com.ethan.twfaith.commands.methods.*;
import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.data.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.io.*;
import java.util.*;

public class FaithCommand implements CommandExecutor {
    // TODO Set up error messages for all commands
    // TODO Add admin commands

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData playerData = PlayerHashMap.playerDataHashMap.get(player.getUniqueId());
            Faith faith = FaithHashMap.playerFaithHashmap.get(player.getUniqueId());

            // Variables that can be used by multiple commands go here
            File faithFolder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "Faiths");
            if (!faithFolder.exists()){faithFolder.mkdir();}
            File player_data_folder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "PlayerData");
            if (!player_data_folder.exists()){player_data_folder.mkdir();}
            File uniquePlayersFile = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "unique_players.json");
            if (!uniquePlayersFile.exists()){
                try{
                    uniquePlayersFile.createNewFile();} catch (IOException e){e.printStackTrace();}
            }
            File senderFaithFile = new File(faithFolder, player.getUniqueId() + ".json");
            if (Objects.equals(args[0], "create") && args.length == 2) {
               CreateFaith createFaith = new CreateFaith();
               createFaith.CreateFaith(player, playerData, args, senderFaithFile);
            }

            if (Objects.equals(args[0], "list") && args.length == 1){
                FaithList faithList = new FaithList();
                faithList.FaithList(faithFolder, player);
            }

            if (Objects.equals(args[0], "disband") && args.length == 1){
                DisbandFaith disbandFaith = new DisbandFaith();
                disbandFaith.DisbandFaith(senderFaithFile, player);
            }

            // TODO fix issues where renamed faith is still referred to by original name in some cases
            // TODO either make the rename change the faith name for all offline players or determine that the faith
            //  value in PlayerData is deprecated and remove it
            if (Objects.equals(args[0], "rename")){
                RenameFaith renameFaith = new RenameFaith();
                renameFaith.RenameFaith(senderFaithFile, args, player);
            }

            if (Objects.equals(args[0], "invite") && Objects.equals(args[1], "add") && args.length == 3){
                InviteFollower inviteFollower = new InviteFollower();
                inviteFollower.InviteFollower(args, player);
            }

            if (Objects.equals(args[0], "join") && args.length == 2){
              JoinFaith joinFaith = new JoinFaith();
              joinFaith.JoinFaith(playerData, player, args);
            }

            if (Objects.equals(args[0], "leave")){
                LeaveFaith leaveFaith = new LeaveFaith();
                leaveFaith.leaveFaith(playerData, player, faithFolder);
            }

            if (Objects.equals(args[0], "upgrade")){
                if (playerData.isLeader()){
                    // Runs custom event for opening a gui menu
                    Bukkit.getPluginManager().callEvent(new OpenGUIEvent(player, "Faith Upgrade"));
                    return true;

                } else{player.sendMessage(ChatColor.RED + "Must be leader to upgrade faith.");}
            }

            if (Objects.equals(args[0], "powers")){
                if (playerData.isLeader()){
                    // Runs custom event for opening a gui menu
                    Bukkit.getPluginManager().callEvent(new OpenGUIEvent(player, "Select Powers"));
                    return true;

                } else{player.sendMessage(ChatColor.RED + "Must be leader to use powers.");}
            }

            if (Objects.equals(args[0], "summon")){
                FaithSummon faithSummon = new FaithSummon();
                faithSummon.summonInvite(player, playerData);
            }

            if (Objects.equals(args[0], "accept")){
                FaithSummon faithSummon = new FaithSummon();
                faithSummon.summonAccept(player, playerData);
            }

            if (Objects.equals(args[0], "balance") && playerData.isLeader()){
                player.sendMessage("You have " + faith.getFaithPoints() + " Faith Points");
            } else if (Objects.equals(args[0], "balance") && !playerData.isLeader()){
                player.sendMessage(ChatColor.RED + "Only the god can use this command.");
            }

            if (Objects.equals(args[0], "kick") && args.length == 2) {
                FaithKick faithKick = new FaithKick();
                faithKick.FaithKick(playerData, player, args);
            }

            if (Objects.equals(args[0].toLowerCase(), "bossbar")){
                if (Objects.equals(args[1].toLowerCase(), "on")){
                    BossBar bossBar = Bukkit.createBossBar(ChatColor.YELLOW + "Stamina", BarColor.YELLOW, BarStyle.SEGMENTED_10);
                    bossBar.setProgress(playerData.getStamina() / playerData.getMaxStamina());
                    bossBar.addPlayer(player);
                    BossBars.boss_bar_map.put(player.getUniqueId(), bossBar);
                    playerData.setStaminaBarEnabled(true);
                    player.sendMessage("Stamina Bar ON");
                } else if (Objects.equals(args[1].toLowerCase(), "off")) {
                    if (BossBars.boss_bar_map.containsKey(player.getUniqueId())){
                        BossBar bossBar = BossBars.boss_bar_map.get(player.getUniqueId());
                        bossBar.removePlayer(player);
                        playerData.setStaminaBarEnabled(false);
                        player.sendMessage(ChatColor.RED + "Stamina Bar OFF");
                    }
                } else{
                    player.sendMessage(ChatColor.RED + "Usage: /faith bossBar on/off");
                }
            }

            if (Objects.equals(args[0], "info") && args.length == 2){
                FaithInfo faithInfo = new FaithInfo();
                faithInfo.faithInfo(args[1], player);
            }

            if (Objects.equals(args[0], "faithpoints") && args.length == 3){

            }
        }
            return false;
    }
}

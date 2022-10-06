package com.ethan.twfaith.commands;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.customevents.OpenGUIEvent;
import com.ethan.twfaith.data.*;
import com.ethan.twfaith.powers.blessings.SummonGod;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerProfile;


import java.io.*;
import java.util.*;

public class FaithCommand implements CommandExecutor {
    // TODO Set up error messages for all commands

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());

            // Variables that can be used by multiple commands go here
            File faith_folder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "Faiths");
            if (!faith_folder.exists()){faith_folder.mkdir();}
            File player_data_folder = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "PlayerData");
            if (!player_data_folder.exists()){player_data_folder.mkdir();}
            File unique_players_file = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("TWFaith")).getDataFolder(), "unique_players.json");
            if (!unique_players_file.exists()){
                try{unique_players_file.createNewFile();} catch (IOException e){e.printStackTrace();}
            }
            File sender_faith_file = new File(faith_folder, player.getUniqueId() + ".json");
            Gson gson = new Gson();
            if (Objects.equals(args[0], "create") && args.length == 2) {
                if (player_data.getIn_faith()){
                    player.sendMessage(ChatColor.RED + "You must leave your current faith before creating a new faith.");
                    return false;
                }

                if (player_data.getLeader()){
                    player.sendMessage(ChatColor.RED + "You are already the leader of a faith.");
                    return false;
                }

                for (Faith faith : FaithHashMap.player_faith_hashmap.values()){
                    if (!(args[1].toLowerCase().equals(faith.getFaith_name().toLowerCase(Locale.ROOT)))){continue;}
                    player.sendMessage(ChatColor.RED + "A faith with that name already exists.");
                    return false;
                }

                player.sendMessage("You have created the faith named " + args[1]);
                Faith faith = new Faith(args[1], player.getUniqueId(), 0, new ArrayList<>(), new ArrayList<>(),
                        0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, false, new ArrayList<>());
                FaithHashMap.player_faith_hashmap.put(player.getUniqueId(), faith);

                // Give the player a Stamina Boss Bar
                BossBar boss_bar = Bukkit.createBossBar(ChatColor.YELLOW + "Stamina", BarColor.YELLOW, BarStyle.SEGMENTED_10);
                boss_bar.setProgress(1);
                boss_bar.addPlayer(player);
                BossBars.boss_bar_map.put(player.getUniqueId(), boss_bar);

                try{
                    if (!sender_faith_file.exists()){sender_faith_file.createNewFile();}

                    // Saving new Faith to file
                    Writer writer = new FileWriter(sender_faith_file, false);
                    gson.toJson(faith, writer);
                    writer.flush();
                    writer.close();

                    // Changing Founder's Player Data to reflect creating the faith

                    player_data.setFaith(args[1]);
                    player_data.setIn_faith(true);
                    player_data.setLeader(true);
                    player_data.setLed_by(player.getUniqueId());

                    PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);

                    System.out.println("Saved data!");
                }catch(IOException e){e.printStackTrace();}
            }

            if (Objects.equals(args[0], "list") && args.length == 1){
                try{
                    // This loop iterates through the unique players list and sends the names of all faiths
                    // to the player running the command

                    for (File file : Objects.requireNonNull(faith_folder.listFiles())){
                        Reader faith_reader = new FileReader(file);
                        Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                        player.sendMessage(read_faith.getFaith_name());

                    }
                }catch(IOException e){e.printStackTrace();}
            }

            if (Objects.equals(args[0], "disband") && args.length == 1){
                try{
                    if (sender_faith_file.exists()){
                        Reader faith_reader = new FileReader(sender_faith_file);
                        Faith read_faith = gson.fromJson(faith_reader, Faith.class);

                        PlayerData leader_player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
                        leader_player_data.setLeader(false);
                        leader_player_data.setFaith("");
                        leader_player_data.setIn_faith(false);
                        leader_player_data.setDivine_intervention(0);
                        leader_player_data.setHells_fury(0);
                        leader_player_data.setMana(0);
                        leader_player_data.setPowerful_flock(0);
                        leader_player_data.setSummon_god(0);
                        leader_player_data.setTerrain_bonus(0);
                        leader_player_data.setCrumbling(0);
                        leader_player_data.setDiscombobulate(0);
                        leader_player_data.setEntangle(0);
                        leader_player_data.setHeavy_boots(0);
                        leader_player_data.setIntoxicate(0);
                        leader_player_data.setLions_heart(0);
                        leader_player_data.setSavior(0);
                        leader_player_data.setInsidious(0);
                        leader_player_data.setFlood(0);
                        leader_player_data.setExplosive_landing(0);
                        leader_player_data.setTaunt(0);
                        leader_player_data.setFaith_points(0);
                        PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), leader_player_data);

                        for (UUID unique_player : read_faith.getFollowers()){
                            Player follower = Bukkit.getPlayer(unique_player);
                            assert follower != null;
                            PlayerData follower_data = PlayerHashMap.player_data_hashmap.get(follower.getUniqueId());
                            follower_data.setLeader(false);
                            follower_data.setIn_faith(false);
                            follower_data.setFaith("");
                            PlayerHashMap.player_data_hashmap.put(follower.getUniqueId(), follower_data);
                        }
                        faith_reader.close();
                        sender_faith_file.delete();
                        player.sendMessage("Your faith has been deleted.");
                    }
                }catch(Exception e){e.printStackTrace();}
            }

            if (Objects.equals(args[0], "rename")){
                try{
                    if (sender_faith_file.exists()){
                        Reader faith_reader = new FileReader(sender_faith_file);
                        Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                        read_faith.setFaith_name(args[1]);

                        Writer writer = new FileWriter(sender_faith_file, false);
                        gson.toJson(read_faith, writer);
                        writer.flush();
                        writer.close();

                        PlayerData leader_player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
                        leader_player_data.setFaith(args[1]);
                        PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), leader_player_data);

                        for (UUID unique_player : read_faith.getFollowers()){
                            Player follower = Bukkit.getPlayer(unique_player);
                            assert follower != null;
                            PlayerData follower_data = PlayerHashMap.player_data_hashmap.get(follower.getUniqueId());
                            follower_data.setFaith(args[1]);
                            PlayerHashMap.player_data_hashmap.put(follower.getUniqueId(), follower_data);
                        }

                        player.sendMessage("Renamed faith to " + args[1]);

                    }else{player.sendMessage(ChatColor.RED + "ERROR: Could not rename faith. Are you sure you are the" +
                            " leader of a faith?");}
                }catch(IOException e){e.printStackTrace();}
            }

            if (Objects.equals(args[0], "invite") && Objects.equals(args[1], "add") && args.length == 3){
                if(Bukkit.getPlayerExact(args[2]) != null){
                    UUID invited_player = Objects.requireNonNull(Bukkit.getPlayerExact(args[2])).getUniqueId();
                    String faith_name = null;
                    List<UUID> invited_players = new ArrayList<>();
                    try{
                        File file = new File(faith_folder, player.getUniqueId()+".json");
                        Reader faith_reader = new FileReader(file);
                        Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                        faith_name = read_faith.getFaith_name();
                        invited_players = read_faith.getInvited_players();
                        System.out.println("The loaded invited players list says: " + invited_players);
                    }catch(IOException e){e.printStackTrace();}
                    // Adds the invited player to the invited players list in the faith file
                    if (!invited_players.contains(invited_player)){
                        System.out.println("Adding player to invited players list.");
                        invited_players.add(invited_player);
                        System.out.println(invited_players);
                        try{
                            Reader faith_reader = new FileReader(sender_faith_file);
                            Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                            read_faith.setInvited_players(invited_players);

                            // After you set a value, you have to remember to write the Faith object to the JSON XD
                            Writer writer = new FileWriter(sender_faith_file, false);
                            gson.toJson(read_faith, writer);
                            writer.flush();
                            writer.close();
                            // System.out.println("Player successfully added.");
                        }catch(IOException e){e.printStackTrace(); System.out.println("There was an error adding to list.");}
                    }
                    player.sendMessage("You have invited " + args[2] + " to the faith.");
                    Objects.requireNonNull(Bukkit.getPlayerExact(args[2])).sendMessage("You have been invited to the faith " + faith_name);
                }
            }

            if (Objects.equals(args[0], "join") && args.length == 2){
                if (player_data.getIn_faith()){
                    player.sendMessage(ChatColor.RED + "You must leave your current faith before joining a new faith.");
                    return false;
                }

                if (player_data.getLeader()){
                    player.sendMessage(ChatColor.RED + "You are already the leader of a faith. Disband your faith to join another.");
                    return false;
                }

                for (File faith_file : Objects.requireNonNull(faith_folder.listFiles())){
                    try{
                        Reader faith_reader = new FileReader(faith_file);
                        Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                        List<UUID> invited_players = read_faith.getInvited_players();
                        List<UUID> followers = read_faith.getFollowers();
                        System.out.println("file detected");
                        if (read_faith.getFaith_name().equals(args[1]) && read_faith.getInvited_players().contains(player.getUniqueId()) && !followers.contains(player.getUniqueId())){
                            invited_players.remove(player.getUniqueId());
                            followers.add(player.getUniqueId());
                            player.sendMessage("You have become a follower of " + read_faith.getFaith_name());
                            read_faith.setInvited_players(invited_players);
                            read_faith.setFollowers(followers);

                            if (Objects.requireNonNull(Bukkit.getPlayer(read_faith.getLeader())).isOnline()){
                                Objects.requireNonNull(Bukkit.getPlayer(read_faith.getLeader())).sendMessage(player.getDisplayName() + " has become a follower of " + read_faith.getFaith_name());
                            }

                            Writer writer = new FileWriter(faith_file, false);
                            gson.toJson(read_faith, writer);
                            writer.flush();
                            writer.close();

                            player_data.setFaith(args[1]);
                            player_data.setIn_faith(true);
                            player_data.setLed_by(read_faith.getLeader());
                            PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);
                        }

                    }catch(IOException e){e.printStackTrace();}
                    }
            }

            if (Objects.equals(args[0], "leave")){
                if (player_data.getLeader()){
                    player.sendMessage(ChatColor.RED + "A leader cannot leave the faith. You must disband it.");
                    return false;
                }
                try{
                    if (player_data.getIn_faith()){
                        // Removing the player from the faith data file
                        File faith_file = new File (faith_folder, player_data.getLed_by() + ".json");
                        Reader faith_reader = new FileReader(faith_file);
                        Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                        List<UUID> followers = read_faith.getFollowers();
                        followers.remove(player.getUniqueId());
                        Writer faith_data_writer = new FileWriter(faith_file, false);
                        gson.toJson(read_faith, faith_data_writer);
                        faith_data_writer.flush();
                        faith_data_writer.close();

                        // Removing the faith from the player data file
                        player_data.setIn_faith(false);
                        player_data.setLeader(false);
                        player_data.setFaith("");
                        player_data.setLed_by(null);
                        PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);

                        player.sendMessage("You have left " + read_faith.getFaith_name());
                    }
                }catch (IOException e){e.printStackTrace();}
            }

            if (Objects.equals(args[0], "upgrade")){
                if (player_data.getLeader()){
                    // Runs custom event for opening a gui menu
                    Bukkit.getPluginManager().callEvent(new OpenGUIEvent(player, "Faith Upgrade"));
                    return true;

                } else{player.sendMessage(ChatColor.RED + "Must be leader to upgrade faith.");}
            }

            if (Objects.equals(args[0], "powers")){
                if (player_data.getLeader()){
                    // Runs custom event for opening a gui menu
                    Bukkit.getPluginManager().callEvent(new OpenGUIEvent(player, "Select Powers"));
                    return true;

                } else{player.sendMessage(ChatColor.RED + "Must be leader to use powers.");}
            }

            if (Objects.equals(args[0], "summon")){
                if (player_data.getLeader()){
                    player.sendMessage(ChatColor.RED + "Leader cannot summon self.");
                }
                SummonGod summon = new SummonGod();
                summon.summonTrigger(player, player_data);
            }

            if (Objects.equals(args[0], "accept")){
                UUID requester_id = SummonGod.summon_requests.get(player.getUniqueId());
                if (Bukkit.getPlayer(requester_id) == null){return true;}
                Player requester = Bukkit.getPlayer(requester_id);
                assert requester != null;
                // Handle cooldown
                checkCoolDown("Summon", player, TWFaith.getPlugin().getConfig().getLong("summon-cooldown"));
                player_data.getCool_downs().put("Summon", System.currentTimeMillis() / 1000);
                PlayerHashMap.player_data_hashmap.put(player.getUniqueId(), player_data);
                // Handle stamina
                if (player_data.getStamina() < TWFaith.getPlugin().getConfig().getInt("summon-stamina")){
                    player.sendMessage(ChatColor.RED + "Not enough stamina.");
                    return true;
                }
                player_data.setStamina(player_data.getStamina() - TWFaith.getPlugin().getConfig().getInt("summon-stamina"));
                requester.sendMessage(player.getDisplayName() + " has been summoned!");
                player.teleport(requester.getLocation());
                SummonGod.summon_requests.remove(player.getUniqueId());
            }

            if (Objects.equals(args[0], "balance")){
                player.sendMessage("You have " + player_data.getFaith_points() + " Faith Points");
            }

            if (Objects.equals(args[0], "kick") && args.length == 2) {

                // Only leaders can use this command
                if (!player_data.getLeader()){
                    player.sendMessage(ChatColor.RED + "Must be leader to kick follower.");
                    return true;
                }

                // If the kicked player is not online, search the list of offline players. If there is still no result,
                // tell the player he typed it wrong.
                Player kicked = Bukkit.getPlayer(args[1]);
                if (kicked == null){
                    boolean found_offline = false;
                    for (OfflinePlayer offline : Bukkit.getOfflinePlayers()){
                        if (!offline.getName().equals(args[1])){continue;}
                        // If the player is offline, we have to change the data in the JSON file.
                        PlayerProfile kicked_profile = offline.getPlayerProfile();
                        UUID kicked_uuid = kicked_profile.getUniqueId();
                        File kicked_folder = new File(TWFaith.getPlugin().getDataFolder(), "PlayerData");
                        File kicked_file = new File(kicked_folder, kicked_uuid + ".json");
                        try{
                            FileReader kicked_reader = new FileReader(kicked_file);
                            PlayerData kicked_data = gson.fromJson(kicked_reader, PlayerData.class);

                            kicked_data.setLed_by(kicked_uuid);
                            kicked_data.setFaith("");
                            kicked_data.setIn_faith(false);

                            FileWriter kicked_writer = new FileWriter(kicked_file, false);
                            gson.toJson(kicked_data, kicked_writer);
                            kicked_writer.flush();
                            kicked_writer.close();
                        }catch (IOException exception){return true;}
                        Faith faith = FaithHashMap.player_faith_hashmap.get(player.getUniqueId());
                        List<UUID> followers = faith.getFollowers();
                        followers.remove(kicked_uuid);
                        player.sendMessage("Kicked " + args[1]+ " from " + player_data.getFaith() + ".");
                        return true;
                    }
                    if (!found_offline){
                        player.sendMessage(ChatColor.RED + "Invalid Username");
                        return true;
                    }
                }

                PlayerData kicked_data = PlayerHashMap.player_data_hashmap.get(kicked.getUniqueId());
                Faith faith = FaithHashMap.player_faith_hashmap.get(player.getUniqueId());

                // Kicking the player
                if (kicked_data.getLed_by().equals(player_data.getLed_by()) && !kicked_data.getLeader()){
                    kicked_data.setLed_by(kicked.getUniqueId());
                    kicked_data.setFaith("");
                    kicked_data.setIn_faith(false);
                    List<UUID> followers = faith.getFollowers();
                    followers.remove(kicked.getUniqueId());
                    faith.setFollowers(followers);
                    player.sendMessage("Kicked " + kicked.getDisplayName() + " from " + player_data.getFaith() + ".");
                    kicked.sendMessage("You have been kicked from " + player_data.getFaith() + ".");
                } else if(!kicked_data.getLed_by().equals(player_data.getLed_by())){
                    player.sendMessage(ChatColor.RED + "That player is not in your faction.");
                } else{
                    player.sendMessage(ChatColor.RED + "You cannot kick yourself.");
                }
            }

        }
            return false;
    }

    public boolean checkCoolDown(String power, Player player, long cooldown){
        PlayerData player_data = PlayerHashMap.player_data_hashmap.get(player.getUniqueId());
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

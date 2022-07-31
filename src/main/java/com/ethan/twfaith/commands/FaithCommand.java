package com.ethan.twfaith.commands;

import com.ethan.twfaith.customevents.FaithUpgradeEvent;
import com.ethan.twfaith.files.Faith;
import com.ethan.twfaith.files.PlayerData;
import com.ethan.twfaith.files.UniquePlayers;
import com.ethan.twfaith.guis.FaithUpgrade;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.util.*;

public class FaithCommand implements CommandExecutor {
    // TODO Set up error messages for all commands
    // TODO Set up autocomplete for all commands
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Variables that can be used by multiple commands go here
            File faith_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Faiths");
            if (!faith_folder.exists()){faith_folder.mkdir();}
            File player_data_folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "PlayerData");
            if (!player_data_folder.exists()){player_data_folder.mkdir();}
            File unique_players_file = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "unique_players.json");
            if (!unique_players_file.exists()){
                try{unique_players_file.createNewFile();} catch (IOException e){e.printStackTrace();}
            }
            File sender_faith_file = new File(faith_folder, player.getUniqueId() + ".json");
            File sender_data_file = new File(player_data_folder, player.getUniqueId() + ".json");
            Gson gson = new Gson();

            if (Objects.equals(args[0], "create") && args.length == 2) {
                player.sendMessage("You have created the faith named " + args[1]);
                Faith faith = new Faith(args[1], player.getUniqueId(), 0, new ArrayList<>(), new ArrayList<>());

                try{
                    if (!sender_faith_file.exists()){sender_faith_file.createNewFile();}

                    Writer writer = new FileWriter(sender_faith_file, false);
                    gson.toJson(faith, writer);
                    writer.flush();
                    writer.close();

                    Reader reader = new FileReader(sender_data_file);
                    PlayerData player_data = gson.fromJson(reader, PlayerData.class);
                    player_data.setFaith(args[1]);
                    player_data.setIn_faith(true);
                    player_data.setLeader(true);
                    player_data.setLed_by(String.valueOf(player.getUniqueId()));
                    Writer player_data_writer = new FileWriter(sender_data_file, false);
                    gson.toJson(player_data, player_data_writer);
                    player_data_writer.flush();
                    player_data_writer.close();

                    System.out.println("Saved data!");
                }catch(IOException e){e.printStackTrace();}
            }

            if (Objects.equals(args[0], "list") && args.length == 1){
                try{

                    Reader reader = new FileReader(unique_players_file);
                    UniquePlayers read_unique_players = gson.fromJson(reader, UniquePlayers.class);

                    // This loop iterates through the unique players list and sends the names of all faiths
                    // to the player running the command
                    for (UUID unique_player : read_unique_players.getUnique_player_list()){
                        try{
                            File faith_file = new File(faith_folder, unique_player + ".json");
                            if (unique_players_file.exists()){
                                Reader faith_reader = new FileReader(faith_file);
                                Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                                player.sendMessage(read_faith.getFaith_name());
                            }
                        }catch(IOException e){
                            System.out.println(e);
                        }
                    }
                }catch(IOException e){e.printStackTrace();}
            }

            if (Objects.equals(args[0], "disband") && args.length == 1){
                try{
                    if (sender_faith_file.exists()){
                        Reader faith_reader = new FileReader(sender_faith_file);
                        Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                        File leader_data_file = new File(player_data_folder, read_faith.getLeader() + ".json");
                        Reader leader_data_reader = new FileReader(leader_data_file);
                        PlayerData leader_player_data = gson.fromJson(leader_data_reader, PlayerData.class);
                        leader_player_data.setLeader(false);
                        leader_player_data.setFaith("");
                        leader_player_data.setIn_faith(false);
                        Writer leader_player_data_writer = new FileWriter(leader_data_file, false);
                        gson.toJson(leader_player_data, leader_player_data_writer);
                        leader_player_data_writer.flush();
                        leader_player_data_writer.close();
                        leader_data_reader.close();
                        for (UUID unique_player : read_faith.getFollowers()){
                            File player_data_file = new File(player_data_folder, unique_player + ".json");
                            Reader player_data_reader = new FileReader(player_data_file);
                            PlayerData player_data = gson.fromJson(player_data_reader, PlayerData.class);
                            player_data.setLeader(false);
                            player_data.setIn_faith(false);
                            player_data.setFaith("");
                            Writer player_data_writer = new FileWriter(player_data_file, false);
                            // This needs its own Gson for some reason.
                            Gson nerd = new Gson();
                            nerd.toJson(player_data_reader, player_data_writer);
                            player_data_writer.flush();
                            player_data_writer.close();
                            player_data_reader.close();
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

                        File leader_data_file = new File(player_data_folder, read_faith.getLeader() + ".json");
                        Reader leader_data_reader = new FileReader(leader_data_file);
                        PlayerData leader_player_data = gson.fromJson(leader_data_reader, PlayerData.class);
                        leader_player_data.setFaith(args[1]);
                        Writer leader_player_data_writer = new FileWriter(leader_data_file, false);
                        gson.toJson(leader_player_data, leader_player_data_writer);
                        leader_player_data_writer.flush();
                        leader_player_data_writer.close();
                        leader_data_reader.close();
                        for (UUID unique_player : read_faith.getFollowers()){
                            File player_data_file = new File(player_data_folder, unique_player + ".json");
                            Reader player_data_reader = new FileReader(player_data_file);
                            PlayerData player_data = gson.fromJson(player_data_reader, PlayerData.class);
                            player_data.setFaith(args[1]);
                            Writer player_data_writer = new FileWriter(player_data_file, false);
                            gson.toJson(player_data_reader, player_data_writer);
                            player_data_writer.flush();
                            player_data_writer.close();
                            player_data_reader.close();
                        }

                        player.sendMessage("Renamed faith to " + args[1]);

                    }else{player.sendMessage(ChatColor.RED + "ERROR: Could not rename faith. Are you sure you are the" +
                            " leader of a faith?");}
                }catch(IOException e){System.out.println(e);}
            }

            if (Objects.equals(args[0], "invite") && Objects.equals(args[1], "add") && args.length == 3){
                if(Bukkit.getPlayerExact(args[2]) != null){
                    UUID invited_player = Bukkit.getPlayerExact(args[2]).getUniqueId();
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
                            System.out.println("Player successfully added.");
                        }catch(IOException e){e.printStackTrace(); System.out.println("There was an error adding to list.");}
                    }
                    Bukkit.getPlayerExact(args[2]).sendMessage("You have been invited to the faith " + faith_name);
                }
            }

            if (Objects.equals(args[0], "join") && args.length == 2){
                try{
                    Reader reader = new FileReader(unique_players_file);
                    UniquePlayers read_unique_players = gson.fromJson(reader, UniquePlayers.class);

                    for (UUID unique_player : read_unique_players.getUnique_player_list()){
                        try{
                            File folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Faiths");
                            File faith_file = new File(folder, unique_player + ".json");
                            if (faith_file.exists()){
                                Reader faith_reader = new FileReader(faith_file);
                                Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                                List<UUID> invited_players = read_faith.getInvited_players();
                                List<UUID> followers = read_faith.getFollowers();
                                System.out.println("file detected");
                                if (read_faith.getFaith_name().equals(args[1]) && read_faith.getInvited_players().contains(player.getUniqueId()) && !followers.contains(player.getUniqueId())){
                                    invited_players.remove(unique_player);
                                    followers.add(unique_player);
                                    player.sendMessage("You have become a follower of " + read_faith.getFaith_name());
                                    read_faith.setInvited_players(invited_players);
                                    read_faith.setFollowers(followers);

                                    if (Bukkit.getPlayer(read_faith.getLeader()).isOnline()){
                                        Bukkit.getPlayer(read_faith.getLeader()).sendMessage(player + " has become a follower of " + read_faith.getFaith_name());
                                    }

                                    Writer writer = new FileWriter(faith_file, false);
                                    gson.toJson(read_faith, writer);
                                    writer.flush();
                                    writer.close();

                                    Reader player_data_reader = new FileReader(sender_data_file);
                                    PlayerData read_player_data = gson.fromJson(player_data_reader, PlayerData.class);
                                    read_player_data.setFaith(args[1]);
                                    read_player_data.setIn_faith(true);
                                    read_player_data.setLed_by(String.valueOf(read_faith.getLeader()));

                                    Writer player_data_writer = new FileWriter(sender_data_file, false);
                                    gson.toJson(read_player_data, player_data_writer);
                                    player_data_writer.flush();
                                    player_data_writer.close();
                                }
                            }
                        }catch(IOException e){e.printStackTrace();}
                    }
                }catch(IOException e){e.printStackTrace();}
            }

            if (Objects.equals(args[0], "leave")){
                try{
                    Reader player_data_reader = new FileReader(sender_data_file);
                    PlayerData read_player_data = gson.fromJson(player_data_reader, PlayerData.class);
                    if (read_player_data.getIn_faith()){
                        // Removing the player from the faith data file
                        File faith_file = new File (faith_folder, read_player_data.getLed_by() + ".json");
                        Reader faith_reader = new FileReader(faith_file);
                        Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                        List<UUID> followers = read_faith.getFollowers();
                        followers.remove(player.getUniqueId());
                        Writer faith_data_writer = new FileWriter(faith_file, false);
                        gson.toJson(read_faith, faith_data_writer);
                        faith_data_writer.flush();
                        faith_data_writer.close();

                        // Removing the faith from the player data file
                        read_player_data.setIn_faith(false);
                        read_player_data.setLeader(false);
                        read_player_data.setFaith("");
                        read_player_data.setLed_by("");
                        Writer player_data_writer = new FileWriter(sender_data_file, false);
                        gson.toJson(read_player_data, player_data_writer);
                        player_data_writer.flush();
                        player_data_writer.close();

                        player.sendMessage("You have left " + read_faith.getFaith_name());
                    }
                }catch (IOException e){e.printStackTrace();}
            }

            if (Objects.equals(args[0], "upgrade")){
                File player_data_file = new File(player_data_folder, player.getUniqueId() + ".json");
                try {
                    Reader player_data_reader = new FileReader(player_data_file);
                    PlayerData player_data = gson.fromJson(player_data_reader, PlayerData.class);
                    if (player_data.getLeader()){
                        // Runs custom event for opening a gui menu
                        Bukkit.getPluginManager().callEvent(new FaithUpgradeEvent(player, "Faith Upgrade"));
                        return true;

                    } else{player.sendMessage(ChatColor.RED + "Error: Must be leader to upgrade faith.");}
                } catch (FileNotFoundException e) {e.printStackTrace();}
            }
        }
            return false;
        }
}

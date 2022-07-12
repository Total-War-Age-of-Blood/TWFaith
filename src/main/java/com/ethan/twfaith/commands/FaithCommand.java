package com.ethan.twfaith.commands;

import com.ethan.twfaith.files.Faith;
import com.ethan.twfaith.files.UniquePlayers;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;

public class FaithCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // TODO add interactions with PlayerData when appropriate

            if (Objects.equals(args[0], "create") && args.length == 2) {
                player.sendMessage("You have created the faith named " + args[1]);
                Faith faith = new Faith(args[1], player.getUniqueId(), 0, new ArrayList<>(), new ArrayList<>());

                try{
                    Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder().mkdir();
                    File folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Faiths");
                    if (!folder.exists()){
                        folder.mkdir();
                    }
                    File file = new File(folder,
                            player.getUniqueId()+".json");
                    if (!file.exists()){
                        file.createNewFile();
                    }
                    Gson gson = new Gson();
                    Writer writer = new FileWriter(file, false);
                    gson.toJson(faith, writer);
                    writer.flush();
                    writer.close();

                    System.out.println("Saved data!");
                }catch(IOException e){
                    e.printStackTrace();
                }


            }

            if (Objects.equals(args[0], "list") && args.length == 1){
                try{
                    Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder().mkdir();
                    File file = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(),
                            "unique_players.json");
                    if (!file.exists()){
                        file.createNewFile();
                    }

                    Gson gson = new Gson();
                    Reader reader = new FileReader(file);
                    UniquePlayers read_unique_players = gson.fromJson(reader, UniquePlayers.class);

                    // This loop iterates through the unique players list and sends the names of all faiths
                    // to the player running the command
                    for (UUID unique_player : read_unique_players.getUnique_player_list()){
                        try{
                            File folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Faiths");
                            File faith_file = new File(folder, unique_player + ".json");
                            if (file.exists()){
                                Reader faith_reader = new FileReader(faith_file);
                                Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                                player.sendMessage(read_faith.getFaith_name());
                            }
                        }catch(IOException e){
                            System.out.println(e);
                        }
                    }
                }catch(IOException e){

                }
            }

            if (Objects.equals(args[0], "disband") && args.length == 1){
                try{
                    File folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Faiths");
                    File faith_file = new File (folder, player.getUniqueId() + ".json");
                    if (faith_file.exists()){
                        faith_file.delete();
                        player.sendMessage("Your faith has been deleted.");
                    }
                }catch(Exception e){e.printStackTrace();}


            }

            if (Objects.equals(args[0], "rename")){
                try{
                    File folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Faiths");
                    File faith_file = new File (folder,
                            player.getUniqueId() + ".json");
                    if (faith_file.exists()){
                        Gson gson = new Gson();
                        Reader faith_reader = new FileReader(faith_file);
                        Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                        read_faith.setFaith_name(args[1]);

                        Writer writer = new FileWriter(faith_file, false);
                        gson.toJson(read_faith, writer);
                        writer.flush();
                        writer.close();

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
                        Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder().mkdir();
                        File folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Faiths");
                        File file = new File(folder, player.getUniqueId()+".json");
                        Gson gson = new Gson();
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
                            File folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Faiths");
                            File file = new File(folder,
                                    player.getUniqueId() + ".json");
                            Gson gson = new Gson();
                            Reader faith_reader = new FileReader(file);
                            Faith read_faith = gson.fromJson(faith_reader, Faith.class);
                            read_faith.setInvited_players(invited_players);

                            // After you set a value, you have to remember to write the Faith object to the JSON XD
                            Writer writer = new FileWriter(file, false);
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
                    Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder().mkdir();
                    File file = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(),
                            "unique_players.json");
                    if (!file.exists()){
                        file.createNewFile();
                    }

                    Gson gson = new Gson();
                    Reader reader = new FileReader(file);
                    UniquePlayers read_unique_players = gson.fromJson(reader, UniquePlayers.class);

                    for (UUID unique_player : read_unique_players.getUnique_player_list()){
                        try{
                            File folder = new File(Bukkit.getPluginManager().getPlugin("TWFaith").getDataFolder(), "Faiths");
                            File faith_file = new File(folder, unique_player + ".json");
                            if (file.exists()){
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


                                }
                            }
                        }catch(IOException e){
                            System.out.println(e);
                        }
                    }
                }catch(IOException e){

                }
            }

            //TODO add leave faith

        }
            return false;
        }

}


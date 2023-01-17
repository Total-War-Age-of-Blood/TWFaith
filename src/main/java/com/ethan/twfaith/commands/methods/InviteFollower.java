package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.Faith;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class InviteFollower {
    public void InviteFollower(File faithFolder,File senderFaithFile, String[] args, Player player){
        if(Bukkit.getPlayerExact(args[2]) != null){
            UUID invited_player = Objects.requireNonNull(Bukkit.getPlayerExact(args[2])).getUniqueId();
            String faith_name = null;
            List<UUID> invited_players = new ArrayList<>();
            try{
                File file = new File(faithFolder, player.getUniqueId()+".json");
                Reader faith_reader = new FileReader(file);
                Faith read_faith = TWFaith.getGson().fromJson(faith_reader, Faith.class);
                faith_name = read_faith.getFaithName();
                invited_players = read_faith.getInvitedPlayers();
                System.out.println("The loaded invited players list says: " + invited_players);
            }catch(IOException e){e.printStackTrace();}
            // Adds the invited player to the invited players list in the faith file
            if (!invited_players.contains(invited_player)){
                System.out.println("Adding player to invited players list.");
                invited_players.add(invited_player);
                System.out.println(invited_players);
                try{
                    Reader faith_reader = new FileReader(senderFaithFile);
                    Faith read_faith = TWFaith.getGson().fromJson(faith_reader, Faith.class);
                    read_faith.setInvitedPlayers(invited_players);

                    // After you set a value, you have to remember to write the Faith object to the JSON XD
                    Writer writer = new FileWriter(senderFaithFile, false);
                    TWFaith.getGson().toJson(read_faith, writer);
                    writer.flush();
                    writer.close();
                    // System.out.println("Player successfully added.");
                }catch(IOException e){e.printStackTrace(); System.out.println("There was an error adding to list.");}
            }
            player.sendMessage("You have invited " + args[2] + " to the faith.");
            Objects.requireNonNull(Bukkit.getPlayerExact(args[2])).sendMessage("You have been invited to the faith " + faith_name);
        }
    }
}

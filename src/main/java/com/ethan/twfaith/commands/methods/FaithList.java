package com.ethan.twfaith.commands.methods;

import com.ethan.twfaith.TWFaith;
import com.ethan.twfaith.data.Faith;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

public class FaithList {
    public void FaithList(File faithFolder, Player player){

        try{
            // This loop iterates through the unique players list and sends the names of all faiths
            // to the player running the command

            for (File file : Objects.requireNonNull(faithFolder.listFiles())){
                Reader faithReader = new FileReader(file);
                Faith readFaith = TWFaith.getGson().fromJson(faithReader, Faith.class);
                player.sendMessage(readFaith.getFaithName());
            }
        }catch(IOException e){e.printStackTrace();}
    }
}

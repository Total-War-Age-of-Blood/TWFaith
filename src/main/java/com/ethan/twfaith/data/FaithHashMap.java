package com.ethan.twfaith.data;

import com.ethan.twfaith.TWFaith;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class FaithHashMap {
    // Because the faith file holds data shared by all players in a faith, it will not be loaded and modified per player.
    // Instead, the faith file will be loaded on startup and saved on shutdown. Players will access the faith's information
    // using the getLeader method of PlayerData.
    public static HashMap<UUID, Faith> playerFaithHashmap = new HashMap<>();
    File faithsFolder = new File(TWFaith.getPlugin().getDataFolder(), "Faiths");

   public void loadFaiths(){
       if(!faithsFolder.exists()){return;}
       for (File file : Objects.requireNonNull(faithsFolder.listFiles())){
           try{
               FileReader file_reader = new FileReader(file);
               Faith faith = TWFaith.getGson().fromJson(file_reader, Faith.class);
               playerFaithHashmap.put(faith.getLeader(), faith);
           }catch (IOException exception){exception.printStackTrace();}
       }
   }

   public void saveFaiths(){
       for (Map.Entry<UUID, Faith> entry : playerFaithHashmap.entrySet()){
           try {
               File saveFile = new File(faithsFolder, entry.getValue().getLeader() + ".json");
               FileWriter fileWriter = new FileWriter(saveFile, false);
               TWFaith.getGson().toJson(entry.getValue(), fileWriter);
               fileWriter.flush();
               fileWriter.close();
           }catch (IOException exception){exception.printStackTrace();}
       }
   }

}

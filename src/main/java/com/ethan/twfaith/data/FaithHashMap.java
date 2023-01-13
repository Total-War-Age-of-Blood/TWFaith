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
    public static HashMap<UUID, Faith> player_faith_hashmap = new HashMap<>();
    File faiths_folder = new File(TWFaith.getPlugin().getDataFolder(), "Faiths");

   public void loadFaiths(){
       if(!faiths_folder.exists()){return;}
       for (File file : Objects.requireNonNull(faiths_folder.listFiles())){
           try{
               FileReader file_reader = new FileReader(file);
               Faith faith = TWFaith.getGson().fromJson(file_reader, Faith.class);
               player_faith_hashmap.put(faith.getLeader(), faith);
           }catch (IOException exception){exception.printStackTrace();}
       }
   }

   public void saveFaiths(){
       for (Map.Entry<UUID, Faith> entry : player_faith_hashmap.entrySet()){
           try {
               File save_file = new File(faiths_folder, entry.getValue().getLeader() + ".json");
               FileWriter file_writer = new FileWriter(save_file, false);
               TWFaith.getGson().toJson(entry.getValue(), file_writer);
               file_writer.flush();
               file_writer.close();
           }catch (IOException exception){exception.printStackTrace();}
       }
   }

}

package com.ethan.twfaith.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OpenGUIEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player player;
    String gui_name;

    public OpenGUIEvent(Player player, String gui_name){
        this.player = player;
        this.gui_name = gui_name;
    }

    public Player getPlayer(){
        return player;
    }

    public String getGui_name(){
        return gui_name;
    }


    @Override
    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

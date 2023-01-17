package com.ethan.twfaith.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OpenGUIEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player player;
    String guiName;

    public OpenGUIEvent(Player player, String gui_name){
        this.player = player;
        this.guiName = gui_name;
    }

    public Player getPlayer(){
        return player;
    }

    public String getGuiName(){
        return guiName;
    }


    @Override
    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

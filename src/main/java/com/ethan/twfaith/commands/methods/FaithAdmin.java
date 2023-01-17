package com.ethan.twfaith.commands.methods;

public class FaithAdmin {

    // TODO make commands to add/remove faith points
    // TODO make commands to allow admins to force add/remove followers as well as disband faith

    // This method allows admins to change the faith points of a faith.
    public boolean editPoints(String key){
        switch (key){
            case "give":
                return true;
            case "take":
                return true;
            case "set":
                return true;
        }
        return false;
    }
}

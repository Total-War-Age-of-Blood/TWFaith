package com.ethan.twfaith.files;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.UUID;

public class PlayerData {

    UUID uuid;
    String faith;
    boolean in_faith;
    boolean leader;

    public PlayerData(UUID uuid, String faith, boolean in_faith, boolean leader) {
        this.uuid = uuid;
        this.faith = faith;
        this.in_faith = in_faith;
        this.leader = leader;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFaith() {
        return faith;
    }

    public void setFaith(String faith) {
        this.faith = faith;
    }

    public boolean getIn_faith() {
        return in_faith;
    }

    public void setIn_faith(boolean in_faith) {
        this.in_faith = in_faith;
    }

    public boolean getLeader() {
        return leader;
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }
}

package com.ethan.twfaith.files;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.UUID;

public class PlayerData {

    UUID uuid;
    String led_by;
    String faith;
    boolean in_faith;
    boolean leader;

    public PlayerData(UUID uuid, String led_by, String faith, boolean in_faith, boolean leader) {
        this.uuid = uuid;
        this.led_by = led_by;
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

    public String getLed_by() { return led_by; }

    public void setLed_by(String led_by) { this.led_by = led_by; }

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

package com.ethan.twfaith.data;

import java.util.UUID;

public class PlayerData {

    UUID uuid;
    UUID led_by;
    String faith;
    boolean in_faith;
    boolean leader;
    long last_prayer;
    int faith_points;
    int terrain_bonus;
    int summon_god;
    int god_proximity_bonus;
    int name_prophets;
    int god_of_defense;
    int god_of_crafstman;
    int crumbling;
    int heavy_boots;
    int intoxicate;
    int discombobulate;
    int entangle;
    int lions_heart;
    int savior;
    int taunt;
    int insidious;
    int explosive_landing;
    int flood;
    boolean taunted;
    UUID taunter;


    public PlayerData(UUID uuid, UUID led_by, String faith, boolean in_faith, boolean leader, long last_prayer, int faith_points,
                      int terrain_bonus, int summon_god, int god_proximity_bonus, int name_prophets, int god_of_defense,
                      int god_of_crafstman, int crumbling, int heavy_boots, int intoxicate, int discombobulate, int entangle,
                      int lions_heart, int savior, int taunt, int insidious, int explosive_landing, int flood, boolean taunted, UUID taunter) {
        this.uuid = uuid;
        this.led_by = led_by;
        this.faith = faith;
        this.in_faith = in_faith;
        this.leader = leader;
        this.last_prayer = last_prayer;
        this.faith_points = faith_points;
        this.terrain_bonus = terrain_bonus;
        this.summon_god = summon_god;
        this.god_proximity_bonus = god_proximity_bonus;
        this.name_prophets = name_prophets;
        this.god_of_defense = god_of_defense;
        this.god_of_crafstman = god_of_crafstman;
        this.crumbling = crumbling;
        this.heavy_boots = heavy_boots;
        this.intoxicate = intoxicate;
        this.discombobulate = discombobulate;
        this.entangle = entangle;
        this.lions_heart = lions_heart;
        this.savior = savior;
        this.taunt = taunt;
        this.insidious = insidious;
        this.explosive_landing = explosive_landing;
        this.flood = flood;
        this.taunted = taunted;
        this.taunter = taunter;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getLed_by() { return led_by; }

    public void setLed_by(UUID led_by) { this.led_by = led_by; }

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

    public long getLast_prayer() { return last_prayer;}

    public void setLast_prayer(long last_prayer) { this.last_prayer = last_prayer;}

    public int getFaith_points() { return faith_points;}

    public void setFaith_points(int faith_points) { this.faith_points = faith_points;}

    public int getTerrain_bonus() {
        return terrain_bonus;
    }

    public void setTerrain_bonus(int terrain_bonus) {
        this.terrain_bonus = terrain_bonus;
    }

    public int getSummon_god() {
        return summon_god;
    }

    public void setSummon_god(int summon_god) {
        this.summon_god = summon_god;
    }

    public int getGod_proximity_bonus() {
        return god_proximity_bonus;
    }

    public void setGod_proximity_bonus(int god_proximity_bonus) {
        this.god_proximity_bonus = god_proximity_bonus;
    }

    public int getName_prophets() {
        return name_prophets;
    }

    public void setName_prophets(int name_prophets) {
        this.name_prophets = name_prophets;
    }

    public int getGod_of_defense() {
        return god_of_defense;
    }

    public void setGod_of_defense(int god_of_defense) {
        this.god_of_defense = god_of_defense;
    }

    public int getGod_of_crafstman() {
        return god_of_crafstman;
    }

    public void setGod_of_crafstman(int god_of_crafstman) {
        this.god_of_crafstman = god_of_crafstman;
    }

    public int getCrumbling() {
        return crumbling;
    }

    public void setCrumbling(int crumbling) {
        this.crumbling = crumbling;
    }

    public int getHeavy_boots() {
        return heavy_boots;
    }

    public void setHeavy_boots(int heavy_boots) {
        this.heavy_boots = heavy_boots;
    }

    public int getIntoxicate() {
        return intoxicate;
    }

    public void setIntoxicate(int intoxicate) {
        this.intoxicate = intoxicate;
    }

    public int getDiscombobulate() {
        return discombobulate;
    }

    public void setDiscombobulate(int discombobulate) {
        this.discombobulate = discombobulate;
    }

    public int getEntangle() {
        return entangle;
    }

    public void setEntangle(int entangle) {
        this.entangle = entangle;
    }

    public int getLions_heart() {
        return lions_heart;
    }

    public void setLions_heart(int lions_heart) {
        this.lions_heart = lions_heart;
    }

    public int getSavior() {
        return savior;
    }

    public void setSavior(int savior) {
        this.savior = savior;
    }

    public int getTaunt() {
        return taunt;
    }

    public void setTaunt(int taunt) {
        this.taunt = taunt;
    }

    public int getInsidious() {
        return insidious;
    }

    public void setInsidious(int insidious) {
        this.insidious = insidious;
    }

    public int getExplosive_landing() {
        return explosive_landing;
    }

    public void setExplosive_landing(int explosive_landing) {
        this.explosive_landing = explosive_landing;
    }

    public int getFlood() {
        return flood;
    }

    public void setFlood(int flood) {
        this.flood = flood;
    }

    public boolean isTaunted() {return taunted;}

    public void setTaunted(boolean taunted) {this.taunted = taunted;}

    public UUID getTaunter() {return taunter;}

    public void setTaunter(UUID taunter) {this.taunter = taunter;}
}

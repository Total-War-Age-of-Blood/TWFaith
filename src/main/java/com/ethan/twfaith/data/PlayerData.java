package com.ethan.twfaith.data;

import java.util.HashMap;
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
    boolean lions_heart_active;
    boolean savior_active;
    boolean insidious_active;
    boolean explosive_landing_active;
    boolean crumbling_active;
    boolean crumbling_victim;
    boolean heavy_boots_active;
    boolean heavy_boots_victim;
    boolean intoxicate_active;
    boolean intoxicate_victim;
    boolean discombobulate_victim;
    boolean entangle_victim;
    int powerful_flock;
    int hells_fury;
    int divine_intervention;
    int mana;
    boolean terrain_bonus_active;
    boolean summon_god_active;
    boolean powerful_flock_active;
    boolean in_flock;
    double nearby_friends;
    boolean hells_fury_active;
    double stamina;
    double max_stamina;
    HashMap<String, Long> cool_downs;



    public PlayerData(UUID uuid, UUID led_by, String faith, boolean in_faith, boolean leader, long last_prayer, int faith_points,
                      int terrain_bonus, int summon_god, int god_proximity_bonus, int name_prophets, int god_of_defense,
                      int god_of_crafstman, int crumbling, int heavy_boots, int intoxicate, int discombobulate, int entangle,
                      int lions_heart, int savior, int taunt, int insidious, int explosive_landing, int flood, boolean taunted,
                      UUID taunter, boolean lions_heart_active, boolean savior_active, boolean insidious_active,
                      boolean explosive_landing_active, boolean crumbling_active, boolean crumbling_victim, boolean heavy_boots_active, boolean heavy_boots_victim,
                      boolean intoxicate_active, boolean intoxicate_victim, boolean discombobulate_victim,
                      boolean entangle_victim, int powerful_flock, int hells_fury, int divine_intervention,
                      int mana, boolean terrain_bonus_active, boolean summon_god_active, boolean powerful_flock_active, boolean in_flock,
                      double nearby_friends, boolean hells_fury_active, double stamina, double max_stamina, HashMap<String, Long> cool_downs) {
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
        this.lions_heart_active = lions_heart_active;
        this.savior_active = savior_active;
        this.insidious_active = insidious_active;
        this.explosive_landing_active = explosive_landing_active;
        this.crumbling_active = crumbling_active;
        this.crumbling_victim = crumbling_victim;
        this.heavy_boots_active = heavy_boots_active;
        this.heavy_boots_victim = heavy_boots_victim;
        this.intoxicate_active = intoxicate_active;
        this.intoxicate_victim = intoxicate_victim;
        this.discombobulate_victim = discombobulate_victim;
        this.entangle_victim = entangle_victim;
        this.powerful_flock = powerful_flock;
        this.hells_fury = hells_fury;
        this.divine_intervention = divine_intervention;
        this.mana = mana;
        this.terrain_bonus_active = terrain_bonus_active;
        this.summon_god_active = summon_god_active;
        this.powerful_flock_active = powerful_flock_active;
        this.in_flock = in_flock;
        this.nearby_friends = nearby_friends;
        this.hells_fury_active = hells_fury_active;
        this.stamina = stamina;
        this.max_stamina = max_stamina;
        this.cool_downs = cool_downs;
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

    public int getHeavyBoots() {
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

    public boolean isIn_faith() { return in_faith; }

    public boolean isLeader() {
        return leader;
    }

    public boolean isLions_heart_active() {
        return lions_heart_active;
    }

    public void setLions_heart_active(boolean lions_heart_active) {
        this.lions_heart_active = lions_heart_active;
    }

    public boolean isSavior_active() {
        return savior_active;
    }

    public void setSavior_active(boolean savior_active) {
        this.savior_active = savior_active;
    }

    public boolean isInsidious_active() {
        return insidious_active;
    }

    public void setInsidious_active(boolean insidious_active) {
        this.insidious_active = insidious_active;
    }

    public boolean isExplosive_landing_active() {
        return explosive_landing_active;
    }

    public void setExplosive_landing_active(boolean explosive_landing_active) {
        this.explosive_landing_active = explosive_landing_active;
    }

    public boolean isCrumbling_active() {
        return crumbling_active;
    }

    public void setCrumbling_active(boolean crumbling_active) {
        this.crumbling_active = crumbling_active;
    }

    public boolean isCrumbling_victim() {
        return crumbling_victim;
    }

    public void setCrumbling_victim(boolean crumbling_victim) {
        this.crumbling_victim = crumbling_victim;
    }

    public boolean isHeavy_boots_active() {
        return heavy_boots_active;
    }

    public void setHeavy_boots_active(boolean heavy_boots_active) {
        this.heavy_boots_active = heavy_boots_active;
    }

    public boolean isIntoxicate_active() {
        return intoxicate_active;
    }

    public void setIntoxicate_active(boolean intoxicate_active) {
        this.intoxicate_active = intoxicate_active;
    }

    public boolean isHeavy_boots_victim() {
        return heavy_boots_victim;
    }

    public void setHeavy_boots_victim(boolean heavy_boots_victim) {
        this.heavy_boots_victim = heavy_boots_victim;
    }

    public boolean isIntoxicate_victim() {
        return intoxicate_victim;
    }

    public void setIntoxicate_victim(boolean intoxicate_victim) {
        this.intoxicate_victim = intoxicate_victim;
    }

    public boolean isDiscombobulate_victim() {
        return discombobulate_victim;
    }

    public void setDiscombobulate_victim(boolean discombobulate_victim) {
        this.discombobulate_victim = discombobulate_victim;
    }

    public boolean isEntangle_victim() {
        return entangle_victim;
    }

    public void setEntangle_victim(boolean entangle_victim) {
        this.entangle_victim = entangle_victim;
    }

    public int getPowerful_flock() {
        return powerful_flock;
    }

    public void setPowerful_flock(int powerful_flock) {
        this.powerful_flock = powerful_flock;
    }

    public int getHells_fury() {
        return hells_fury;
    }

    public void setHells_fury(int hells_fury) {
        this.hells_fury = hells_fury;
    }

    public int getDivine_intervention() {
        return divine_intervention;
    }

    public void setDivine_intervention(int divine_intervention) {
        this.divine_intervention = divine_intervention;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public boolean isTerrain_bonus_active() {
        return terrain_bonus_active;
    }

    public void setTerrain_bonus_active(boolean terrain_bonus_active) {
        this.terrain_bonus_active = terrain_bonus_active;
    }

    public boolean isSummon_god_active() {
        return summon_god_active;
    }

    public void setSummon_god_active(boolean summon_god_active) {
        this.summon_god_active = summon_god_active;
    }

    public boolean isPowerful_flock_active() {
        return powerful_flock_active;
    }

    public void setPowerful_flock_active(boolean powerful_flock_active) {
        this.powerful_flock_active = powerful_flock_active;
    }

    public boolean isIn_flock() {
        return in_flock;
    }

    public void setIn_flock(boolean in_flock) {
        this.in_flock = in_flock;
    }

    public double getNearby_friends() {
        return nearby_friends;
    }

    public void setNearby_friends(double nearby_friends) {
        this.nearby_friends = nearby_friends;
    }

    public boolean isHells_fury_active() {
        return hells_fury_active;
    }

    public void setHells_fury_active(boolean hells_fury_active) {
        this.hells_fury_active = hells_fury_active;
    }

    public double getStamina() {
        return stamina;
    }

    public void setStamina(double stamina) {
        this.stamina = stamina;
    }

    public double getMax_stamina() {
        return max_stamina;
    }

    public void setMax_stamina(double max_stamina) {
        this.max_stamina = max_stamina;
    }

    public HashMap<String, Long> getCool_downs() {
        return cool_downs;
    }

    public void setCool_downs(HashMap<String, Long> cool_downs) {
        this.cool_downs = cool_downs;
    }
}

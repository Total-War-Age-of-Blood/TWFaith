package com.ethan.twfaith.data;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {

    UUID uuid;
    UUID ledBy;
    String faith;
    boolean inFaith;
    boolean leader;
    long lastPrayer;
    int faithPoints;
    boolean taunted;
    UUID taunter;
    boolean lionsHeartActive;
    boolean saviorActive;
    boolean insidiousActive;
    boolean explosiveLandingActive;
    boolean crumblingActive;
    boolean crumblingVictim;
    boolean heavyBootsActive;
    boolean heavyBootsVictim;
    boolean intoxicateActive;
    boolean intoxicateVictim;
    boolean discombobulateVictim;
    boolean entangleVictim;
    boolean terrainBonusActive;
    boolean summonGodActive;
    boolean powerfulFlockActive;
    boolean inFlock;
    double nearbyFriends;
    boolean hellsFuryActive;
    double stamina;
    double maxStamina;
    HashMap<String, Long> coolDowns;

    public PlayerData(UUID uuid, UUID ledBy, String faith, boolean in_faith, boolean leader, long lastPrayer,
                      int faithPoints, boolean taunted, UUID taunter, boolean lionsHeartActive, boolean saviorActive,
                      boolean insidiousActive, boolean explosiveLandingActive, boolean crumblingActive,
                      boolean crumblingVictim, boolean heavyBootsActive, boolean heavyBootsVictim,
                      boolean intoxicateActive, boolean intoxicateVictim, boolean discombobulateVictim,
                      boolean entangleVictim, boolean terrainBonusActive, boolean summonGodActive,
                      boolean powerfulFlockActive, boolean inFlock, double nearbyFriends, boolean hellsFuryActive,
                      double stamina, double maxStamina, HashMap<String, Long> coolDowns) {
        this.uuid = uuid;
        this.ledBy = ledBy;
        this.faith = faith;
        this.inFaith = in_faith;
        this.leader = leader;
        this.lastPrayer = lastPrayer;
        this.faithPoints = faithPoints;
        this.taunted = taunted;
        this.taunter = taunter;
        this.lionsHeartActive = lionsHeartActive;
        this.saviorActive = saviorActive;
        this.insidiousActive = insidiousActive;
        this.explosiveLandingActive = explosiveLandingActive;
        this.crumblingActive = crumblingActive;
        this.crumblingVictim = crumblingVictim;
        this.heavyBootsActive = heavyBootsActive;
        this.heavyBootsVictim = heavyBootsVictim;
        this.intoxicateActive = intoxicateActive;
        this.intoxicateVictim = intoxicateVictim;
        this.discombobulateVictim = discombobulateVictim;
        this.entangleVictim = entangleVictim;
        this.terrainBonusActive = terrainBonusActive;
        this.summonGodActive = summonGodActive;
        this.powerfulFlockActive = powerfulFlockActive;
        this.inFlock = inFlock;
        this.nearbyFriends = nearbyFriends;
        this.hellsFuryActive = hellsFuryActive;
        this.stamina = stamina;
        this.maxStamina = maxStamina;
        this.coolDowns = coolDowns;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getLedBy() {
        return ledBy;
    }

    public void setLedBy(UUID ledBy) {
        this.ledBy = ledBy;
    }

    public String getFaith() {
        return faith;
    }

    public void setFaith(String faith) {
        this.faith = faith;
    }

    public boolean isInFaith() {
        return inFaith;
    }

    public void setInFaith(boolean inFaith) {
        this.inFaith = inFaith;
    }

    public boolean isLeader() {
        return leader;
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    public long getLastPrayer() {
        return lastPrayer;
    }

    public void setLastPrayer(long lastPrayer) {
        this.lastPrayer = lastPrayer;
    }

    public int getFaithPoints() {
        return faithPoints;
    }

    public void setFaithPoints(int faithPoints) {
        this.faithPoints = faithPoints;
    }

    public boolean isTaunted() {
        return taunted;
    }

    public void setTaunted(boolean taunted) {
        this.taunted = taunted;
    }

    public UUID getTaunter() {
        return taunter;
    }

    public void setTaunter(UUID taunter) {
        this.taunter = taunter;
    }

    public boolean isLionsHeartActive() {
        return lionsHeartActive;
    }

    public void setLionsHeartActive(boolean lionsHeartActive) {
        this.lionsHeartActive = lionsHeartActive;
    }

    public boolean isSaviorActive() {
        return saviorActive;
    }

    public void setSaviorActive(boolean saviorActive) {
        this.saviorActive = saviorActive;
    }

    public boolean isInsidiousActive() {
        return insidiousActive;
    }

    public void setInsidiousActive(boolean insidiousActive) {
        this.insidiousActive = insidiousActive;
    }

    public boolean isExplosiveLandingActive() {
        return explosiveLandingActive;
    }

    public void setExplosiveLandingActive(boolean explosiveLandingActive) {
        this.explosiveLandingActive = explosiveLandingActive;
    }

    public boolean isCrumblingActive() {
        return crumblingActive;
    }

    public void setCrumblingActive(boolean crumblingActive) {
        this.crumblingActive = crumblingActive;
    }

    public boolean isCrumblingVictim() {
        return crumblingVictim;
    }

    public void setCrumblingVictim(boolean crumblingVictim) {
        this.crumblingVictim = crumblingVictim;
    }

    public boolean isHeavyBootsActive() {
        return heavyBootsActive;
    }

    public void setHeavyBootsActive(boolean heavyBootsActive) {
        this.heavyBootsActive = heavyBootsActive;
    }

    public boolean isHeavyBootsVictim() {
        return heavyBootsVictim;
    }

    public void setHeavyBootsVictim(boolean heavyBootsVictim) {
        this.heavyBootsVictim = heavyBootsVictim;
    }

    public boolean isIntoxicateActive() {
        return intoxicateActive;
    }

    public void setIntoxicateActive(boolean intoxicateActive) {
        this.intoxicateActive = intoxicateActive;
    }

    public boolean isIntoxicateVictim() {
        return intoxicateVictim;
    }

    public void setIntoxicateVictim(boolean intoxicateVictim) {
        this.intoxicateVictim = intoxicateVictim;
    }

    public boolean isDiscombobulateVictim() {
        return discombobulateVictim;
    }

    public void setDiscombobulateVictim(boolean discombobulateVictim) {
        this.discombobulateVictim = discombobulateVictim;
    }

    public boolean isEntangleVictim() {
        return entangleVictim;
    }

    public void setEntangleVictim(boolean entangleVictim) {
        this.entangleVictim = entangleVictim;
    }

    public boolean isTerrainBonusActive() {
        return terrainBonusActive;
    }

    public void setTerrainBonusActive(boolean terrainBonusActive) {
        this.terrainBonusActive = terrainBonusActive;
    }

    public boolean isSummonGodActive() {
        return summonGodActive;
    }

    public void setSummonGodActive(boolean summonGodActive) {
        this.summonGodActive = summonGodActive;
    }

    public boolean isPowerfulFlockActive() {
        return powerfulFlockActive;
    }

    public void setPowerfulFlockActive(boolean powerfulFlockActive) {
        this.powerfulFlockActive = powerfulFlockActive;
    }

    public boolean isInFlock() {
        return inFlock;
    }

    public void setInFlock(boolean inFlock) {
        this.inFlock = inFlock;
    }

    public double getNearbyFriends() {
        return nearbyFriends;
    }

    public void setNearbyFriends(double nearbyFriends) {
        this.nearbyFriends = nearbyFriends;
    }

    public boolean isHellsFuryActive() {
        return hellsFuryActive;
    }

    public void setHellsFuryActive(boolean hellsFuryActive) {
        this.hellsFuryActive = hellsFuryActive;
    }

    public double getStamina() {
        return stamina;
    }

    public void setStamina(double stamina) {
        this.stamina = stamina;
    }

    public double getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(double maxStamina) {
        this.maxStamina = maxStamina;
    }

    public HashMap<String, Long> getCoolDowns() {
        return coolDowns;
    }

    public void setCoolDowns(HashMap<String, Long> coolDowns) {
        this.coolDowns = coolDowns;
    }
}

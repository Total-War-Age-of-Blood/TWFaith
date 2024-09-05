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
    boolean lionsHeartActive = false;
    boolean saviorActive = false;
    boolean insidiousActive = false;
    boolean explosiveLandingActive = false;
    boolean crumblingActive = false;
    boolean crumblingVictim = false;
    boolean heavyBootsActive = false;
    boolean heavyBootsVictim = false;
    boolean intoxicateActive = false;
    boolean intoxicateVictim = false;
    boolean discombobulateVictim = false;
    boolean entangleVictim = false;
    boolean terrainBonusActive = false;
    boolean summonGodActive = false;
    boolean powerfulFlockActive = false;
    boolean inFlock = false;
    double nearbyFriends = 0;
    boolean hellsFuryActive = false;
    double stamina = 100;
    double maxStamina = 100;
    HashMap<String, Long> coolDowns;
    boolean staminaBarEnabled = true;

    public PlayerData(UUID uuid, UUID ledBy, String faith, boolean in_faith, boolean leader, long lastPrayer,
                      int faithPoints, boolean taunted, UUID taunter, HashMap<String, Long> coolDowns) {
        this.uuid = uuid;
        this.ledBy = ledBy;
        this.faith = faith;
        this.inFaith = in_faith;
        this.leader = leader;
        this.lastPrayer = lastPrayer;
        this.faithPoints = faithPoints;
        this.taunted = taunted;
        this.taunter = taunter;
        this.coolDowns = coolDowns;
    }

    public PlayerData(PlayerData playerData){
        this.uuid = playerData.getUuid();
        this.ledBy = playerData.getLedBy();
        this.faith = playerData.getFaith();
        this.inFaith = playerData.isInFaith();
        this.leader = playerData.isLeader();
        this.lastPrayer = playerData.getLastPrayer();
        this.faithPoints = playerData.getFaithPoints();
        this.taunted = playerData.isTaunted();
        this.taunter = playerData.getTaunter();
        this.lionsHeartActive = playerData.isLionsHeartActive();
        this.saviorActive = playerData.isSaviorActive();
        this.insidiousActive = playerData.isInsidiousActive();
        this.explosiveLandingActive = playerData.isExplosiveLandingActive();
        this.crumblingActive = playerData.isCrumblingActive();
        this.crumblingVictim = playerData.isCrumblingVictim();
        this.heavyBootsActive = playerData.isHeavyBootsActive();
        this.heavyBootsVictim = playerData.isHeavyBootsVictim();
        this.intoxicateActive = playerData.isIntoxicateActive();
        this.intoxicateVictim = playerData.isIntoxicateVictim();
        this.discombobulateVictim = playerData.isDiscombobulateVictim();
        this.entangleVictim = playerData.isEntangleVictim();
        this.terrainBonusActive = playerData.isTerrainBonusActive();
        this.summonGodActive = playerData.isSummonGodActive();
        this.powerfulFlockActive = playerData.isPowerfulFlockActive();
        this.inFlock = playerData.isInFlock();
        this.nearbyFriends = playerData.getNearbyFriends();
        this.hellsFuryActive = playerData.isHellsFuryActive();
        this.stamina = playerData.getStamina();
        this.maxStamina = playerData.getMaxStamina();
        this.coolDowns = playerData.getCoolDowns();
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

    public boolean isStaminaBarEnabled() {
        return staminaBarEnabled;
    }

    public void setStaminaBarEnabled(boolean staminaBarEnabled) {
        this.staminaBarEnabled = staminaBarEnabled;
    }
}

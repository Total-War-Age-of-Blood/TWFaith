package com.ethan.twfaith.data;

import java.util.List;
import java.util.UUID;

public class Faith {
    private String faithName;
    private UUID leader;
    private int faithPoints;
    private List<UUID> invitedPlayers;
    private List<UUID> followers;
    int terrainSnowy;
    int terrainCold;
    int terrainWarm;
    int terrainTemperate;
    int terrainAquatic;
    int terrainCave;
    int terrainNether;
    int terrainEnd;
    int terrainStrength;
    int terrainHaste;
    int terrainSpeed;
    int terrainResistance;
    int terrainFireResistance;
    int terrainWaterBreathing;
    int terrainDolphinsGrace;
    int terrainBonus;
    int summonGod;
    int godProximityBonus;
    int nameProphets;
    int godOfDefense;
    int godOfCrafstman;
    int crumbling;
    int heavyBoots;
    int intoxicate;
    int discombobulate;
    int entangle;
    int lionsHeart;
    int savior;
    int taunt;
    int insidious;
    int explosiveLanding;
    int flood;
    int powerfulFlock;
    int hellsFury;
    int divineIntervention;
    int mana;
    boolean terrainBonusActive;
    List<String> terrainActivePowers;


    public Faith(String faithName, UUID leader, int faithPoints, List<UUID> invitedPlayers, List<UUID> followers,
                 int terrainSnowy, int terrainCold, int terrainWarm, int terrainTemperate, int terrainAquatic,
                 int terrainCave, int terrainNether, int terrainEnd, int terrainStrength, int terrainHaste,
                 int terrainSpeed, int terrainResistance, int terrainFireResistance, int terrainWaterBreathing,
                 int terrainDolphinsGrace, int terrainBonus, int summonGod, int godProximityBonus,
                 int nameProphets, int godOfDefense, int godOfCrafstman, int crumbling, int heavyBoots,
                 int intoxicate, int discombobulate, int entangle, int lionsHeart, int savior, int taunt,
                 int insidious, int explosiveLanding, int flood, int powerfulFlock, int hellsFury,
                 int divineIntervention, int mana, boolean terrainBonusActive, List<String> terrainActivePowers) {
        this.faithName = faithName;
        this.leader = leader;
        this.faithPoints = faithPoints;
        this.invitedPlayers = invitedPlayers;
        this.followers = followers;
        this.terrainSnowy = terrainSnowy;
        this.terrainCold = terrainCold;
        this.terrainWarm = terrainWarm;
        this.terrainTemperate = terrainTemperate;
        this.terrainAquatic = terrainAquatic;
        this.terrainCave = terrainCave;
        this.terrainNether = terrainNether;
        this.terrainEnd = terrainEnd;
        this.terrainStrength = terrainStrength;
        this.terrainHaste = terrainHaste;
        this.terrainSpeed = terrainSpeed;
        this.terrainResistance = terrainResistance;
        this.terrainFireResistance = terrainFireResistance;
        this.terrainWaterBreathing = terrainWaterBreathing;
        this.terrainDolphinsGrace = terrainDolphinsGrace;
        this.terrainBonus = terrainBonus;
        this.summonGod = summonGod;
        this.godProximityBonus = godProximityBonus;
        this.nameProphets = nameProphets;
        this.godOfDefense = godOfDefense;
        this.godOfCrafstman = godOfCrafstman;
        this.crumbling = crumbling;
        this.heavyBoots = heavyBoots;
        this.intoxicate = intoxicate;
        this.discombobulate = discombobulate;
        this.entangle = entangle;
        this.lionsHeart = lionsHeart;
        this.savior = savior;
        this.taunt = taunt;
        this.insidious = insidious;
        this.explosiveLanding = explosiveLanding;
        this.flood = flood;
        this.powerfulFlock = powerfulFlock;
        this.hellsFury = hellsFury;
        this.divineIntervention = divineIntervention;
        this.mana = mana;
        this.terrainBonusActive = terrainBonusActive;
        this.terrainActivePowers = terrainActivePowers;
    }

    public String getFaithName() {
        return faithName;
    }

    public void setFaithName(String faithName) {
        this.faithName = faithName;
    }

    public UUID getLeader() {
        return leader;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public int getFaithPoints() {
        return faithPoints;
    }

    public void setFaithPoints(int faithPoints) {
        this.faithPoints = faithPoints;
    }

    public List<UUID> getInvitedPlayers() {
        return invitedPlayers;
    }

    public void setInvitedPlayers(List<UUID> invitedPlayers) {
        this.invitedPlayers = invitedPlayers;
    }

    public List<UUID> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UUID> followers) {
        this.followers = followers;
    }

    public int getTerrainSnowy() {
        return terrainSnowy;
    }

    public void setTerrainSnowy(int terrainSnowy) {
        this.terrainSnowy = terrainSnowy;
    }

    public int getTerrainCold() {
        return terrainCold;
    }

    public void setTerrainCold(int terrainCold) {
        this.terrainCold = terrainCold;
    }

    public int getTerrainWarm() {
        return terrainWarm;
    }

    public void setTerrainWarm(int terrainWarm) {
        this.terrainWarm = terrainWarm;
    }

    public int getTerrainTemperate() {
        return terrainTemperate;
    }

    public void setTerrainTemperate(int terrainTemperate) {
        this.terrainTemperate = terrainTemperate;
    }

    public int getTerrainAquatic() {
        return terrainAquatic;
    }

    public void setTerrainAquatic(int terrainAquatic) {
        this.terrainAquatic = terrainAquatic;
    }

    public int getTerrainCave() {
        return terrainCave;
    }

    public void setTerrainCave(int terrainCave) {
        this.terrainCave = terrainCave;
    }

    public int getTerrainNether() {
        return terrainNether;
    }

    public void setTerrainNether(int terrainNether) {
        this.terrainNether = terrainNether;
    }

    public int getTerrainEnd() {
        return terrainEnd;
    }

    public void setTerrainEnd(int terrainEnd) {
        this.terrainEnd = terrainEnd;
    }

    public int getTerrainStrength() {
        return terrainStrength;
    }

    public void setTerrainStrength(int terrainStrength) {
        this.terrainStrength = terrainStrength;
    }

    public int getTerrainHaste() {
        return terrainHaste;
    }

    public void setTerrainHaste(int terrainHaste) {
        this.terrainHaste = terrainHaste;
    }

    public int getTerrainSpeed() {
        return terrainSpeed;
    }

    public void setTerrainSpeed(int terrainSpeed) {
        this.terrainSpeed = terrainSpeed;
    }

    public int getTerrainResistance() {
        return terrainResistance;
    }

    public void setTerrainResistance(int terrainResistance) {
        this.terrainResistance = terrainResistance;
    }

    public int getTerrainFireResistance() {
        return terrainFireResistance;
    }

    public void setTerrainFireResistance(int terrainFireResistance) {
        this.terrainFireResistance = terrainFireResistance;
    }

    public int getTerrainWaterBreathing() {
        return terrainWaterBreathing;
    }

    public void setTerrainWaterBreathing(int terrainWaterBreathing) {
        this.terrainWaterBreathing = terrainWaterBreathing;
    }

    public int getTerrainDolphinsGrace() {
        return terrainDolphinsGrace;
    }

    public void setTerrainDolphinsGrace(int terrainDolphinsGrace) {
        this.terrainDolphinsGrace = terrainDolphinsGrace;
    }

    public int getTerrainBonus() {
        return terrainBonus;
    }

    public void setTerrainBonus(int terrainBonus) {
        this.terrainBonus = terrainBonus;
    }

    public int getSummonGod() {
        return summonGod;
    }

    public void setSummonGod(int summonGod) {
        this.summonGod = summonGod;
    }

    public int getGodProximityBonus() {
        return godProximityBonus;
    }

    public void setGodProximityBonus(int godProximityBonus) {
        this.godProximityBonus = godProximityBonus;
    }

    public int getNameProphets() {
        return nameProphets;
    }

    public void setNameProphets(int nameProphets) {
        this.nameProphets = nameProphets;
    }

    public int getGodOfDefense() {
        return godOfDefense;
    }

    public void setGodOfDefense(int godOfDefense) {
        this.godOfDefense = godOfDefense;
    }

    public int getGodOfCrafstman() {
        return godOfCrafstman;
    }

    public void setGodOfCrafstman(int godOfCrafstman) {
        this.godOfCrafstman = godOfCrafstman;
    }

    public int getCrumbling() {
        return crumbling;
    }

    public void setCrumbling(int crumbling) {
        this.crumbling = crumbling;
    }

    public int getHeavyBoots() {
        return heavyBoots;
    }

    public void setHeavyBoots(int heavyBoots) {
        this.heavyBoots = heavyBoots;
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

    public int getLionsHeart() {
        return lionsHeart;
    }

    public void setLionsHeart(int lionsHeart) {
        this.lionsHeart = lionsHeart;
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

    public int getExplosiveLanding() {
        return explosiveLanding;
    }

    public void setExplosiveLanding(int explosiveLanding) {
        this.explosiveLanding = explosiveLanding;
    }

    public int getFlood() {
        return flood;
    }

    public void setFlood(int flood) {
        this.flood = flood;
    }

    public int getPowerfulFlock() {
        return powerfulFlock;
    }

    public void setPowerfulFlock(int powerfulFlock) {
        this.powerfulFlock = powerfulFlock;
    }

    public int getHellsFury() {
        return hellsFury;
    }

    public void setHellsFury(int hellsFury) {
        this.hellsFury = hellsFury;
    }

    public int getDivineIntervention() {
        return divineIntervention;
    }

    public void setDivineIntervention(int divineIntervention) {
        this.divineIntervention = divineIntervention;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public boolean isTerrainBonusActive() {
        return terrainBonusActive;
    }

    public void setTerrainBonusActive(boolean terrainBonusActive) {
        this.terrainBonusActive = terrainBonusActive;
    }

    public List<String> getTerrainActivePowers() {
        return terrainActivePowers;
    }

    public void setTerrainActivePowers(List<String> terrainActivePowers) {
        this.terrainActivePowers = terrainActivePowers;
    }
}

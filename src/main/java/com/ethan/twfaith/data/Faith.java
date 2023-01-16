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
    boolean terrainBonusActive;
    List<String> terrainActivePowers;


    public Faith(String faithName, UUID leader, int faith_points, List<UUID> invited_players, List<UUID> followers,
                 int terrain_snowy, int terrain_cold, int terrain_warm, int terrain_temperate, int terrain_aquatic,
                 int terrain_cave, int terrain_nether, int terrain_end, int terrain_strength, int terrain_haste,
                 int terrain_speed, int terrain_resistance, int terrain_fire_resistance, int terrain_water_breathing, int terrain_dolphins_grace, boolean terrain_bonus_active, List<String> terrain_active_powers) {
        this.faithName = faithName;
        this.leader = leader;
        this.faithPoints = faith_points;
        this.invitedPlayers = invited_players;
        this.followers = followers;
        this.terrainSnowy = terrain_snowy;
        this.terrainCold = terrain_cold;
        this.terrainWarm = terrain_warm;
        this.terrainTemperate = terrain_temperate;
        this.terrainAquatic = terrain_aquatic;
        this.terrainCave = terrain_cave;
        this.terrainNether = terrain_nether;
        this.terrainEnd = terrain_end;
        this.terrainStrength = terrain_strength;
        this.terrainHaste = terrain_haste;
        this.terrainSpeed = terrain_speed;
        this.terrainResistance = terrain_resistance;
        this.terrainFireResistance = terrain_fire_resistance;
        this.terrainWaterBreathing = terrain_water_breathing;
        this.terrainDolphinsGrace = terrain_dolphins_grace;
        this.terrainBonusActive = terrain_bonus_active;
        this.terrainActivePowers = terrain_active_powers;
    }

    public String getFaithName() {
        return faithName;
    }

    public UUID getLeader() {
        return leader;
    }

    public int getFaithPoints() {
        return faithPoints;
    }

    public List<UUID> getInvitedPlayers(){
        return invitedPlayers;
    }

    public List<UUID> getFollowers() {
        return followers;
    }

    public void setFaithName(String faithName) {
        this.faithName = faithName;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public void setFaithPoints(int faithPoints) {
        this.faithPoints = faithPoints;
    }

    public void setInvitedPlayers(List<UUID> invitedPlayers){
        this.invitedPlayers = invitedPlayers;
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

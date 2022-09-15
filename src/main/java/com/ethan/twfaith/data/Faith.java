package com.ethan.twfaith.data;

import java.util.List;
import java.util.UUID;

public class Faith {
    private String faith_name;
    private UUID leader;
    private int faith_points;
    private List<UUID> invited_players;
    private List<UUID> followers;
    int terrain_snowy;
    int terrain_cold;
    int terrain_warm;
    int terrain_temperate;
    int terrain_aquatic;
    int terrain_cave;
    int terrain_nether;
    int terrain_end;
    int terrain_strength;
    int terrain_haste;
    int terrain_speed;
    int terrain_resistance;
    int terrain_fire_resistance;
    int terrain_water_breathing;
    int terrain_dolphins_grace;
    boolean terrain_bonus_active;
    List<String> terrain_active_powers;


    public Faith(String faith_name, UUID leader, int faith_points, List<UUID> invited_players, List<UUID> followers,
                 int terrain_snowy, int terrain_cold, int terrain_warm, int terrain_temperate, int terrain_aquatic,
                 int terrain_cave, int terrain_nether, int terrain_end, int terrain_strength, int terrain_haste,
                 int terrain_speed, int terrain_resistance, int terrain_fire_resistance, int terrain_water_breathing, int terrain_dolphins_grace, boolean terrain_bonus_active, List<String> terrain_active_powers) {
        this.faith_name = faith_name;
        this.leader = leader;
        this.faith_points = faith_points;
        this.invited_players = invited_players;
        this.followers = followers;
        this.terrain_snowy = terrain_snowy;
        this.terrain_cold = terrain_cold;
        this.terrain_warm = terrain_warm;
        this.terrain_temperate = terrain_temperate;
        this.terrain_aquatic = terrain_aquatic;
        this.terrain_cave = terrain_cave;
        this.terrain_nether = terrain_nether;
        this.terrain_end = terrain_end;
        this.terrain_strength = terrain_strength;
        this.terrain_haste = terrain_haste;
        this.terrain_speed = terrain_speed;
        this.terrain_resistance = terrain_resistance;
        this.terrain_fire_resistance = terrain_fire_resistance;
        this. terrain_water_breathing = terrain_water_breathing;
        this.terrain_dolphins_grace = terrain_dolphins_grace;
        this.terrain_bonus_active = terrain_bonus_active;
        this.terrain_active_powers = terrain_active_powers;
    }

    public String getFaith_name() {
        return faith_name;
    }

    public UUID getLeader() {
        return leader;
    }

    public int getFaith_points() {
        return faith_points;
    }

    public List<UUID> getInvited_players(){
        return invited_players;
    }

    public List<UUID> getFollowers() {
        return followers;
    }

    public void setFaith_name(String faith_name) {
        this.faith_name = faith_name;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public void setFaith_points(int faith_points) {
        this.faith_points = faith_points;
    }

    public void setInvited_players(List<UUID> invited_players){
        this.invited_players = invited_players;
    }

    public void setFollowers(List<UUID> followers) {
        this.followers = followers;
    }

    public int getTerrain_snowy() {
        return terrain_snowy;
    }

    public void setTerrain_snowy(int terrain_snowy) {
        this.terrain_snowy = terrain_snowy;
    }

    public int getTerrain_cold() {
        return terrain_cold;
    }

    public void setTerrain_cold(int terrain_cold) {
        this.terrain_cold = terrain_cold;
    }

    public int getTerrain_warm() {
        return terrain_warm;
    }

    public void setTerrain_warm(int terrain_warm) {
        this.terrain_warm = terrain_warm;
    }

    public int getTerrain_temperate() {
        return terrain_temperate;
    }

    public void setTerrain_temperate(int terrain_temperate) {
        this.terrain_temperate = terrain_temperate;
    }

    public int getTerrain_aquatic() {
        return terrain_aquatic;
    }

    public void setTerrain_aquatic(int terrain_aquatic) {
        this.terrain_aquatic = terrain_aquatic;
    }

    public int getTerrain_cave() {
        return terrain_cave;
    }

    public void setTerrain_cave(int terrain_cave) {
        this.terrain_cave = terrain_cave;
    }

    public int getTerrain_nether() {
        return terrain_nether;
    }

    public void setTerrain_nether(int terrain_nether) {
        this.terrain_nether = terrain_nether;
    }

    public int getTerrain_end() {
        return terrain_end;
    }

    public void setTerrain_end(int terrain_end) {
        this.terrain_end = terrain_end;
    }

    public int getTerrain_strength() {
        return terrain_strength;
    }

    public void setTerrain_strength(int terrain_strength) {
        this.terrain_strength = terrain_strength;
    }

    public int getTerrain_haste() {
        return terrain_haste;
    }

    public void setTerrain_haste(int terrain_haste) {
        this.terrain_haste = terrain_haste;
    }

    public int getTerrain_speed() {
        return terrain_speed;
    }

    public void setTerrain_speed(int terrain_speed) {
        this.terrain_speed = terrain_speed;
    }

    public int getTerrain_resistance() {
        return terrain_resistance;
    }

    public void setTerrain_resistance(int terrain_resistance) {
        this.terrain_resistance = terrain_resistance;
    }

    public int getTerrain_fire_resistance() {
        return terrain_fire_resistance;
    }

    public void setTerrain_fire_resistance(int terrain_fire_resistance) {
        this.terrain_fire_resistance = terrain_fire_resistance;
    }

    public int getTerrain_water_breathing() {
        return terrain_water_breathing;
    }

    public void setTerrain_water_breathing(int terrain_water_breathing) {
        this.terrain_water_breathing = terrain_water_breathing;
    }

    public int getTerrain_dolphins_grace() {
        return terrain_dolphins_grace;
    }

    public void setTerrain_dolphins_grace(int terrain_dolphins_grace) {
        this.terrain_dolphins_grace = terrain_dolphins_grace;
    }

    public boolean isTerrain_bonus_active() {
        return terrain_bonus_active;
    }

    public void setTerrain_bonus_active(boolean terrain_bonus_active) {
        this.terrain_bonus_active = terrain_bonus_active;
    }

    public List<String> getTerrain_active_powers() {
        return terrain_active_powers;
    }

    public void setTerrain_active_powers(List<String> terrain_active_powers) {
        this.terrain_active_powers = terrain_active_powers;
    }
}

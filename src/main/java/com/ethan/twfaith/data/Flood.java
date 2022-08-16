package com.ethan.twfaith.data;

import java.util.ArrayList;
import java.util.UUID;

public class Flood {
    UUID uuid;
    ArrayList<ArrayList<Integer>> block_coordinates;

    public Flood(UUID uuid, ArrayList<ArrayList<Integer>> block_coordinates) {
        this.uuid = uuid;
        this.block_coordinates = block_coordinates;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ArrayList<ArrayList<Integer>> getBlock_coordinates() {
        return block_coordinates;
    }

    public void setBlock_coordinates(ArrayList<ArrayList<Integer>> block_coordinates) {
        this.block_coordinates = block_coordinates;
    }
}

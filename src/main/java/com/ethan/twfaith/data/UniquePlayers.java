package com.ethan.twfaith.data;

import java.util.List;
import java.util.UUID;

public class UniquePlayers {
    private List<UUID> unique_player_list;

    public UniquePlayers(List<UUID> unique_player_list) {
        this.unique_player_list = unique_player_list;
    }

    public List<UUID> getUnique_player_list() {
        return unique_player_list;
    }
}

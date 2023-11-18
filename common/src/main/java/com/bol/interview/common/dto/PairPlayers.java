package com.bol.interview.common.dto;

import java.util.List;

public record PairPlayers(List<Player> playerList) {
    public PairPlayers {
        if (playerList==null || playerList.isEmpty()) {
            throw new IllegalArgumentException("Player list can not be null or empty!");
        }
    }
}

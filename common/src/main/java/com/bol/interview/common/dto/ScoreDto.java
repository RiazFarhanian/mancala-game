package com.bol.interview.common.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ScoreDto(List<PlayerScore> playersScore, LocalDateTime gameDate) {
    public record PlayerScore(String playerName,Integer score) {

    }
}

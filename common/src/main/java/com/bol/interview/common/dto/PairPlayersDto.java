package com.bol.interview.common.dto;

import java.util.List;

public record PairPlayersDto(List<PlayerDto> playerDtoList) {
    public PairPlayersDto {
        if (playerDtoList ==null || playerDtoList.isEmpty()) {
            throw new IllegalArgumentException("Player list can not be null or empty!");
        }
    }
}

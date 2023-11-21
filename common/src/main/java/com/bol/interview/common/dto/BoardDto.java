package com.bol.interview.common.dto;

import java.util.List;

public record BoardDto(List<PitDto> pitDtoList, String playerName) {
    public record PitDto(Integer order, Integer value) {
    }
}

package com.bol.interview.common.dto;

import java.util.List;

public record BoardDto(List<PitDto> pitDtoList, PlayerDto playerDto,Integer score) {
    public record PitDto(Integer order, Integer value) {
    }
}

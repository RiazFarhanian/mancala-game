package com.bol.interview.common.dto;

import java.util.List;

public record GameDto(String joinId, String turnPlayerId, List<BoardDto> boardList) {
}

package com.bol.interview.mancalaservice.service;

import com.bol.interview.common.dto.ScoreDto;

import java.util.List;

public interface ScoreService {
    List<ScoreDto> getTopScores(String playerId);
}

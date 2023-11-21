package com.bol.interview.mancalaservice.service;


import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.mancalaservice.model.GameContext;

import java.util.NoSuchElementException;

public interface PairPlayerService {
    PairPlayersDto getPairPlayers(GameContext context) throws NoSuchElementException;
}

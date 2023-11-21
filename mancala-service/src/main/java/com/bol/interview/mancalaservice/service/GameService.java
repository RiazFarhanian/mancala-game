package com.bol.interview.mancalaservice.service;


import com.bol.interview.common.dto.GameDto;
import com.bol.interview.common.dto.PairPlayersDto;

public interface GameService  {
    void newGame(String joinId, PairPlayersDto pairPlayersDto);
    GameDto makeMove(String joinId, String playerId, int selectedPitIndex);

}

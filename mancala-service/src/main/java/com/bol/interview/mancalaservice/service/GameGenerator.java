package com.bol.interview.mancalaservice.service;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.mancalaservice.entity.Board;
import com.bol.interview.mancalaservice.entity.Game;

import java.util.List;

public interface GameGenerator {
    Game generateNewGame(String joinId, PairPlayersDto pairPlayersDto);

    Board generateNewBoard(PlayerDto playerDto);

    List<Board.Pit> generateNewPitList();
}

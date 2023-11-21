package com.bol.interview.mancalaservice.service;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.mancalaservice.entity.Board;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.mapper.PlayerMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bol.interview.mancalaservice.configuration.MancalaConstants.DEFAULT_PIT_VALUE;
import static com.bol.interview.mancalaservice.configuration.MancalaConstants.MAX_PIT_NUMBER;

@Service
public class TwoPlayersGameGeneratorService implements GameGenerator {

    private final PlayerMapper playerMapper;

    public TwoPlayersGameGeneratorService(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }


    @Override
    public Game generateNewGame(String joinId, PairPlayersDto pairPlayersDto) {
        Game game = new Game();
        game.setId(UUID.randomUUID().toString());
        game.setVersion(1L);
        game.setCreatedDate(LocalDateTime.now());
        game.setLastModifiedDate(LocalDateTime.now());

        game.setStatus(Game.Status.New);
        game.setJoinId(joinId);

        List<Board> boardList = pairPlayersDto.playerDtoList().stream().map(this::generateNewBoard).toList();
        game.setBoardList(boardList);

        return game;
    }

    @Override
    public Board generateNewBoard(PlayerDto playerDto) {
        return new Board(
                generateNewPitList(),
                playerMapper.playerDtoToPlayer(playerDto)
        );
    }

    @Override
    public List<Board.Pit> generateNewPitList() {
        List<Board.Pit> pitList = new ArrayList<>();
        for (int i = 0; i < MAX_PIT_NUMBER; i++) {
            pitList.add(new Board.Pit(i, DEFAULT_PIT_VALUE));
        }
        return pitList;
    }
}

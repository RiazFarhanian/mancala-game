package com.bol.interview.mancalaservice.service;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.mancalaservice.configuration.MancalaConstants;
import com.bol.interview.mancalaservice.entity.Board;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.exception.ExceptionMessages;
import com.bol.interview.mancalaservice.mapper.PlayerMapper;
import com.bol.interview.mancalaservice.model.GameContext;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class PairPlayerServiceImpl implements PairPlayerService {
    private final PlayerMapper playerMapper;

    public PairPlayerServiceImpl(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    @Override
    public PairPlayersDto getPairPlayers(GameContext context) throws NoSuchElementException {
        return getPairPlayers(context, MancalaConstants.EMPTY);
    }

    @Override
    public PairPlayersDto getPairPlayers(GameContext context, String excludeUserName) throws NoSuchElementException {
        Game game = context.getGame();
        if (game==null)
            throw new NoSuchElementException(ExceptionMessages.GAME_IS_NULL);
        List<PlayerDto> players = game.getBoardList()
                .stream()
                .map(Board::getPlayer)
                .map(playerMapper::playerToPlayerDto)
                .filter(playerDto -> !playerDto.userName().equals(excludeUserName))
                .toList();
        return new PairPlayersDto(players);

    }
}

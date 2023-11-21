package com.bol.interview.mancalaservice.service;

import com.bol.interview.common.dto.ScoreDto;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MancalaScoreService implements ScoreService {
    private final GameRepository gameRepository;

    public MancalaScoreService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<ScoreDto> getTopScores(String playerId) {
        List<Game> games = gameRepository.
                findTop10ByBoardListPlayerUserNameAndStatusOrderByCreatedDateDesc(playerId, Game.Status.Finished);
        return gameToScoreDtoList(games);
    }

    private static List<ScoreDto> gameToScoreDtoList(List<Game> games) {
        List<ScoreDto> scoreDtoList = new ArrayList<>();
        for (Game game : games) {
            Map<String, Integer> scoreMap = new HashMap<>();
            game.getBoardList().forEach(board -> scoreMap.put(board.getPlayer().getName(), board.getScore()));
            scoreDtoList.add(new ScoreDto(scoreMap));
        }
        return scoreDtoList;
    }
}

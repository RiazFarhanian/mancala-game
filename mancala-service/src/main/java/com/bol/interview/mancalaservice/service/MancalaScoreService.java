package com.bol.interview.mancalaservice.service;

import com.bol.interview.common.dto.ScoreDto;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.repository.GameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MancalaScoreService implements ScoreService {
    private final GameRepository gameRepository;

    public MancalaScoreService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<ScoreDto> getTopScores(String playerId) {
        Pageable pageRequest1 =  PageRequest.of(0, 10, Sort.by("createdDate").descending());
        Page<Game> games = gameRepository.findAllByStatusEquals(Game.Status.Finished,pageRequest1);
        return gameToScoreDtoList(games.get().toList());
    }

    private static List<ScoreDto> gameToScoreDtoList(List<Game> games) {
        List<ScoreDto> scoreDtoList = new ArrayList<>();
        for (Game game : games) {
            List<ScoreDto.PlayerScore> playerScoreList = new ArrayList<>();
            game.getBoardList().forEach(board -> {
                ScoreDto.PlayerScore score = new ScoreDto.PlayerScore(board.getPlayer().getName(), board.getScore());
                playerScoreList.add(score);
            });
            scoreDtoList.add(new ScoreDto(playerScoreList, game.getCreatedDate()));
        }
        return scoreDtoList;
    }
}

package com.bol.interview.mancalaservice.service;

import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.repository.GameRepository;
import com.bol.interview.mancalaservice.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class MancalaScoreServiceTest {

    ScoreService mancalaScoreService;
    @Mock
    GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        mancalaScoreService = new MancalaScoreService(gameRepository);
    }

    @Test
    void getTopScores() {
        List<Game> games = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            games.add(TestUtil.createDummyGameWithScore());

//        when(gameRepository.
//                findTop10ByBoardListPlayerUserNameAndStatusOrderByCreatedDateDesc(any(), any())).
//                thenReturn(games);
//
//        assertEquals(10, mancalaScoreService.getTopScores("test").size());
    }
}
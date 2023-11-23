package com.bol.interview.mancalaservice.service;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.mapper.PlayerMapper;
import com.bol.interview.mancalaservice.model.GameContext;
import com.bol.interview.mancalaservice.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class PairPlayerServiceImplTest {

    @Mock
    PlayerMapper playerMapper;

    PairPlayerService pairPlayerService;

    GameContext context;

    Game game;


    @BeforeEach
    void setUp() {
        pairPlayerService = new PairPlayerServiceImpl(playerMapper);
        game = TestUtil.createDummyGame();
        context = GameContext.builder()
                .game(game)
                .build();
    }

    @Test
    void getPairPlayers() {
        when(playerMapper.playerToPlayerDto(any())).thenReturn(TestUtil.createDummyPlayerDto("HarryPotter"));
        PairPlayersDto pairPlayers = pairPlayerService.getPairPlayers(context);
        assertEquals(2, pairPlayers.playerDtoList().size());
    }

}
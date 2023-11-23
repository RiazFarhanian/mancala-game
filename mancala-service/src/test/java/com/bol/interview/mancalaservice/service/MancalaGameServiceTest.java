package com.bol.interview.mancalaservice.service;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.events.GameNotificationEvent;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.mapper.GameMapper;
import com.bol.interview.mancalaservice.repository.GameRepository;
import com.bol.interview.mancalaservice.service.rules.GameRuleProcessor;
import com.bol.interview.mancalaservice.service.validation.GameValidation;
import com.bol.interview.mancalaservice.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class MancalaGameServiceTest {

    private MancalaGameService mancalaGameService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;
    @Mock
    private GameGenerator gameGenerator;

    @Mock
    private GameValidation moveGameValidation;

    @Mock
    private GameValidation leaveGameValidation;

    @Mock
    private GameRuleProcessor ruleProcessor;

    @Mock
    private KafkaTemplate<PairPlayersDto, GameNotificationEvent> gameNotificationTemplate;

    @Mock
    private PairPlayerService pairPlayerService;

    @BeforeEach
    void setUp() {
        mancalaGameService = new MancalaGameService(gameRepository, gameMapper, gameGenerator, moveGameValidation,
                leaveGameValidation, ruleProcessor, gameNotificationTemplate, pairPlayerService);
    }

    @Test
    void newGame() {
        String joinId = "joinId";
        PairPlayersDto pairPlayersDto = TestUtil.createDummyPairPlayersDto();
        Game game = TestUtil.createDummyGame();
        when(gameGenerator.generateNewGame(joinId, pairPlayersDto)).thenReturn(game);
        when(gameRepository.insert(gameGenerator.generateNewGame(joinId, pairPlayersDto))).thenReturn(game);
        when(gameMapper.gameToGameDto(game)).thenReturn(TestUtil.createDummyGameDto());
        when(gameNotificationTemplate.send(any(), any(), any())).thenReturn(null);

        assertDoesNotThrow(() -> mancalaGameService.newGame(joinId, pairPlayersDto));
    }

    @Test
    void makeMove() {
        String joinId = "joinId";
        String playerId = "playerId";
        int selectedPitIndex = 1;

        Game game = TestUtil.createDummyGame();
        when(gameRepository.findByJoinId(joinId)).thenReturn(game);
        doNothing().when(moveGameValidation).validate(any());
        doNothing().when(ruleProcessor).applyRule(any());
        when(gameRepository.save(any())).thenReturn(game);
        when(gameMapper.gameToGameDto(game)).thenReturn(TestUtil.createDummyGameDto());
        when(gameNotificationTemplate.send(any(), any(), any())).thenReturn(null);
        assertDoesNotThrow(() -> mancalaGameService.makeMove(joinId, playerId, selectedPitIndex));
    }

    @Test
    void leave() {
        String joinId = "joinId";
        String playerId = "playerId";
        Game game = TestUtil.createDummyGame();
        when(gameRepository.findByJoinId(joinId)).thenReturn(game);
        when(gameRepository.save(any())).thenReturn(game);
        doNothing().when(leaveGameValidation).validate(any());
        when(gameMapper.gameToGameDto(game)).thenReturn(TestUtil.createDummyGameDto());
        when(pairPlayerService.getPairPlayers(any(),any())).thenReturn(TestUtil.createDummyPairPlayersDto());
        when(gameNotificationTemplate.send(any(), any(), any())).thenReturn(null);
        assertDoesNotThrow(() -> mancalaGameService.leave(joinId, playerId));
    }
}
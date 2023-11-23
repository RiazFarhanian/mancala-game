package com.bol.interview.mancalaservice.service.rules;

import com.bol.interview.common.dto.PairPlayersDto;
import com.bol.interview.common.events.GameNotificationEvent;
import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.mapper.GameMapper;
import com.bol.interview.mancalaservice.model.GameContext;
import com.bol.interview.mancalaservice.service.PairPlayerService;
import com.bol.interview.mancalaservice.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class GameFinishTest {

    GameRules gameFinish;
    @Mock
    private PairPlayerService pairPlayerService;
    @Mock
    private GameMapper gameMapper;

    @Mock
    private KafkaTemplate<PairPlayersDto, GameNotificationEvent> gameNotificationTemplate;

    GameContext finalGameContext;
    @BeforeEach
    void setUp() {
        gameFinish = new GameFinish(pairPlayerService, gameMapper, gameNotificationTemplate);
        finalGameContext = TestUtil.createFinishContextForGameFinishTest();
    }

    @Test
    void applyRule() {
        gameFinish.applyRule(finalGameContext);
        assertEquals(finalGameContext.getGame().getStatus(), Game.Status.Finished);
        assertEquals(finalGameContext.getGame().getBoardList().get(0).getScore(), Integer.valueOf(0));
        assertEquals(finalGameContext.getGame().getBoardList().get(1).getScore(), Integer.valueOf(7));
    }
}
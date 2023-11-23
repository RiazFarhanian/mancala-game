package com.bol.interview.mancalaservice.service.rules;

import com.bol.interview.mancalaservice.model.GameContext;
import com.bol.interview.mancalaservice.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
class GameTurnTest {
    GameRules gameTurn;
    GameContext normalGameContext;
    GameContext lastPitIsScorePitGameContext;

    @BeforeEach
    void setUp() {
        gameTurn = new GameTurn();
        normalGameContext = TestUtil.createNormalContext();
        lastPitIsScorePitGameContext = TestUtil.createLastPitIsScorePitContext();
    }

    @Test
    void applyRule() {
        gameTurn.applyRule(normalGameContext);
        assertNotEquals(normalGameContext.getPlayerId(), normalGameContext.getGame().getTurnPlayerId());

        gameTurn.applyRule(lastPitIsScorePitGameContext);
        assertEquals(lastPitIsScorePitGameContext.getPlayerId(), lastPitIsScorePitGameContext.getGame().getTurnPlayerId());

    }
}
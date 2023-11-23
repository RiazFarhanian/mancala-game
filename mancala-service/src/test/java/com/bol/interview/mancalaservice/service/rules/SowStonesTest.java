package com.bol.interview.mancalaservice.service.rules;

import com.bol.interview.mancalaservice.model.GameContext;
import com.bol.interview.mancalaservice.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class SowStonesTest {

    SowStones sowStones;
    GameContext normalGameContext;
    @BeforeEach
    void setUp() {
        sowStones = new SowStones();
        normalGameContext = TestUtil.createNormalContext();
    }

    @Test
    void applyRule() {
        sowStones.applyRule(normalGameContext);
        assertEquals(0, normalGameContext.getPitViews().get(0).getValue());
        assertEquals(1, normalGameContext.getPitViews().get(1).getValue());
        assertEquals(1, normalGameContext.getPitViews().get(2).getValue());
        assertEquals(2, normalGameContext.getLastPit().getOrder());
        assertEquals(2, normalGameContext.getLastPitIndex());


    }
}
package com.bol.interview.mancalaservice.service.rules;

import com.bol.interview.mancalaservice.exception.PitIsEmpty;
import com.bol.interview.mancalaservice.model.GameContext;
import com.bol.interview.mancalaservice.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PitProcessorTest {

    PitProcessor pitProcessor;
    GameContext selectedPitEmptyContext;

    GameContext normalContext;
    @BeforeEach
    void setUp() {
        pitProcessor = new PitProcessor();
        selectedPitEmptyContext = TestUtil.createPitEmptyContextForPitProcessor();
        normalContext = TestUtil.createNormalContextForPitProcessor();
    }

    @Test
    void applyRule() {
        assertThrows(PitIsEmpty.class,()->pitProcessor.applyRule(selectedPitEmptyContext));


        pitProcessor.applyRule(normalContext);
        assertEquals(13, normalContext.getPitViews().size());
        assertTrue(normalContext.getPitViews().get(6).getIsScorePit());
        assertEquals(normalContext.getSelectedPitIndexInGame(), normalContext.getSelectedPit().getOrder());
    }
}
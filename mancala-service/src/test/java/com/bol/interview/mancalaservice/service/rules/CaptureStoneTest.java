package com.bol.interview.mancalaservice.service.rules;

import com.bol.interview.mancalaservice.model.GameContext;
import com.bol.interview.mancalaservice.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class CaptureStoneTest {

    GameRules captureStone;
    GameContext captureStoneContext;

    GameContext noCaptureStoneContext;

    @BeforeEach
    void setUp() {
        captureStone = new CaptureStone();
        captureStoneContext = TestUtil.createContextForCaptureStoneTest();
        noCaptureStoneContext = TestUtil.createNormalContext();
    }

    @Test
    void applyRule() {
        captureStone.applyRule(captureStoneContext);
        assertEquals(8, captureStoneContext.getPitViews().get(0).getValue());
        assertEquals(0, captureStoneContext.getPitViews().get(12).getValue());

        captureStone.applyRule(noCaptureStoneContext);
        assertEquals(2, noCaptureStoneContext.getPitViews().get(0).getValue());
        assertEquals(7, noCaptureStoneContext.getPitViews().get(12).getValue());
    }
}
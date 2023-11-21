package com.bol.interview.mancalaservice.service.rules;

import com.bol.interview.mancalaservice.model.GameContext;
import com.bol.interview.mancalaservice.model.PitView;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bol.interview.mancalaservice.configuration.MancalaConstants.ONE;
@Component
@Order(1)
public class SowStones implements GameRules {
    @Override
    public void applyRule(GameContext context) {
        List<PitView> pitViews = context.getPitViews();

        //sow these stones one by one into the following pits, including their own big pit
        int stonesToDistribute = context.getSelectedPit().getValue();
        int size = context.getSelectedPitIndexInPitView() + stonesToDistribute + 1;
        int index = -1;
        for (int i = context.getSelectedPitIndexInPitView() + 1; i < size; i++) {
            index = (i < pitViews.size()) ? i : i - pitViews.size();
            pitViews.get(index).setValuePlus(ONE);
        }
        context.setSelectedPit(pitViews.get(index));
        context.setLastPitIndex(index);
    }
}

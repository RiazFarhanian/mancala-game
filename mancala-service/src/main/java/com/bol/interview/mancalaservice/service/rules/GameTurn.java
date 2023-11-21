package com.bol.interview.mancalaservice.service.rules;

import com.bol.interview.mancalaservice.model.GameContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class GameTurn implements GameRules {
    @Override
    public void applyRule(GameContext context) {
        if (!context.getLastPit().getIsScorePit()) {
            context.setTurnPlayerId(context.getNextPlayerUserName());
        }
    }
}

package com.bol.interview.mancalaservice.service.rules;


import com.bol.interview.mancalaservice.model.GameContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRuleProcessor implements GameRules {
    private final List<GameRules> gameRules;

    public GameRuleProcessor(List<GameRules> gameRules) {
        this.gameRules = gameRules;
    }

    @Override
    public void applyRule(GameContext context) {
        gameRules.forEach(rules -> rules.applyRule(context));
    }
}

package com.bol.interview.mancalaservice.service.validation;

import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.exception.ExceptionMessages;
import com.bol.interview.mancalaservice.exception.NotPlayableGame;
import com.bol.interview.mancalaservice.model.GameContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class validateGameStatus implements GameValidation {
    @Override
    public void validate(GameContext context) {
        if (context.getGameStatus() == Game.Status.Left || context.getGameStatus() == Game.Status.Finished) {
            throw new NotPlayableGame(ExceptionMessages.NOT_PLAYABLE_GAME);
        }
    }
}

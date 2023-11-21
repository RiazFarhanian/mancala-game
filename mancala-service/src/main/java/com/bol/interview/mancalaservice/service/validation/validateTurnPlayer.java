package com.bol.interview.mancalaservice.service.validation;

import com.bol.interview.mancalaservice.exception.ExceptionMessages;
import com.bol.interview.mancalaservice.exception.WrongTurn;
import com.bol.interview.mancalaservice.model.GameContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class validateTurnPlayer implements GameValidation{

    @Override
    public void validate(GameContext context) {
        if (!context.getPlayerId().equals(context.getGame().getTurnPlayerId())) {
            throw new WrongTurn(ExceptionMessages.WRONG_TURN);
        }
    }
}

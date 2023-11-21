package com.bol.interview.mancalaservice.service.validation;

import com.bol.interview.mancalaservice.exception.ExceptionMessages;
import com.bol.interview.mancalaservice.exception.ResourceNotFound;
import com.bol.interview.mancalaservice.model.GameContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ValidateGame implements GameValidation{

    @Override
    public void validate(GameContext context) {
        if (context.getGame() == null)
        {
            throw new ResourceNotFound(ExceptionMessages.GAME_NOT_FOUND);
        }
    }
}

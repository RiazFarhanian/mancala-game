package com.bol.interview.mancalaservice.service.validation;

import com.bol.interview.mancalaservice.configuration.MancalaConstants;
import com.bol.interview.mancalaservice.exception.ExceptionMessages;
import com.bol.interview.mancalaservice.exception.InvalidPitSelected;
import com.bol.interview.mancalaservice.model.GameContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class validateSelectedPitIndex implements GameValidation {
    private final static int PIT_SCORE_INDEX = MancalaConstants.MAX_PIT_NUMBER - 1;

    @Override
    public void validate(GameContext context) {
        if (context.getSelectedPitIndexInGame() < 0 || context.getSelectedPitIndexInGame() > PIT_SCORE_INDEX) {
            throw new InvalidPitSelected(ExceptionMessages.INVALID_PIT_SELECTED);
        }
    }
}

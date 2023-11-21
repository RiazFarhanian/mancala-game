package com.bol.interview.mancalaservice.service.validation;

import com.bol.interview.mancalaservice.model.GameContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameValidateCollection implements GameValidation{
    private final List<GameValidation> validationList;

    public GameValidateCollection(List<GameValidation> validationList) {
        this.validationList = validationList;
    }

    @Override
    public void validate(GameContext context) {
        validationList.forEach(gameValidation -> gameValidation.validate(context));
    }
}

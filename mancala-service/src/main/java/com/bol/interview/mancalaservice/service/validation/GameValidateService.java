package com.bol.interview.mancalaservice.service.validation;

import com.bol.interview.mancalaservice.model.GameContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameValidateService implements GameValidation{
    private final List<GameValidation> validationList;

    public GameValidateService(List<GameValidation> validationList) {
        this.validationList = validationList;
    }

    @Override
    public void validate(GameContext context) {
        validationList.forEach(gameValidation -> gameValidation.validate(context));
    }
}

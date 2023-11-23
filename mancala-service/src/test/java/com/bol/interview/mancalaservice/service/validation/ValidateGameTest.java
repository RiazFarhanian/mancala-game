package com.bol.interview.mancalaservice.service.validation;

import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.exception.ResourceNotFound;
import com.bol.interview.mancalaservice.model.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidateGameTest {

    GameValidation gameValidation;
    GameContext normalContext;
    GameContext nullGameContext;


    @BeforeEach
    void setUp() {
        gameValidation = new ValidateGame();
        normalContext = GameContext.builder()
                .game(new Game())
                .build();

        nullGameContext = GameContext.builder()
                .build();
    }

    @Test
    void validate() {
        assertDoesNotThrow(() -> gameValidation.validate(normalContext));
        assertThrows(ResourceNotFound.class, () -> gameValidation.validate(nullGameContext));
    }
}
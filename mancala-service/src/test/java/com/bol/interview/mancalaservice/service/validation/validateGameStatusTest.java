package com.bol.interview.mancalaservice.service.validation;

import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.model.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class validateGameStatusTest {

    GameValidation gameValidation;
    GameContext normalContext;
    GameContext finishGameContext;
    GameContext leftGameContext;

    @BeforeEach
    void setUp() {
        gameValidation = new validateGameStatus();
        Game finishedGame = new Game();
        finishedGame.setStatus(Game.Status.Finished);

        Game leftGame = new Game();
        leftGame.setStatus(Game.Status.Left);

        Game normalGame = new Game();
        normalGame.setStatus(Game.Status.New);

        leftGameContext = GameContext.builder()
                .game(leftGame)
                .build();
        finishGameContext = GameContext.builder()
                .game(finishedGame)
                .build();

        normalContext = GameContext.builder()
                .game(normalGame)
                .build();
    }

    @Test
    void validate() {
        assertThrows(Exception.class, () -> gameValidation.validate(leftGameContext));
        assertThrows(Exception.class, () -> gameValidation.validate(finishGameContext));
        assertDoesNotThrow(() -> gameValidation.validate(normalContext));
    }


}
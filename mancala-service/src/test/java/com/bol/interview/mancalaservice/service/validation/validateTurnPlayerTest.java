package com.bol.interview.mancalaservice.service.validation;

import com.bol.interview.mancalaservice.entity.Game;
import com.bol.interview.mancalaservice.exception.WrongTurn;
import com.bol.interview.mancalaservice.model.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class validateTurnPlayerTest {

    GameValidation gameValidation;
    GameContext wrongTurnContext;
    GameContext correctTurnContext;

    @BeforeEach
    void setUp() {
        gameValidation = new validateTurnPlayer();
        Game game = new Game();
        game.setTurnPlayerId("2");
        wrongTurnContext = GameContext.builder()
                .playerId("1")
                .game(game).build();

        correctTurnContext = GameContext.builder()
                .playerId("2")
                .game(game).build();
    }

    @Test
    void validate() {
        assertThrows(WrongTurn.class, () -> gameValidation.validate(wrongTurnContext));
        assertDoesNotThrow(() -> gameValidation.validate(correctTurnContext));
    }
}
package com.bol.interview.mancalaservice.service.validation;

import com.bol.interview.mancalaservice.exception.InvalidPitSelected;
import com.bol.interview.mancalaservice.model.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class validateSelectedPitIndexTest {

    GameValidation gameValidation;
    GameContext normalContext;
    GameContext wrongPitIndexContext;
    @BeforeEach
    void setUp() {
        gameValidation = new validateSelectedPitIndex();
        wrongPitIndexContext = GameContext.builder()
                .selectedPitIndexInGame(7)
                .build();

        normalContext = GameContext.builder()
                .selectedPitIndexInGame(1)
                .build();
    }

    @Test
    void validate() {
        assertThrows(InvalidPitSelected.class, () -> gameValidation.validate(wrongPitIndexContext));
        assertDoesNotThrow(() -> gameValidation.validate(normalContext));
    }
}
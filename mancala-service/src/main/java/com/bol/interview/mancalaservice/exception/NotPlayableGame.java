package com.bol.interview.mancalaservice.exception;

import com.bol.interview.common.exceptions.MancalaGameException;

public class NotPlayableGame extends MancalaGameException {

    public NotPlayableGame(String message) {
        super(message);
    }
}

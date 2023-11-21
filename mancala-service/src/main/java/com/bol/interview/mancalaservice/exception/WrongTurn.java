package com.bol.interview.mancalaservice.exception;

import com.bol.interview.common.exceptions.MancalaGameException;

public class WrongTurn extends MancalaGameException {

    public WrongTurn(String message) {
        super(message);
    }
}

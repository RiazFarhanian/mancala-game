package com.bol.interview.mancalaservice.exception;

import com.bol.interview.common.exceptions.MancalaGameException;

public class InvalidPitSelected extends MancalaGameException {

    public InvalidPitSelected(String message) {
        super(message);
    }
}

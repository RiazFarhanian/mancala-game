package com.bol.interview.mancalaservice.exception;

import com.bol.interview.common.exceptions.MancalaGameException;

public class PitIsEmpty extends MancalaGameException {

    public PitIsEmpty(String message) {
        super(message);
    }
}

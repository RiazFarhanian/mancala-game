package com.bol.interview.mancalaservice.exception;

import com.bol.interview.common.exceptions.MancalaGameException;

public class ResourceNotFound extends MancalaGameException {

    public ResourceNotFound(String message) {
        super(message);
    }
}

package com.bol.interview.common.exceptions;

public class MancalaGameException extends RuntimeException {
    public MancalaGameException(Throwable cause) {
        super(cause);
    }

    public MancalaGameException(String message) {
        super(message);
    }
}

package com.bol.interview.waitingroomservice.exceptions;

import com.bol.interview.common.exceptions.MancalaGameException;

public class UnableToJoinException extends MancalaGameException {

    public UnableToJoinException(Exception e) {
        super(e);
    }
}

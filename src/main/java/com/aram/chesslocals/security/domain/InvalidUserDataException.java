package com.aram.chesslocals.security.domain;

public abstract class InvalidUserDataException extends RuntimeException {

    public InvalidUserDataException(String message) {
        super(message);
    }

    public InvalidUserDataException(){}

}

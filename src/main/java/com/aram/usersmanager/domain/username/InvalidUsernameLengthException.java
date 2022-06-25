package com.aram.chesslocals.security.domain.username;

import com.aram.chesslocals.security.domain.InvalidUserDataException;

import static java.lang.String.format;

public final class InvalidUsernameLengthException extends InvalidUserDataException {

    InvalidUsernameLengthException(int minLength, int maxLength) {
        super(message(minLength, maxLength));
    }

    private static String message(int minLength, int maxLength) {
        return format("Invalid username length. Must be between %d and %d characters long",
                       minLength, maxLength);
    }

}
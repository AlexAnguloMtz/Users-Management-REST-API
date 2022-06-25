package com.aram.chesslocals.security.domain.email;

import com.aram.chesslocals.security.domain.InvalidUserDataException;

import static java.lang.String.format;

public final class InvalidEmailException extends InvalidUserDataException {

    InvalidEmailException() {
        super("Invalid email");
    }

}

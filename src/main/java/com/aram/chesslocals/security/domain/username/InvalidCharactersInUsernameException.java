package com.aram.chesslocals.security.domain.username;

import com.aram.chesslocals.security.domain.InvalidUserDataException;

final class InvalidCharactersInUsernameException extends InvalidUserDataException {

    private static final String MESSAGE = "Invalid username. Valid characters: a-z, A-Z, 0-9, underscore '_'";

    InvalidCharactersInUsernameException() {
        super(MESSAGE);
    }


}

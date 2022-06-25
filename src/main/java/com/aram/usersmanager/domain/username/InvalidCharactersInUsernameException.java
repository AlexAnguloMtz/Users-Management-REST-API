package com.aram.usersmanager.domain.username;

import com.aram.usersmanager.domain.InvalidUserDataException;

public final class InvalidCharactersInUsernameException extends InvalidUserDataException {

    private static final String MESSAGE = "Invalid username. Valid characters: a-z, A-Z, 0-9, underscore '_'";

    InvalidCharactersInUsernameException() {
        super(MESSAGE);
    }


}

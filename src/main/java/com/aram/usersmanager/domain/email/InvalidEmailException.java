package com.aram.usersmanager.domain.email;

import com.aram.usersmanager.domain.InvalidUserDataException;

import static java.lang.String.format;

public final class InvalidEmailException extends InvalidUserDataException {

    InvalidEmailException() {
        super("Invalid email");
    }

}

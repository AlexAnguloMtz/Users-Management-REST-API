package com.aram.usersmanager.domain.password;

import com.aram.usersmanager.domain.InvalidUserDataException;

public final class PasswordFormatException extends InvalidUserDataException {

    private static String MESSAGE = "Password must contain at least 8 characters, " +
            "1 lowercase letter, 1 uppercase letter, 1 number and 1 special character";
    PasswordFormatException() {
        super(MESSAGE);
    }
}

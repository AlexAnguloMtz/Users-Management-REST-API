package com.aram.chesslocals.security.service;

import static java.lang.String.format;

public class UsernameAlreadyExistsException extends RuntimeException {

    UsernameAlreadyExistsException(String username) {
        super(customMessage(username));
    }

    private static String customMessage(String username) {
        return format("User with username '%s' already exists", username);
    }

}

package com.aram.chesslocals.security.service;

public class UsernameDoesNotExistException extends RuntimeException {
    UsernameDoesNotExistException() {
        super("Username does not exist");
    }
}

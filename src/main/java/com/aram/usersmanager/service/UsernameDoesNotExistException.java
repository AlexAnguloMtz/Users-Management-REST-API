package com.aram.usersmanager.service;

public class UsernameDoesNotExistException extends RuntimeException {
    UsernameDoesNotExistException() {
        super("Username does not exist");
    }
}

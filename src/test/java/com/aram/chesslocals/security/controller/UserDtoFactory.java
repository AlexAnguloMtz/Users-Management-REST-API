package com.aram.chesslocals.security.controller;

import com.aram.chesslocals.security.service.dto.UserDto;

import java.util.concurrent.atomic.AtomicInteger;

import static com.aram.chesslocals.security.common.UserTestData.*;

public class UserDtoFactory {

    private AtomicInteger VALID_USER_COUNTER = new AtomicInteger();

    UserDto nextValidUserDto() {
        return new UserDto(nextAvailableUsername(), VALID_PASSWORD, VALID_EMAIL);
    }

    String nextAvailableUsername() {
        return VALID_USERNAME + VALID_USER_COUNTER.addAndGet(1);
    }

    public UserDto withUsername(String username) {
        return new UserDto(username, VALID_PASSWORD, VALID_EMAIL);
    }

    public UserDto withPassword(String password) {
        return new UserDto(VALID_USERNAME, password, VALID_EMAIL);
    }

    public UserDto withEmail(String email) {
        return new UserDto(VALID_USERNAME, VALID_PASSWORD, email);
    }
}

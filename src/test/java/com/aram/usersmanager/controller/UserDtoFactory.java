package com.aram.chesslocals.security.controller;

import com.aram.chesslocals.security.service.dto.UserDto;

import java.util.concurrent.atomic.AtomicInteger;

import static com.aram.chesslocals.security.common.UserTestData.*;

public class UserDtoFactory {


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

package com.aram.usersmanager.controller;

import com.aram.usersmanager.service.dto.UserDto;

import static com.aram.usersmanager.common.UserTestData.*;

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

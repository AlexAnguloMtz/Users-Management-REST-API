package com.aram.usersmanager.common;

import com.aram.usersmanager.domain.Role;
import com.aram.usersmanager.domain.User;
import com.aram.usersmanager.service.dto.UserDto;

public abstract class UserTestData {

    public static final String VALID_USERNAME = "Jack_Frost1000000";
    public static final String VALID_PASSWORD = "abAB99!%";
    public static final String VALID_EMAIL = "jack@gmail.com";
    public static final Role VALID_ROLE = Role.REGULAR;

    public static final User VALID_USER = new User(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ROLE);

    public static final UserDto VALID_USER_DTO = new UserDto(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL);

    public static final UserDto INVALID_USER_DTO = new UserDto(null, null, null);

}

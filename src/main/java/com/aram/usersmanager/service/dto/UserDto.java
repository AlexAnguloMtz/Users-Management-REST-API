package com.aram.usersmanager.service.dto;

import lombok.Data;

@Data
public class UserDto {
    private final String username;
    private final String password;
    private final String email;
}

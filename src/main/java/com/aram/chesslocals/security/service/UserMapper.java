package com.aram.chesslocals.security.service;

import com.aram.chesslocals.security.domain.Role;
import com.aram.chesslocals.security.domain.User;
import com.aram.chesslocals.security.service.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
final class UserMapper {

    User mapToRegularUser(UserDto userDto) {
        return userWithRole(userDto, Role.REGULAR);
    }

    User mapToAdmin(UserDto userDto) {
        return userWithRole(userDto, Role.ADMIN);
    }

    UserDto mapToDto(User user) {
        return new UserDto(user.getUsername(), user.getPassword(), user.getEmail());
    }

    private User userWithRole(UserDto userDto, Role role) {
        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .authority(role)
                .build();
    }
}

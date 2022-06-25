package com.aram.usersmanager.service;

import com.aram.usersmanager.domain.Role;
import com.aram.usersmanager.domain.User;
import com.aram.usersmanager.service.UserMapper;
import com.aram.usersmanager.service.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.aram.usersmanager.common.UserTestData.VALID_USER_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private UserDto userDto;

    @BeforeEach
    void setup() {
        this.userDto = VALID_USER_DTO;
    }

    @Test
    void givenUserDto_whenMappingToRegularUser_mappingShouldBeCorrect() {
        User user = new UserMapper().mapToRegularUser(userDto);

        //Verify that both usernames match
        assertEquals(user.getUsername(), userDto.getUsername());

        //Verify that both passwords match
        assertEquals(user.getPassword(), userDto.getPassword());

        //Verify that both emails match
        assertEquals(user.getEmail(), userDto.getEmail());

        //Verify that user is a regular user
        assertEquals(user.getAuthority(), Role.REGULAR);

    }

    @Test
    void givenUserDto_whenMappingToAdmin_mappingShouldBeCorrect() {
        User user = new UserMapper().mapToAdmin(userDto);

        //Verify that both usernames match
        assertEquals(user.getUsername(), userDto.getUsername());

        //Verify that both passwords match
        assertEquals(user.getPassword(), userDto.getPassword());

        //Verify that both emails match
        assertEquals(user.getEmail(), userDto.getEmail());

        //Verify that user is an admin
        assertEquals(user.getAuthority(), Role.ADMIN);

    }

}

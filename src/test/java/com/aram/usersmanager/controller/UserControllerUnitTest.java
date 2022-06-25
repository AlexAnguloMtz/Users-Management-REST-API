package com.aram.chesslocals.security.controller;

import com.aram.chesslocals.security.service.UserService;
import com.aram.chesslocals.security.service.UsernameDoesNotExistException;
import com.aram.chesslocals.security.service.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.aram.chesslocals.security.common.UserTestData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    UserController userController;

    private static UserDto userDto;

    @BeforeEach
    void setup() {
        userDto = VALID_USER_DTO;
    }

    @Test
    void givenValidUserData_whenSavingNewUser_thenShouldReturnCorrectResponseAfterSavingUserCorrectly() {
        // Mock the service
        when(userService.save(userDto)).thenReturn(userDto);

        // Get the Controller response
        ResponseEntity<UserDto> response = userController.save(userDto);

        // Assert we got HTTP 201 and assert we got the user back in the response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(userDto);
    }

    @Test
    void givenUserExists_whenReceivesGetUserByUsernameRequest_thenReturnsUserAndHttpStatusOk() {
        String username = userDto.getUsername();

        // Mock the service It should return the requested user by username
        when(userService.findUserDtoByUsername(username))
            .thenReturn(userDto);

        // Get the Controller response
        ResponseEntity<UserDto> response = userController.loadByUsername(username);

        // Assert we got HTTP 200 and assert we got the requested user
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDto);
    }

    @Test
    void givenUserDoesNotExist_whenReceivesGetUserByUsernameRequest_thenThrowsRelatedException() {
        String username = userDto.getUsername();

        // Mock the service. It should throw a related exception because username does not exist
        when(userService.findUserDtoByUsername(username))
                .thenThrow(UsernameDoesNotExistException.class);

        // Assert we throw related exception
        assertThrows(UsernameDoesNotExistException.class,
                () -> userController.loadByUsername(username));
    }

    @Test
    void whenReceivesDeleteUserRequest_thenDeletesUserAndReturnsHttpOk() {
        HttpStatus statusAfterDeleteUser = userController.delete(userDto.getUsername());
        assertThat(statusAfterDeleteUser).isEqualTo(HttpStatus.OK);
    }


}
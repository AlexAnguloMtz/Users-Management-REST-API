package com.aram.chesslocals.security.controller;

import com.aram.chesslocals.security.domain.email.InvalidEmailException;
import com.aram.chesslocals.security.domain.password.PasswordFormatException;
import com.aram.chesslocals.security.domain.username.InvalidUsernameLengthException;
import com.aram.chesslocals.security.service.UsernameAlreadyExistsException;
import com.aram.chesslocals.security.service.UsernameDoesNotExistException;
import com.aram.chesslocals.security.service.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode;
import static com.aram.chesslocals.security.common.UserTestData.VALID_USER_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Drop de database after each test, so that tests don't interfere with each other
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private UserDto validUserDto;

    private ObjectMapper objectMapper;

    private static final UserDtoFactory DTO_FACTORY = new UserDtoFactory();

    private static final String CREATE_USER_URL = "/api/v1/user/create";
    private static final String GET_USER_URL = "/api/v1/user/{username}";
    private static final String DELETE_USER_URL = "/api/v1/user/delete/{username}";


    @BeforeEach
    void init() {
        this.validUserDto = VALID_USER_DTO;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void givenValidUserData_whenCallingSaveUserEndpoint_thenReturnsUserDataAndHttpCreated() throws Exception {
        String validJson = json(validUserDto);
        doPost(CREATE_USER_URL, validJson)
                .andExpect(status().isCreated())
                .andExpect(content().json(validJson));
    }

    @Test
    public void givenInvalidUsername_whenCallingSaveUserEndpoint_thenReturnsHttpUnprocessableEntity() throws Exception {
        String invalidUsername = "###";
        UserDto dtoWithInvalidUsername = DTO_FACTORY.withUsername(invalidUsername);
        String jsonWithInvalidUsername = json(dtoWithInvalidUsername);

        doPost(CREATE_USER_URL, jsonWithInvalidUsername)
                 .andExpect(status().isUnprocessableEntity())
                 .andExpect(result -> assertExceptionClass(result, InvalidUsernameLengthException.class));

    }

    @Test
    public void givenInvalidPassword_whenCallingSaveUserEndpoint_thenReturnsHttpUnprocessableEntity() throws Exception {
        String invalidPassword = "!!!!";
        UserDto dtoWithInvalidPassword = DTO_FACTORY.withPassword(invalidPassword);
        String jsonWithInvalidPassword = json(dtoWithInvalidPassword);

        doPost(CREATE_USER_URL, jsonWithInvalidPassword)
                  .andExpect(status().isUnprocessableEntity())
                  .andExpect(result -> assertExceptionClass(result, PasswordFormatException.class));

    }

    @Test
    public void givenInvalidEmail_whenCallingSaveUserEndpoint_thenReturnsHttpUnprocessableEntity() throws Exception {
        String invalidEmail = "AABBCC";
        UserDto dtoWithInvalidEmail = DTO_FACTORY.withEmail(invalidEmail);
        String jsonWithInvalidEmail = json(dtoWithInvalidEmail);

        doPost(CREATE_USER_URL, jsonWithInvalidEmail)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertExceptionClass(result, InvalidEmailException.class));
    }

    @Test
    public void givenUsernameAlreadyExists_whenCallingSaveUserEndpoint_thenReturnsHttpConflict() throws Exception {
        UserDto userDto = validUserDto;

        String json = json(userDto);

        // Save user for the first time
        doPost(CREATE_USER_URL, json);

        // Attempt to save user with the same username for the second time.
        // Must throw an UsernameAlreadyExistsException and return Http 409 because username already exists
        doPost(CREATE_USER_URL, json)
                 .andExpect(status().isConflict())
                 .andExpect(result -> assertExceptionClass(result, UsernameAlreadyExistsException.class));
    }

    @Test
    public void givenGetUserByUsernameRequest_whenUserExists_thenReturnsResponseWithHttpOKAndRequestedUser() throws Exception {
        // Save the dummy user with a POST request
        String userJson = json(validUserDto);
        doPost(CREATE_USER_URL, userJson);

        // Assemble the URL to GET the dummy user by username
        String username = validUserDto.getUsername();
        String getUserByUsernameUrl = assembleGetUserByUsernameUrl(username);

        // Do a GET request to get the user by username.
        // Assert HTTP 200
        // Assert content type equals Json
        // Assert json in response matches our dummy user
        doGet(getUserByUsernameUrl)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(userJson));
    }

    @Test
    public void givenGetUserByUsernameRequest_whenUserDoesNotExist_thenReturnsHttp404AndRelatedException() throws Exception {
         // Assemble the URL to GET the dummy user by username
        UserDto user = validUserDto;
        String username = user.getUsername();
        String getUserByUsernameUrl = assembleGetUserByUsernameUrl(username);

        // Do a GET request to get the user by username.
        // This should fail because user does not exist yet in the repository.
        // Assert HTTP 404
        // Assert Exception class is related to this scenario
        doGet(getUserByUsernameUrl)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertExceptionClass(result, UsernameDoesNotExistException.class));
    }

    @Test
    public void givenUserExists_whenReceivesDeleteUserRequest_thenUserIsDeletedAndReturnsHttpOk() throws Exception {
        // First, save the user with a POST
        String userJson = json(validUserDto);
        doPost(CREATE_USER_URL, userJson);

        // Assemble delete user url
        String username = validUserDto.getUsername();
        String deleteUserUrl = assembleDeleteUrl(username);

        // Perform DELETE request
        // Assert http 200
        this.mockMvc.perform(delete(deleteUserUrl))
                .andExpect(status().isOk());

        // Attempt to GET the user. This must fail because the user should no longer exist
        // Assert Http 404
        // Assert related exception is thrown
        String getUserUrl = assembleGetUserByUsernameUrl(username);
        doGet(getUserUrl)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertExceptionClass(result, UsernameDoesNotExistException.class));
    }

    @Test
    public void givenUserDoesNotExist_whenReceivesDeleteUserRequest_thenReturnsHttpOk() throws Exception {
        // Assemble delete user url
        String username = validUserDto.getUsername();
        String deleteUserUrl = assembleDeleteUrl(username);

        // Perform DELETE request
        // Assert http 200
        this.mockMvc.perform(delete(deleteUserUrl))
                .andExpect(status().isOk());
    }

    private String assembleDeleteUrl(String username) {
        return DELETE_USER_URL.replace("{username}", username);
    }

    private ResultActions doGet(String url) throws Exception {
        return this.mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON));
    }


    private ResultActions doPost(String path, String json) throws Exception {
        return this.mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    private String json(UserDto userDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userDto);
    }

    private void assertExceptionClass(MvcResult result, Class<? extends RuntimeException> expected) {
        Exception exception = result.getResolvedException();
        Class<? extends Exception> actual = exception.getClass();
        assertEquals(expected, actual);
    }

    private String assembleGetUserByUsernameUrl(String username) {
        return GET_USER_URL.replace("{username}", username);
    }


}
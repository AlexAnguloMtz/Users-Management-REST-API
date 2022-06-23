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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.aram.chesslocals.security.common.UserTestData.VALID_USER_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private UserDto validUserDto;

    private ObjectMapper objectMapper;

    // We use a UserDtoFactory to create UserDtos with unique username and email
    // for those tests that need it. It needs to be a static field to guarantee data uniqueness.
    private static UserDtoFactory DTO_FACTORY = new UserDtoFactory();

    private static String CREATE_USER_URL = "/api/v1/user/create";
    private static final String GET_USER_URL = "/api/v1/user/{username}";


    @BeforeEach
    void init() {
        this.validUserDto = VALID_USER_DTO;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void givenValidUserData_whenCallingSaveUserEndpoint_thenReturnsUserDataAndHttpCreated() throws Exception {
        String validJson = json(nextValidUser());
        post(CREATE_USER_URL, validJson)
                .andExpect(status().isCreated())
                .andExpect(content().json(validJson));
    }

    @Test
    public void givenInvalidUsername_whenCallingSaveUserEndpoint_thenReturnsHttpUnprocessableEntity() throws Exception {
        String invalidUsername = "###";
        UserDto dtoWithInvalidUsername = DTO_FACTORY.withUsername(invalidUsername);
        String jsonWithInvalidUsername = json(dtoWithInvalidUsername);

        post(CREATE_USER_URL, jsonWithInvalidUsername)
                 .andExpect(status().isUnprocessableEntity())
                 .andExpect(result -> assertExceptionClass(result, InvalidUsernameLengthException.class));

    }

    @Test
    public void givenInvalidPassword_whenCallingSaveUserEndpoint_thenReturnsHttpUnprocessableEntity() throws Exception {
        String invalidPassword = "!!!!";
        UserDto dtoWithInvalidPassword = DTO_FACTORY.withPassword(invalidPassword);
        String jsonWithInvalidPassword = json(dtoWithInvalidPassword);

        post(CREATE_USER_URL, jsonWithInvalidPassword)
                  .andExpect(status().isUnprocessableEntity())
                  .andExpect(result -> assertExceptionClass(result, PasswordFormatException.class));

    }

    @Test
    public void givenInvalidEmail_whenCallingSaveUserEndpoint_thenReturnsHttpUnprocessableEntity() throws Exception {
        String invalidEmail = "AABBCC";
        UserDto dtoWithInvalidEmail = DTO_FACTORY.withEmail(invalidEmail);
        String jsonWithInvalidEmail = json(dtoWithInvalidEmail);

        post(CREATE_USER_URL, jsonWithInvalidEmail)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertExceptionClass(result, InvalidEmailException.class));
    }



    @Test
    public void givenUsernameAlreadyExists_whenCallingSaveUserEndpoint_thenReturnsHttpConflict() throws Exception {
        UserDto userDto = nextValidUser();

        String json = json(userDto);

        // Save user for the first time
        post(CREATE_USER_URL, json);

        // Attempt to save user with the same username for the second time.
        // Must throw an UsernameAlreadyExistsException and return Http 409 because username already exists
        post(CREATE_USER_URL, json)
                 .andExpect(status().isConflict())
                 .andExpect(result -> assertExceptionClass(result, UsernameAlreadyExistsException.class));
    }

    @Test
    public void givenGetUserByUsernameRequest_whenUserExists_thenReturnsResponseWithHttpOKAndRequestedUser() throws Exception {
        // Save the dummy user with a POST request
        String userJson = json(validUserDto);
        post(CREATE_USER_URL, userJson);

        // Assemble the URL to GET the dummy user by username
        String username = validUserDto.getUsername();
        String getUserByUsernameUrl = assembleGetUserByUsernameUrl(username);

        // Do a GET request to get the user by username.
        // Assert HTTP 200
        // Assert content type equals Json
        // Assert json in response matches our dummy user
        get(getUserByUsernameUrl)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(userJson));
    }

    @Test
    public void givenGetUserByUsernameRequest_whenUserDoesNotExist_thenReturnsHttp404AndRelatedException() throws Exception {
         // Assemble the URL to GET the dummy user by username
        String username = validUserDto.getUsername();
        String getUserByUsernameUrl = assembleGetUserByUsernameUrl(username);

        // Do a GET request to get the user by username.
        // This should fail because user does not exist yet in the repository.
        // Assert HTTP 404
        // Assert Exception class is related to this scenario
        get(getUserByUsernameUrl)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertExceptionClass(result, UsernameDoesNotExistException.class));
    }

    private ResultActions get(String url) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON));
    }


    private ResultActions post(String path, String json) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    private String json(UserDto userDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userDto);
    }

    private UserDto nextValidUser() {
        return DTO_FACTORY.nextValidUserDto();
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
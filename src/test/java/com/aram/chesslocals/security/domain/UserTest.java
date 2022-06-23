package com.aram.chesslocals.security.domain;

import com.aram.chesslocals.security.common.UserTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

final class UserTest extends UserTestData {

    @Test
    void givenNullUsername_whenCreatingUser_ThenThrowsException() {
        String nullUsername = null;
        assertThrows( InvalidUserDataException.class,
                () -> new User(nullUsername, VALID_PASSWORD, VALID_EMAIL, VALID_ROLE));
    }

    @Test
    void givenUsernameTooShort_whenCreatingUser_thenThrowsException() {
        String shortUsername = "Jack";
        assertThrows( InvalidUserDataException.class,
                      () -> new User(shortUsername, VALID_PASSWORD, VALID_EMAIL, VALID_ROLE));
    }

    @Test
    void givenUsernameTooLong_whenCreatingUser_thenThrowsException() {
        String longUsername = "JackFrostHasFrozenPowersAndCanFlyOverTheCity";
        assertThrows( InvalidUserDataException.class,
                () -> new User(longUsername, VALID_PASSWORD, VALID_EMAIL, VALID_ROLE));
    }

    @Test
    void givenUsernameWithInvalidCharacters_whenCreatingUser_thenThrowsException() {
        String usernameWithInvalidCharacters = "JackFrost!%$#";
        assertThrows( InvalidUserDataException.class,
                () -> new User(usernameWithInvalidCharacters, VALID_PASSWORD, VALID_EMAIL, VALID_ROLE));
    }

    @Test
    void givenNullEmail_whenCreatingUser_thenThrowsException() {
        String nullEmail = null;
        assertThrows( InvalidUserDataException.class,
                () -> new User(VALID_USERNAME, VALID_PASSWORD, nullEmail, VALID_ROLE));
    }

    @Test
    void givenInvalidEmail_whenCreatingUser_thenThrowsException() {
        String invalidEmail = "this_is_an_invalid_email";
        assertThrows( InvalidUserDataException.class,
                () -> new User(VALID_USERNAME, VALID_PASSWORD, invalidEmail, VALID_ROLE));
    }

    @Test
    void givenNullPassword_whenCreatingUser_thenThrowsException() {
        String nullPassword = null;
        assertThrows( InvalidUserDataException.class,
                () -> new User(VALID_USERNAME, nullPassword, VALID_EMAIL, VALID_ROLE));
    }

    @Test
    void givenTooShortPassword_whenCreatingUser_thenThrowsException() {
        String tooShortPassword = "123";
        assertThrows( InvalidUserDataException.class,
                () -> new User(VALID_USERNAME, tooShortPassword, VALID_EMAIL, VALID_ROLE));
    }

    @Test
    void givenPasswordWithoutLowerCaseLetters_whenCreatingUser_thenThrowsException() {
        String passwordWithoutLowerCaseLetters = "AAAAAAAA99!%";
        assertThrows( InvalidUserDataException.class,
                () -> new User(VALID_USERNAME, passwordWithoutLowerCaseLetters, VALID_EMAIL, VALID_ROLE));
    }

    @Test
    void givenPasswordWithoutUpperCaseLetters_whenCreatingUser_thenThrowsException() {
        String passwordWithoutUpperCaseLetters = "aaaaaaaa99!%";
        assertThrows( InvalidUserDataException.class,
                () -> new User(VALID_USERNAME, passwordWithoutUpperCaseLetters, VALID_EMAIL, VALID_ROLE));
    }

    @Test
    void givenPasswordWithoutNumbers_whenCreatingUser_thenThrowsException() {
        String passwordWithoutNumbers = "aaaaAAAA####";
        assertThrows( InvalidUserDataException.class,
                () -> new User(VALID_USERNAME, passwordWithoutNumbers, VALID_EMAIL, VALID_ROLE));
    }

    @Test
    void givenPasswordWithoutSpecialCharacters_whenCreatingUser_thenThrowsException() {
        String passwordWithoutSpecialCharacters = "aaaaAAAA9999";
        assertThrows( InvalidUserDataException.class,
                () -> new User(VALID_USERNAME, passwordWithoutSpecialCharacters, VALID_EMAIL, VALID_ROLE));
    }

    // When we attempt to create a user with a null role we throw a simple NullPointerException
    // because it is not a client error, the decision of assigning user roles
    // is done in the server. Thus, we throw a regular NullPointerException
    // because it must be indeed a programming error
    @Test
    void givenNullRole_whenCreatingUser_ThenThrowsException() {
        Role nullRole = null;
        assertThrows( NullPointerException.class,
                () -> new User(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, nullRole));
    }



}

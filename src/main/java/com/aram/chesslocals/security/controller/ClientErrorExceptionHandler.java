package com.aram.chesslocals.security.controller;

import com.aram.chesslocals.security.domain.InvalidUserDataException;
import com.aram.chesslocals.security.service.UsernameAlreadyExistsException;
import com.aram.chesslocals.security.service.UsernameDoesNotExistException;
import com.aram.chesslocals.security.service.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ClientErrorExceptionHandler implements CustomExceptionHandler {

    // When requested username does not exist we return HTTP 404 'NotFound'
    @ExceptionHandler(UsernameDoesNotExistException.class)
    ResponseEntity<ExceptionDto> handleUsernameDoesNotExist(UsernameDoesNotExistException exception) {
        return mapToResponseEntity(exception, HttpStatus.NOT_FOUND);
    }

    // When attempting to create a new user with a username that already exists we return HTTP 409 'CONFLICT'
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    ResponseEntity<ExceptionDto> handleUsernameAlreadyExists(UsernameAlreadyExistsException exception) {
        return mapToResponseEntity(exception, HttpStatus.CONFLICT);
    }

    // When we receive a request to save a new user with invalid data, we return HTTP 422 'UNPROCESSABLE_ENTITY'
    @ExceptionHandler(InvalidUserDataException.class)
    ResponseEntity<ExceptionDto> handleInvalidUserData(InvalidUserDataException exception) {
        return mapToResponseEntity(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}

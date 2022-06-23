package com.aram.chesslocals.security.controller;

import com.aram.chesslocals.security.service.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServerErrorExceptionHandler implements CustomExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    ResponseEntity<ExceptionDto> handleNullPointerException(NullPointerException theException) {
        var exception = new RuntimeException(http500().getReasonPhrase());
        return mapToResponseEntity(exception, http500());
    }

    HttpStatus http500() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}

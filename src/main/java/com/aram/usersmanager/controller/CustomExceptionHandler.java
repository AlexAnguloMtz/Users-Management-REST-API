package com.aram.chesslocals.security.controller;

import com.aram.chesslocals.security.service.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

interface CustomExceptionHandler {

    default ResponseEntity<ExceptionDto> mapToResponseEntity(RuntimeException exception, HttpStatus status) {
        var exceptionDto = new ExceptionDto(exception, status);
        return new ResponseEntity<>(exceptionDto, status);
    }

}

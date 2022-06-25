package com.aram.usersmanager.service.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.String.format;
@Getter
public class ExceptionDto {

    private final String message;
    private final String status;
    private final LocalDateTime time;

    public ExceptionDto(RuntimeException exception, HttpStatus status) {
        Objects.requireNonNull(exception, format("Null Exception in %s constructor", getClass().getName()));
        Objects.requireNonNull(status, format("Null HttpStatus in %s constructor", getClass().getName()));
        this.message = exception.getMessage();
        this.status = status.toString();
        this.time = LocalDateTime.now();
    }

}

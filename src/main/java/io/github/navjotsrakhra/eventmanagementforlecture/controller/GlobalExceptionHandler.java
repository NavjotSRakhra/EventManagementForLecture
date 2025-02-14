package io.github.navjotsrakhra.eventmanagementforlecture.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception e) {
        UUID errorUuid = UUID.randomUUID();
        log.error("Error encountered: {}, error UUID: {}", e.getMessage(), errorUuid, e);
        String errorUrl = UriComponentsBuilder
                .fromPath("/errors")
                .queryParam("errorUuid", errorUuid.toString()).encode()
                .queryParam("errorMessage", e.getMessage()).encode()
                .toUriString();
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .header("Location", errorUrl)
                .build();
    }
}

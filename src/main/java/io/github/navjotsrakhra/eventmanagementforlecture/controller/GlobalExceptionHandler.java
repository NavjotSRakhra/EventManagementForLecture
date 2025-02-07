package io.github.navjotsrakhra.eventmanagementforlecture.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception e) {
        UUID errorUuid = UUID.randomUUID();
        log.error("Error encountered: {}, error UUID: {}", e.getMessage(), errorUuid, e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error UUID: " + errorUuid + (e.getMessage() != null ? ", error message: " + e.getMessage() : ""));
    }
}

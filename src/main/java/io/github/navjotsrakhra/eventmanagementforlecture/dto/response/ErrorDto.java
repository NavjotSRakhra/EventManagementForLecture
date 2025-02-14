package io.github.navjotsrakhra.eventmanagementforlecture.dto.response;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public record ErrorDto(UUID errorUuid, HttpStatus status, String message) {
}

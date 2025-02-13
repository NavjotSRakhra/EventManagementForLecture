package io.github.navjotsrakhra.eventmanagementforlecture.dto.request;

import jakarta.validation.constraints.NotNull;

public record EventRequestDto(@NotNull String title, @NotNull String description) {

}

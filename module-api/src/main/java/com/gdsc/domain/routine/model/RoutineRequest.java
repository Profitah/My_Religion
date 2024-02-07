package com.gdsc.domain.routine.model;

import jakarta.validation.constraints.NotBlank;

public record RoutineRequest(
        @NotBlank
        String content
) {
}

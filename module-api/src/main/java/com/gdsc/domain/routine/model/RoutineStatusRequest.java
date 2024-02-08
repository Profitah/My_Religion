package com.gdsc.domain.routine.model;

import com.gdsc.domain.routine.entity.RoutineStatus;
import jakarta.validation.constraints.NotBlank;

public record RoutineStatusRequest(
        @NotBlank
        RoutineStatus routineStatus
) {
}

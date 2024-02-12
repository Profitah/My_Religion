package com.gdsc.domain.track.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record TrackRequest(
        @NotBlank
        String content,
        @NotBlank
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate,
        @NotBlank
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate
) {
}

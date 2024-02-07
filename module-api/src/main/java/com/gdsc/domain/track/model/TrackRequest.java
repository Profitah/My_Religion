package com.gdsc.domain.track.model;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record TrackRequest(
        @NotBlank
        String content
) {
}

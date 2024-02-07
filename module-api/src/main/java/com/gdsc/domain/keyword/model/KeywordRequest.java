package com.gdsc.domain.keyword.model;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record KeywordRequest(
        @NotBlank
        List<String> keywords
) {
}

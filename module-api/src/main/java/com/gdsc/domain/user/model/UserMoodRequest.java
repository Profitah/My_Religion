package com.gdsc.domain.user.model;

import com.gdsc.domain.user.entity.Mood;
import jakarta.validation.constraints.NotBlank;

public record UserMoodRequest (
    @NotBlank
    Mood mood
){
}

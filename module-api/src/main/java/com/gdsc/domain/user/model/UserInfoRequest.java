package com.gdsc.domain.user.model;


import com.gdsc.domain.user.entity.Gender;
import jakarta.validation.constraints.NotBlank;

public record UserInfoRequest(
        @NotBlank
        String nickname,
        @NotBlank
        Gender gender,
        @NotBlank
        Integer age
) {
}

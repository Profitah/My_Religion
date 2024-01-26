package com.gdsc.domain.user.model;


import com.gdsc.auth.entity.Gender;

public record UserInfoRequest(
        String nickname,
        Gender gender,
        Integer age
) {
}

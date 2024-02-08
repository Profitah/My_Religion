package com.gdsc.domain.user.model;

import com.gdsc.domain.user.entity.Gender;
import com.gdsc.domain.user.entity.Mood;
import com.gdsc.domain.user.entity.User;
import lombok.Builder;

@Builder
public record UserResponse (
        String nickname,
        Gender gender,
        Integer age,
        Mood mood) {
    public static UserResponse of(User user) {
        return UserResponse.builder()
                .nickname(user.getNickname())
                .gender(user.getGender())
                .age(user.getAge())
                .mood(user.getMood())
                .build();
    }
}

package com.gdsc.domain.user.model;

import com.gdsc.domain.user.entity.Mood;
import com.gdsc.domain.user.entity.User;
import lombok.Builder;

@Builder
public record UserMoodResponse (
        Mood mood) {
    public static UserMoodResponse of(User user) {
        return UserMoodResponse.builder()
                .mood(user.getMood())
                .build();
    }
}

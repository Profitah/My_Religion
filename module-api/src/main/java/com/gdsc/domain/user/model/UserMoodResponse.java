package com.gdsc.domain.user.model;

import com.gdsc.domain.user.entity.Mood;
import com.gdsc.domain.user.entity.User;
import lombok.Builder;

@Builder
public record UserMoodResponse (
        Mood mood,
        String imgUrl) {
    public static UserMoodResponse of(User user) {
        return UserMoodResponse.builder()
                .mood(user.getMood())
                .imgUrl(user.getMood().getImgUrl())
                .build();
    }
}

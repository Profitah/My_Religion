package com.gdsc.domain.user.model;

import com.gdsc.domain.user.entity.User;
import lombok.Builder;

@Builder
public record UserKeywordsResponse(
        String keyword1,
        String keyword2,
        String keyword3) {
    public static UserKeywordsResponse of(User user) {
        return UserKeywordsResponse.builder()
                .keyword1(user.getKeyword1())
                .keyword2(user.getKeyword2())
                .keyword3(user.getKeyword3())
                .build();
    }
}

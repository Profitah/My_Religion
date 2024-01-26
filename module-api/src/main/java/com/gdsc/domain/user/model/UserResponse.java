package com.gdsc.domain.user.model;

import com.gdsc.auth.entity.Gender;
import com.gdsc.auth.entity.User;
import lombok.Builder;

@Builder
public record UserResponse (
    String nickname,
    Gender gender,
    Integer age,
    String keyword1,
    String keyword2,
    String keyword3) {
        public static UserResponse of(User user) {
            return UserResponse.builder()
                    .nickname(user.getNickname())
                    .gender(user.getGender())
                    .age(user.getAge())
                    .keyword1(user.getKeyword1())
                    .keyword2(user.getKeyword2())
                    .keyword3(user.getKeyword3())
                    .build();
        }
}


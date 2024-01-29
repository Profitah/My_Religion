package com.gdsc.fixture;

import com.gdsc.domain.user.entity.Gender;
import com.gdsc.domain.user.entity.Role;
import com.gdsc.domain.user.entity.User;
import com.gdsc.domain.user.model.UserInfoRequest;
import com.gdsc.domain.user.model.UserKeywordsRequest;

public class DomainFixture {

    public static final User 유저1 = User.builder()
            .firebaseUid("firebaseUid1")
            .nickname("닉네임")
            .email("dofarming@gmail.com")
            .gender(Gender.MALE)
            .age(0)
            .role(Role.ROLE_USER)
            .build();

    public static final User 유저2 = User.builder()
            .firebaseUid("firebaseUid2")
            .nickname("닉네임")
            .email("dofarming@gmail.com")
            .gender(Gender.MALE)
            .age(0)
            .role(Role.ROLE_USER)
            .build();

    public static final UserInfoRequest 유저_정보_변경_요청1 = new UserInfoRequest("닉네임 변경", Gender.MALE, 20);
    public static final UserKeywordsRequest 유저_키워드_변경_요청1 = new UserKeywordsRequest("변경할 키워드1", "변경할 키워드2", "변경할 키워드3");

}


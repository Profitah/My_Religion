package com.gdsc.domain.user.service;

import com.gdsc.domain.user.entity.User;
import com.gdsc.domain.user.model.UserMoodRequest;
import com.gdsc.security.service.UserDetailService;
import com.gdsc.domain.user.model.UserInfoRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDetailService userDetailService;
    @Transactional
    public User updateUserInfo(UserInfoRequest userInfoRequest, User user) {
        User findUser = userDetailService.loadUserByUsername(user.getFirebaseUid());

        return findUser.updateUser(userInfoRequest.nickname(), userInfoRequest.gender(), userInfoRequest.age());
    }

    @Transactional
    public User updateUserMood(UserMoodRequest userMoodRequest, User user) {
        User findUser = userDetailService.loadUserByUsername(user.getFirebaseUid());

        return findUser.updateMood(userMoodRequest.mood());
    }
}

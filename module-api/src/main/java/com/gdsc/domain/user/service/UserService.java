package com.gdsc.domain.user.service;

import com.gdsc.auth.entity.User;
import com.gdsc.auth.service.UserDetailService;
import com.gdsc.domain.user.model.UserInfoRequest;

import com.gdsc.domain.user.model.UserKeywordsRequest;
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
    public User updateKeywords(UserKeywordsRequest userKeywordsRequest, User user){
        User findUser = userDetailService.loadUserByUsername(user.getFirebaseUid());
        return findUser.updateKeywords(userKeywordsRequest.keyword1(), userKeywordsRequest.keyword2(), userKeywordsRequest.keyword3());
    }
}

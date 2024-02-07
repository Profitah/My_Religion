package com.gdsc.domain.user.service;

import com.gdsc.domain.user.entity.User;
import com.gdsc.security.service.UserDetailService;
import com.gdsc.domain.user.model.UserRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDetailService userDetailService;
    @Transactional
    public User updateUserInfo(UserRequest userRequest, User user) {
        User findUser = userDetailService.loadUserByUsername(user.getFirebaseUid());

        return findUser.updateUser(userRequest.nickname(), userRequest.gender(), userRequest.age());
    }
}

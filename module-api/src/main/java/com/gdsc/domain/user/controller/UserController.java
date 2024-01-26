package com.gdsc.domain.user.controller;

import com.gdsc.auth.entity.User;
import com.gdsc.domain.user.model.*;
import com.gdsc.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PatchMapping("/user/info")
    public ResponseEntity<UserInfoResponse> updateUserInfo(@AuthenticationPrincipal User user,
                                                           @RequestBody UserInfoRequest userInfoRequest) {
        User updateUser = userService.updateUserInfo(userInfoRequest, user);

        return ResponseEntity.ok(UserInfoResponse.of(updateUser));
    }

    @PatchMapping("/user/keywords")
    public ResponseEntity<UserKeywordsResponse> updateUserKeywords(@AuthenticationPrincipal User user,
                                                               @RequestBody UserKeywordsRequest userKeywordsRequest) {
        User updateUser = userService.updateKeywords(userKeywordsRequest, user);
        log.info(userKeywordsRequest.keyword1());

        return ResponseEntity.ok(UserKeywordsResponse.of(updateUser));
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserResponse.of(user));
    }
}

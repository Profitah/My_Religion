package com.gdsc.domain.user.controller;

import com.gdsc.domain.user.entity.User;
import com.gdsc.domain.user.model.*;
import com.gdsc.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 정보", description = "사용자 정보 API")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/info")
    public ResponseEntity<UserInfoResponse> updateUserInfo(@AuthenticationPrincipal User user,
                                                           @RequestBody @Validated UserInfoRequest userInfoRequest) {
        User updateUser = userService.updateUserInfo(userInfoRequest, user);

        return ResponseEntity.ok(UserInfoResponse.of(updateUser));
    }

    @PatchMapping("/mood")
    public ResponseEntity<UserMoodResponse> updateUserMood(@AuthenticationPrincipal User user,
                                                           @RequestBody @Validated UserMoodRequest userMoodRequest) {
        User updateUser = userService.updateUserMood(userMoodRequest, user);

        return ResponseEntity.ok(UserMoodResponse.of(updateUser));
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserResponse.of(user));
    }
}

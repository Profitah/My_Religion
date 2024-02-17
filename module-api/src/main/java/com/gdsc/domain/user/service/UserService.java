package com.gdsc.domain.user.service;

import com.gdsc.domain.image.entity.Image;
import com.gdsc.domain.image.service.ImageService;
import com.gdsc.domain.user.entity.User;
import com.gdsc.domain.user.model.UserMoodRequest;
import com.gdsc.security.service.UserDetailService;
import com.gdsc.domain.user.model.UserInfoRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDetailService userDetailService;
    private final ImageService imageService;
    
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

    @Transactional
    public User updateUserImage(MultipartFile multipartFile, User user){
        User findUser = userDetailService.loadUserByUsername(user.getFirebaseUid());

        if(findUser.getImage() == null){
            return saveImage(multipartFile, findUser);
        }

        imageService.delete(findUser.getImage());

        return saveImage(multipartFile, findUser);
    }

    private User saveImage(MultipartFile multipartFile, User findUser) {
        Image image = imageService.upload(multipartFile);

        return findUser.updateImage(image);
    }
}

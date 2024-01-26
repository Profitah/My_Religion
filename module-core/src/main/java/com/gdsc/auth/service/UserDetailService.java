package com.gdsc.auth.service;

import com.gdsc.auth.entity.Gender;
import com.gdsc.auth.entity.Role;
import com.gdsc.auth.entity.User;
import com.gdsc.auth.repository.UserRepository;
import com.gdsc.common.exception.ApplicationErrorException;
import com.gdsc.common.exception.ApplicationErrorType;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String firebaseUid) throws UsernameNotFoundException {
        return userRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    @Transactional
    public User create(FirebaseToken firebaseToken, Role role) {
        User user = User.builder()
                .firebaseUid(firebaseToken.getUid())
                .email(firebaseToken.getEmail())
                .nickname("user" + UUID.randomUUID())
                .gender(Gender.MALE)
                .age(0)
                .role(role)
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public User updateByUsername(FirebaseToken firebaseToken) {
        User user = userRepository.findByFirebaseUid(firebaseToken.getUid())
                .orElseThrow(() -> new ApplicationErrorException(ApplicationErrorType.USER_NOT_FOUND, String.format("해당 유저(%s)를 찾을 수 없습니다.", firebaseToken.getEmail())));

        user.update(firebaseToken);

        return userRepository.save(user);
    }
}

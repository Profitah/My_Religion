package com.gdsc.common.security;


import com.gdsc.domain.user.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {
    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {

        User user = User.builder()
                .image(annotation.image())
                .nickname(annotation.username())
                .email(annotation.email())
                .gender(annotation.gender())
                .age(0)
                .mood(annotation.mood())
                .build();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, List.of(new SimpleGrantedAuthority(annotation.role())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
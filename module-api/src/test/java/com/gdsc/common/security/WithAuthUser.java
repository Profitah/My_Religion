package com.gdsc.common.security;

import com.gdsc.domain.user.entity.Gender;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory.class)
public @interface WithAuthUser {
    String username() default "username";
    String email() default "email";
    String picture() default "picture url";
    Gender gender() default Gender.MALE;
    String role() default "ROLE_USER";
}
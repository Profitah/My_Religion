package com.gdsc.common.security;

import com.gdsc.domain.image.entity.Image;
import com.gdsc.domain.user.entity.Gender;
import com.gdsc.domain.user.entity.Mood;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory.class)
public @interface WithAuthUser {
    String username() default "username";
    String email() default "email";
    Gender gender() default Gender.MALE;

    Mood mood() default Mood.CALM;
    String role() default "ROLE_USER";
}
package com.gdsc.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.domain.user.entity.Role;
import com.gdsc.domain.user.entity.User;
import com.gdsc.security.service.UserDetailService;
import com.gdsc.common.exception.ApplicationErrorException;
import com.gdsc.common.exception.ApplicationErrorType;
import com.gdsc.common.model.ErrorResponseDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class FirebaseTokenFilter extends OncePerRequestFilter {
    private final UserDetailService userDetailService;
    private final FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (isInvalidHeader(header)) {
            handleFirebaseError(response, ApplicationErrorType.INVALID_HEADER);
            return;
        }

        String token = header.substring(7);

        try {
            FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(token);
            User user = getUser(firebaseToken);

            authenticateUser(user);

        } catch (FirebaseAuthException e) {
            handleFirebaseError(response, ApplicationErrorType.INVALID_FIREBASE_TOKEN);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isInvalidHeader(String header) {
        return header == null || !header.startsWith("Bearer ");
    }

    private User getUser(FirebaseToken firebaseToken) {
        User user;
        try {
            user = userDetailService.updateByUsername(firebaseToken);
        } catch (ApplicationErrorException e) {
            user = userDetailService.create(firebaseToken, Role.ROLE_USER);
        }
        return user;
    }

    private void authenticateUser(User user) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleFirebaseError(HttpServletResponse response, ApplicationErrorType applicationErrorType) throws IOException {
        setErrorResponseHeader(HttpStatus.UNAUTHORIZED, response);
        setErrorResponseBody(response, applicationErrorType);
    }

    private void setErrorResponseHeader(HttpStatus status, HttpServletResponse response) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private void setErrorResponseBody(HttpServletResponse response, ApplicationErrorType applicationErrorType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(applicationErrorType.name(), applicationErrorType.getMessage());
        objectMapper.writeValue(response.getWriter(), errorResponseDto);
    }
}
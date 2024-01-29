package com.gdsc.security.service;

import com.gdsc.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record SecurityUserDetails(User user) implements UserDetails {
    public SecurityUserDetails(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getFirebaseUid();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

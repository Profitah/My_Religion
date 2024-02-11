package com.gdsc.domain.user.entity;

import com.google.common.base.Objects;
import com.google.firebase.auth.FirebaseToken;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String firebaseUid;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood", nullable = false)
    private Mood mood;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public void update(FirebaseToken token) {
        this.firebaseUid = token.getUid();
        this.email = token.getEmail();
        this.image = token.getPicture();
    }

    public User updateUser(String nickname, Gender gender, Integer age) {
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
        return this;
    }

    public User updateMood(Mood mood) {
        this.mood = mood;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUsername(){
        return firebaseUid;
    }

    @Builder
    public User(String firebaseUid, String email, String image, String nickname, Gender gender, Integer age, Mood mood, Role role) {
        this.firebaseUid = firebaseUid;
        this.email = email;
        this.image = image;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
        this.mood = mood;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(id, user.id)
                && Objects.equal(firebaseUid, user.firebaseUid)
                && role == user.role
                && Objects.equal(nickname, user.nickname)
                && Objects.equal(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, firebaseUid, nickname, role, email);
    }
}

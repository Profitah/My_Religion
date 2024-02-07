package com.gdsc.domain.track.entity;

import com.gdsc.domain.keyword.entity.Keyword;
import com.gdsc.domain.routine.entity.Routine;
import com.gdsc.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_num", nullable = false)
    private Long id;

    @Lob
    @Column(name = "track_content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Routine> routines;

    @Builder
    public Track(String content, User user, Keyword keyword) {
        this.content = content;
        this.user = user;
        this.keyword = keyword;
    }
}

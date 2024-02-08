package com.gdsc.domain.routine.entity;

import com.gdsc.domain.track.entity.Track;
import com.gdsc.domain.user.entity.Role;
import com.gdsc.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_num", nullable = false)
    private Long id;

    @Lob
    @Column(name = "routine_content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "routine_status", nullable = false)
    private RoutineStatus routineStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;

    @Builder
    public Routine(String content, RoutineStatus routineStatus, User user, Track track) {
        this.content = content;
        this.routineStatus = routineStatus;
        this.user = user;
        this.track = track;
    }

    public Routine updateStatus(RoutineStatus routineStatus){
        this.routineStatus = routineStatus;
        return this;
    }
}

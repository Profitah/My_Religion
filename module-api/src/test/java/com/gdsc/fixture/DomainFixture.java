package com.gdsc.fixture;

import com.gdsc.domain.keyword.entity.Keyword;
import com.gdsc.domain.keyword.model.KeywordRequest;
import com.gdsc.domain.routine.entity.Routine;
import com.gdsc.domain.routine.entity.RoutineStatus;
import com.gdsc.domain.routine.model.RoutineRequest;
import com.gdsc.domain.routine.model.RoutineStatusRequest;
import com.gdsc.domain.track.entity.Track;
import com.gdsc.domain.track.model.TrackRequest;
import com.gdsc.domain.user.entity.Gender;
import com.gdsc.domain.user.entity.Mood;
import com.gdsc.domain.user.entity.Role;
import com.gdsc.domain.user.entity.User;
import com.gdsc.domain.user.model.UserInfoRequest;
import com.gdsc.domain.user.model.UserMoodRequest;

import java.util.Arrays;
import java.util.List;

public class DomainFixture {

    public static final User 유저1 = User.builder()
            .firebaseUid("firebaseUid1")
            .nickname("닉네임")
            .email("dofarming@gmail.com")
            .image("image")
            .gender(Gender.MALE)
            .age(0)
            .mood(Mood.CALM)
            .role(Role.ROLE_USER)
            .build();

    public static final User 유저2 = User.builder()
            .firebaseUid("firebaseUid2")
            .nickname("닉네임")
            .email("dofarming@gmail.com")
            .image("image")
            .gender(Gender.MALE)
            .age(0)
            .mood(Mood.CALM)
            .role(Role.ROLE_USER)
            .build();

    public static final Keyword 키워드1 = Keyword.builder()
            .content("키워드1")
            .user(유저1)
            .build();

    public static final Keyword 키워드2 = Keyword.builder()
            .content("키워드2")
            .user(유저1)
            .build();

    public static final Keyword 키워드3 = Keyword.builder()
            .content("키워드3")
            .user(유저1)
            .build();

    public static final Track 트랙1 = Track.builder()
            .content("내용1")
            .user(유저1)
            .build();

    public static final Track 트랙2 = Track.builder()
            .content("내용2")
            .user(유저1)
            .build();

    public static final Track 트랙3 = Track.builder()
            .content("내용3")
            .user(유저1)
            .build();

    public static final Track 트랙4 = Track.builder()
            .content("내용1")
            .user(유저2)
            .keyword(키워드1)
            .build();

    public static final Track 트랙5 = Track.builder()
            .content("내용2")
            .user(유저2)
            .keyword(키워드1)
            .build();

    public static final Track 트랙6 = Track.builder()
            .content("내용3")
            .user(유저2)
            .keyword(키워드1)
            .build();

    public static final Routine 루틴1 = Routine.builder()
            .content("내용1")
            .routineStatus(RoutineStatus.PROCEEDING)
            .user(유저1)
            .track(트랙1)
            .build();

    public static final Routine 루틴2 = Routine.builder()
            .content("내용2")
            .routineStatus(RoutineStatus.PROCEEDING)
            .user(유저1)
            .track(트랙1)
            .build();

    public static final Routine 루틴3 = Routine.builder()
            .content("내용3")
            .routineStatus(RoutineStatus.PROCEEDING)
            .user(유저1)
            .track(트랙1)
            .build();

    public static final Routine 루틴4 = Routine.builder()
            .content("내용1")
            .routineStatus(RoutineStatus.PROCEEDING)
            .user(유저2)
            .track(트랙2)
            .build();

    public static final Routine 루틴5 = Routine.builder()
            .content("내용2")
            .routineStatus(RoutineStatus.PROCEEDING)
            .user(유저2)
            .track(트랙2)
            .build();

    public static final Routine 루틴6 = Routine.builder()
            .content("내용3")
            .routineStatus(RoutineStatus.PROCEEDING)
            .user(유저2)
            .track(트랙2)
            .build();

    public static final Routine 완료_루틴 = Routine.builder()
            .content("내용1")
            .routineStatus(RoutineStatus.COMPLETE)
            .user(유저1)
            .track(트랙1)
            .build();

    public static final List<Keyword> 키워드_리스트 = Arrays.asList(키워드1, 키워드2, 키워드3);

    public static final List<Track> 트랙_리스트 = Arrays.asList(트랙1, 트랙2, 트랙3);

    public static final List<Track> 트랙_초기_리스트 = Arrays.asList(트랙4, 트랙5, 트랙6);

    public static final List<Routine> 루틴_리스트 = Arrays.asList(루틴1, 루틴2, 루틴3);

    public static final List<Routine> 루틴_초기_리스트 = Arrays.asList(루틴4, 루틴5, 루틴6);

    public static final UserInfoRequest 유저_정보_변경_요청1 = new UserInfoRequest("닉네임 변경", Gender.MALE, 20);
    public static final UserMoodRequest 유저_기분_변경_요청1 = new UserMoodRequest(Mood.HAPPY);

    public static final KeywordRequest 유저_키워드_저장_요청1 = new KeywordRequest(List.of("키워드1", "키워드2", "키워드3"));

    public static final TrackRequest 유저_트랙_저장_요청1 = new TrackRequest("내용");
    public static final RoutineRequest 유저_루틴_저장_요청1 = new RoutineRequest("내용");
    public static final RoutineStatusRequest 유저_루틴_상태_변경_요청1 = new RoutineStatusRequest(RoutineStatus.COMPLETE);
}


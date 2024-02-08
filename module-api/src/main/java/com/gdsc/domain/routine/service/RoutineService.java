package com.gdsc.domain.routine.service;

import com.gdsc.common.exception.ApplicationErrorException;
import com.gdsc.common.exception.ApplicationErrorType;
import com.gdsc.domain.routine.entity.Routine;
import com.gdsc.domain.routine.entity.RoutineStatus;
import com.gdsc.domain.routine.model.RoutineRequest;
import com.gdsc.domain.routine.model.RoutineStatusRequest;
import com.gdsc.domain.routine.repo.RoutineRepository;
import com.gdsc.domain.track.entity.Track;
import com.gdsc.domain.track.service.TrackService;
import com.gdsc.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gdsc.common.exception.ApplicationErrorType.ROUTINE_NOT_FOUND;
import static com.gdsc.common.exception.ApplicationErrorType.TRACK_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final TrackService trackService;

    @Transactional
    public Routine save(RoutineRequest routineRequest, Long trackId, User user){
        Track track = trackService.findById(trackId);

        Routine routine = Routine.builder()
                .content(routineRequest.content())
                .routineStatus(RoutineStatus.PROCEEDING)
                .track(track)
                .user(user)
                .build();

        return routineRepository.save(routine);
    }

    @Transactional
    public Routine updateStatus(RoutineStatusRequest routineStatusRequest, Long routineId, User user){
        Routine routine = findById(routineId);

        if (isNotSameWriter(routine, user)) {
            throw new ApplicationErrorException(ApplicationErrorType.NO_AUTHENTICATION, String.format("해당 루틴(%s)에 접근 권한이 없습니다.", routineId));
        }

        return routine.updateStatus(routineStatusRequest.routineStatus());
    }

    @Transactional
    public void delete(Long routineId, User user){
        Routine routine = findById(routineId);

        if (isNotSameWriter(routine, user)) {
            throw new ApplicationErrorException(ApplicationErrorType.NO_AUTHENTICATION, String.format("해당 루틴(%s)에 접근 권한이 없습니다.", routineId));
        }

        routineRepository.delete(routine);
    }

    public Routine findById(Long routineId) {
        return routineRepository.findById(routineId).orElseThrow(() -> new ApplicationErrorException(ROUTINE_NOT_FOUND, "해당 루틴을 찾을 수 없습니다."));
    }

    public List<Routine> findByTrack(Long trackId){
        Track track = trackService.findById(trackId);

        return routineRepository.findByTrack(track).orElseThrow(() -> new ApplicationErrorException(ROUTINE_NOT_FOUND, "해당 루틴을 찾을 수 없습니다."));
    }

    private boolean isNotSameWriter(Routine routine, User user) {
        return !routine.getUser().equals(user);
    }
}

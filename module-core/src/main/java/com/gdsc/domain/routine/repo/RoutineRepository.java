package com.gdsc.domain.routine.repo;

import com.gdsc.domain.routine.entity.Routine;
import com.gdsc.domain.track.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

    Optional<List<Routine>> findByTrack(Track track);
}

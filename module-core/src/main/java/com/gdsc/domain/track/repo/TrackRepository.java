package com.gdsc.domain.track.repo;

import com.gdsc.domain.keyword.entity.Keyword;
import com.gdsc.domain.track.entity.Track;
import com.gdsc.domain.track.entity.TrackStatus;
import com.gdsc.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, Long> {

    Optional<List<Track>> findByKeyword(Keyword keyword);

    Optional<List<Track>> findByUser(User user);

    void deleteByUser(User user);

    Optional<List<Track>> findByTrackStatus(TrackStatus trackStatus);
}

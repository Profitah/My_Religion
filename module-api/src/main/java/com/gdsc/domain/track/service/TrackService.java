package com.gdsc.domain.track.service;

import com.gdsc.common.exception.ApplicationErrorException;
import com.gdsc.common.exception.ApplicationErrorType;
import com.gdsc.domain.keyword.entity.Keyword;
import com.gdsc.domain.keyword.service.KeywordService;
import com.gdsc.domain.track.entity.Track;
import com.gdsc.domain.track.entity.TrackStatus;
import com.gdsc.domain.track.model.TrackRequest;
import com.gdsc.domain.track.repo.TrackRepository;
import com.gdsc.domain.user.entity.User;
import com.gdsc.security.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.gdsc.common.exception.ApplicationErrorType.TRACK_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final KeywordService keywordService;

    @Transactional
    public Track save(TrackRequest trackRequest, User user){
        Track track = Track.builder()
                .content(trackRequest.content())
                .startDate(trackRequest.startDate())
                .endDate(trackRequest.endDate())
                .trackStatus(TrackStatus.PROCEEDING)
                .user(user)
                .build();

        return trackRepository.save(track);
    }

    @Transactional
    public void delete(Long trackId, User user){
        Track track = findById(trackId);

        if (isNotSameWriter(track, user)) {
            throw new ApplicationErrorException(ApplicationErrorType.NO_AUTHENTICATION, String.format("해당 트랙(%s)에 접근 권한이 없습니다.", trackId));
        }

        trackRepository.delete(track);
    }

    @Transactional
    public void deleteAll(User user){
        trackRepository.deleteByUser(user);
    }

    public Track findById(Long trackId) {
        return trackRepository.findById(trackId).orElseThrow(() -> new ApplicationErrorException(TRACK_NOT_FOUND, "해당 트랙을 찾을 수 없습니다."));
    }

    public List<Track> findByUser(User user){
        return trackRepository.findByUser(user).orElseThrow(() -> new ApplicationErrorException(TRACK_NOT_FOUND, "해당 트랙을 찾을 수 없습니다."));
    }

    public List<Track> findByKeyword(String content){
        Keyword keyword = keywordService.findByContent(content);

        return trackRepository.findByKeyword(keyword)
                .orElseThrow(() -> new ApplicationErrorException(TRACK_NOT_FOUND, "해당 트랙을 찾을 수 없습니다."));
    }

    public List<Track> findByTrackStatus(TrackStatus trackStatus){
        return trackRepository.findByTrackStatus(trackStatus).orElseThrow(() -> new ApplicationErrorException(TRACK_NOT_FOUND, "해당 트랙을 찾을 수 없습니다."));
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void updateTrackStatusByEndDate(){
        List<Track> tracks = findByTrackStatus(TrackStatus.PROCEEDING);

        LocalDate currentDate = LocalDate.now();

        List<Track> expiredTracks = tracks.stream()
                .filter(track -> currentDate.isAfter(track.getEndDate()))
                .toList();

        for(Track track:expiredTracks){
            track.updateTrackStatus(TrackStatus.EXPIRATION);
        }
    }

    private boolean isNotSameWriter(Track track, User user) {
        return !track.getUser().equals(user);
    }
}

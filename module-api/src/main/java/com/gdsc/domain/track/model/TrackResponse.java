package com.gdsc.domain.track.model;

import com.gdsc.domain.track.entity.Track;
import com.gdsc.domain.track.entity.TrackStatus;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public record TrackResponse(
        Long trackId,
        String content,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate,
        TrackStatus trackStatus
) {
    public static TrackResponse of(Track track) {
        return TrackResponse.builder()
                .trackId(track.getId())
                .content(track.getContent())
                .startDate(track.getStartDate())
                .endDate(track.getEndDate())
                .trackStatus(track.getTrackStatus())
                .build();
    }
}

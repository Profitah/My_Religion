package com.gdsc.domain.track.model;

import com.gdsc.domain.track.entity.Track;
import lombok.Builder;

@Builder
public record TrackResponse(
        Long trackId,
        String content
) {
    public static TrackResponse of(Track track) {
        return TrackResponse.builder()
                .trackId(track.getId())
                .content(track.getContent())
                .build();
    }
}

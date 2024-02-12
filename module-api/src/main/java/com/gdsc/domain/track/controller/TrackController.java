package com.gdsc.domain.track.controller;

import com.gdsc.domain.track.entity.Track;
import com.gdsc.domain.track.model.TrackRequest;
import com.gdsc.domain.track.model.TrackResponse;
import com.gdsc.domain.track.service.TrackService;
import com.gdsc.domain.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "트랙", description = "트랙 API")
@RestController
@RequestMapping("/api/v1/track")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @GetMapping("/init/{keyword}")
    public ResponseEntity<List<TrackResponse>> findInitTrackByKeyword(@PathVariable(value = "keyword") String keyword){
        List<Track> initTracks = trackService.findByKeyword(keyword);

        List<TrackResponse> trackResponses = getTrackResponses(initTracks);

        return ResponseEntity.ok(trackResponses);
    }

    @GetMapping
    public ResponseEntity<List<TrackResponse>> findUserTrack(@AuthenticationPrincipal User user){
        List<Track> tracks = trackService.findByUser(user);

        List<TrackResponse> trackResponses = getTrackResponses(tracks);

        return ResponseEntity.ok(trackResponses);
    }

    @PostMapping
    public ResponseEntity<TrackResponse> saveTrack(@AuthenticationPrincipal User user,
                                                         @RequestBody @Validated TrackRequest trackRequest){
        Track track = trackService.save(trackRequest, user);

        return ResponseEntity.ok(TrackResponse.of(track));
    }

    @DeleteMapping("/{trackId}")
    public ResponseEntity<Void> deleteTrack(@AuthenticationPrincipal User user,
                                            @PathVariable(value = "trackId") Long trackId){
        trackService.delete(trackId, user);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTrack(@AuthenticationPrincipal User user){
        trackService.deleteAll(user);

        return ResponseEntity.noContent().build();
    }

    private static List<TrackResponse> getTrackResponses(List<Track> tracks) {
        return tracks.stream()
                .map(TrackResponse::of)
                .toList();
    }
}

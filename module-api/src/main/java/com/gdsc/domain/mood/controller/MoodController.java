package com.gdsc.domain.mood.controller;

import com.gdsc.domain.mood.service.MoodService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "기분", description = "기분 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MoodController {
    private final MoodService moodService;

    @GetMapping("/moods")
    public ResponseEntity<Map<String, String>> findAll() {
        Map<String, String> moods = moodService.findAll();

        return ResponseEntity.ok(moods);
    }
}

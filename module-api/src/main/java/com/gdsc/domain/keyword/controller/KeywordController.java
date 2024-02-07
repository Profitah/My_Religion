package com.gdsc.domain.keyword.controller;

import com.gdsc.domain.keyword.entity.Keyword;
import com.gdsc.domain.keyword.model.KeywordRequest;
import com.gdsc.domain.keyword.model.KeywordResponse;
import com.gdsc.domain.keyword.service.KeywordService;
import com.gdsc.domain.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "키워드", description = "키워드 API")
@RestController
@RequestMapping("/api/v1/keyword")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping("/init")
    public ResponseEntity<KeywordResponse> findInitKeywords(){
        List<Keyword> initKeywords = keywordService.findInitKeywords();

        return ResponseEntity.ok(KeywordResponse.of(initKeywords));
    }

    @GetMapping
    public ResponseEntity<KeywordResponse> findUserKeywords(@AuthenticationPrincipal User user){
        List<Keyword> keywords = keywordService.findByUser(user);

        return ResponseEntity.ok(KeywordResponse.of(keywords));
    }

    @PostMapping
    public ResponseEntity<KeywordResponse> saveUserKeywords(@AuthenticationPrincipal User user,
                                                              @RequestBody @Validated KeywordRequest KeywordRequest) {
        List<Keyword> keywords = keywordService.save(KeywordRequest, user);

        return ResponseEntity.ok(KeywordResponse.of(keywords));
    }
}

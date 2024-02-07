package com.gdsc.domain.keyword.service;

import com.gdsc.common.exception.ApplicationErrorException;
import com.gdsc.domain.keyword.entity.Keyword;
import com.gdsc.domain.keyword.model.KeywordRequest;
import com.gdsc.domain.keyword.repo.KeywordRepository;
import com.gdsc.domain.user.entity.User;
import com.gdsc.security.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gdsc.common.exception.ApplicationErrorType.KEYWORD_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final UserDetailService userDetailService;

    @Transactional
    public List<Keyword> save(KeywordRequest keywordRequest, User user){
        List<Keyword> keywords = keywordRequest.keywords().stream()
                .map(keyword -> Keyword.builder().content(keyword).user(user).build())
                .toList();

        return keywordRepository.saveAll(keywords);
    }

    public List<Keyword> findByUser(User user){
        return keywordRepository.findByUser(user)
                .orElseThrow(() -> new ApplicationErrorException(KEYWORD_NOT_FOUND, "해당 키워드를 찾을 수 없습니다."));
    }

    public List<Keyword> findInitKeywords(){
        User user = userDetailService.loadUserByUsername("admin");

        return keywordRepository.findByUser(user)
                .orElseThrow(() -> new ApplicationErrorException(KEYWORD_NOT_FOUND, "해당 키워드를 찾을 수 없습니다."));
    }

    public Keyword findByContent(String keywordContent) {
        return keywordRepository.findByContent(keywordContent).orElseThrow(() -> new ApplicationErrorException(KEYWORD_NOT_FOUND, "해당 키워드를 찾을 수 없습니다."));
    }
}

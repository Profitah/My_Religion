package com.gdsc.domain.keyword.model;

import com.gdsc.domain.keyword.entity.Keyword;
import lombok.Builder;

import java.util.List;

@Builder
public record KeywordResponse(
        List<String> keywords) {
    public static KeywordResponse of(List<Keyword> keywords) {
        List<String> stringKeywords = keywords.stream()
                .map(Keyword::getContent)
                .toList();

        return new KeywordResponse(stringKeywords);
    }
}

package com.gdsc.domain.mood.service;

import com.gdsc.domain.user.entity.Mood;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MoodService {
    public Map<String, String> findAll() {
        return Arrays.stream(Mood.values())
                .sorted(Comparator.comparing(Mood::getCode))
                .collect(Collectors.toMap(Mood::name, Mood::getImgUrl, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}

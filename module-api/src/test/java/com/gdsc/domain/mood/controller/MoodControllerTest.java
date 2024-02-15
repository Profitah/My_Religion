package com.gdsc.domain.mood.controller;

import com.gdsc.docs.util.RestDocsTest;
import com.gdsc.domain.mood.service.MoodService;
import com.gdsc.domain.user.entity.Mood;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;
import java.util.stream.Collectors;

import static com.gdsc.docs.util.ApiDocumentUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MoodController 테스트")
@ContextConfiguration(classes = MoodController.class)
@WebMvcTest(value = MoodController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class MoodControllerTest extends RestDocsTest {
    @MockBean
    MoodService moodService;

    @Test
    @DisplayName("기분 이미지 조회")
    void find_all_moods() throws Exception {
        //given
        List<Mood> moods = Arrays.asList(Mood.values());

        Map<String, String> moodList = moods.stream()
                .sorted(Comparator.comparing(Mood::getCode))
                .collect(Collectors.toMap(Mood::name, Mood::getImgUrl, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        given(moodService.findAll()).willReturn(moodList);
        //when & from
        mvc.perform(get("/api/v1/moods")
                        .header("Authorization", "Bearer FirebaseToken"))
                .andExpect(status().isOk())
                .andDo(document("moods-list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader()));
    }
}

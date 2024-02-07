package com.gdsc.domain.keyword.controller;


import com.gdsc.domain.keyword.model.KeywordRequest;
import com.gdsc.domain.keyword.service.KeywordService;
import com.gdsc.domain.user.entity.User;
import com.gdsc.common.security.WithAuthUser;
import com.gdsc.docs.util.RestDocsTest;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.ContextConfiguration;

import static com.gdsc.docs.util.ApiDocumentUtils.*;
import static com.gdsc.fixture.DomainFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("KeywordController 테스트")
@ContextConfiguration(classes = KeywordController.class)
@WebMvcTest(value = KeywordController.class)
class KeywordControllerTest extends RestDocsTest {
    @MockBean
    private KeywordService keywordService;

    private static final String REQUEST_URL = "/api/v1/keyword";

    @Test
    @DisplayName("사용자의 키워드를 저장한다.")
    @WithAuthUser
    void save_user_keywords() throws Exception {
        //given
        given(keywordService.save(any(KeywordRequest.class), any(User.class))).willReturn(키워드_리스트);
        //when & then
        mvc.perform(post(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson(유저_키워드_저장_요청1))
                        .header("Authorization", "Bearer FirebaseToken"))
                .andExpect(status().isOk())
                .andDo(document("user-keywords-save",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        requestFields(
                                fieldWithPath("keywords").type(JsonFieldType.ARRAY).description("키워드 리스트")
                        ),
                        responseFieldsByUserKeywords()));
    }

    @Test
    @DisplayName("사용자의 키워드를 조회한다.")
    @WithAuthUser
    void get_keywords() throws Exception {
        //given
        given(keywordService.findByUser(any(User.class))).willReturn(키워드_리스트);
        //when & then
        mvc.perform(get(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer FirebaseToken"))
                .andDo(document("get-keywords",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        responseFieldsByUserKeywords()));
    }

    @Test
    @DisplayName("초기 키워드를 조회한다.")
    @WithAuthUser
    void get_init_keywords() throws Exception {
        //given
        given(keywordService.findInitKeywords()).willReturn(키워드_리스트);
        //when & then
        mvc.perform(get(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer FirebaseToken"))
                .andDo(document("get-init-keywords",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        responseFieldsByUserKeywords()));
    }

    private static ResponseFieldsSnippet responseFieldsByUserKeywords() {
        return responseFields(
                fieldWithPath("keywords").type(JsonFieldType.ARRAY).description("키워드 리스트")
        );
    }

}

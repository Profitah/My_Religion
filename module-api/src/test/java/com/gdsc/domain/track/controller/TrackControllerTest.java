package com.gdsc.domain.track.controller;

import com.gdsc.domain.track.model.TrackRequest;
import com.gdsc.domain.track.service.TrackService;
import com.gdsc.domain.user.entity.User;
import com.gdsc.common.security.WithAuthUser;
import com.gdsc.docs.util.RestDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("TrackController 테스트")
@ContextConfiguration(classes = TrackController.class)
@WebMvcTest(value = TrackController.class)
class TrackControllerTest extends RestDocsTest {
    @MockBean
    private TrackService trackService;

    private static final String REQUEST_URL = "/api/v1/track";

    @Test
    @DisplayName("사용자의 트랙를 저장한다.")
    @WithAuthUser
    void save_user_track() throws Exception {
        //given
        given(trackService.save(any(TrackRequest.class), any(User.class))).willReturn(트랙1);
        //when & then
        mvc.perform(post(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson(유저_트랙_저장_요청1))
                        .header("Authorization", "Bearer FirebaseToken"))
                .andExpect(status().isOk())
                .andDo(document("user-track-save",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader()));
    }

    @Test
    @DisplayName("사용자의 트랙을 조회한다.")
    @WithAuthUser
    void get_tracks() throws Exception {
        //given
        given(trackService.findByUser(any(User.class))).willReturn(트랙_리스트);
        //when & then
        mvc.perform(get(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer FirebaseToken"))
                .andDo(document("get-tracks",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader()));
    }

    @Test
    @DisplayName("초기 트랙을 조회한다.")
    @WithAuthUser
    void get_init_tracks() throws Exception {
        //given
        given(trackService.findByKeyword("키워드1")).willReturn(트랙_초기_리스트);
        //when & then
        mvc.perform(get(REQUEST_URL + "/{keyword}", "키워드1")
                        .queryParam("keyword", "키워드")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer FirebaseToken"))
                .andDo(document("get-init-tracks",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        pathParameters(
                                parameterWithName("keyword").description("키워드"))));
    }

    @DisplayName("트랙 삭제")
    @Test
    void delete_track() throws Exception{
        //when & then
        mvc.perform(delete(REQUEST_URL + "/{trackId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer FirebaseToken"))
                .andExpect(status().isNoContent())
                .andDo(document("delete-track",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        pathParameters(
                                parameterWithName("trackId").description("트랙 id"))));
    }

    @DisplayName("사용자의 모든 트랙 삭제")
    @Test
    void delete_all_track() throws Exception{
        //when & then
        mvc.perform(delete(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer FirebaseToken"))
                .andExpect(status().isNoContent())
                .andDo(document("delete-all-track",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader()));
    }

    private static ResponseFieldsSnippet responseFieldsByUserTrack() {
        return responseFields(
                fieldWithPath("trackId").type(JsonFieldType.NUMBER).description("트랙 id"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("트랙 내용"),
                fieldWithPath("startDate").type(JsonFieldType.STRING).description("시작 날짜"),
                fieldWithPath("endDate").type(JsonFieldType.STRING).description("종료 날짜"),
                fieldWithPath("trackStatus").type(JsonFieldType.STRING).description("트랙 상태")
        );
    }

    private static ResponseFieldsSnippet responseFieldsByUserTrackList() {
        return responseFields(
                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("트랙 리스트"),
                fieldWithPath("[].trackId").type(JsonFieldType.NUMBER).description("트랙 id"),
                fieldWithPath("[].content").type(JsonFieldType.STRING).description("트랙 내용"),
                fieldWithPath("[].startDate").type(JsonFieldType.STRING).description("시작 날짜"),
                fieldWithPath("[].endDate").type(JsonFieldType.STRING).description("종료 날짜"),
                fieldWithPath("[].trackStatus").type(JsonFieldType.STRING).description("트랙 상태")
        );
    }

}

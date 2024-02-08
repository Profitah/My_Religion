package com.gdsc.domain.routine.controller;

import com.gdsc.domain.keyword.controller.KeywordController;
import com.gdsc.domain.keyword.model.KeywordRequest;
import com.gdsc.domain.keyword.service.KeywordService;
import com.gdsc.domain.routine.entity.Routine;
import com.gdsc.domain.routine.model.RoutineRequest;
import com.gdsc.domain.routine.model.RoutineStatusRequest;
import com.gdsc.domain.routine.service.RoutineService;
import com.gdsc.domain.track.controller.TrackController;
import com.gdsc.domain.track.model.TrackRequest;
import com.gdsc.domain.track.service.TrackService;
import com.gdsc.domain.user.entity.User;
import com.gdsc.common.security.WithAuthUser;
import com.gdsc.docs.util.RestDocsTest;
import com.gdsc.domain.user.model.UserMoodRequest;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("RoutineController 테스트")
@ContextConfiguration(classes = RoutineController.class)
@WebMvcTest(value = RoutineController.class)
class RoutineControllerTest extends RestDocsTest {
    @MockBean
    private RoutineService routineService;

    private static final String REQUEST_URL = "/api/v1/routine";

    @Test
    @DisplayName("트랙의 루틴을 저장한다.")
    @WithAuthUser
    void save_track_routine() throws Exception {
        //given
        given(routineService.save(any(RoutineRequest.class), any(Long.class), any(User.class))).willReturn(루틴1);
        //when & then
        mvc.perform(post(REQUEST_URL + "/{trackId}", 1L)
                        .queryParam("trackId", "트랙 id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson(유저_루틴_저장_요청1))
                        .header("Authorization", "Bearer FirebaseToken"))
                .andExpect(status().isOk())
                .andDo(document("track-routine-save",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        pathParameters(
                                parameterWithName("trackId").description("트랙 id")),
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("루틴 내용")
                        )));
    }

    @Test
    @DisplayName("트랙의 루틴을 조회한다.")
    @WithAuthUser
    void get_routines() throws Exception {
        //given
        given(routineService.findByTrack(any(Long.class))).willReturn(루틴_리스트);
        //when & then
        mvc.perform(get(REQUEST_URL + "/{trackId}", 1L)
                        .queryParam("trackId", "트랙 id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer FirebaseToken"))
                .andDo(document("get-routines",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        pathParameters(
                                parameterWithName("trackId").description("트랙 id"))));
    }

    @Test
    @DisplayName("초기 트랙의 루틴을 조회한다.")
    @WithAuthUser
    void get_init_routines() throws Exception {
        //given
        given(routineService.findByTrack(any(Long.class))).willReturn(루틴_초기_리스트);
        //when & then
        mvc.perform(get(REQUEST_URL + "/{trackId}", 1L)
                        .queryParam("trackId", "트랙 id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer FirebaseToken"))
                .andDo(document("get-init-routines",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        pathParameters(
                                parameterWithName("trackId").description("트랙 id"))));
    }

    @Test
    @DisplayName("루틴의 상태를 변경한다.")
    @WithAuthUser
    void update_routine_status() throws Exception {
        //given
        given(routineService.updateStatus(any(RoutineStatusRequest.class),any(Long.class),any(User.class))).willReturn(완료_루틴);
        //when & then
        mvc.perform(patch(REQUEST_URL + "/{routineId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson(유저_루틴_상태_변경_요청1))
                        .header("Authorization", "Bearer FirebaseToken"))
                .andExpect(status().isOk())
                .andDo(document("routine-status-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        pathParameters(
                                parameterWithName("routineId").description("루틴 id")),
                        requestFields(
                                fieldWithPath("routineStatus").type(JsonFieldType.STRING).description("변경할 상태")
                        )));
    }

    @DisplayName("루틴 삭제")
    @Test
    void delete_routine() throws Exception{
        //when & then
        mvc.perform(delete(REQUEST_URL + "/{routineId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer FirebaseToken"))
                .andExpect(status().isNoContent())
                .andDo(document("delete-routine",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        pathParameters(
                                parameterWithName("routineId").description("루틴 id"))));
    }

    private static ResponseFieldsSnippet responseFieldsByUserRoutine() {
        return responseFields(
                fieldWithPath("routineId").type(JsonFieldType.NUMBER).description("루틴 id"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("루틴 내용"),
                fieldWithPath("routineStatus").type(JsonFieldType.STRING).description("루틴 상태")
        );
    }

    private static ResponseFieldsSnippet responseFieldsByUserRoutineList() {
        return responseFields(
                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("루틴 리스트"),
                fieldWithPath("[].routineId").type(JsonFieldType.NUMBER).description("루틴 id"),
                fieldWithPath("[].content").type(JsonFieldType.STRING).description("루틴 내용")
        );
    }

}
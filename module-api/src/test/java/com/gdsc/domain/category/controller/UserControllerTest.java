package com.gdsc.domain.category.controller;

import com.gdsc.domain.user.controller.UserController;
import com.gdsc.domain.user.entity.User;
import com.gdsc.common.security.WithAuthUser;
import com.gdsc.docs.util.RestDocsTest;
import com.gdsc.domain.user.model.UserInfoRequest;
import com.gdsc.domain.user.model.UserKeywordsRequest;
import com.gdsc.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.ContextConfiguration;

import static com.gdsc.docs.util.ApiDocumentUtils.*;
import static com.gdsc.fixture.DomainFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController 테스트")
@ContextConfiguration(classes = UserController.class)
@WebMvcTest(value = UserController.class)
class UserControllerTest extends RestDocsTest {
    @MockBean
    private UserService userService;

    private static final String REQUEST_URL = "/api/v1/user";

    @Test
    @DisplayName("사용자의 정보를 변경한다.")
    @WithAuthUser
    void update_user_info() throws Exception {
        //given
        given(userService.updateUserInfo(any(UserInfoRequest.class), any(User.class))).willReturn(유저1);
        //when & then
        mvc.perform(patch(REQUEST_URL + "/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson(유저_정보_변경_요청1))
                        .header("Authorization", "Bearer FirebaseToken"))
                .andExpect(status().isOk())
                .andDo(document("user-info-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        requestFields(
                                fieldWithPath("nickname").description("변경할 닉네임"),
                                fieldWithPath("gender").description("변경할 성별"),
                                fieldWithPath("age").description("변경할 나이")
                        ),
                        responseFieldsByUserInfo()));
    }

    @Test
    @DisplayName("사용자의 키워드를 변경한다.")
    @WithAuthUser
    void update_user_keywords() throws Exception {
        //given
        given(userService.updateKeywords(any(UserKeywordsRequest.class), any(User.class))).willReturn(유저1);
        //when & then
        mvc.perform(patch(REQUEST_URL + "/keywords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson(유저_키워드_변경_요청1))
                        .header("Authorization", "Bearer FirebaseToken"))
                .andExpect(status().isOk())
                .andDo(document("user-keywords-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        requestFields(
                                fieldWithPath("keyword1").description("변경할 키워드1"),
                                fieldWithPath("keyword2").description("변경할 키워드2"),
                                fieldWithPath("keyword3").description("변경할 키워드3")
                        ),
                        responseFieldsByUserKeywords()));
    }

    @Test
    @DisplayName("사용자 정보를 조회한다.")
    @WithAuthUser
    void get_user() throws Exception {
        //when & then
        mvc.perform(get(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer FirebaseToken"))
                .andDo(document("get-user",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        responseFieldsByUser()));
    }

    private static ResponseFieldsSnippet responseFieldsByUserInfo() {
        return responseFields(
                fieldWithPath("nickname").description("사용자 닉네임").type("String"),
                fieldWithPath("gender").description("사용자 성별").type("Gender"),
                fieldWithPath("age").description("사용자 나이").type("Integer")
        );
    }

    private static ResponseFieldsSnippet responseFieldsByUserKeywords() {
        return responseFields(
                fieldWithPath("keyword1").description("사용자 키워드1").type("String"),
                fieldWithPath("keyword2").description("사용자 키워드2").type("String"),
                fieldWithPath("keyword3").description("사용자 키워드3").type("String")
        );
    }

    private static ResponseFieldsSnippet responseFieldsByUser() {
        return responseFields(
                fieldWithPath("nickname").description("사용자 닉네임").type("String"),
                fieldWithPath("gender").description("사용자 성별").type("Gender"),
                fieldWithPath("age").description("사용자 나이").type("Integer"),
                fieldWithPath("keyword1").description("사용자 키워드1").type("String"),
                fieldWithPath("keyword2").description("사용자 키워드2").type("String"),
                fieldWithPath("keyword3").description("사용자 키워드3").type("String")
        );
    }

}

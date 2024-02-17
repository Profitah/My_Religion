package com.gdsc.domain.user.controller;

import com.gdsc.domain.image.entity.Image;
import com.gdsc.domain.user.entity.User;
import com.gdsc.common.security.WithAuthUser;
import com.gdsc.docs.util.RestDocsTest;
import com.gdsc.domain.user.model.UserInfoRequest;
import com.gdsc.domain.user.model.UserMoodRequest;
import com.gdsc.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.ContextConfiguration;

import java.nio.charset.StandardCharsets;

import static com.gdsc.docs.util.ApiDocumentUtils.*;
import static com.gdsc.fixture.DomainFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경할 닉네임"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("변경할 성별"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("변경할 나이")
                        ),
                        responseFieldsByUserInfo()));
    }

    @Test
    @DisplayName("사용자의 기분을 변경한다.")
    @WithAuthUser
    void update_user_mood() throws Exception {
        //given
        given(userService.updateUserMood(any(UserMoodRequest.class), any(User.class))).willReturn(유저1);
        //when & then
        mvc.perform(patch(REQUEST_URL + "/mood")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson(유저_기분_변경_요청1))
                        .header("Authorization", "Bearer FirebaseToken"))
                .andExpect(status().isOk())
                .andDo(document("user-mood-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        getAuthorizationHeader(),
                        requestFields(
                                fieldWithPath("mood").type(JsonFieldType.STRING).description("변경할 기분")
                        ),
                        responseFields(
                                fieldWithPath("mood").type(JsonFieldType.STRING).description("기분"),
                                fieldWithPath("imgUrl").type(JsonFieldType.STRING).description("기분 이미지 URL")
                        )));
    }

//    @Test
//    @DisplayName("사용자의 프로필을 변경한다")
//    @WithAuthUser
//    void update_user_image() throws Exception {
//        //given
//        MockMultipartFile mockMultipartFile = new MockMultipartFile(
//                "multipartFile",
//                "image.png",
//                "multipart/form-data",
//                "image.png".getBytes(StandardCharsets.UTF_8));
//
//        given(userService.updateUserImage(mockMultipartFile, any(User.class))).willReturn(유저1);
//        //when & then
//        mvc.perform(multipart(REQUEST_URL + "/image")
//                        .file(mockMultipartFile)
//                        .contentType(MediaType.MULTIPART_FORM_DATA)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer FirebaseToken")
//                .andExpect(status().isOk())
//                .andDo(document("update_user_image",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        getAuthorizationHeader(),
//                        requestParts(
//                                partWithName("multipartFile").description("이미지 파일 (png, jpg, jpeg)")
//                        ),
//                        responseFieldsByUser())));
//    }

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
                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이")
        );
    }

    private static ResponseFieldsSnippet responseFieldsByUser() {
        return responseFields(
                fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                fieldWithPath("mood").type(JsonFieldType.STRING).description("기분"),
                fieldWithPath("moodImgUrl").type(JsonFieldType.STRING).description("기분 이미지 URL")
        );
    }

}

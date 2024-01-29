package com.gdsc.docs.error;

import com.gdsc.docs.util.RestDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import static com.gdsc.docs.util.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@DisplayName("Error Response")
@ContextConfiguration(classes = ErrorController.class)
@WebMvcTest(value = ErrorController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class ErrorControllerTest extends RestDocsTest {
    @Test
    @DisplayName("에러 응답")
    void error_response() throws Exception {
        //when & then
        mvc.perform(get("/error"))
                .andDo(document("error-response",
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("errorCode").description("에러 코드"),
                                fieldWithPath("reason").description("에러 이유")
                        )));
    }
}

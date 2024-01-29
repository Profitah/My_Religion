package com.gdsc.docs.util;

import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public interface ApiDocumentUtils {
    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()
                        .scheme("https")
                        .host("dofarming.duckdns.org")
                        .removePort()
                , prettyPrint());
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }

    static RequestHeadersSnippet getAuthorizationHeader(){
        return requestHeaders(
                headerWithName("Authorization").description("Bearer Token")
        );
    }
}

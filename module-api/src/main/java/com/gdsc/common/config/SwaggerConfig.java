package com.gdsc.common.config;

import com.gdsc.common.model.ErrorResponseDto;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Dofarming API Document")
                .version("v0.0.1")
                .description("Dofarming 문서")
                .contact(new Contact().name("WanF-Project").url("https://github.com/Finna-GDSC/DoFarming_SERVER"))
                .license(new License().name("MIT License").url("https://github.com/Finna-GDSC/DoFarming_SERVER/blob/main/LICENSE"));

        String authName = "Firebase token";

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(authName);
        Components components = new Components()
                .addSecuritySchemes(
                        authName,
                        new SecurityScheme()
                                .name(authName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("Firebase")
                                .description("Access Token(Firebase Token) 토큰을 입력해주세요.(Bearer 붙이지 않아도 됩니다~)")
                );

        Map<String, ApiResponse> responses = getResponses();

        for (String key : responses.keySet()) {
            components.addResponses(key, responses.get(key));
        }

        Server prodServer = new Server();

        prodServer.description("Production Server")
                .url("https://dofarming.duckdns.org");

        Server devServer = new Server();

        devServer.description("Development Server")
                .url("http://localhost:8080");

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components)
                .info(info)
                .servers(Arrays.asList(prodServer, devServer));
    }

    private Map<String, ApiResponse> getResponses() {
        ApiResponse noContent, badRequest, unauthorized, forbidden, notFound, conflict, internalServerError;
        var schema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(new AnnotatedType(ErrorResponseDto.class)).schema;

        noContent = new ApiResponse()
                .description("데이터 없음")
                .content(new Content()
                        .addMediaType("application/json",
                                new MediaType().schema(null)
                        )
                );

        badRequest = new ApiResponse()
                .description("잘못된 요청입니다.")
                .content(new Content()
                        .addMediaType("application/json",
                                new MediaType().schema(schema)
                        )
                );

        unauthorized = new ApiResponse()
                .description("인증을 할 수 없습니다.(토큰 없음, 만료된 토큰, 잘못된 토큰 ...)")
                .content(new Content()
                        .addMediaType("application/json",
                                new MediaType().schema(schema)
                        )
                );

        forbidden = new ApiResponse()
                .description("접근할 수 없습니다.")
                .content(new Content()
                        .addMediaType("application/json",
                                new MediaType().schema(schema)
                        )
                );

        notFound = new ApiResponse()
                .description("데이터를 찾을 수 없습니다.")
                .content(new Content()
                        .addMediaType("application/json",
                                new MediaType().schema(schema)
                        )
                );

        conflict = new ApiResponse()
                .description("서버의 현재 상태와 요청이 충돌 상태입니다.")
                .content(new Content()
                        .addMediaType("application/json",
                                new MediaType().schema(schema)
                        )
                );

        internalServerError = new ApiResponse()
                .description("서버 오류(관리자 문의)")
                .content(new Content()
                        .addMediaType("application/json",
                                new MediaType().schema(schema)
                        )
                );

        return Map.of(
                "204", noContent,
                "400", badRequest,
                "401", unauthorized,
                "403", forbidden,
                "404", notFound,
                "409", conflict,
                "500", internalServerError
        );
    }
}

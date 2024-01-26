package com.gdsc.common.model;

public record ErrorResponseDto(
        String errorCode,
        String reason
) {
    public static ErrorResponseDto of(String errorCode, String reason) {
        return new ErrorResponseDto(errorCode, reason);
    }
}
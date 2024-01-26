package com.gdsc.common.exception;

import lombok.Getter;

@Getter
public class ApplicationErrorException extends RuntimeException {
    private final ApplicationErrorType applicationErrorType;
    private String customMessage;
    private String[] args;
    private Object data;

    public ApplicationErrorException(ApplicationErrorType applicationErrorType) {
        super(applicationErrorType.getMessage());
        this.applicationErrorType = applicationErrorType;
    }

    public ApplicationErrorException(ApplicationErrorType applicationErrorType, String customMessage) {
        super(applicationErrorType.getMessage());
        this.applicationErrorType = applicationErrorType;
        this.customMessage = customMessage;
    }

    public ApplicationErrorException(ApplicationErrorType applicationErrorType, String customMessage, String... args) {
        super(applicationErrorType.getMessage());
        this.applicationErrorType = applicationErrorType;
        this.customMessage = customMessage;
        this.args = args;
    }

    public ApplicationErrorException(ApplicationErrorType applicationErrorType, Throwable throwable) {
        super(throwable);
        this.applicationErrorType = applicationErrorType;
    }

    public ApplicationErrorException(ApplicationErrorType applicationErrorType, Throwable throwable,String customMessage) {
        super(throwable);
        this.applicationErrorType = applicationErrorType;
        this.customMessage = customMessage;
    }

}

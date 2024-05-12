package me.jameschan.supervisor.exception;

import org.springframework.graphql.execution.ErrorType;

public class ServiceException extends RuntimeException {
    private final Integer errorCode;
    private final ErrorType errorType;

    public ServiceException(
        final Integer errorCode,
        final ErrorType errorType,
        final String message
    ) {
        super(message);
        this.errorCode = errorCode;
        this.errorType = errorType;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}

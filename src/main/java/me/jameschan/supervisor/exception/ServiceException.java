package me.jameschan.supervisor.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.execution.ErrorType;

public class ServiceException extends RuntimeException {
    private final Integer errorCode;
    private final ErrorType errorType;

    public ServiceException(
        @NotNull final Integer errorCode,
        @NotNull final ErrorType errorType,
        @NotNull final String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorType = errorType;
    }

    public @NotNull Integer getErrorCode() {
        return errorCode;
    }

    public @NotNull ErrorType getErrorType() {
        return errorType;
    }
}

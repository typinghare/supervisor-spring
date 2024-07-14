package me.jameschan.supervisor.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.execution.ErrorType;

// errorCode = 10000 ~ 19999
public class ResourceException extends ServiceException {
    public ResourceException(
        @NotNull final Integer errorCode,
        @NotNull final ErrorType errorType,
        @NotNull final String message) {
        super(errorCode, errorType, message);
    }
}

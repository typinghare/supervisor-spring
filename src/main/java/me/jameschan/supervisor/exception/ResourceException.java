package me.jameschan.supervisor.exception;

import org.springframework.graphql.execution.ErrorType;

// errorCode = 10000 ~ 19999
public class ResourceException extends ServiceException {
    public ResourceException(
        final Integer errorCode,
        final ErrorType errorType,
        final String message
    ) {
        super(errorCode, errorType, message);
    }
}

package me.jameschan.supervisor.exception;

import org.springframework.http.HttpStatus;

public class ResourceException extends RuntimeException {
    private final Integer errorCode;
    private final HttpStatus httpStatus;
    private final String message;

    public ResourceException(
        final Integer errorCode,
        final HttpStatus httpStatus,
        final String message
    ) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package me.jameschan.supervisor.exception;

import org.springframework.http.HttpStatus;

public class ResourceInUseException extends ResourceException {
    public final static ResourceNotFoundException USERNAME
        = new ResourceNotFoundException(11000, "Username already in use.");

    public final static ResourceNotFoundException EMAIL
        = new ResourceNotFoundException(11000, "Email already in use.");

    public ResourceInUseException(
        final Integer errorCode,
        final HttpStatus httpStatus,
        final String message
    ) {
        super(errorCode, HttpStatus.FORBIDDEN, message);
    }
}

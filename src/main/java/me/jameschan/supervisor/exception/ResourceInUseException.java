package me.jameschan.supervisor.exception;

import org.springframework.graphql.execution.ErrorType;

// errorCode = 11000 ~ 11999
public class ResourceInUseException extends ResourceException {
    public final static ResourceNotFoundException USERNAME
        = new ResourceNotFoundException(11000, "Username already in use.");

    public final static ResourceNotFoundException EMAIL
        = new ResourceNotFoundException(11001, "Email already in use.");

    public ResourceInUseException(
        final Integer errorCode,
        final String message
    ) {
        super(errorCode, ErrorType.FORBIDDEN, message);
    }
}

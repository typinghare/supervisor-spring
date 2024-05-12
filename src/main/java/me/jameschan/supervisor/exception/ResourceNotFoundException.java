package me.jameschan.supervisor.exception;

import org.springframework.graphql.execution.ErrorType;

// errorCode = 10000 ~ 10999
public class ResourceNotFoundException extends ResourceException {
    public final static ResourceNotFoundException USER
        = new ResourceNotFoundException(10000, "User not found.");

    public ResourceNotFoundException(final Integer errorCode, final String message) {
        super(errorCode, ErrorType.NOT_FOUND, message);
    }
}

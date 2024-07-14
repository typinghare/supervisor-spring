package me.jameschan.supervisor.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.execution.ErrorType;

// errorCode = 10000 ~ 10999
public final class ResourceNotFoundException extends ResourceException {
    public static final ResourceNotFoundException USER =
        new ResourceNotFoundException(10000, "User not found.");

    public static final ResourceNotFoundException PROJECT =
        new ResourceNotFoundException(10001, "Project not found.");

    public ResourceNotFoundException(
        @NotNull final Integer errorCode, @NotNull final String message) {
        super(errorCode, ErrorType.NOT_FOUND, message);
    }
}

package me.jameschan.supervisor.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.execution.ErrorType;

// errorCode = 11000 ~ 11999
public final class ResourceInUseException extends ResourceException {
    public static final ResourceNotFoundException USERNAME =
        new ResourceNotFoundException(11000, "Username already in use.");

    public static final ResourceNotFoundException EMAIL =
        new ResourceNotFoundException(11001, "Email already in use.");

    public ResourceInUseException(@NotNull final Integer errorCode, @NotNull final String message) {
        super(errorCode, ErrorType.FORBIDDEN, message);
    }
}

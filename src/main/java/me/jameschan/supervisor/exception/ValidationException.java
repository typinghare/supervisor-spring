package me.jameschan.supervisor.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.execution.ErrorType;

// errorCode = 20000 ~ 29999
public final class ValidationException extends ServiceException {
    public static ValidationException PASSWORD =
        new ValidationException(20000, "Password is incorrect.");

    public static ValidationException USER_SESSION =
        new ValidationException(20000, "User session not found.");

    public ValidationException(@NotNull final Integer errorCode, @NotNull final String message) {
        super(errorCode, ErrorType.BAD_REQUEST, message);
    }
}

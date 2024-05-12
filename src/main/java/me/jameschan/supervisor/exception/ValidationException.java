package me.jameschan.supervisor.exception;

import org.springframework.graphql.execution.ErrorType;

// errorCode = 20000 ~ 29999
public class ValidationException extends ServiceException {
    public static ValidationException PASSWORD
        = new ValidationException(20000, "Password is incorrect.");

    public ValidationException(
        final Integer errorCode,
        final String message
    ) {
        super(errorCode, ErrorType.BAD_REQUEST, message);
    }
}
package me.jameschan.supervisor.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ResourceException {
    public final static ResourceNotFoundException USER
        = new ResourceNotFoundException(10000, "User not found.");

    public ResourceNotFoundException(final Integer errorCode, final String message) {
        super(errorCode, HttpStatus.NOT_FOUND, message);
    }
}

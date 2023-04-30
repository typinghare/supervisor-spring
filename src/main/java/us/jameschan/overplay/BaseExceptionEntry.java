package us.jameschan.overplay;

import org.springframework.http.HttpStatus;

public record BaseExceptionEntry(
    int entryCode,
    HttpStatus httpStatus,
    String message,
    String entryName
) {
    @Override
    public int entryCode() {
        return entryCode;
    }

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public String entryName() {
        return entryName;
    }
}

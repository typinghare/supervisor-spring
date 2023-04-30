package us.jameschan.overplay.exception;

public class BaseExceptionNotRegisteredException extends RuntimeException {
    public BaseExceptionNotRegisteredException(Class<?> exceptionClass) {
        super(String.format("Base exception class not found: <%s>.", exceptionClass.getName()));
    }
}

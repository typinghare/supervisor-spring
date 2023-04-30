package us.jameschan.overplay.exception;

public class IncorrectExtensionException extends RuntimeException {
    public IncorrectExtensionException(Class<?> annotatedClass) {
        super(String.format("Overplay exception class is annotated by @ExceptionConfiguration, " +
            "but not extends BaseException: <%s>.", annotatedClass.getName()));
    }
}

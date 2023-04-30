package us.jameschan.overplay.exception;

public class BaseExceptionEntryNotFoundException extends RuntimeException {
    public BaseExceptionEntryNotFoundException() {
        super("This exception is not expected to be thrown. Check the source code of Overplay.");
    }
}

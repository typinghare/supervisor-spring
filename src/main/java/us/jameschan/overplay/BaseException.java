package us.jameschan.overplay;

import org.springframework.http.HttpStatus;
import us.jameschan.overplay.annotation.OverplayException;

@OverplayException(typeCode = 0)
public class BaseException extends RuntimeException {
    /**
     * Overplay manager.
     */
    protected OverplayManager overplayManager;

    /**
     * Returns the error code of this exception.
     * @return the error code of this exception.
     */
    public String getErrorCode() {
        return overplayManager.getErrorCode(this);
    }

    /**
     * Returns the HTTP status code of this exception.
     * @return the HTTP status code of this exception.
     */
    public HttpStatus getHttpStatus() {
        return overplayManager.getHttpStatus(this);
    }

    /**
     * Returns the error message of this exception.
     * @return the error message of this exception.
     */
    public String getErrorMessage() {
        return String.format("%s: %s",
            overplayManager.getEntryName(this),
            overplayManager.getMessage(this)
        );
    }

    @Override
    public String getMessage() {
        return overplayManager.getMessage(this);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}

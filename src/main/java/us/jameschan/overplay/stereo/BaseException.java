package us.jameschan.overplay.stereo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import us.jameschan.overplay.OverplayManager;
import us.jameschan.overplay.annotation.ExceptionConfiguration;

@ExceptionConfiguration(exceptionCode = 0)
public class BaseException extends RuntimeException {
    protected final OverplayManager overplayManager;

    @Autowired
    protected BaseException(OverplayManager overplayManager) {
        this.overplayManager = overplayManager;
    }

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

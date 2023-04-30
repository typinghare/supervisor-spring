package us.jameschan.supervisor.exception;

import org.springframework.http.HttpStatus;
import us.jameschan.overplay.OverplayManager;
import us.jameschan.overplay.annotation.Entry;
import us.jameschan.overplay.annotation.ExceptionConfiguration;
import us.jameschan.overplay.stereo.BaseException;

@ExceptionConfiguration(exceptionCode = 11)
public class SubjectException extends BaseException {
    public SubjectException(OverplayManager overplayManager) {
        super(overplayManager);
    }

    @Entry(code = 1, status = HttpStatus.NOT_FOUND, message = "Subject does not exist.")
    public static SubjectException SUBJECT_NOT_FOUND;

    @Entry(code = 2, status = HttpStatus.NOT_FOUND, message = "A subject with the same name already exists.")
    public static SubjectException SUBJECT_ALREADY_EXIST;
}

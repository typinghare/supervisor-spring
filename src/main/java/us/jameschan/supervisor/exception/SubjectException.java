package us.jameschan.supervisor.exception;

import org.springframework.http.HttpStatus;
import us.jameschan.overplay.BaseException;
import us.jameschan.overplay.annotation.Entry;
import us.jameschan.overplay.annotation.OverplayException;

@OverplayException(typeCode = 11)
public class SubjectException extends BaseException {
    @Entry(code = 1, status = HttpStatus.NOT_FOUND, message = "Subject does not exist.")
    public static SubjectException SUBJECT_NOT_FOUND;
    @Entry(code = 2, status = HttpStatus.FORBIDDEN, message = "A subject with the same name already exists.")
    public static SubjectException SUBJECT_ALREADY_EXIST;
    @Entry(code = 3, status = HttpStatus.FORBIDDEN, message = "You have no permission to update the subject.")
    public static SubjectException NO_PERMISSION_UPDATE_SUBJECT;
}

package us.jameschan.supervisor.exception;

import org.springframework.http.HttpStatus;
import us.jameschan.overplay.BaseException;
import us.jameschan.overplay.annotation.Entry;
import us.jameschan.overplay.annotation.OverplayException;

@OverplayException(typeCode = 13)
public class TaskException extends BaseException {
    @Entry(code = 1, status = HttpStatus.NOT_FOUND, message = "THe task does not exist.")
    public static TaskException TASK_NOT_FOUND;
    @Entry(code = 2, status = HttpStatus.BAD_REQUEST, message = "Illegal task stage is given.")
    public static TaskException ILLEGAL_TASK_STAGE;
    @Entry(code = 3, status = HttpStatus.BAD_REQUEST, message = "The resumed time is null, but it should not be.")
    public static TaskException RESUMED_TIME_IS_NULL;
    @Entry(code = 4, status = HttpStatus.BAD_REQUEST, message = "The task comment does not exist.")
    public static TaskException TASK_COMMENT_NOT_FOUND;
    @Entry(code = 5, status = HttpStatus.BAD_REQUEST, message = "Illegal task action is given.")
    public static TaskException ILLEGAL_TASK_ACTION;
}

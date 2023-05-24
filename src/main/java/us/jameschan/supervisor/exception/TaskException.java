package us.jameschan.supervisor.exception;

import org.springframework.http.HttpStatus;
import us.jameschan.overplay.BaseException;
import us.jameschan.overplay.annotation.Entry;
import us.jameschan.overplay.annotation.OverplayException;

@OverplayException(typeCode = 13)
public class TaskException extends BaseException {
    @Entry(code = 1, status = HttpStatus.NOT_FOUND, message = "Task does not exist.")
    public static TaskException TASK_NOT_FOUND;
    @Entry(code = 2, status = HttpStatus.BAD_REQUEST, message = "Illegal task stage is given.")
    public static TaskException ILLEGAL_TASK_STAGE;
    @Entry(code = 3, status = HttpStatus.BAD_REQUEST, message = "The resumed time is null.")
    public static TaskException RESUMED_TIME_IS_NULL;
}

package us.jameschan.supervisor.exception;

import org.springframework.http.HttpStatus;
import us.jameschan.overplay.BaseException;
import us.jameschan.overplay.annotation.Entry;
import us.jameschan.overplay.annotation.OverplayException;

@OverplayException(typeCode = 12)
public class CategoryException extends BaseException {
    @Entry(code = 1, status = HttpStatus.NOT_FOUND, message = "Category does not exist.")
    public static CategoryException CATEGORY_NOT_FOUND;
    @Entry(code = 2, status = HttpStatus.FORBIDDEN, message = "A category with the same name already exists.")
    public static CategoryException CATEGORY_ALREADY_EXIST;
    @Entry(code = 3, status = HttpStatus.FORBIDDEN, message = "You have no permission to update the category.")
    public static CategoryException NO_PERMISSION_UPDATE_SUBJECT;
}

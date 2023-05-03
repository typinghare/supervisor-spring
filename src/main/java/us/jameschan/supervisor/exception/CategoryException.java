package us.jameschan.supervisor.exception;

import org.springframework.http.HttpStatus;
import us.jameschan.overplay.OverplayManager;
import us.jameschan.overplay.annotation.Entry;
import us.jameschan.overplay.annotation.OverplayException;
import us.jameschan.overplay.stereo.BaseException;

@OverplayException(typeCode = 1)
public class CategoryException extends BaseException {
    @Entry(code = 1, status = HttpStatus.NOT_FOUND, message = "Category does not exist.")
    public static CategoryException CATEGORY_NOT_FOUND;
    @Entry(code = 2, status = HttpStatus.NOT_FOUND, message = "A category with the same name already exists.")
    public static CategoryException CATEGORY_ALREADY_EXIST;

    public CategoryException(OverplayManager overplayManager) {
        super(overplayManager);
    }
}

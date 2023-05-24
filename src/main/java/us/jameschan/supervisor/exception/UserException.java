package us.jameschan.supervisor.exception;

import org.springframework.http.HttpStatus;
import us.jameschan.overplay.BaseException;
import us.jameschan.overplay.annotation.Entry;
import us.jameschan.overplay.annotation.OverplayException;

/**
 * User exception.
 */
@OverplayException(typeCode = 10)
public class UserException extends BaseException {
    @Entry(code = 1, status = HttpStatus.NOT_FOUND, message = "User does not exist.")
    public static UserException USER_NOT_FOUND;
    @Entry(code = 2, status = HttpStatus.FORBIDDEN, message = "User's password is incorrect!")
    public static UserException INCORRECT_PASSWORD;
    @Entry(code = 3, status = HttpStatus.UNAUTHORIZED, message = "Missing token.")
    public static UserException MISSING_TOKEN;
    @Entry(code = 4, status = HttpStatus.UNAUTHORIZED, message = "The token is expired or invalid.")
    public static UserException TOKEN_VALIDATION_FAILS;
    @Entry(code = 5, status = HttpStatus.UNAUTHORIZED, message = "The action is unauthorized!")
    public static UserException UNAUTHORIZED_ACTION;
    @Entry(code = 6, status = HttpStatus.FORBIDDEN, message = "The email already signed up.")
    public static UserException EMAIL_ALREADY_SIGNED_UP;
    @Entry(code = 7, status = HttpStatus.BAD_REQUEST, message = "Missing email.")
    public static UserException MISSING_EMAIL;
}

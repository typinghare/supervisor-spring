package us.jameschan.overplay.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicates a request exception field.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entry {
    @AliasFor("code")
    int value() default 0;

    /**
     * Returns the request exception entry code.
     *
     * @return the request exception entry code
     */
    @AliasFor("value")
    int code() default 0;

    /**
     * Returns http status.
     *
     * @return http status.
     */
    HttpStatus status();

    /**
     * Returns the exception message.
     *
     * @return the exception message.
     */
    String message() default "Unknown exception.";
}

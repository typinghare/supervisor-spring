package us.jameschan.overplay.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Overplay exception configuration.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface OverplayException {
    /**
     * Returns the type code of the annotated exception class.
     * @return the type code of the annotated exception class.
     */
    int typeCode();
}

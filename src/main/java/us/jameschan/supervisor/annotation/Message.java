package us.jameschan.supervisor.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Message {
    /**
     * Returns the message for a successful response.
     * @return the message for a successful response.
     */
    String value();
}

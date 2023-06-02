package us.jameschan.neater.exception;

/**
 * Custom exception class to indicate that a class is not a JavaBean.
 * This exception is typically thrown when a class does not adhere to the conventions of a JavaBean.
 * @author James Chan
 */
public class NotJavaBeanClassException extends RuntimeException {

    /**
     * Constructs a new NotJavaBeanClassException with the specified class.
     * @param _class the class that is not a JavaBean
     */
    public NotJavaBeanClassException(Class<?> _class) {
        super("This class is not a Java bean: [ " + _class + " ].");
    }
}
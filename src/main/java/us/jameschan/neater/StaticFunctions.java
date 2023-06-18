package us.jameschan.neater;

import us.jameschan.neater.exception.NotJavaBeanClassException;

import java.beans.JavaBean;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

/**
 * @author James Chan
 */
public class StaticFunctions {
    /**
     * Executes the specified consumer on the given object and returns the same object.
     * @param <T>      the type of the object
     * @param object   the object on which the consumer will be executed
     * @param consumer the consumer to be executed on the object
     * @return the object after executing the consumer
     */
    public static <T> T let(T object, Consumer<T> consumer) {
        try {
            consumer.accept(object);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }

        return object;
    }

    /**
     * Executes the specified consumer on a new instance of the given JavaBean class,
     * ensuring that the class is annotated with the JavaBean annotation.
     * @param <T>           the type of the JavaBean class.
     * @param javaBeanClass the JavaBean class on which the consumer will be executed.
     * @param consumer      the consumer to be executed on the JavaBean object.
     * @return the JavaBean object after executing the consumer.
     * @throws NotJavaBeanClassException if the javaBeanClass is not a valid JavaBean.
     */
    public static <T> T createBean(final Class<T> javaBeanClass, final Consumer<T> consumer) {
        if (!javaBeanClass.isAnnotationPresent(JavaBean.class)) {
            throw new NotJavaBeanClassException(javaBeanClass);
        }

        final String javaBeanClassName = javaBeanClass.getName();
        try {
            return let(javaBeanClass.getDeclaredConstructor().newInstance(), consumer);
        } catch (InvocationTargetException | InstantiationException e) {
            throw new RuntimeException("Exception encountered while instantiating class [" + javaBeanClassName + "].", e);
        } catch (IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException("No public empty constructor found in [" + javaBeanClassName + "].");
        }
    }

    /**
     * Throws a runtime exception if the given condition is true.
     * @param condition        a specified condition.
     * @param runtimeException a runtime exception to throw if the given condition is true.
     * @throws RuntimeException if the given condition is true.
     */
    public static void throwIf(Boolean condition, RuntimeException runtimeException) {
        if (condition != null && condition) {
            throw runtimeException;
        }
    }

    /**
     * Throws a runtime exception if the given condition is false.
     * @param condition        a specified condition.
     * @param runtimeException a runtime exception to throw if the given condition is false.
     * @throws RuntimeException if the given condition is false.
     */
    public static void throwIfNot(Boolean condition, RuntimeException runtimeException) {
        if (!condition) {
            throw runtimeException;
        }
    }

    /**
     * Throws a runtime exception if the given object is null.
     * @param object           a specified object to test.
     * @param runtimeException a runtime exception to throw if the given object is null.
     * @throws RuntimeException if the given object is null.
     */
    public static void throwIfNull(Object object, RuntimeException runtimeException) {
        if (object == null) {
            throw runtimeException;
        }
    }

    /**
     * Throws a runtime exception if the given object is null.
     * @param object           a specified object to test.
     * @param runtimeException a runtime exception to throw if the given object is null.
     * @throws RuntimeException if the given object is null.
     */
    public static void throwIfNotNull(Object object, RuntimeException runtimeException) {
        if (object != null) {
            throw runtimeException;
        }
    }
}
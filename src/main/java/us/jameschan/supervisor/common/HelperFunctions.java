package us.jameschan.supervisor.common;

import java.util.function.Consumer;

public final class HelperFunctions {
    /**
     * Applies a consumer on an object and returns it.
     * @param object   the given object
     * @param consumer the consumer to apply on the object
     * @param <T>      the type of the given object
     * @return the given object
     */
    public static <T> T apply(T object, Consumer<T> consumer) {
        consumer.accept(object);

        return object;
    }
}

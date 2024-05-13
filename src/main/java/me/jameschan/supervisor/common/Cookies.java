package me.jameschan.supervisor.common;

import graphql.GraphQLContext;
import jakarta.servlet.http.Cookie;

import java.util.ArrayList;

/**
 * Utility class for handling cookies in GraphQL context.
 */
public class Cookies {
    /**
     * Inner class containing predefined cookie key constants.
     */
    public static class Key {
        /**
         * Key for user ID cookie.
         */
        public static final String USER_ID = "uid";

        /**
         * Key for session ID cookie.
         */
        public static final String SESSION_ID = "sid";
    }

    /**
     * Sets a cookie in the GraphQL context with specified properties.
     * @param cookie  The cookie to be set.
     * @param context The GraphQL context in which the cookie is set.
     */
    public static void set(final Cookie cookie, final GraphQLContext context) {
        // Set cookie properties
        cookie.setMaxAge(3600); // Expires in 1 hour
        cookie.setPath("/");    // Cookie is valid for the entire application context

        // Get the list of cookies from the GraphQL context, or create a new list if it doesn't exist
        final var cookieList = context.getOrDefault(ContextKey.COOKIE_LIST, new ArrayList<>());

        // Add the cookie to the list
        cookieList.add(cookie);

        // Update the cookie list in the GraphQL context
        context.put(ContextKey.COOKIE_LIST, cookieList);
    }

    /**
     * Deletes a cookie from the GraphQL context.
     * @param cookie  The cookie to be deleted.
     * @param context The GraphQL context from which the cookie is deleted.
     */
    public static void delete(final Cookie cookie, final GraphQLContext context) {
        // Expire the cookie
        cookie.setMaxAge(-1);

        // Get the list of cookies from the GraphQL context, or create a new list if it doesn't exist
        final var cookieList = context.getOrDefault(ContextKey.COOKIE_LIST, new ArrayList<>());

        // Add the expired cookie to the list
        cookieList.add(cookie);

        // Update the cookie list in the GraphQL context
        context.put(ContextKey.COOKIE_LIST, cookieList);
    }
}
package me.jameschan.supervisor.controller;

import graphql.GraphQLContext;
import jakarta.servlet.http.Cookie;
import me.jameschan.supervisor.common.Cookies;
import me.jameschan.supervisor.model.User;
import me.jameschan.supervisor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public User userById(@Argument final Long id) {
        return userService.getUserById(id);
    }

    @MutationMapping
    public User createUser(
        @Argument final String username,
        @Argument final String email,
        @Argument final String password
    ) {
        return userService.createUser(username, email, password);
    }

    @QueryMapping
    public User userBySessionId(@Argument final String sessionId) {
        return userService.getUserBySessionId(sessionId);
    }

    @QueryMapping
    public User signIn(
        @Argument final String username,
        @Argument final String password,
        GraphQLContext context
    ) {
        final var user = userService.signIn(username, password);
        final var sessionId = userService.createUserSession(user.getId());
        final var sessionIdCookie = new Cookie(Cookies.Key.SESSION_ID, sessionId);
        final var userIdCookie = new Cookie(Cookies.Key.USER_ID, user.getId().toString());
        Cookies.set(sessionIdCookie, context);
        Cookies.set(userIdCookie, context);

        return user;
    }
}

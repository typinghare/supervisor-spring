package me.jameschan.supervisor.controller;

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
}

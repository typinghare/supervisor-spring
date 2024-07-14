package me.jameschan.supervisor.service;

import me.jameschan.supervisor.common.Emails;
import me.jameschan.supervisor.exception.ResourceInUseException;
import me.jameschan.supervisor.exception.ResourceNotFoundException;
import me.jameschan.supervisor.exception.ValidationException;
import me.jameschan.supervisor.model.User;
import me.jameschan.supervisor.redis.UserSession;
import me.jameschan.supervisor.redis.UserSessionRepository;
import me.jameschan.supervisor.repository.UserRepository;
import me.jameschan.supervisor.utility.Encryptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class UserService {
    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final Encryptor encryptor;

    @Autowired
    public UserService(
        @NotNull final UserRepository userRepository,
        @NotNull final UserSessionRepository userSessionRepository,
        @NotNull final Encryptor encryptor) {
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
        this.encryptor = encryptor;
    }

    public @NotNull User getUserById(@NotNull final Long id) {
        return userRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.USER);
    }

    public @NotNull User getUserByUsername(@NotNull final String username) {
        return userRepository
            .findByUsername(username)
            .orElseThrow(() -> ResourceNotFoundException.USER);
    }

    public @NotNull User getUserByEmail(@NotNull final String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> ResourceNotFoundException.USER);
    }

    public @NotNull User createUser(
        @NotNull final String username, @NotNull final String email,
        @NotNull final String password) {
        // Check if the username is in use
        if (userRepository.findByUsername(username).isPresent()) {
            throw ResourceInUseException.USERNAME;
        }

        // Check if the email is in use
        if (userRepository.findByEmail(email).isPresent()) {
            throw ResourceInUseException.EMAIL;
        }

        // Encrypt password
        final var authString = encryptor.encrypt(password);

        // Save the user info
        final var user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setAuthString(authString);

        return userRepository.save(user);
    }

    public @NotNull User signIn(@NotNull final String username, @NotNull final String password) {
        final var isEmailAddress = Emails.isEmail(username);
        return isEmailAddress
            ? signInWithEmail(username, password)
            : signInWithUsername(username, password);
    }

    public @NotNull String createUserSession(@NotNull final Long userId) {
        final var sessionId = UUID.randomUUID().toString();
        final var userSession = new UserSession();
        userSession.setId(sessionId);
        userSession.setUserId(userId);
        userSessionRepository.save(userSession);

        return sessionId;
    }

    public @NotNull UserSession getUserSession(@NotNull final String sessionId) {
        return userSessionRepository
            .findById(sessionId)
            .orElseThrow(() -> ValidationException.USER_SESSION);
    }

    public @NotNull User getUserBySessionId(@NotNull final String sessionId) {
        final var userSession = getUserSession(sessionId);
        return getUserById(userSession.getUserId());
    }

    private @NotNull User signInWithUsername(
        @NotNull final String username, @NotNull final String password) {
        final var user = getUserByUsername(username);
        final var authString = user.getAuthString();
        if (!encryptor.matches(password, authString)) {
            throw ValidationException.PASSWORD;
        }

        return user;
    }

    private @NotNull User signInWithEmail(
        @NotNull final String email, @NotNull final String password) {
        final var user = getUserByEmail(email);
        final var authString = user.getAuthString();
        if (!encryptor.matches(password, authString)) {
            throw ValidationException.PASSWORD;
        }

        return user;
    }
}

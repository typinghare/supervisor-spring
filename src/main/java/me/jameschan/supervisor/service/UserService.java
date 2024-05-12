package me.jameschan.supervisor.service;

import me.jameschan.supervisor.exception.ResourceInUseException;
import me.jameschan.supervisor.exception.ResourceNotFoundException;
import me.jameschan.supervisor.exception.ValidationException;
import me.jameschan.supervisor.model.User;
import me.jameschan.supervisor.repository.UserRepository;
import me.jameschan.supervisor.utility.Encryptor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final Encryptor encryptor;

    @Autowired
    public UserService(
        final UserRepository userRepository,
        final Encryptor encryptor
    ) {
        this.userRepository = userRepository;
        this.encryptor = encryptor;
    }

    public User getUserById(final Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> ResourceNotFoundException.USER);
    }

    public User getUserByUsername(final String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> ResourceNotFoundException.USER);
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> ResourceNotFoundException.USER);
    }

    public User createUser(final String username, final String email, final String password) {
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

        // Register
        final User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setAuthString(authString);

        return userRepository.save(user);
    }

    public User signIn(final String username, final String password) {
        final var isEmailAddress = EmailValidator.getInstance().isValid(username);
        return isEmailAddress ?
            signInWithEmail(username, password) :
            signInWithUsername(username, password);
    }

    private User signInWithUsername(final String username, final String password) {
        final var user = getUserByUsername(username);
        final var authString = user.getAuthString();
        if (!encryptor.matches(password, authString)) {
            throw ValidationException.PASSWORD;
        }

        return user;
    }

    private User signInWithEmail(final String email, final String password) {
        final var user = getUserByEmail(email);
        final var authString = user.getAuthString();
        if (!encryptor.matches(password, authString)) {
            throw ValidationException.PASSWORD;
        }

        return user;
    }
}

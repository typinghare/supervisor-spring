package me.jameschan.supervisor.service;

import me.jameschan.supervisor.exception.ResourceInUseException;
import me.jameschan.supervisor.exception.ResourceNotFoundException;
import me.jameschan.supervisor.model.User;
import me.jameschan.supervisor.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(final Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> ResourceNotFoundException.USER);
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> ResourceNotFoundException.USER);
    }

    public User getUserByUsername(final String username) {
        return userRepository.findByUsername(username)
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


        // Register
        final User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setAuthString(password);

        return userRepository.save(user);
    }
}

package us.jameschan.supervisor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A general encoder.
 * @author James Chan.
 */
@Component
public class Encoder {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Encoder(String encoderSecret, Integer encoderSaltLength, Integer encoderIterations) {
        passwordEncoder = new Pbkdf2PasswordEncoder(
            encoderSecret,
            encoderSaltLength,
            encoderIterations,
            Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
        );
    }

    /**
     * Encodes the raw password.
     * @return an encoded string
     */
    public final String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Verifies the encoded password.
     * @return true if the password matches, false otherwise
     */
    public final boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
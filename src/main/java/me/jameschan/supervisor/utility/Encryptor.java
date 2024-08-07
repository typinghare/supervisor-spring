package me.jameschan.supervisor.utility;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * Utility class for encrypting and verifying passwords using PBKDF2 algorithm.
 */
public final class Encryptor {
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an Encryptor instance with the specified secret, salt length, and iteration
     * count.
     * @param secret     the secret used for encryption
     * @param saltLength the length of the salt
     * @param iterations the number of iterations for key derivation
     */
    public Encryptor(
        @NotNull final String secret,
        @NotNull final Integer saltLength,
        @NotNull final Integer iterations
    ) {
        passwordEncoder =
            new Pbkdf2PasswordEncoder(
                secret,
                saltLength,
                iterations,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
    }

    /**
     * Encrypts a raw string and returns the encrypted representation.
     * @param rawString the raw string to be encrypted
     * @return the encrypted string
     */
    public String encrypt(@NotNull final CharSequence rawString) {
        return passwordEncoder.encode(rawString);
    }

    /**
     * Verifies whether the provided raw string matches the encrypted password.
     * @param rawString         the raw string to verify
     * @param encryptedPassword the encrypted password to compare against
     * @return true if the raw string matches the encrypted password, false otherwise
     */
    public boolean matches(
        @NotNull final CharSequence rawString,
        @NotNull final String encryptedPassword
    ) {
        return passwordEncoder.matches(rawString, encryptedPassword);
    }
}

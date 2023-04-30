package us.jameschan.supervisor.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import us.jameschan.supervisor.common.Encoder;
import us.jameschan.supervisor.common.Throws;
import us.jameschan.supervisor.common.Token;
import us.jameschan.supervisor.dto.UserDto;
import us.jameschan.supervisor.dto.UserSignInDto;
import us.jameschan.supervisor.dto.UserSignInResponseDto;
import us.jameschan.supervisor.dto.UserSignUpDto;
import us.jameschan.supervisor.exception.UserException;
import us.jameschan.supervisor.model.User;
import us.jameschan.supervisor.repository.UserRepository;

import java.util.Objects;

import static us.jameschan.supervisor.common.HelperFunctions.apply;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final Encoder encoder;

    private final Token token;

    @Autowired
    public UserService(UserRepository userRepository, Encoder encoder, Token token) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.token = token;
    }

    /**
     * Returns a user DTO.
     */
    public UserDto getUserDto(Long userId) {
        return userRepository.findById(userId)
            .map(user -> apply(new UserDto(), it -> {
                it.setId(user.getId());
                it.setEmail(user.getEmail());
                it.setUsername(user.getUsername());
            })).orElseThrow(() -> UserException.USER_NOT_FOUND);
    }

    /**
     * User signs in.
     * @throws UserException if user does not exist.
     * @throws UserException if the password is not correct.
     */
    public UserSignInResponseDto signIn(UserSignInDto userSignInDto) {
        final String username = userSignInDto.getUsername();
        final User user = userRepository.findFirstByUsername(username)
            .orElseThrow(() -> UserException.USER_NOT_FOUND);

        if (!encoder.matches(userSignInDto.getPassword(), user.getAuthString())) {
            throw UserException.INCORRECT_PASSWORD;
        }

        return apply(new UserSignInResponseDto(), it -> {
            it.setId(user.getId());
            it.setToken(token.generate(user.getId()));
            it.setEmail(user.getEmail());
            it.setUsername(username);
        });
    }

    /**
     * Users signs up.
     */
    public UserDto signUp(UserSignUpDto userSignUpDto) {
        final String email = userSignUpDto.getEmail();
        Throws.ifNull(email, UserException.MISSING_EMAIL);

        if (userRepository.findFirstByEmail(email).isPresent()) {
            throw UserException.EMAIL_ALREADY_SIGNED_UP;
        }

        final User user = userRepository.save(apply(new User(), it -> {
            it.setEmail(email);
            it.setUsername(userSignUpDto.getUsername());
            it.setAuthString(getAuthString(userSignUpDto.getPassword()));
        }));

        return apply(new UserDto(), it -> {
            it.setId(user.getId());
            it.setEmail(user.getEmail());
            it.setUsername(user.getUsername());
        });
    }

    /**
     * Checks whether the id of the current user is the same as the given id.
     * @throws UserException if the id of the current user is different from the given id.
     */
    public void checkUserToBe(Long userId) {
        if (!Objects.equals(userId, getUserIdByToken())) {
            throw UserException.UNAUTHORIZED_ACTION;
        }
    }

    /**
     * Returns the id of the user by token.
     * @throws UserException if token is missing or token is invalid.
     */
    public Long getUserIdByToken() {
        final HttpServletRequest request
            = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final String tokenString = request.getHeader("token");

        Throws.ifNull(tokenString, UserException.MISSING_TOKEN);

        final Long userId = token.getUserId(tokenString);
        Throws.ifNull(userId, UserException.TOKEN_VALIDATION_FAILS);

        return userId;
    }

    /**
     * Returns authentication string.
     * @param rawPassword raw password to encode.
     * @return the authentication string.
     */
    private String getAuthString(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}

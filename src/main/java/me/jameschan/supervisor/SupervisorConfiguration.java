package me.jameschan.supervisor;

import graphql.GraphQLContext;
import jakarta.servlet.http.Cookie;
import me.jameschan.supervisor.common.ContextKey;
import me.jameschan.supervisor.utility.Encryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Configuration
@PropertySource({
    // Environment properties all sensitive properties that should not be saved to repositories,
    // such as database password and encryptor parameters
    "classpath:.env.properties"
})
public class SupervisorConfiguration {
    @Value("${encryptor.secret}")
    private String encryptorSecret;

    @Value("${encryptor.saltLength}")
    private Integer encryptorSaltLength;

    @Value("${encryptor.iterations}")
    private Integer encryptorIterations;

    @Bean
    public Encryptor encryptor() {
        return new Encryptor(encryptorSecret, encryptorSaltLength, encryptorIterations);
    }
}

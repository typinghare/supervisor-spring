package us.jameschan.supervisor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * This configuration file enables developers to define properties and register them with Spring beans.
 * @author James Chan.
 */
@Configuration
@PropertySource({
    "classpath:.env.properties"
})
public class PropertyConfiguration {
    @Value("${encoder.saltLength}")
    private Integer encoderSaltLength;

    @Value("${encoder.iterations}")
    private Integer encoderIterations;

    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${token.validity-period}")
    private Integer tokenValidityPeriod;

    @Value("${encoder.secret}")
    private String encoderSecret;

    @Bean
    public String encoderSecret() {
        return encoderSecret;
    }

    @Bean
    public Integer encoderSaltLength() {
        return encoderSaltLength;
    }

    @Bean
    public Integer encoderIterations() {
        return encoderIterations;
    }

    @Bean
    public Integer tokenValidityPeriod() {
        return tokenValidityPeriod;
    }

    @Bean
    public String tokenSecret() {
        return tokenSecret;
    }
}

package me.jameschan.supervisor;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({
    // Environment properties all sensitive properties that should not be saved to repositories,
    // such as database password
    "classpath:.env.properties"
})
public class SupervisorConfiguration {
}

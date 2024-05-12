package me.jameschan.supervisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Supervisor backend based on Spring Boot.
 * @author James Chan
 */
@SpringBootApplication
public class SupervisorApplication {
    public static void main(final String[] args) {
        SpringApplication.run(SupervisorApplication.class, args);
    }
}

package us.jameschan.supervisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Supervisor backend.
 * @author James Chan (TypingHare)
 */
@SpringBootApplication(scanBasePackages = {
    "us.jameschan.supervisor",
    "us.jameschan.overplay",
})
public class SupervisorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SupervisorApplication.class, args);
    }
}

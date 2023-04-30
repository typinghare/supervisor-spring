package us.jameschan.supervisor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Supervisor application runner.
 */
@Component
public class SupervisorApplicationRunner implements ApplicationRunner {
    private final ApplicationContext applicationContext;

    @Autowired
    public SupervisorApplicationRunner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}

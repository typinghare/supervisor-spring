package us.jameschan.overplay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class OverplayApplicationRunner implements ApplicationRunner {
    private final ApplicationContext applicationContext;

    @Autowired
    private OverplayApplicationRunner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Initialize overplay manager.
        applicationContext.getBean(OverplayManager.class).init();
    }
}

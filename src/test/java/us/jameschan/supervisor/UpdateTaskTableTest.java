package us.jameschan.supervisor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import us.jameschan.supervisor.service.TaskService;

@SpringBootTest(classes = {
    us.jameschan.supervisor.SupervisorApplication.class
})
public class UpdateTaskTableTest {
    private final TaskService taskService;

    @Autowired
    public UpdateTaskTableTest(TaskService taskService) {
        this.taskService = taskService;
    }

    @Test
    public void loadSubjectId() {
        taskService.loadSubjectId();
    }
}

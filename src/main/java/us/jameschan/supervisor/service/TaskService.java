package us.jameschan.supervisor.service;

import org.springframework.stereotype.Service;
import us.jameschan.supervisor.dto.TaskDto;
import us.jameschan.supervisor.model.Task;
import us.jameschan.supervisor.repository.TaskRepository;

import static us.jameschan.supervisor.common.HelperFunctions.apply;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Retrieves a task by id.
     */
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow();
    }

    /**
     * Converts a task to task DTO.
     */
    public TaskDto toTaskDto(Task task) {
        if (task == null) return null;

        return apply(new TaskDto(), it -> {
            it.setId(task.getId());
            it.setCategoryId(task.getCategoryId());
        });
    }
}

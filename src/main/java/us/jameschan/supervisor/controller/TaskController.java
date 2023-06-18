package us.jameschan.supervisor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import us.jameschan.supervisor.annotation.Message;
import us.jameschan.supervisor.constant.TaskAction;
import us.jameschan.supervisor.dto.TaskCommentDto;
import us.jameschan.supervisor.dto.TaskCreateDto;
import us.jameschan.supervisor.dto.TaskDto;
import us.jameschan.supervisor.dto.TaskUpdateDto;
import us.jameschan.supervisor.model.Task;
import us.jameschan.supervisor.service.TaskService;

import java.util.List;

@Controller()
@RequestMapping("/api/supervisor/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/")
    @ResponseBody
    @Message("Created the task successfully.")
    public TaskDto createTask(@RequestBody TaskCreateDto taskCreateDto) {
        return taskService.toTaskDto(taskService.createTask(taskCreateDto.getCategoryId()));
    }

    @PutMapping("/{taskId}/")
    @ResponseBody
    @Message("updated the task successfully.")
    public TaskDto updateTask(
        @PathVariable Long taskId,
        @RequestBody TaskUpdateDto taskUpdateDto
    ) {
        final Integer taskAction = taskUpdateDto.getTaskAction();
        if (taskAction != null) {
            final Task task = taskService.updateTaskStage(taskId, TaskAction.fromNumber(taskAction));

            return taskService.toTaskDto(task);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{taskId}/")
    @ResponseBody
    @Message("Deleted the task successfully.")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    @GetMapping("/{taskId}/comments/")
    @ResponseBody
    @Message("Got all comments of the task successfully.")
    public List<TaskCommentDto> getAllCommentForTask(@PathVariable Long taskId) {
        return taskService.getAllTaskComment(taskId)
            .stream().map(taskService::toTaskCommentDto).toList();
    }

    @PostMapping("/{taskId}/comments/")
    @ResponseBody
    @Message("Created a comment successfully.")
    public TaskCommentDto createComment(
        @PathVariable Long taskId,
        @RequestBody TaskCommentDto taskCommentDto
    ) {
        return taskService.toTaskCommentDto(
            taskService.createTaskComment(taskId, taskCommentDto.getContent())
        );
    }

    @DeleteMapping("/comments/{taskCommentId}/")
    @ResponseBody
    @Message("Deleted a comment successfully.")
    public void deleteComment(@PathVariable Long taskCommentId) {
        taskService.deleteTaskComment(taskCommentId);
    }
}

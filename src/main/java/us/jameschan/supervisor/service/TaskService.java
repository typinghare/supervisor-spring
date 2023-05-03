package us.jameschan.supervisor.service;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import us.jameschan.supervisor.common.Throws;
import us.jameschan.supervisor.common.TimestampRange;
import us.jameschan.supervisor.constant.TaskAction;
import us.jameschan.supervisor.constant.TaskStage;
import us.jameschan.supervisor.dto.CategoryDto;
import us.jameschan.supervisor.dto.SubjectDto;
import us.jameschan.supervisor.dto.TaskDto;
import us.jameschan.supervisor.exception.TaskException;
import us.jameschan.supervisor.model.Category;
import us.jameschan.supervisor.model.Subject;
import us.jameschan.supervisor.model.Task;
import us.jameschan.supervisor.repository.TaskRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static us.jameschan.supervisor.common.HelperFunctions.apply;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    private final UserService userService;

    private final CategoryService categoryService;

    private final SubjectService subjectService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService, CategoryService categoryService, SubjectService subjectService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.subjectService = subjectService;
    }

    /**
     * Converts a task to task DTO.
     */
    public TaskDto toTaskDto(Task task) {
        if (task == null) return null;

        return apply(new TaskDto(), it -> {
            it.setId(task.getId());
            it.setCategoryId(task.getCategoryId());

            // category
            final Category category = categoryService.getCategory(task.getCategoryId());
            final CategoryDto categoryDto = categoryService.toCategoryDto(category);
            it.setCategoryDto(categoryDto);

            // category.subject
            final Subject subject = subjectService.getSubject(category.getSubjectId());
            final SubjectDto subjectDto = subjectService.toSubjectDto(subject);
            categoryDto.setSubjectDto(subjectDto);
        });
    }

    /**
     * Retrieves a task by id.
     */
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> TaskException.TASK_NOT_FOUND);
    }

    /**
     * Creates a task.
     */
    public Task createTask(Long categoryId) {
        final Long userId = userService.getUserIdByToken();

        final Timestamp timestamp = new Timestamp(new Date().getTime());
        final Task task = apply(new Task(), it -> {
            it.setUserId(userId);
            it.setCategoryId(categoryId);
            it.setStage(TaskStage.PENDING.getNumber());
            it.setDuration(0);
            it.setCreatedAt(timestamp);
            it.setUpdatedAt(timestamp);
            it.setStartedAt(null);
            it.setResumedAt(null);
            it.setEndedAt(null);
        });

        return taskRepository.save(task);
    }

    /**
     * Updates a task by applying an action on it.
     * @param taskId the id of the task.
     * @param action the action to be applied on the task.
     * @return the task after applying the action.
     */
    public Task updateTaskStage(Long taskId, TaskAction action) {
        final Task task = taskRepository.findById(taskId).orElseThrow(() -> TaskException.TASK_NOT_FOUND);
        userService.checkUserToBe(task.getUserId());

        final TaskStage originalStage = TaskStage.fromNumber(task.getStage());
        if (originalStage == null) {
            throw TaskException.ILLEGAL_TASK_STAGE;
        }

        final TaskStage newStage = changeStage(originalStage, action);
        if (newStage == null) {
            throw TaskException.ILLEGAL_TASK_STAGE;
        }

        // Update stage from the original stage to the new stage.
        task.setStage(newStage.getNumber());

        // Update various times.
        final Timestamp timestamp = new Timestamp(new Date().getTime());
        switch (newStage) {
            case ONGOING -> {
                if (originalStage == TaskStage.PENDING) {
                    task.setStartedAt(timestamp);
                }
                task.setResumedAt(timestamp);
            }
            case PAUSED -> {
                Throws.ifNull(task.getResumedAt(), TaskException.RESUMED_TIME_IS_NULL);
                task.setDuration(task.getDuration() + getDifferenceInMinutes(task.getResumedAt()));
                task.setResumedAt(null);
            }
            case ENDED -> {
                Throws.ifNull(task.getResumedAt(), TaskException.RESUMED_TIME_IS_NULL);
                task.setDuration(task.getDuration() + getDifferenceInMinutes(task.getResumedAt()));
                task.setResumedAt(null);
                task.setEndedAt(timestamp);
            }
        }

        // Persist the task to the database.
        return taskRepository.save(task);
    }

    /**
     * Changes the stage of a task by applying the given action.
     * @return the new stage; null if it cannot finish.
     */
    public TaskStage changeStage(TaskStage originalStage, TaskAction taskAction) {
        return switch (taskAction) {
            case START -> originalStage == TaskStage.PENDING ? TaskStage.ONGOING : null;
            case PAUSE -> originalStage == TaskStage.ONGOING ? TaskStage.PAUSED : null;
            case RESUME -> originalStage == TaskStage.PAUSED ? TaskStage.ONGOING : null;
            case FINISH -> originalStage == TaskStage.ONGOING || originalStage == TaskStage.PAUSED
                ? TaskStage.ENDED : null;
            default -> null;
        };
    }

    /**
     * Retrieves tasks.
     */
    public List<Task> getTasks(TaskDto taskDto, TimestampRange timestampRange) {
        final Specification<Task> taskSpecification = ((root, query, criteriaBuilder) -> {
            final List<Predicate> predicateList = new ArrayList<>();

            if (taskDto.getUserId() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("user_id"), taskDto.getUserId()));
            }

            if (taskDto.getStage() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("stage"), taskDto.getStage()));
            }

            final Timestamp startTimestamp = timestampRange.getStartTimestamp();
            final Timestamp endTimestamp = timestampRange.getEndTimestamp();
            if (startTimestamp != null) {
                predicateList.add(criteriaBuilder.greaterThan(root.get("created_at"), startTimestamp));
            }
            if (endTimestamp != null) {
                predicateList.add(criteriaBuilder.greaterThan(root.get("created_at"), endTimestamp));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        });

        return taskRepository.findAll(taskSpecification);
    }

    /**
     * Returns the difference in minutes of the current timestamp and the given timestamp.
     */
    private int getDifferenceInMinutes(Timestamp timestamp) {
        final double differenceInSeconds = new Date().getTime() - timestamp.getTime();
        return (int) Math.floor(differenceInSeconds / 60);
    }
}

package us.jameschan.supervisor.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import us.jameschan.supervisor.constant.TaskAction;
import us.jameschan.supervisor.constant.TaskStage;
import us.jameschan.supervisor.dto.TaskCommentDto;
import us.jameschan.supervisor.dto.TaskDto;
import us.jameschan.supervisor.exception.TaskException;
import us.jameschan.supervisor.model.Category;
import us.jameschan.supervisor.model.Subject;
import us.jameschan.supervisor.model.Task;
import us.jameschan.supervisor.model.TaskComment;
import us.jameschan.supervisor.repository.TaskCommentRepository;
import us.jameschan.supervisor.repository.TaskRepository;
import us.jameschan.supervisor.util.Dates;
import us.jameschan.supervisor.util.Pagination;
import us.jameschan.supervisor.util.TimestampRange;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static us.jameschan.neater.StaticFunctions.createBean;
import static us.jameschan.neater.StaticFunctions.throwIfNull;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskCommentRepository taskCommentRepository;
    private final UserService userService;
    private final SubjectService subjectService;
    private final CategoryService categoryService;

    @PersistenceContext
    private EntityManager entityManager;

    public TaskService(
            TaskRepository taskRepository,
            TaskCommentRepository taskCommentRepository,
            UserService userService,
            SubjectService subjectService,
            CategoryService categoryService
    ) {
        this.taskRepository = taskRepository;
        this.taskCommentRepository = taskCommentRepository;
        this.userService = userService;
        this.subjectService = subjectService;
        this.categoryService = categoryService;
    }

    /**
     * Converts a task to task DTO.
     */
    public TaskDto toTaskDto(Task task) {
        if (task == null) return null;

        return createBean(TaskDto.class, it -> {
            it.setId(task.getId());
            it.setUserId(task.getUserId());
            it.setCategoryId(task.getCategoryId());
            it.setStage(task.getStage());
            it.setDuration(task.getDuration());
            it.setCreatedAt(Dates.toDateString(task.getCreatedAt()));
            it.setUpdatedAt(Dates.toDateString(task.getUpdatedAt()));
            it.setStartedAt(Dates.toDateString(task.getStartedAt()));
            it.setResumedAt(Dates.toDateString(task.getResumedAt()));
            it.setEndedAt(Dates.toDateString(task.getEndedAt()));

            // Category
            final Category category = categoryService.getCategoryById(task.getCategoryId());
            it.setCategoryName(category.getName());

            // Subject
            final Subject subject = subjectService.getSubjectById(category.getSubjectId());
            it.setSubjectName(subject.getName());
        });
    }

    /**
     * Converts a task comment to task comment DTO.
     */
    public TaskCommentDto toTaskCommentDto(TaskComment taskComment) {
        if (taskComment == null) return null;

        return createBean(TaskCommentDto.class, it -> {
            it.setId(taskComment.getId());
            it.setTaskId(taskComment.getTaskId());
            it.setContent(taskComment.getContent());
            it.setCreatedAt(Dates.toDateString(taskComment.getCreatedAt()));
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
        final Task task = createBean(Task.class, it -> {
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
     * Updates a task by createBeaning an action on it.
     * @param taskId the id of the task.
     * @param action the action to be applied on the task.
     * @return the task after createBeaning the action.
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

        // Update updated time, resumed time, and other time.
        final Timestamp timestamp = new Timestamp(new Date().getTime());
        switch (newStage) {
            case ONGOING -> {
                if (originalStage == TaskStage.PENDING) {
                    task.setStartedAt(timestamp);
                }
                task.setResumedAt(timestamp);
            }
            case PAUSED -> {
                throwIfNull(task.getResumedAt(), TaskException.RESUMED_TIME_IS_NULL);
                task.setDuration(task.getDuration() + getDifferenceInMinutes(task.getResumedAt()));
                task.setResumedAt(null);
            }
            case ENDED -> {
                if (originalStage == TaskStage.ONGOING) {
                    throwIfNull(task.getResumedAt(), TaskException.RESUMED_TIME_IS_NULL);
                    task.setDuration(task.getDuration() + getDifferenceInMinutes(task.getResumedAt()));
                }
                task.setResumedAt(null);
                task.setEndedAt(timestamp);
            }
        }

        // Persist the task to the database.
        return taskRepository.save(task);
    }

    /**
     * Changes the stage of a task by createBeaning the given action.
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
    public List<Task> getTasks(TaskDto taskDto, TimestampRange timestampRange, Pagination pagination) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        final Root<Task> root = criteriaQuery.from(Task.class);
        final List<Predicate> predicateList = new ArrayList<>();

        // Specify soft deleted.
        predicateList.add(criteriaBuilder.isNull(root.get("deletedAt")));

        // Specify user.
        if (taskDto.getUserId() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("userId"), taskDto.getUserId()));
        }

        // Specify category.
        if (taskDto.getCategoryId() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("category_id"), taskDto.getCategoryId()));
        }

        // Specify stage.
        if (taskDto.getStage() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("stage"), taskDto.getStage()));
        }

        // Specify time range.
        final Timestamp startTimestamp = timestampRange.getStartTimestamp();
        final Timestamp endTimestamp = timestampRange.getEndTimestamp();
        if (startTimestamp != null) {
            predicateList.add(criteriaBuilder.greaterThan(root.get("createdAt"), startTimestamp));
        }
        if (endTimestamp != null) {
            predicateList.add(criteriaBuilder.lessThan(root.get("createdAt"), endTimestamp));
        }

        // Combine the conditions using 'and'
        criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[0])));

        // Set order.
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdAt")));

        // Create the TypedQuery
        TypedQuery<Task> typedQuery = entityManager.createQuery(criteriaQuery);

        // Set pagination
        final Integer limit = pagination.limit();
        final Integer page = pagination.page();
        if (limit != null) {
            typedQuery.setMaxResults(limit);
            if (page != null) {
                typedQuery.setFirstResult((page - 1) * limit);
            }
        }

        // Execute the query
        return typedQuery.getResultList();
    }

    /**
     * Deletes a specified task.
     */
    public void deleteTask(Long taskId) {
        final Task task = getTaskById(taskId);
        userService.checkUserToBe(task.getUserId());

        // Soft delete.
        task.setDeletedAt(new Timestamp(new Date().getTime()));
        taskRepository.save(task);
    }

    /**
     * Retrieves all comments for a specified task.
     */
    public List<TaskComment> getAllTaskComment(Long taskId) {
        return taskCommentRepository.findAllByTaskId(taskId);
    }

    /**
     * Returns the difference in minutes of the current timestamp and the given timestamp.
     */
    private int getDifferenceInMinutes(Timestamp timestamp) {
        final double differenceInSeconds = new Date().getTime() - timestamp.getTime();
        return (int) Math.floor(differenceInSeconds / 60000);
    }
}

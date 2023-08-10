package us.jameschan.supervisor.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.jameschan.supervisor.dto.CategoryDto;
import us.jameschan.supervisor.exception.CategoryException;
import us.jameschan.supervisor.exception.SubjectException;
import us.jameschan.supervisor.model.Category;
import us.jameschan.supervisor.model.Subject;
import us.jameschan.supervisor.model.Task;
import us.jameschan.supervisor.model.TaskComment;
import us.jameschan.supervisor.repository.CategoryRepository;
import us.jameschan.supervisor.repository.SubjectRepository;
import us.jameschan.supervisor.repository.TaskRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static us.jameschan.neater.StaticFunctions.createBean;
import static us.jameschan.neater.StaticFunctions.let;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubjectRepository subjectRepository;
    private final UserService userService;
    private final TaskRepository taskRepository;
    private final EntityManager entityManager;

    @Autowired
    public CategoryService(
        CategoryRepository categoryRepository,
        SubjectRepository subjectRepository,
        UserService userService,
        TaskRepository taskRepository,
        EntityManager entityManager) {
        this.categoryRepository = categoryRepository;
        this.subjectRepository = subjectRepository;
        this.userService = userService;
        this.taskRepository = taskRepository;
        this.entityManager = entityManager;
    }

    /**
     * Converts a category to category DTO.
     */
    public CategoryDto toCategoryDto(Category category) {
        return createBean(CategoryDto.class, it -> {
            it.setId(category.getId());
            it.setName(category.getName());
            it.setSubjectId(category.getSubjectId());
            it.setExpectedDuration(category.getExpectedDuration());
        });
    }

    /**
     * Retrieves category by its id.
     */
    public Category getCategoryById(Long categoryId) {
        return categoryRepository
            .findById(categoryId)
            .orElseThrow(() -> CategoryException.CATEGORY_NOT_FOUND);
    }

    /**
     * Retrieves all categories by a given subject id.
     */
    public List<Category> findCategoriesBySubjectId(Long subjectId) {
        return categoryRepository.findBySubjectId(subjectId);
    }

    /**
     * Creates a category.
     */
    public Category createCategory(Long subjectId, String categoryName, Integer expectedDuration) {
        if (categoryRepository.findFirstBySubjectIdAndName(subjectId, categoryName).isPresent()) {
            throw CategoryException.CATEGORY_ALREADY_EXIST;
        }

        final Subject subject = subjectRepository
            .findById(subjectId)
            .orElseThrow(() -> SubjectException.SUBJECT_NOT_FOUND);
        userService.checkUserToBe(subject.getUserId());

        return categoryRepository.save(createBean(Category.class, it -> {
            it.setUserId(subject.getUserId());
            it.setSubjectId(subjectId);
            it.setName(categoryName);
            it.setExpectedDuration(expectedDuration);
        }));
    }

    /**
     * Deletes a category.
     * @param categoryId the id of the category to delete.
     */
    public void deleteCategory(Long categoryId) {
        final Category category = getCategoryById(categoryId);

        final Subject subject = subjectRepository
            .findById(category.getSubjectId())
            .orElseThrow(() -> SubjectException.SUBJECT_NOT_FOUND);
        userService.checkUserToBe(subject.getUserId());

        categoryRepository.deleteById(categoryId);
    }

    /**
     * Updates a category.
     */
    public Category updateCategory(Long categoryId, CategoryDto categoryDto) {
        final Category category = getCategoryById(categoryId);
        userService.checkUserToBe(category.getUserId());

        if (categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }

        if (category.getExpectedDuration() != null) {
            category.setExpectedDuration(category.getExpectedDuration());
        }

        return categoryRepository.save(category);
    }

    /**
     * Gets all historical comments' content of a category.
     * @param categoryId the id of the category
     * @return a list of task comments' content
     */
    public List<String> getHistoricalComment(Long categoryId) {
        final List<Task> taskList = taskRepository.findAllByCategoryId(categoryId);
        final List<Long> taskIdList = taskList.stream().map(Task::getId).toList();
        final Set<Long> taskIdSet = new HashSet<>(taskIdList);

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<TaskComment> criteriaQuery = criteriaBuilder.createQuery(TaskComment.class);
        final Root<TaskComment> root = criteriaQuery.from(TaskComment.class);
        final List<Predicate> predicateList = let(new ArrayList<>(), it -> {
            it.add(criteriaBuilder.isNull(root.get("deletedAt")));
            it.add(criteriaBuilder.in(root.get("taskId")).value(taskIdSet));
        });

        criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[0])));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));

        final TypedQuery<TaskComment> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setMaxResults(20);

        final List<TaskComment> taskCommentList = typedQuery.getResultList();

        // Extract comment content (distinct)
        return taskCommentList.stream()
            .map(TaskComment::getContent)
            .distinct()
            .toList();
    }
}

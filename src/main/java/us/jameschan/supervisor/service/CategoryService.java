package us.jameschan.supervisor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.jameschan.supervisor.dto.CategoryDto;
import us.jameschan.supervisor.exception.CategoryException;
import us.jameschan.supervisor.exception.SubjectException;
import us.jameschan.supervisor.model.Category;
import us.jameschan.supervisor.model.Subject;
import us.jameschan.supervisor.repository.CategoryRepository;
import us.jameschan.supervisor.repository.SubjectRepository;

import java.util.List;

import static us.jameschan.neater.StaticFunctions.createBean;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubjectRepository subjectRepository;
    private final UserService userService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           SubjectRepository subjectRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.subjectRepository = subjectRepository;
        this.userService = userService;
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
}

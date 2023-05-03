package us.jameschan.supervisor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.jameschan.supervisor.dto.CategoryDto;
import us.jameschan.supervisor.exception.CategoryException;
import us.jameschan.supervisor.model.Category;
import us.jameschan.supervisor.repository.CategoryRepository;

import java.util.List;

import static us.jameschan.supervisor.common.HelperFunctions.apply;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Converts a category to category DTO.
     */
    public CategoryDto toCategoryDto(Category category) {
        return apply(new CategoryDto(), it -> {
            it.setId(category.getId());
            it.setName(category.getName());
            it.setSubjectId(category.getSubjectId());
        });
    }

    /**
     * Retrieves category by its id.
     */
    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> CategoryException.CATEGORY_NOT_FOUND);
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
    public Category createCategory(Long subjectId, String categoryName) {
        if (categoryRepository.findFirstBySubjectIdAndName(subjectId, categoryName).isPresent()) {
            throw CategoryException.CATEGORY_ALREADY_EXIST;
        }

        return categoryRepository.save(apply(new Category(), it -> {
            it.setSubjectId(subjectId);
            it.setName(categoryName);
        }));
    }

    /**
     * Deletes a category.
     * @param categoryId the id of the category to delete.
     */
    public void deleteCategory(Long categoryId) {
        if (categoryRepository.findById(categoryId).isEmpty()) {
            throw CategoryException.CATEGORY_NOT_FOUND;
        }

        categoryRepository.deleteById(categoryId);
    }

    /**
     * Updates a category.
     */
    public Category updateCategory(Long categoryId, CategoryDto categoryDto) {
        if (categoryRepository.findById(categoryId).isEmpty()) {
            throw CategoryException.CATEGORY_NOT_FOUND;
        }

        final Category category = apply(new Category(), it -> {
            it.setId(categoryId);
            it.setName(categoryDto.getName());
        });

        return categoryRepository.save(category);
    }
}

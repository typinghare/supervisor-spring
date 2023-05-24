package us.jameschan.supervisor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import us.jameschan.supervisor.annotation.Message;
import us.jameschan.supervisor.dto.CategoryDto;
import us.jameschan.supervisor.service.CategoryService;

@Controller()
@RequestMapping("categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    @ResponseBody
    @Message("Created a category successfully.")
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.toCategoryDto(
                categoryService.createCategory(categoryDto.getSubjectId(), categoryDto.getName())
        );
    }

    @PutMapping("/{categoryId}/")
    @ResponseBody
    @Message("Updated a category successfully.")
    public CategoryDto updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryDto categoryDto
    ) {
        return categoryService.toCategoryDto(categoryService.updateCategory(categoryId, categoryDto));
    }

    @DeleteMapping("/{categoryId}/")
    @ResponseBody
    @Message("Deleted a category successfully.")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}

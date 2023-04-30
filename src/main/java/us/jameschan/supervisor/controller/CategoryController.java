package us.jameschan.supervisor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import us.jameschan.supervisor.annotation.Message;
import us.jameschan.supervisor.dto.CategoryDto;
import us.jameschan.supervisor.service.CategoryService;

@Controller()
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    @ResponseBody
    @Message("Successfully created a category.")
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.toCategoryDto(
            categoryService.createCategory(categoryDto.getSubjectId(), categoryDto.getName())
        );
    }

    @PutMapping("/{categoryId}")
    @ResponseBody
    @Message("Successfully updated a category.")
    public CategoryDto updateCategory(
        @PathVariable("categoryId") Long categoryId,
        @RequestBody CategoryDto categoryDto
    ) {
        return categoryService.toCategoryDto(categoryService.updateCategory(categoryId, categoryDto));
    }

    @DeleteMapping("/{categoryId}")
    @ResponseBody
    @Message("Successfully deleted a category.")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}

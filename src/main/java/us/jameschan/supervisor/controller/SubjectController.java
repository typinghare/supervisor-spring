package us.jameschan.supervisor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import us.jameschan.supervisor.annotation.Message;
import us.jameschan.supervisor.dto.CategoryDto;
import us.jameschan.supervisor.dto.SubjectDto;
import us.jameschan.supervisor.service.CategoryService;
import us.jameschan.supervisor.service.SubjectService;

import java.util.List;

@Controller()
@RequestMapping("subjects")
public class SubjectController {
    private final SubjectService subjectService;

    private final CategoryService categoryService;

    public SubjectController(SubjectService subjectService, CategoryService categoryService) {
        this.subjectService = subjectService;
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    @ResponseBody
    @Message("Successfully created a subject.")
    public SubjectDto createSubject(@RequestBody SubjectDto subjectDto) {
        return subjectService.toSubjectDto(subjectService.createSubject(subjectDto.getName()));
    }

    @PutMapping("/{subjectId}")
    @ResponseBody
    @Message("Successfully updated a subject.")
    public SubjectDto deleteSubject(
        @PathVariable("subjectId") Long subjectId,
        @RequestBody SubjectDto subjectDto
    ) {
        return subjectService.toSubjectDto(subjectService.updateSubject(subjectId, subjectDto));
    }

    @DeleteMapping("/{subjectId}")
    @ResponseBody
    @Message("")
    public void deleteSubject(@PathVariable("subjectId") Long subjectId) {
        subjectService.deleteSubject(subjectId);
    }

    @GetMapping("/{subjectId}/categories")
    @ResponseBody
    @Message("Successfully get all categories.")
    public List<CategoryDto> getCategoriesForSubject(@PathVariable("subjectId") Long subjectId) {
        return categoryService.findCategoriesBySubjectId(subjectId).stream()
            .map(categoryService::toCategoryDto).toList();
    }
}

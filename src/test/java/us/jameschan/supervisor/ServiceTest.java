package us.jameschan.supervisor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import us.jameschan.supervisor.model.Category;
import us.jameschan.supervisor.service.CategoryService;

import java.util.List;

@SpringBootTest(classes = {
    us.jameschan.supervisor.SupervisorApplication.class
})
public class ServiceTest {
    private final CategoryService categoryService;

    @Autowired
    public ServiceTest(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Test
    void testFindCategory() {
        final List<Category> categoryList = categoryService.findCategoriesBySubjectId(1L);
        System.out.println(categoryList);
    }
}

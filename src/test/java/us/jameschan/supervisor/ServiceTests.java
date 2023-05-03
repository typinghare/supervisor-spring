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
public class ServiceTests {
    private final CategoryService categoryService;

    @Autowired
    public ServiceTests(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Test
    void testFindCategory() {
        final List<Category> categoryList = categoryService.findCategoriesBySubjectId(1L);
//        categoryList.forEach(category -> {
//            System.out.println(category.getSubject());
//        });
    }
}

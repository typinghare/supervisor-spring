package us.jameschan.supervisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.jameschan.supervisor.model.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findBySubjectId(Long subjectId);

    Optional<Category> findFirstBySubjectIdAndName(Long subjectId, String name);
}

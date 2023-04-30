package us.jameschan.supervisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.jameschan.supervisor.model.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findFirstByUserIdAndName(Long userId, String name);

    List<Subject> findByUserId(Long userId);
}

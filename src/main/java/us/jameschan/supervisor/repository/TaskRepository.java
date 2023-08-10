package us.jameschan.supervisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.jameschan.supervisor.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    Optional<Task> findFirstByUserIdAndStage(Long userId, Integer stage);

    @Query("SELECT t FROM Task t WHERE t.categoryId = :categoryId AND t.deletedAt IS NULL ORDER BY ID DESC")
    List<Task> findAllByCategoryId(Long categoryId);
}

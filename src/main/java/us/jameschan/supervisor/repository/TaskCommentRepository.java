package us.jameschan.supervisor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.jameschan.supervisor.model.TaskComment;

import java.util.List;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    List<TaskComment> findAllByTaskId(Long taskId);

    @Query("SELECT tc FROM TaskComment tc WHERE tc.taskId IN :taskIdList AND tc.deletedAt IS NULL ORDER BY ID DESC")
    @Deprecated
    List<TaskComment> findAllByTaskIdList(List<Long> taskIdList);
}

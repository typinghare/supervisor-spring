package me.jameschan.supervisor.repository;

import me.jameschan.supervisor.model.Project;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @NotNull List<Project> findAllByUserId(Long userId);
}

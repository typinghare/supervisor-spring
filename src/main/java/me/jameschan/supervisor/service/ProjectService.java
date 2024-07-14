package me.jameschan.supervisor.service;

import me.jameschan.supervisor.exception.ResourceNotFoundException;
import me.jameschan.supervisor.model.Project;
import me.jameschan.supervisor.repository.ProjectRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(@NotNull final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public @NotNull Project createProject(@NotNull final Long userId, @NotNull final String name) {
        final var project = new Project();
        project.setUserId(userId);
        project.setName(name);

        return projectRepository.save(project);
    }

    /**
     * Retrieves all projects.
     * @param userId The id of the user.
     * @return All projects associated with the given user ID.
     */
    public @NotNull List<Project> getAllProjects(final long userId) {
        return projectRepository.findAllByUserId(userId);
    }

    public @NotNull Project getProjectById(final long projectId) {
        return projectRepository.findById(projectId)
            .orElseThrow(() -> ResourceNotFoundException.PROJECT);
    }
}

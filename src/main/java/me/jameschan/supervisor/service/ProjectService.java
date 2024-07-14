package me.jameschan.supervisor.service;

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
        return projectRepository.save(
            new Project() {
                {
                    setUserId(userId);
                    setName(name);
                }
            });
    }

    public @NotNull List<Project> getAllProjects(@NotNull final Long userId) {
        return projectRepository.findAllByUserId(userId);
    }
}

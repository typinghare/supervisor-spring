package me.jameschan.supervisor.service;

import me.jameschan.supervisor.model.Project;
import me.jameschan.supervisor.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(final Long userId, final String name) {
        return projectRepository.save(new Project() {{
            setUserId(userId);
            setName(name);
        }});
    }

    public List<Project> getAllProjects(final Long userId) {
        return projectRepository.findAllByUserId(userId);
    }
}

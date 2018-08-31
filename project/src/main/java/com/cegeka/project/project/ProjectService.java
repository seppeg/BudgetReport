package com.cegeka.project.project;

import com.cegeka.project.infrastructure.UnexistingResourceException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectRAssembler projectRAssembler;

    public Collection<ProjectR> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectRAssembler::assemble)
                .collect(toList());
    }

    public ProjectR createProject(CreateProjectR projectR) throws ProjectAlreadyExistsException {
        validateProjectName(projectR);
        Project project = projectRepository.save(new Project(projectR.getName(), projectR.getBudget()));
        return projectRAssembler.assemble(project);
    }

    public ProjectR updateProject(UUID projectId, CreateProjectR projectR) throws UnexistingResourceException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new UnexistingResourceException("project", projectId));
        project.update(projectR.getName(), projectR.getBudget());
        return projectRAssembler.assemble(project);
    }

    private void validateProjectName(CreateProjectR projectR) throws ProjectAlreadyExistsException {
        if (projectRepository.existsProjectByName(projectR.getName())) {
            throw new ProjectAlreadyExistsException(projectR.getName());
        }
    }

    public Optional<String> getProjectName(UUID projectId) {
        return projectRepository.findById(projectId).map(Project::getName);
    }
}

package com.cegeka.project.project;

import com.cegeka.project.infrastructure.UnexistingResourceException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
@Log4j2
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/project")
    public Collection<ProjectR> getProjects() {
        return projectService.getAllProjects();
    }

    @PutMapping("/project")
    public ProjectR createProject(@RequestBody CreateProjectR projectR) throws ProjectAlreadyExistsException {
        return projectService.createProject(projectR);
    }

    @PostMapping("/project/{projectId}")
    public ProjectR updateProject(@PathVariable("projectId") UUID projectId, @RequestBody CreateProjectR projectR) throws UnexistingResourceException {
        return projectService.updateProject(projectId, projectR);
    }
}

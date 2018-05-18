package com.cegeka.project.controller;

import com.cegeka.project.domain.Project;
import com.cegeka.project.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/project")
    public Collection<ProjectR> getAll() {
        return projectService.getAllProjects()
                .stream()
                .map(this::toProjectR)
                .collect(toList());
    }

    @PutMapping("/project")
    public void createProject(@RequestBody ProjectR projectR){
        projectService.createProject(projectR);
    }

    private ProjectR toProjectR(Project project) {
        return new ProjectR(project.getDescription(), project.getWorkorder(), project.getBudget(), project.getHoursSpent());
    }
}

package com.cegeka.project.controller;

import com.cegeka.project.domain.Project;
import com.cegeka.project.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @RequestMapping("/")
    public Collection<ProjectR> getAll() {
        return projectService.getAllProjects()
                .stream()
                .map(this::toProjectR)
                .collect(toList());
    }

    private ProjectR toProjectR(Project project) {
        return new ProjectR(project.getDescription(), project.getWorkorder(), project.getBudget(), project.getHoursSpent());
    }
}

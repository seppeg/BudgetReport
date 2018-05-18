package com.cegeka.project.controller;

import com.cegeka.project.domain.Project;
import com.cegeka.project.domain.Workorder;
import com.cegeka.project.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @RequestMapping("/project")
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
        return new ProjectR(project.getDescription(), mapToWorkordersR(project), project.getBudget(), project.getHoursSpent());
    }

    private List<WorkorderR> mapToWorkordersR(Project project) {
        return project.getWorkorders().stream().map(workorder -> new WorkorderR(workorder.getId(),workorder.getWorkorder())).collect(toList());
    }
}

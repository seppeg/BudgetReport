package com.cegeka.project.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectRAssembler {

    public ProjectR assemble(Project project){
        return new ProjectR(project.getId(), project.getName(), project.getBudget(), project.getHoursSpent());
    }
}

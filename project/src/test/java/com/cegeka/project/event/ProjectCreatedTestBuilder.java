package com.cegeka.project.event;

import com.cegeka.project.domain.Workorder;

import java.util.List;
import java.util.UUID;

public final class ProjectCreatedTestBuilder {
    private UUID id;
    private String description;
    private List<Workorder> workorders;
    private double budget;

    private ProjectCreatedTestBuilder() {
    }

    public static ProjectCreatedTestBuilder createProject() {
        return new ProjectCreatedTestBuilder();
    }

    public ProjectCreatedTestBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public ProjectCreatedTestBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ProjectCreatedTestBuilder workorders(List<Workorder> workorders) {
        this.workorders = workorders;
        return this;
    }

    public ProjectCreatedTestBuilder budget(double budget) {
        this.budget = budget;
        return this;
    }

    public ProjectCreated build() {
        return new ProjectCreated(id, description, workorders, budget);
    }
}

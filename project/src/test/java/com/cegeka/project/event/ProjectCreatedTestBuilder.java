package com.cegeka.project.event;

import java.util.UUID;

public final class ProjectCreatedTestBuilder {
    private UUID id;
    private String description;
    private String workorder;
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

    public ProjectCreatedTestBuilder workorder(String workorder) {
        this.workorder = workorder;
        return this;
    }

    public ProjectCreatedTestBuilder budget(double budget) {
        this.budget = budget;
        return this;
    }

    public ProjectCreated build() {
        return new ProjectCreated(id, description, workorder, budget);
    }
}

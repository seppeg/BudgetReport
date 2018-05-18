package com.cegeka.project.event;

import java.util.UUID;

public final class ProjectCreatedBuilder {
    private UUID id;
    private String description;
    private String workorder;
    private double budget;

    private ProjectCreatedBuilder() {
    }

    public static ProjectCreatedBuilder projectCreated() {
        return new ProjectCreatedBuilder();
    }

    public ProjectCreatedBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public ProjectCreatedBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ProjectCreatedBuilder workorder(String workorder) {
        this.workorder = workorder;
        return this;
    }

    public ProjectCreatedBuilder budget(double budget) {
        this.budget = budget;
        return this;
    }

    public ProjectCreated build() {
        return new ProjectCreated(id, description, workorder, budget);
    }
}
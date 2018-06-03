package com.cegeka.project.event;

import com.cegeka.project.project.ProjectCreated;

import java.util.List;
import java.util.UUID;

public final class ProjectCreatedTestBuilder {
    private UUID id;
    private String name;
    private List<String> workOrders;
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

    public ProjectCreatedTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProjectCreatedTestBuilder workorders(List<String> workOrders) {
        this.workOrders = workOrders;
        return this;
    }

    public ProjectCreatedTestBuilder budget(double budget) {
        this.budget = budget;
        return this;
    }

    public ProjectCreated build() {
        return new ProjectCreated(id, name, workOrders, budget);
    }
}

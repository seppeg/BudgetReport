package com.cegeka.project.controller;

import com.cegeka.project.project.ProjectR;
import com.cegeka.project.workorder.WorkOrderR;

import java.util.List;
import java.util.UUID;

public final class ProjectRTestBuilder {
    private UUID projectId;
    private String name;
    private List<WorkOrderR> workorder;
    private double budget;
    private double hoursSpent;

    private ProjectRTestBuilder() {
    }

    public static ProjectRTestBuilder projectR() {
        return new ProjectRTestBuilder();
    }

    public ProjectRTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProjectRTestBuilder workorder(List<WorkOrderR> workorder) {
        this.workorder = workorder;
        return this;
    }

    public ProjectRTestBuilder budget(double budget) {
        this.budget = budget;
        return this;
    }

    public ProjectRTestBuilder hoursSpent(double hoursSpent) {
        this.hoursSpent = hoursSpent;
        return this;
    }

    public ProjectRTestBuilder id(UUID id) {
        this.projectId = id;
        return this;
    }

    public ProjectR build() {
        return new ProjectR(projectId, name, workorder, budget, hoursSpent);
    }
}

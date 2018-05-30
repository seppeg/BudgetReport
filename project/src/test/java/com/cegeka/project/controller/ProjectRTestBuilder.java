package com.cegeka.project.controller;

import java.util.List;

public final class ProjectRTestBuilder {
    private String description;
    private List<WorkorderR> workorder;
    private double budget;
    private double hoursSpent;

    private ProjectRTestBuilder() {
    }

    public static ProjectRTestBuilder projectR() {
        return new ProjectRTestBuilder();
    }

    public ProjectRTestBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ProjectRTestBuilder workorder(List<WorkorderR> workorder) {
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

    public ProjectR build() {
        return new ProjectR(description, workorder, budget, hoursSpent);
    }
}

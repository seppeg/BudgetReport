package com.cegeka.project.project;

public final class ProjectTestBuilder {
    private String name = "project";
    private double budget = 100;
    private double hoursSpent = 5;

    private ProjectTestBuilder() {
    }

    public static ProjectTestBuilder project() {
        return new ProjectTestBuilder();
    }

    public ProjectTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProjectTestBuilder budget(double budget) {
        this.budget = budget;
        return this;
    }

    public ProjectTestBuilder hoursSpent(double hoursSpent) {
        this.hoursSpent = hoursSpent;
        return this;
    }

    public Project build() {
        Project project = new Project(name, budget);
        project.addHoursSpent(hoursSpent);
        return project;
    }
}

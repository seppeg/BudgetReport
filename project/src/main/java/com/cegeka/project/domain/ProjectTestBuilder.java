package com.cegeka.project.domain;

import java.util.List;

import static java.util.List.of;

public class ProjectTestBuilder {

    private int hours;

    public static ProjectTestBuilder project() {
        return new ProjectTestBuilder();
    }

    public ProjectTestBuilder hoursSpent(int hours) {
        this.hours = hours;
        return this;
    }

    public Project build() {
        Project project = new Project(of(new Workorder("COCFL871.004")), "description", 500);
        project.addHoursSpent(hours);
        return project;
    }
}

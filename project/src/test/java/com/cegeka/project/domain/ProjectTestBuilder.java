package com.cegeka.project.domain;

import com.cegeka.project.domain.project.Project;
import com.cegeka.project.domain.workorder.WorkOrder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public final class ProjectTestBuilder {
    private String description = "description";
    private List<WorkOrder> workOrders = newArrayList();
    private double budget = 500;
    private double hoursSpent = 0;

    private ProjectTestBuilder() {
    }

    public static ProjectTestBuilder project() {
        return new ProjectTestBuilder();
    }

    public ProjectTestBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ProjectTestBuilder workorders(List<WorkOrder> workOrders) {
        this.workOrders = workOrders;
        return this;
    }

    public ProjectTestBuilder workorder(WorkOrder workOrder) {
        this.workOrders.add(workOrder);
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
        Project project = new Project(description, workOrders, budget);
        ReflectionTestUtils.setField(project, "hoursSpent", hoursSpent);
        return project;
    }
}

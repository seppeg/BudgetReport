package com.cegeka.project.domain;

import com.cegeka.project.project.Project;
import com.cegeka.project.project.ProjectYearBudget;
import com.cegeka.project.workorder.WorkOrder;
import org.assertj.core.util.Sets;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.SortedSet;

import static com.google.common.collect.Lists.newArrayList;

public final class ProjectTestBuilder {
    private String name = "description";
    private List<WorkOrder> workOrders = newArrayList();
    private SortedSet<ProjectYearBudget> budgets = Sets.newTreeSet(new ProjectYearBudget(2018, 500));
    private double hoursSpent = 0;

    private ProjectTestBuilder() {
    }

    public static ProjectTestBuilder project() {
        return new ProjectTestBuilder();
    }

    public ProjectTestBuilder name(String name) {
        this.name = name;
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

    public ProjectTestBuilder budget(SortedSet<ProjectYearBudget> budget) {
        this.budgets = budget;
        return this;
    }

    public ProjectTestBuilder hoursSpent(double hoursSpent) {
        this.hoursSpent = hoursSpent;
        return this;
    }

    public Project build() {
        Project project = new Project(name, workOrders, budgets);
        ReflectionTestUtils.setField(project, "hoursSpent", hoursSpent);
        return project;
    }
}

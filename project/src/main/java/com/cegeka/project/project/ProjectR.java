package com.cegeka.project.project;

import com.cegeka.project.workorder.WorkOrderR;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

@Value
@AllArgsConstructor
public class ProjectR {
    private final UUID id;
    private final String name;
    private final List<WorkOrderR> workOrders;
    private final SortedSet<ProjectYearBudgetR> budgets;
    private final double hoursSpent;

    public ProjectR(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.workOrders = project.getWorkOrders().stream().map(WorkOrderR::new).collect(toList());
        this.budgets = project.getBudgets().stream().map(ProjectYearBudgetR::new).collect(toCollection(TreeSet::new));
        this.hoursSpent = project.getHoursSpent();
    }

}

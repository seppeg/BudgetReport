package com.cegeka.project.controller;

import com.cegeka.project.domain.project.Project;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Value
@AllArgsConstructor
public class ProjectR {
    private final UUID id;
    private final String name;
    private final List<WorkOrderR> workOrders;
    private final double budget;
    private final double hoursSpent;

    public ProjectR(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.workOrders = project.getWorkOrders().stream().map(WorkOrderR::new).collect(toList());
        this.budget = project.getBudget();
        this.hoursSpent = project.getHoursSpent();
    }

}

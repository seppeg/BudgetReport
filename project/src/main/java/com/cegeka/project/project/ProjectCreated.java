package com.cegeka.project.project;

import com.cegeka.project.workorder.WorkOrderR;
import lombok.*;

import java.util.List;
import java.util.SortedSet;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreated {

    private UUID id;
    private String name;
    private List<String> workOrders;
    private SortedSet<ProjectYearBudgetR> budgets;

    public ProjectCreated(ProjectR projectR){
        this.id = UUID.randomUUID();
        this.name = projectR.getName();
        this.budgets = projectR.getBudgets();
        this.workOrders = projectR.getWorkOrders().stream().map(WorkOrderR::getWorkOrder).collect(toList());
    }
}

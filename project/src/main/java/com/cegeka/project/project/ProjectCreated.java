package com.cegeka.project.project;

import com.cegeka.project.workorder.WorkOrderR;
import lombok.*;

import java.util.List;
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
    private double budget;

    public ProjectCreated(ProjectR projectR){
        this.id = UUID.randomUUID();
        this.name = projectR.getName();
        this.budget = projectR.getBudget();
        this.workOrders = projectR.getWorkOrders().stream().map(WorkOrderR::getWorkOrder).collect(toList());
    }
}

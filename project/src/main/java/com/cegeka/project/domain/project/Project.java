package com.cegeka.project.domain.project;

import com.cegeka.project.domain.workorder.WorkOrder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Project {

    @Id
    private UUID id;

    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "project_id", nullable = false)
    private Collection<WorkOrder> workOrders;

    private double budget;

    private double hoursSpent;

    public Project(String name, Collection<WorkOrder> workOrders, double budget) {
        this.id = UUID.randomUUID();
        this.workOrders = workOrders;
        this.name = name;
        this.hoursSpent = 0;
        this.budget = budget;
    }

    public void addHoursSpent(double hoursSpent) {
        this.hoursSpent += hoursSpent;
    }

    public Optional<WorkOrder> getWorkOrder(String workOrder){
        return getWorkOrders().stream().filter(w -> w.getWorkOrder().equals(workOrder)).findFirst();
    }

    public void removeHoursSpent(double hours) {
        this.hoursSpent -= hours;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}

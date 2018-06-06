package com.cegeka.project.project;

import com.cegeka.project.workorder.WorkOrder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Project {

    @Id
    private UUID id;

    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    private Collection<WorkOrder> workOrders;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "project_id", nullable = false)
    @OrderBy("year")
    private SortedSet<ProjectYearBudget> budgets = new TreeSet<>();

    private double hoursSpent;

    public Project(String name, Collection<WorkOrder> workOrders, Set<ProjectYearBudget> budgets) {
        this.id = UUID.randomUUID();
        this.workOrders = workOrders;
        this.name = name;
        this.hoursSpent = 0;
        this.budgets.addAll(budgets);
    }

    public void addHoursSpent(double hoursSpent) {
        this.hoursSpent += hoursSpent;
    }

    public void removeHoursSpent(double hours) {
        this.hoursSpent -= hours;
    }

    public void update(String name, List<WorkOrder> workOrders, Set<ProjectYearBudget> budgets) {
        this.name = name;
        this.workOrders.clear();
        this.workOrders.addAll(workOrders);
        this.budgets.clear();
        this.budgets.addAll(budgets);
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

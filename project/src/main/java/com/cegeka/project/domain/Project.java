package com.cegeka.project.domain;

import com.cegeka.project.event.ProjectCreated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Project {

    @Id
    private UUID id;
    private String description;
    @OneToMany
    @JoinColumn(referencedColumnName = "project_id", nullable = false)
    private List<Workorder> workorders;
    private double budget;
    private double hoursSpent;

    Project() {
    }

    public Project(List<Workorder> workorders, String description, double budget) {
        this.id = UUID.randomUUID();
        this.workorders = workorders;
        this.description = description;
        this.hoursSpent = 0;
        this.budget = budget;
    }

    public Project(ProjectCreated projectCreated){
        this.id = UUID.randomUUID();
        this.workorders = projectCreated.getWorkorders();
        this.description = projectCreated.getDescription();
        this.budget = projectCreated.getBudget();
        this.hoursSpent = 0;
    }

    public void addHoursSpent(double hoursSpent) {
        this.hoursSpent += hoursSpent;
    }

    public double getBudget() {
        return budget;
    }

    public double getHoursSpent() {
        return hoursSpent;
    }

    public List<Workorder> getWorkorders() {
        return workorders;
    }

    public String getDescription() {
        return description;
    }

    public void removeHoursSpent(double hours) {
        this.hoursSpent -= hours;
    }
}

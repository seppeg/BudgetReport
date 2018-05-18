package com.cegeka.project.domain;

import com.cegeka.project.event.ProjectCreated;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Project {

    @Id
    private UUID id;
    private String description;
    private String workorder;
    private double budget;
    private double hoursSpent;

    Project() {
    }

    Project(String workorder, String description, double budget) {
        this.id = UUID.randomUUID();
        this.workorder = workorder;
        this.description = description;
        this.hoursSpent = 0;
        this.budget = budget;
    }

    public Project(ProjectCreated projectCreated){
        this.id = UUID.randomUUID();
        this.workorder = projectCreated.getWorkorder();
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

    public String getWorkorder() {
        return workorder;
    }

    public String getDescription() {
        return description;
    }

    public void removeHoursSpent(double hours) {
        this.hoursSpent -= hours;
    }
}

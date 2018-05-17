package com.cegeka.project.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Project {

    @Id
    @GeneratedValue
    private long id;
    private String description;
    private String workorder;
    private double budget;
    private double hoursSpent;

    Project() {
    }

    public Project(String workorder, String description, double budget) {
        this.workorder = workorder;
        this.description = description;
        this.hoursSpent = 0;
        this.budget = budget;
    }

    public void addHoursSpent(double hoursSpent) {
        this.hoursSpent += hoursSpent;
    }
}

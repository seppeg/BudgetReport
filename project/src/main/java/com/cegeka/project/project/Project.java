package com.cegeka.project.project;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Project {

    @Id
    private UUID id;

    private String name;

    private double budget;

    private double hoursSpent;

    public Project(String name, double budget) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.budget = budget;
        this.hoursSpent = 0;
    }

    public void addHoursSpent(double hoursSpent) {
        this.hoursSpent += hoursSpent;
    }

    public void removeHoursSpent(double hours) {
        this.hoursSpent -= hours;
    }

    public void update(String name, double budget) {
        this.name = name;
        this.budget = budget;
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

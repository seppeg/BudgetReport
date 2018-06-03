package com.cegeka.project.project;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

import static java.lang.Integer.compare;

@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString
public class ProjectYearBudget implements Comparable<ProjectYearBudget> {

    @Id
    private UUID id;

    private int year;

    private double budget;

    public ProjectYearBudget(int year, double budget){
        this.id = UUID.randomUUID();
        this.year = year;
        this.budget = budget;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectYearBudget that = (ProjectYearBudget) o;

        return id.equals(that.id);
    }

    @Override
    public final int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(ProjectYearBudget o) {
        return compare(year, o.year);
    }
}

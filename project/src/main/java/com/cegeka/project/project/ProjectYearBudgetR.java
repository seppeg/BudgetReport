package com.cegeka.project.project;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ProjectYearBudgetR implements Comparable<ProjectYearBudgetR> {

    private final int year;
    private final double budget;

    public ProjectYearBudgetR(ProjectYearBudget projectYearBudget){
        this.year = projectYearBudget.getYear();
        this.budget = projectYearBudget.getBudget();
    }

    @Override
    public int compareTo(ProjectYearBudgetR o) {
        return Integer.compare(year, o.year);
    }
}

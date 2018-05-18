package com.cegeka.project.controller;

import lombok.Value;

import java.util.List;

@Value
public class ProjectR {
    private final String description;
    private final List<WorkorderR> workorder;
    private final double budget;
    private final double hoursSpent;

}

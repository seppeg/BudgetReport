package com.cegeka.project.controller;

import lombok.Value;

@Value
public class ProjectR {
    private final String description;
    private final String workorder;
    private final double budget;
    private final double hoursSpent;
}

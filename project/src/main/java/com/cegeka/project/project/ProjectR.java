package com.cegeka.project.project;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor
public class ProjectR {
    private final UUID id;
    private final String name;
    private final double budget;
    private final double hoursSpent;

}

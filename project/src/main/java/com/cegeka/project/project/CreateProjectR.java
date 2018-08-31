package com.cegeka.project.project;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor
public class CreateProjectR {
    private final String name;
    private final double budget;

}

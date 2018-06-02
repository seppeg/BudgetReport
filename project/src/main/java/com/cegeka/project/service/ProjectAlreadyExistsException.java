package com.cegeka.project.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProjectAlreadyExistsException extends Exception {

    private final String projectName;

    @Override
    public String getMessage() {
        return "Project with name '"+projectName+"' already exists.";
    }
}

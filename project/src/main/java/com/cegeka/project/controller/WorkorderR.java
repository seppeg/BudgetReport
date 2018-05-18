package com.cegeka.project.controller;

import java.util.UUID;

public class WorkorderR {
    private final UUID id;
    private final String workorder;

    public WorkorderR(UUID id, String workorder) {
        this.id = id;
        this.workorder = workorder;
    }

    public UUID getId() {
        return id;
    }

    public String getWorkorder() {
        return workorder;
    }
}

package com.cegeka.project.service;

import java.time.LocalDate;
import java.util.UUID;

public final class ProjectTOTestBuilder {
    private UUID id;
    private String workorder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

    private ProjectTOTestBuilder() {
    }

    public static ProjectTOTestBuilder projectTO() {
        return new ProjectTOTestBuilder();
    }

    public ProjectTOTestBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public ProjectTOTestBuilder workorder(String workorder) {
        this.workorder = workorder;
        return this;
    }

    public ProjectTOTestBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public ProjectTOTestBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ProjectTOTestBuilder hours(double hours) {
        this.hours = hours;
        return this;
    }

    public ProjectTOTestBuilder employee(String employee) {
        this.employee = employee;
        return this;
    }

    public BookingCreated build() {
        return new BookingCreated(id, workorder, date, description, hours, employee);
    }
}

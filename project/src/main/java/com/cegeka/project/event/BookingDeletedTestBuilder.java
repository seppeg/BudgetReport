package com.cegeka.project.event;

import java.time.LocalDate;
import java.util.UUID;

public final class BookingDeletedTestBuilder {
    private UUID id;
    private String workorder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

    private BookingDeletedTestBuilder() {
    }

    public static BookingDeletedTestBuilder bookingDeleted() {
        return new BookingDeletedTestBuilder();
    }

    public BookingDeletedTestBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public BookingDeletedTestBuilder workorder(String workorder) {
        this.workorder = workorder;
        return this;
    }

    public BookingDeletedTestBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public BookingDeletedTestBuilder description(String description) {
        this.description = description;
        return this;
    }

    public BookingDeletedTestBuilder hours(double hours) {
        this.hours = hours;
        return this;
    }

    public BookingDeletedTestBuilder employee(String employee) {
        this.employee = employee;
        return this;
    }

    public BookingDeleted build() {
        return new BookingDeleted(id, workorder, date, description, hours, employee);
    }
}

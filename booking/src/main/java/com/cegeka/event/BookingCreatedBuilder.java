package com.cegeka.event;

import java.time.LocalDate;
import java.util.UUID;

public final class BookingCreatedBuilder {
    private UUID id;
    private String workorder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

    private BookingCreatedBuilder() {
    }

    public static BookingCreatedBuilder createBooking() {
        return new BookingCreatedBuilder();
    }

    public BookingCreatedBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public BookingCreatedBuilder workorder(String workorder) {
        this.workorder = workorder;
        return this;
    }

    public BookingCreatedBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public BookingCreatedBuilder description(String description) {
        this.description = description;
        return this;
    }

    public BookingCreatedBuilder hours(double hours) {
        this.hours = hours;
        return this;
    }

    public BookingCreatedBuilder employee(String employee) {
        this.employee = employee;
        return this;
    }

    public BookingCreated build() {
        return new BookingCreated(id, workorder, date, description, hours, employee);
    }
}

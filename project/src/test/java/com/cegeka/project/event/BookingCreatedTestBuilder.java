package com.cegeka.project.event;

import com.cegeka.project.booking.BookingCreated;

import java.time.LocalDate;
import java.util.UUID;

public final class BookingCreatedTestBuilder {
    private UUID id;
    private String workorder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

    private BookingCreatedTestBuilder() {
    }

    public static BookingCreatedTestBuilder bookingCreated() {
        return new BookingCreatedTestBuilder();
    }

    public BookingCreatedTestBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public BookingCreatedTestBuilder workorder(String workorder) {
        this.workorder = workorder;
        return this;
    }

    public BookingCreatedTestBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public BookingCreatedTestBuilder description(String description) {
        this.description = description;
        return this;
    }

    public BookingCreatedTestBuilder hours(double hours) {
        this.hours = hours;
        return this;
    }

    public BookingCreatedTestBuilder employee(String employee) {
        this.employee = employee;
        return this;
    }

    public BookingCreated build() {
        return new BookingCreated(id, workorder, date, description, hours, employee);
    }
}

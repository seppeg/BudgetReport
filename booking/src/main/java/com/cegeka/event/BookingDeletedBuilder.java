package com.cegeka.event;

import java.time.LocalDate;
import java.util.UUID;

public final class BookingDeletedBuilder {
    private UUID id;
    private String workOrder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

    private BookingDeletedBuilder() {
    }

    public static BookingDeletedBuilder deletedBooking() {
        return new BookingDeletedBuilder();
    }

    public BookingDeletedBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public BookingDeletedBuilder workOrder(String workorder) {
        this.workOrder = workorder;
        return this;
    }

    public BookingDeletedBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public BookingDeletedBuilder description(String description) {
        this.description = description;
        return this;
    }

    public BookingDeletedBuilder hours(double hours) {
        this.hours = hours;
        return this;
    }

    public BookingDeletedBuilder employee(String employee) {
        this.employee = employee;
        return this;
    }

    public BookingDeleted build() {
        return new BookingDeleted(id, workOrder, date, description, hours, employee);
    }
}

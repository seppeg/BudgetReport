package com.cegeka.api;

import java.time.LocalDate;

public final class BookingRTestBuilder {
    private String workorder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

    private BookingRTestBuilder() {
    }

    public static BookingRTestBuilder booking() {
        return new BookingRTestBuilder();
    }

    public BookingRTestBuilder workorder(String workorder) {
        this.workorder = workorder;
        return this;
    }

    public BookingRTestBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public BookingRTestBuilder description(String description) {
        this.description = description;
        return this;
    }

    public BookingRTestBuilder hours(double hours) {
        this.hours = hours;
        return this;
    }

    public BookingRTestBuilder employee(String employee) {
        this.employee = employee;
        return this;
    }

    public BookingR build() {
        return new BookingR(workorder, date, description, hours, employee);
    }
}

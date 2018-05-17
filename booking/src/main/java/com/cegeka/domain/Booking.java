package com.cegeka.domain;

import com.cegeka.event.BookingCreated;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Booking {

    @Id
    private UUID id;
    private String workorder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

    Booking(){

    }

    public Booking(BookingCreated bookingCreated){
        this.id = UUID.randomUUID();
        this.workorder = bookingCreated.getWorkorder();
        this.date = bookingCreated.getDate();
        this.description = bookingCreated.getDescription();
        this.employee = bookingCreated.getEmployee();
        this.hours = bookingCreated.getHours();
    }

    public UUID getId() {
        return id;
    }
}

package com.cegeka;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder(builderMethodName = "createBooking")
public class BookingCreated {

    private final UUID id;
    private String workorder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

}

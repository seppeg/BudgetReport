package com.cegeka.event;

import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
public class BookingDeleted {

    private final UUID id;
    private String workOrder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

}

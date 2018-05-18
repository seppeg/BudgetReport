package com.cegeka.api;

import com.cegeka.domain.Booking;
import lombok.*;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingR {

    private String workorder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

    public BookingR(Booking b) {
        this.description = b.getDescription();
        this.workorder = b.getWorkorder();
        this.employee = b.getEmployee();
        this.hours = b.getHours();
        this.date = b.getDate();
    }
}

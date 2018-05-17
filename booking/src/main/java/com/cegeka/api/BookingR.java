package com.cegeka.api;

import lombok.*;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "booking")
public class BookingR {

    private String workorder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;
}

package com.cegeka.project.booking.yearly;

import lombok.Value;

import java.time.Year;

@Value
public class YearlyWorkOrderBookingView {
    private final String workOrder;
    private final Year year;
    private final double hours;

}

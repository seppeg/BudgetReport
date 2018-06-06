package com.cegeka.project.booking.monthly;

import lombok.Value;

import java.time.YearMonth;

@Value
public class MonthlyWorkOrderBookingView {
    private final String workOrder;
    private final YearMonth yearMonth;
    private final double hours;
}

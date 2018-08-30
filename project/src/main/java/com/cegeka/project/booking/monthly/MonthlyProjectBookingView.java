package com.cegeka.project.booking.monthly;

import lombok.Value;

import java.time.YearMonth;

@Value
public class MonthlyProjectBookingView {
    private final String project;
    private final YearMonth yearMonth;
    private final double hours;
}

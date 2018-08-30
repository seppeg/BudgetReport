package com.cegeka.project.booking;

import java.time.LocalDate;

public interface BookingEvent {

    String getWorkOrder();
    double getHours();
    String getDescription();
    LocalDate getDate();
}

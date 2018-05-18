package com.cegeka.camis.connection;

import com.cegeka.camis.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    private BookingService bookingService;

    @Autowired
    ScheduleTask(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @Scheduled(cron = "0 10,18 * * *")
    public void updateBookings() {
        bookingService.updateBookings();
    }

}

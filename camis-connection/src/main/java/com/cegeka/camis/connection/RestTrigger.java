package com.cegeka.camis.connection;

import com.cegeka.camis.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTrigger {

    private BookingService bookingService;

    @Autowired
    RestTrigger(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(path = "/")
    public String trigger(){
        bookingService.updateBookings();
        return "{name: 'TRIGGERD'}";
    }
}

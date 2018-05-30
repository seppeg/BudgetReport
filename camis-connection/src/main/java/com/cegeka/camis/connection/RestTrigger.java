package com.cegeka.camis.connection;

import com.cegeka.camis.booking.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RestTrigger {

    private final BookingService bookingService;
    private final WorkOrderConfig workOrderConfig;

    @GetMapping(path = "/trigger")
    public String trigger(){
        bookingService.updateBookings();
        return "{name: 'TRIGGERD'}";
    }

    @GetMapping(path = "/workorder")
    public List<String> listWorkOrders(){
        return workOrderConfig.getTrackedWorkOrders();
    }
}
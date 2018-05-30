package com.cegeka.camis.connection;

import com.cegeka.camis.booking.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

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

    @PatchMapping(path = "/workorder")
    public void trackWorkOrder(@RequestBody List<WorkOrderR> workOrders){
        checkNotNull(workOrders);
        workOrderConfig.trackWorkOrders(workOrders.stream().map(WorkOrderR::getWorkOrder).collect(toList()));
    }

    @GetMapping(path = "/workorder")
    public List<String> listWorkOrders(){
        return workOrderConfig.getTrackedWorkOrders();
    }
}
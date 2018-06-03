package com.cegeka.project.booking;

import com.cegeka.project.workorder.WorkOrder;
import com.cegeka.project.workorder.WorkOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Log4j2
public class DayBookingReadModel {

    private final DayBookingRepository dayBookingRepository;
    private final WorkOrderRepository workOrderRepository;

    @EventListener
    public void on(BookingCreated event){
        Optional<DayBooking> dayBooking = dayBookingRepository.findByDateAndWorkOrderWorkOrder(event.getDate(), event.getWorkOrder());
        if(dayBooking.isPresent()){
            dayBooking.get().addHours(event.getHours());
        }else{
            dayBookingRepository.save(new DayBooking(event.getDate(), getWorkOrder(event), event.getHours()));
        }
    }

    private WorkOrder getWorkOrder(BookingCreated event) {
        return workOrderRepository.findByWorkOrder(event.getWorkOrder())
                .orElseThrow(() -> new IllegalArgumentException("BookingCreated event for untracked workorder " + event));
    }

    @EventListener
    public void on(BookingDeleted event){
        Optional<DayBooking> dayBooking = dayBookingRepository.findByDateAndWorkOrderWorkOrder(event.getDate(), event.getWorkOrder());
        if(dayBooking.isPresent()){
            dayBooking.get().subtractHours(event.getHours());
        }else{
            log.warn(() -> "BookingDeleted event for unexisting booking: "+event);
        }
    }
}

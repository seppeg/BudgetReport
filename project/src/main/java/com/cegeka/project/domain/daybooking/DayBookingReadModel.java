package com.cegeka.project.domain.daybooking;

import com.cegeka.project.event.BookingCreated;
import com.cegeka.project.event.BookingDeleted;
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

    @EventListener
    public void on(BookingCreated event){
        Optional<DayBooking> dayBooking = dayBookingRepository.findByDateAndWorkOrder(event.getDate(), event.getWorkOrder());
        if(dayBooking.isPresent()){
            dayBooking.get().addHours(event.getHours());
        }else{
            dayBookingRepository.save(new DayBooking(event.getDate(), event.getWorkOrder(), event.getHours()));
        }
    }

    @EventListener
    public void on(BookingDeleted event){
        Optional<DayBooking> dayBooking = dayBookingRepository.findByDateAndWorkOrder(event.getDate(), event.getWorkOrder());
        if(dayBooking.isPresent()){
            dayBooking.get().subtractHours(event.getHours());
        }else{
            log.warn(() -> "BookingDeleted event for unexisting booking: "+event);
        }
    }
}

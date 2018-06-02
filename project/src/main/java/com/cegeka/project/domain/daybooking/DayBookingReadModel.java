package com.cegeka.project.domain.daybooking;

import com.cegeka.project.event.BookingCreated;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class DayBookingReadModel {

    private final DayBookingRepository dayBookingRepository;

    public void on(BookingCreated bookingCreated){
        Optional<DayBooking> dayBooking = dayBookingRepository.findByDateAndWorkOrder(bookingCreated.getDate(), bookingCreated.getWorkOrder());
        if(dayBooking.isPresent()){
            dayBooking.get().addHours(bookingCreated.getHours());
        }else{
            dayBookingRepository.save(new DayBooking(bookingCreated.getDate(), bookingCreated.getWorkOrder(), bookingCreated.getHours()));
        }
    }
}

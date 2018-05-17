package com.cegeka.service;

import com.cegeka.BookingCreated;
import com.cegeka.BookingStreams;
import com.cegeka.api.BookingR;
import com.cegeka.domain.Booking;
import com.cegeka.domain.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static com.cegeka.BookingCreated.createBooking;

@Service
@AllArgsConstructor
@Log4j2
public class BookingService {

    private final BookingStreams bookingStreams;
    private final BookingRepository bookingRepository;

    @Transactional
    public void processBookingData(List<BookingR> bookings){
        log.info(() -> "Creating booking.. already "+bookingRepository.findAll().size()+" in db");
        bookings.forEach(this::processBooking);
    }

    private void processBooking(BookingR bookingR){
        BookingCreated bookingCreated = createBooking()
                .workorder(bookingR.getWorkorder())
                .description(bookingR.getDescription())
                .employee(bookingR.getEmployee())
                .hours(bookingR.getHours())
                .date(bookingR.getDate())
                .id(UUID.randomUUID())
                .build();
        bookingRepository.save(new Booking(bookingCreated));
        raiseEvent(bookingCreated);
    }

    private void raiseEvent(Object event) {
        MessageChannel messageChannel = bookingStreams.outboundBookings();
        Message<Object> message = MessageBuilder.withPayload(event).build();
        messageChannel.send(message);
    }
}

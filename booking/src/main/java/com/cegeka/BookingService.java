package com.cegeka;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class BookingService {

    private final BookingStreams bookingStreams;
    private final BookingRepository bookingRepository;

    public void createBooking(BookingCreated bookingCreated){
        log.info(() -> "Creating booking.. already "+bookingRepository.findAll().size()+" in db");
        raiseEvent(bookingCreated);
    }

    private void raiseEvent(Object event) {
        MessageChannel messageChannel = bookingStreams.outboundBookings();
        Message<Object> message = MessageBuilder.withPayload(event).build();
        messageChannel.send(message);
    }
}

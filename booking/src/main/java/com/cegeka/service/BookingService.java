package com.cegeka.service;

import com.cegeka.BookingStreams;
import com.cegeka.api.BookingR;
import com.cegeka.domain.Booking;
import com.cegeka.domain.BookingRepository;
import com.cegeka.event.BookingCreated;
import com.cegeka.event.BookingCreatedBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.cegeka.event.BookingDeletedBuilder.deletedBooking;
import static com.google.common.collect.Sets.difference;
import static java.time.LocalDate.of;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class BookingService {

    private final BookingStreams bookingStreams;
    private final BookingRepository bookingRepository;

    public void processBookingData(List<BookingR> bookings) {
        List<Booking> storedBookings = bookingRepository.findByDateAfter(of(2010, 1, 1));

        Map<BookingR, Booking> storedBookingsCopy = storedBookings.stream().collect(toMap(BookingR::new, identity()));
        Set<BookingR> bookingsCopy = new HashSet<>(bookings);
        Set<BookingR> removedBookings = difference(storedBookingsCopy.keySet(), bookingsCopy).immutableCopy();
        Set<BookingR> addedBookings = difference(bookingsCopy, storedBookingsCopy.keySet()).immutableCopy();

        log.info(() -> "Removing " + removedBookings.size() + " bookings");
        removedBookings.stream().map(storedBookingsCopy::get).forEach(this::removeBooking);
        log.info(() -> "Adding " + addedBookings.size() + " bookings");
        addedBookings.forEach(this::createBooking);
    }

    private void removeBooking(Booking booking) {
        bookingRepository.delete(booking);
        raiseEvent(deletedBooking()
                .id(UUID.randomUUID())
                .date(booking.getDate())
                .description(booking.getDescription())
                .employee(booking.getEmployee())
                .hours(booking.getHours())
                .workorder(booking.getWorkorder())
                .build());
    }

    private void createBooking(BookingR bookingR) {
        BookingCreated bookingCreated = BookingCreatedBuilder.createBooking()
                .id(UUID.randomUUID())
                .workorder(bookingR.getWorkorder())
                .description(bookingR.getDescription())
                .employee(bookingR.getEmployee())
                .hours(bookingR.getHours())
                .date(bookingR.getDate())
                .build();
        bookingRepository.save(new Booking(bookingCreated));
        raiseEvent(bookingCreated);
    }

    private void raiseEvent(Object event) {
        log.info("Raising event "+event);
        MessageChannel messageChannel = bookingStreams.outboundBookings();
        Message<Object> message = MessageBuilder.withPayload(event).setHeader("type", event.getClass().getSimpleName()).build();
        messageChannel.send(message);
    }
}

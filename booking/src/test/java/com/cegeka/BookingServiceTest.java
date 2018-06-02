package com.cegeka;

import com.cegeka.api.BookingR;
import com.cegeka.domain.Booking;
import com.cegeka.domain.BookingRepository;
import com.cegeka.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.cegeka.api.BookingRTestBuilder.booking;
import static com.cegeka.event.BookingCreatedBuilder.createBooking;
import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext
class BookingServiceTest {

    @Autowired
    private MessageCollector collector;

    @Autowired
    private BookingStreams bookingStreams;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingRepository bookingRepository;

    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(bookingStreams, bookingRepository);
        when(bookingRepository.findByDateAfter(any())).thenReturn(emptyList());
    }

    @Test
    void createBooking_sendsEventToKafkaAsJson() throws InterruptedException, IOException {
        BookingR booking = booking()
                .date(LocalDate.now())
                .description("test")
                .employee("1123")
                .hours(1)
                .workorder("workorder")
                .build();
        bookingService.processBookingData(List.of(booking));

        BlockingQueue<Message<?>> messages = collector.forChannel(bookingStreams.outboundBookings());
        messages.poll(5000, TimeUnit.SECONDS);
        //TODO verify payload, problem: generated UUID different every time
    }

    @Test
    void createBooking_onlyRaisesCreatedEventWhenNewBooking() throws InterruptedException {
        Booking booking = new Booking(createBooking()
                .date(LocalDate.now())
                .description("test")
                .employee("1123")
                .hours(1)
                .workOrder("workorder")
                .build());
        when(bookingRepository.findByDateAfter(any())).thenReturn(List.of(booking));
        BookingR bookingR = booking()
                .date(LocalDate.now())
                .description("test")
                .employee("1123")
                .hours(1)
                .workorder("workorder")
                .build();
        bookingService.processBookingData(List.of(bookingR));

        verify(bookingRepository, times(0)).save(any());
    }

    @Test
    void createBooking_bookingRemoved() throws InterruptedException {
        Booking booking = new Booking(createBooking()
                .date(LocalDate.now())
                .description("test")
                .employee("1123")
                .hours(1)
                .workOrder("workorder")
                .build());
        when(bookingRepository.findByDateAfter(any())).thenReturn(List.of(booking));
        bookingService.processBookingData(emptyList());

        verify(bookingRepository).delete(any());
    }
}
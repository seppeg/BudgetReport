package com.cegeka;

import com.cegeka.api.BookingR;
import com.cegeka.domain.BookingRepository;
import com.cegeka.service.BookingService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.cegeka.api.BookingR.booking;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.when;

//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext
class BookingServiceTest {

    @Autowired
    private MessageCollector collector;

    @Autowired
    private BookingStreams bookingStreams;

    @MockBean
    private BookingRepository bookingRepository;

    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(bookingStreams, bookingRepository);
        when(bookingRepository.findAll()).thenReturn(emptyList());
    }

    @Test
    void createBooking_sendsEventToKafkaAsJson() throws InterruptedException {
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
}
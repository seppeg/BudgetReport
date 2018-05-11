package cegeka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.BlockingQueue;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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
    void createBooking_sendsEventToKafkaAsJson() {
        bookingService.createBooking(new BookingCreated(5L));

        BlockingQueue<Message<?>> messages = collector.forChannel(bookingStreams.outboundBookings());

        assertThat(messages, receivesPayloadThat(is("{\"id\":5}")));
    }
}
package com.cegeka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
class BookingListenerTest {

    @Autowired
    private ExampleStreams exampleStreams;

    @SpyBean
    private BookingListener bookingListener;

    @Test
    void onJsonEventOnBookingTopic_bla() {
        exampleStreams.inboundBookings().send(new GenericMessage<>("{\"id\":5}"));

        verify(bookingListener).book(new BookingR(5L));
    }
}
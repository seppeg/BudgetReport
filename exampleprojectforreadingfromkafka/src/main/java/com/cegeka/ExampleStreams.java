package com.cegeka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ExampleStreams {

    String INPUT = "booking-in";

    @Input(INPUT)
    SubscribableChannel inboundBookings();
}

package com.cegeka;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface BookingStreams {

    String OUTPUT = "booking-out";

    @Output(OUTPUT)
    MessageChannel outboundBookings();
}

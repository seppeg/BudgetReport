package com.cegeka.project.infrastructure;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ProjectStreams {

    String BOOKING_CHANNEL_IN = "booking-in";

    @Input(BOOKING_CHANNEL_IN)
    SubscribableChannel inboundProjects();

    String PROJECT_CHANNEL_OUT = "project-out";

    @Output(PROJECT_CHANNEL_OUT)
    MessageChannel outboundProjects();
}

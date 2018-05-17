package com.cegeka.project.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ProjectStreams {

    String INPUT = "booking-in";

    @Input(INPUT)
    SubscribableChannel inboundProjects();
}

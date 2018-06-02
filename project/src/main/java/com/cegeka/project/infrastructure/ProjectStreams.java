package com.cegeka.project.infrastructure;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ProjectStreams {

    String INPUT = "booking-in";

    @Input(INPUT)
    SubscribableChannel inboundProjects();

    String OUTPUT = "project-out";

    @Output(OUTPUT)
    MessageChannel outboundProjects();
}

package com.cegeka.project.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static com.cegeka.project.infrastructure.ProjectStreams.BOOKING_CHANNEL_IN;

@Component
@AllArgsConstructor
@Transactional
public class KafkaConsumers {

    private final ApplicationEventPublisher applicationEventPublisher;

    @StreamListener(BOOKING_CHANNEL_IN)
    public void on(@Payload Object event) {
        applicationEventPublisher.publishEvent(event);
    }
}

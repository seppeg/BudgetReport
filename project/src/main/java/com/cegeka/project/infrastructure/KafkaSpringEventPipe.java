package com.cegeka.project.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static com.cegeka.project.infrastructure.ProjectStreams.BOOKING_CHANNEL_IN;
import static org.springframework.messaging.support.MessageBuilder.withPayload;

@Component
@AllArgsConstructor
@Transactional
public class KafkaSpringEventPipe {

    private final ProjectStreams projectStreams;
    private final ApplicationEventPublisher applicationEventPublisher;

    @StreamListener(BOOKING_CHANNEL_IN)
    public void on(@Payload Object event) {
        applicationEventPublisher.publishEvent(event);
    }

    @EventListener
    public void on(PayloadApplicationEvent<?> event){
        MessageChannel messageChannel = projectStreams.outboundProjects();
        Message<?> message = withPayload(event.getPayload())
                .setHeader("type", event.getPayload().getClass().getSimpleName())
                .build();
        messageChannel.send(message);
    }
}

package com.cegeka;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class BookingListener {

    @StreamListener(ExampleStreams.INPUT)
    public void book(@Payload BookingR booking){
        System.out.println("got booking "+booking);
    }

}

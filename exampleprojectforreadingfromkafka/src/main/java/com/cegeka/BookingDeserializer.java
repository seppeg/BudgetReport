package com.cegeka;

import org.springframework.kafka.support.serializer.JsonDeserializer;

public class BookingDeserializer extends JsonDeserializer<BookingR> {

    public BookingDeserializer() {
        super(BookingR.class);
    }

}

package com.cegeka.project;

import com.cegeka.project.service.BookingCreated;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class BookingCreatedDeserializer extends JsonDeserializer<BookingCreated> {

    public BookingCreatedDeserializer() {
        super(BookingCreated.class);
    }

}

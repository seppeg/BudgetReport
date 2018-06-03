package com.cegeka.project.infrastructure;

import com.cegeka.project.booking.BookingCreated;
import com.cegeka.project.booking.BookingDeleted;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

public class EventDeserializer extends JsonDeserializer<Object> {

    public EventDeserializer() {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper(){
            @Override
            protected String retrieveHeaderAsString(Headers headers, String headerName) {
                String quotedHeader = super.retrieveHeaderAsString(headers, headerName);
                return quotedHeader.substring(1, quotedHeader.length()-1);
            }
        };
        typeMapper.setClassIdFieldName("type");
        typeMapper.setIdClassMapping(getEventMapping());
        setTypeMapper(typeMapper);
    }

    private Map<String, Class<?>> getEventMapping() {
        return Map.of("BookingCreated", BookingCreated.class,
                "BookingDeleted", BookingDeleted.class);
    }
}

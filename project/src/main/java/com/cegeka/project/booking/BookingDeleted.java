package com.cegeka.project.booking;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BookingDeleted implements BookingEvent {

    private UUID id;
    private String workOrder;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

}

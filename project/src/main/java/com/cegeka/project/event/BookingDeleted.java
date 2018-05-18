package com.cegeka.project.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class BookingDeleted implements BookingEvent {

    private UUID id;
    private String workorder;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;


    public BookingDeleted() {
    }
}

package com.cegeka.domain;

import com.cegeka.event.BookingCreated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Booking {

    @Id
    private UUID id;
    private String workorder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;

    public Booking(BookingCreated bookingCreated){
        this.id = UUID.randomUUID();
        this.workorder = bookingCreated.getWorkorder();
        this.date = bookingCreated.getDate();
        this.description = bookingCreated.getDescription();
        this.employee = bookingCreated.getEmployee();
        this.hours = bookingCreated.getHours();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

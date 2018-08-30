package com.cegeka.project.booking;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString
public class DayBooking {

    @Id
    private UUID id;

    private LocalDate date;

    private UUID projectId;

    private double hours;

    public DayBooking(LocalDate date, UUID projectId, double hours){
        this.id = UUID.randomUUID();
        this.date = date;
        this.projectId = projectId;
        this.hours = hours;
    }

    public void addHours(double hours) {
        checkArgument(hours > 0);
        this.hours += hours;
    }

    public void subtractHours(double hours) {
        checkArgument(hours > 0 && hours <= this.hours);
        this.hours -= hours;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DayBooking that = (DayBooking) o;

        return id.equals(that.id);
    }

    @Override
    public final int hashCode() {
        return id.hashCode();
    }
}

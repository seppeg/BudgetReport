package com.cegeka.project.booking;

import com.cegeka.project.workorder.WorkOrder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

    @OneToOne
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    private double hours;

    public DayBooking(LocalDate date, WorkOrder workOrder, double hours){
        this.id = UUID.randomUUID();
        this.date = date;
        this.workOrder = workOrder;
        this.hours = hours;
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

    public void addHours(double hours) {
        checkArgument(hours > 0);
        this.hours += hours;
    }

    public void subtractHours(double hours) {
        checkArgument(hours > 0 && hours <= this.hours);
        this.hours -= hours;
    }
}

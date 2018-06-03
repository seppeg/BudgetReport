package com.cegeka.project.booking;

import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.YearMonth;

@Entity
@Table(name = "monthly_work_order_bookings")
@Immutable
@IdClass(MonthlyWorkOrderBookingView.MonthlyWorkOrderBookingKey.class)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Getter
@ToString
public class MonthlyWorkOrderBookingView {

    @Id
    private String workOrder;

    @Id
    @Type(type = "com.cegeka.project.infrastructure.YearMonthType")
    @Columns(columns = {
            @Column(name = "year"),
            @Column(name = "month")
    })
    private YearMonth yearMonth;

    private double hours;

    static class MonthlyWorkOrderBookingKey implements Serializable {
        private String workOrder;
        private YearMonth yearMonth;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MonthlyWorkOrderBookingView that = (MonthlyWorkOrderBookingView) o;

        if (!workOrder.equals(that.workOrder)) return false;
        return yearMonth.equals(that.yearMonth);
    }

    @Override
    public final int hashCode() {
        int result = workOrder.hashCode();
        result = 31 * result + yearMonth.hashCode();
        return result;
    }
}
